package support.community.database;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.acegisecurity.AccessDeniedException;
import org.acegisecurity.Authentication;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.util.Assert;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import support.community.application.ElementNotFoundException;
import support.community.application.RoleConstants;
import support.community.util.Cache;

import com.era.community.base.Scalr;
import com.era.community.communities.dao.Community;
import com.era.community.pers.dao.User;

public abstract class AbstractJdbcDaoSupport extends SimpleJdbcDaoSupport implements ApplicationContextAware, ServletContextAware
{
	private ApplicationContext m_applicationContext; 
	private ServletContext m_servletContext; 
	private JdbcDaoManager m_daoManager; 
	private boolean m_autowire = true;
	private int m_autowireMode = AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE;
	private TransactionContextHolder m_transactionContextHolder;
	private String m_tableName;
	private String m_columnNamePrefix = "";
	private String[] m_keyProperties;
	private boolean m_polymorphic = false;
	private boolean m_optimisticLockingEnabled = false;
	private String m_createdTimestampProperty = null;
	//private String m_ModifiedTimestampProperty = null;
	private String m_discriminatorColumnName = "SysType";
	private String m_sequenceColumnName = "SysSequence";
	private KeyGenerator m_keyGenerator = null;
	private TypeMap m_typeMap;   
	private Map m_columnTypes = null;
	private Map m_columnLengths = null;
	private EntityRowMapper m_rowMapper = new EntityRowMapper(this);
	public static final String PSEUDO_BOOLEAN_TRUE_VALUE = "Y";
	public static final String PSEUDO_BOOLEAN_FALSE_VALUE = "N";

	/**
	 * The JAI.create action name for handling a stream.
	 */
	private static final String JAI_STREAM_ACTION = "stream";

	/**
	 * The JAI.create action name for handling a resizing using a subsample averaging technique.
	 */
	private static final String JAI_SUBSAMPLE_AVERAGE_ACTION = "SubsampleAverage";

	/**
	 * The JAI.create encoding format name for JPEG.
	 */
	private static final String JAI_ENCODE_FORMAT_JPEG = "PNG";

	/**
	 * The JAI.create action name for encoding image data.
	 */
	private static final String JAI_ENCODE_ACTION = "encode";

	/**
	 * The http content type/mime-type for JPEG images.
	 */
	private static final String JPEG_CONTENT_TYPE = "image/jpeg";
	private int mMaxWidth = 1024;
	private int mMaxWidthThumbnail = 150;

	protected void initDao() throws Exception
	{
		Assert.notNull(m_daoManager, "No dao manger reference has been injected");
		Assert.notNull(m_transactionContextHolder, "No transaction context holder has been injected");
		m_daoManager.registerDao(this);
	}

	protected Object newEntity(Class type) throws Exception
	{
		Object o = createBean(type);

		if (m_keyGenerator!=null && haveSingleIntegerKey()) {
			String kp = getKeyProperties()[0];
			setProperty(o, kp, new Integer(m_keyGenerator.nextSequence()));
		}

		addToThreadCache(new DaoCacheEntry(null, o, -1));
		return o;
	}

	private Object createBean(Class type) throws Exception
	{
		if (getTypeMap().get(type)==null) 
			throw new Exception("Class "+type+" is not in the type map for "+getClass().getName());

		Object o;
		if (m_autowire==false) { 
			o =  type.newInstance();
		}
		else {
			ConfigurableListableBeanFactory beanFactory = ((ConfigurableApplicationContext)m_applicationContext).getBeanFactory();
			o = beanFactory.autowire(type, m_autowireMode, false);
		}

		if (o==null) throw new Exception("Bean cannot be instantiated");

		return o;
	}

	protected void deleteEntity(Object... keys) throws Exception
	{
		if (getJdbcTemplate().update(getDeleteSql(), keys)!=1)
			logger.warn("Delete issued for "+implode(keys, "|")+" did not affect exactly 1 row");

		DaoCacheEntry tce =  findInThreadCache(keys);
		if (tce!=null) {
			removeFromThreadCache(tce.object);
		}
	}

	protected void deleteEntity(Object o) throws Exception
	{
		deleteEntity(getKeyValuesForObject(o));
	}

	protected final void storeEntity(Object o) throws Exception
	{
		storeEntity(o, false, null, null);
	}

	protected final void storeEntity(Object o, boolean forCountOnly) throws Exception
	{
		storeEntity(o, forCountOnly, null, null);
	}

	private void storeEntity(Object o, boolean forCountOnly, String[] np, Object[] nv) throws Exception
	{
		if (!forCountOnly && isAccessCheckingEnabled() && o instanceof CecSecuredEntity) {
			if (!((CecSecuredEntity)o).isWriteAllowed(getCurrentUser())) 
				throw new AccessDeniedException("Writing "+o.getClass()+" with keys ["+implode(getKeyValuesForObject(o), ",")+"]");
		}

		DaoCacheEntry tce = findInThreadCache(o);
		if (tce==null) throw new IllegalStateException("Object"+o+" not found (maybe it was created with new and not created via Dao)");

		if (tce.keys==null) {
			if (np!=null||nv!=null) throw new Exception("Attempt made to set property ["+np[0]+"] on entity of "+o.getClass()+ " before the entity has been inserted into the database");
			insertEntity(tce);
		}
		else {
			updateEntity(tce, np, nv);
		}
	}

	private void updateEntity(DaoCacheEntry tce, String[] np, Object[] nv) throws Exception
	{
		Object o = tce.object;

		if (np==null) np = getNonKeyProperties(o.getClass());
		if (nv==null) nv = getSqlValuesForProperties(o, np);

		String sql = getUpdateSql(o.getClass(), np);
		logger.debug(sql);

		int[] nt = getSqlTypes(np);

		String[] kp = getKeyProperties();
		Object[] kv = getSqlValuesForProperties(o, kp);
		int[] kt = getSqlTypes(kp);

		Object[] values = isSequencingEnabled() ? new Object[nv.length+kv.length+1] : new Object[nv.length+kv.length];
		int[] sqltypes = new int[values.length];

		for (int n=0; n<nv.length; n++) values[n] = nv[n];
		for (int n=0; n<nt.length; n++) sqltypes[n] = nt[n];

		for (int n=0; n<kv.length; n++) values[nv.length+n] = kv[n];
		for (int n=0; n<kt.length; n++) sqltypes[nt.length+n] = kt[n]; 

		if (isSequencingEnabled()) {
			values[values.length-1] = new Integer(tce.sequence);
			logger.debug("sequence="+tce.sequence);
			sqltypes[sqltypes.length-1] = Types.INTEGER;
		}

		if (getJdbcTemplate().update(sql, values , sqltypes)!=1)
			throw new Exception("Update failed due to contention, please retry. SQL -->" + sql);

		tce.sequence++;
	}

	private void insertEntity(DaoCacheEntry tce) throws Exception
	{  
		Object o = tce.object;

		String[] p = getAllPersistedProperties(o.getClass());
		Object[] v = getSqlValuesForProperties(o, p);
		int[] t = getSqlTypes(p);

		Object[] values;
		int[] sqltypes;

		Object[] data;
		if (isPolymorphic()) {
			values = new Object[v.length+1] ;
			sqltypes = new int[t.length+1];
			for (int n=0; n<v.length; n++) values[n] = v[n];
			for (int n=0; n<t.length; n++) sqltypes[n] = t[n];
			values[values.length-1] = m_typeMap.get(o.getClass()).getName(); 
			sqltypes[sqltypes.length-1] = Types.VARCHAR; 
		}
		else {
			values = v;
			sqltypes = t;
		}

		getJdbcTemplate().update(getInsertSql(o.getClass()), values, sqltypes);

		tce.sequence = 0;
		tce.keys = getKeyValuesForObject(o);
		removeFromThreadCache(o);
		addToThreadCache(tce);
	}

	public Object getEntity(Class type, Object... keys) throws Exception
	{
		logger.debug("Get "+type+" with keys ["+implode(keys, "|")+"]");

		DaoCacheEntry tce = findInThreadCache(getCacheKey(keys)); 
		if (tce!=null) {
			logger.debug("Cache hit"); 
			return tce.object;
		}

		try {
			Object o = getJdbcTemplate().queryForObject(getSelectSql(), keys, m_rowMapper);
			logger.debug("Read object "+o);
			return o;
		}
		catch (IncorrectResultSizeDataAccessException x) {
			if (x.getActualSize()>0) throw x;
			throw new ElementNotFoundException("Get "+type+" with keys ["+implode(keys, "|")+"]");
		}
		catch (Exception x) {
			for (Exception e=x; e!=null; e=(Exception)e.getCause()) {
				if (e instanceof AccessDeniedException) 
					throw (AccessDeniedException)e;
			}
			throw x;
		}
	}

	protected Object getColumn(Object e, String column, Class type) throws Exception
	{
		Object[] keys = getKeyValuesForObject(e);

		logger.debug("Get column "+column+" for "+e.getClass()+" with keys ["+implode(keys, "|")+"]");

		String sql = getSelectSql(new String[] { column }, null, false);

		try {
			Object o = getJdbcTemplate().queryForObject(sql, keys, type);
			return o;
		}
		catch (IncorrectResultSizeDataAccessException x) {
			if (x.getActualSize()>0) throw x;
			throw new ElementNotFoundException("Get column "+column+" for "+e.getClass()+" with keys ["+implode(keys, "|")+"]");
		}

	}

	protected BlobData readBlobItem(final Object e, final String column) throws Exception
	{
		final Object[] keys = getKeyValuesForObject(e);

		logger.debug("Get blob item "+column+" for "+e.getClass()+" with keys ["+implode(keys, "|")+"]");

		BlobData data = (BlobData)getJdbcTemplate().execute(new ConnectionCallback() {
			public Object doInConnection(Connection conn) {

				try {

					String sql = getSelectSql(new String[] { column, column+"Length", column+"ContentType"}, null, false);
					PreparedStatement prep = conn.prepareStatement(sql);
					try {
						for (int i=0; i<keys.length; i++) prep.setObject(i+1, keys[i]);
						ResultSet res = prep.executeQuery();
						if (!res.next())  throw new ElementNotFoundException("Get blob item "+column+" for "+e.getClass()+" with keys ["+implode(keys, "|")+"]");
						return new BlobData(res.getBlob(1), res.getInt(2), res.getString(3));
					}
					finally {
						prep.close();
					}
				}

				catch (Exception x) {
					DataRetrievalFailureException e = new DataRetrievalFailureException(x.toString());
					e.initCause(x);
					throw e;
				}

			} 
		});

		return data;
	}

	protected Object getValue(Object e, String expression, Class type) throws Exception
	{
		Object[] keys = getKeyValuesForObject(e);

		logger.debug("Get value ["+expression+"] for "+e.getClass()+" with keys ["+implode(keys, "|")+"]");

		String sql = getSelectSql(expression, null);

		try {
			Object o = getJdbcTemplate().queryForObject(sql, keys, type);
			return o;
		}
		catch (IncorrectResultSizeDataAccessException x) {
			if (x.getActualSize()>0) throw x;
			throw new ElementNotFoundException("Get value ["+expression+"] for "+e.getClass()+" with keys ["+implode(keys, "|")+"]");
		}

	}

	protected void setColumn(Object e, String column, Object value) throws Exception
	{
		logger.debug("Set column "+column+" for "+e.getClass()+" with keys ["+implode(getKeyValuesForObject(e),"|")+"] and value ["+value+"]");
		storeEntity(e, false, new String[] {column}, new Object[] {value});    
	}

	protected void storeClob(Object o, String column, String data) throws Exception
	{
		logger.debug("Writing clob "+column+" for "+o.getClass()+" with keys []");
		storeEntity(o, false, new String[] {column}, new Object[] {new SqlLobValue(data)});
	}

	protected void storeBlob(Object o, String column, InputStream data, int dataLength, String contentType) throws Exception
	{
		logger.debug("Writing blob "+column+" for "+o.getClass()+" with keys []");
		if (contentType==null||contentType.trim().length()==0)
			contentType = "x-application/octet-stream";
		else
			contentType = contentType.toLowerCase();
		
		if ("image/pjpeg".equals(contentType)) {
			contentType = "image/jpeg";
		} else if ("application/vnd.openxmlformats-officedocument.wordprocessingml.document".equals(contentType)) {
			contentType = "application/msword";
		} else if ("image/pjpeg".equals(contentType)) {
			contentType = "image/jpeg";
		}

		storeEntity(
				o, false, 
				new String[] {column, column+"Length", column+"ContentType", column+"SearchText" }, 
				new Object[] {new SqlLobValue(data, dataLength), new Integer(dataLength), contentType, null }
		);
	}
	
	public void storeBlob(Object o, String column, InputStream data, int dataLength, String contentType, int width, int height) throws Exception
	{
		logger.debug("Writing blob "+column+" for "+o.getClass()+" with keys []");
		if (contentType==null||contentType.trim().length()==0)
			contentType = "x-application/octet-stream";
		else
			contentType = contentType.toLowerCase();
		if ("image/pjpeg".equals(contentType)) contentType = "image/jpeg";

		storeEntity(
				o, false, 
				new String[] {column, column+"Length", column+"ContentType", column+"SearchText", "Width", "Height"}, 
				new Object[] {new SqlLobValue(data, dataLength), new Integer(dataLength), contentType, null, width, height}
		);
	}

	protected void storeBlob(Object o, String column, InputStream data, int dataLength) throws Exception
	{
		logger.debug("Writing blob "+column+" for "+o.getClass()+" with keys []");
		storeBlob(o, column,  data, dataLength ,"x-application/octet-stream");
	}

	protected void storeBlob(Object o, String column, MultipartFile file) throws Exception
	{
		logger.debug("Writing blob "+column+" for "+o.getClass()+" with keys []");

		String contentType = file.getContentType().toLowerCase();
		if (contentType==null||contentType.trim().length()==0)
			contentType = "x-application/octet-stream";
		else
			contentType = contentType.toLowerCase();

		String impliedType = m_servletContext.getMimeType(file.getOriginalFilename());
		if (contentType.equals("x-application/octet-stream") && impliedType!=null)
			contentType = impliedType.toLowerCase();
		
		BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
	    BufferedImage i = new BufferedImage(bufferedImage.getWidth(),
				bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
		  i.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
		  
		  ByteArrayOutputStream encoderOutputStream = new ByteArrayOutputStream();
			ImageIO.write(i, "JPEG", encoderOutputStream);
			// Export to Byte Array
			byte[] resizedImageByteArray = encoderOutputStream.toByteArray();
			storeBlob(o, column, new ByteArrayInputStream(resizedImageByteArray), encoderOutputStream.size(), JPEG_CONTENT_TYPE, i.getWidth(), i.getHeight());
			i.flush(); encoderOutputStream.flush();

		//storeBlob(o, column, file.getInputStream(), (int)file.getSize(), contentType);
	}

	protected void storeBlob(Object o, String column, InputStream imageInputStream) throws Exception
	{
		resizeImageAsJPG(o, column, imageInputStream, mMaxWidth);
	}

	/**
	 * This method takes in an image as a byte array (currently supports GIF, JPG, PNG and
	 * possibly other formats) and
	 * resizes it to have a width no greater than the pMaxWidth parameter in pixels.
	 * It converts the image to a standard
	 * quality JPG and returns the byte array of that JPG image.
	 *
	 * @param pImageData
	 *                the image data.
	 * @param pMaxWidth
	 *                the max width in pixels, 0 means do not scale.
	 * @return the resized JPG image.
	 * @throws IOException
	 *                 if the image could not be manipulated correctly.
	 */
	public void resizeImageAsJPG(Object o, String column, InputStream imageInputStream, int pMaxWidth) throws Exception {
		//InputStream imageInputStream = new ByteArrayInputStream(pImageData);
		// read in the original image from an input stream
		/*SeekableStream seekableImageStream = SeekableStream.wrapInputStream(imageInputStream, true);
		RenderedOp originalImage = JAI.create(JAI_STREAM_ACTION, seekableImageStream);
		((OpImage) originalImage.getRendering()).setTileCache(null);
		int origImageWidth = originalImage.getWidth();
		// now resize the image
		double scale = 1.0;
		if (pMaxWidth > 0 && origImageWidth > pMaxWidth) {
			scale = (double) pMaxWidth / originalImage.getWidth();
		}
		ParameterBlock paramBlock = new ParameterBlock();
		paramBlock.addSource(originalImage); // The source image
		paramBlock.add(scale); // The xScale
		paramBlock.add(scale); // The yScale
		paramBlock.add(0.0); // The x translation
		paramBlock.add(0.0); // The y translation

		RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);

		RenderedOp resizedImage = JAI.create(JAI_SUBSAMPLE_AVERAGE_ACTION, paramBlock, qualityHints);*/
		
		try {
			/*Iterator readers = ImageIO.getImageReadersByFormatName("JPEG");
		    ImageReader reader = null;
		    while(readers.hasNext()) {
		        reader = (ImageReader)readers.next();
		        if(reader.canReadRaster()) {
		            break;
		        }
		    }*/
		    
		    BufferedImage bufferedImage = ImageIO.read(imageInputStream);
		    BufferedImage i = new BufferedImage(bufferedImage.getWidth(),
					bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
			  i.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
			  
		   /* ImageInputStream input =   ImageIO.createImageInputStream(imageInputStream); 
		    reader.setInput(input); 
		    
		    Raster raster = reader.readRaster(0, null);
		    
		    BufferedImage i = new BufferedImage(raster.getWidth(), raster.getHeight(), 
		    		BufferedImage.TYPE_INT_RGB); 
		    i.getRaster().setRect(raster);*/
		    
		    
		    /*BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
					bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
			  newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);

			  // write to jpeg file
			  ImageIO.write(newBufferedImage, "jpg", new File("c:\\javanullpointer.jpg"));*/
		    
			//BufferedImage i = ImageIO.read(imageInputStream);
			if (o instanceof User) {
				if ("Photo".equals(column)) {
					resize(o, i, "Photo", 200, 200);
					resize(o, i, "PhotoThumb", 100, 100);
				} else if ("Cover".equals(column)){
					resize(o, i, "Cover", 1500, 600);
					resize(o, i, "CoverThumb", 400, 200);
				}
			} else if (o instanceof Community){
				if ("CommunityLogo".equals(column)) {
					resize(o, i, "CommunityLogo", 400, 400);
					resize(o, i, "CommunityLogoThumb", 200, 200);
				} else if ("CommunityBanner".equals(column)){
					resize(o, i, "CommunityBanner", 1500, 600);
					resize(o, i, "CommunityBannerThumb", 600, 200);
				}
			}
			i.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void resize(Object o, BufferedImage input, String column, int width, int height) throws Exception {
		BufferedImage out = Scalr.resize(input, width, height);
		ByteArrayOutputStream encoderOutputStream = new ByteArrayOutputStream();
		ImageIO.write(out, "JPEG", encoderOutputStream);
		// Export to Byte Array
		byte[] resizedImageByteArray = encoderOutputStream.toByteArray();
		storeBlob(o, column, new ByteArrayInputStream(resizedImageByteArray), encoderOutputStream.size(), JPEG_CONTENT_TYPE, out.getWidth(), out.getHeight());
		out.flush(); encoderOutputStream.flush();
	}
	
	protected void clearBlob(Object o, String column) throws Exception
	{
		logger.debug("Clearing blob "+column+" for "+o.getClass()+" with keys []");
		storeEntity(
				o, false, 
				new String[] {column, column+"Length", column+"ContentType", column+"SearchText" }, 
				new Object[] { null, new Integer(0), null, null }
		);
	}

	Class getTypeForDiscriminatorValue(String discriminator) 
	{
		if (discriminator==null) {
			if (isPolymorphic()) throw new IllegalArgumentException("Argument cannot be null");
			return getTypeMap().getSoleEntry().getMappedClass();
		}
		else {
			if (!isPolymorphic()) throw new IllegalArgumentException("Argument must be null");
			TypeMapEntry tme = getTypeMap().get(discriminator);
			if (tme==null) throw new IllegalArgumentException("Invalid discriminator value "+discriminator+" for "+getClass().getName());
			return tme.getMappedClass(); 
		} 
	}

	Object instantiateEntity(Map data) throws Exception
	{
		Object[] keyValues = getKeyValuesForMap(data);

		DaoCacheEntry tce = findInThreadCache(keyValues);
		if (tce!=null) {
			logger.debug("Cache hit for "+tce.object); 
			return tce.object;
		}

		String discriminator = isPolymorphic() ? (String)data.get(getDiscriminatorColumnName().toUpperCase()) : null;
		logger.debug("discriminator:"+discriminator);
		Class type = getTypeForDiscriminatorValue(discriminator);
		Object o = createBean(type); 
		logger.debug("Instanting "+o);
		populateValues(o, getAllPersistedProperties(type), data);

		if (m_createdTimestampProperty!=null) {
			Date created = (Date)data.get((getColumnNamePrefix()+m_createdTimestampProperty).toUpperCase());
			setProperty(o, m_createdTimestampProperty, created);
		}
		/*if (m_ModifiedTimestampProperty!=null) {
			Date mod = (Date)data.get((getColumnNamePrefix()+m_ModifiedTimestampProperty).toUpperCase());
			setProperty(o, m_ModifiedTimestampProperty, mod);
		}*/

		if (isAccessCheckingEnabled() && o instanceof CecSecuredEntity) {
			if (!((CecSecuredEntity)o).isReadAllowed(getCurrentUser()))
				throw new AccessDeniedException("Instantiating "+o.getClass()+" with keys ["+implode(keyValues, ",")+"]");
		}

		Object[] keys = getKeyValuesForObject(o);
		int seq = 0; 
		if (m_optimisticLockingEnabled) {
			String col = getSequenceColumnName().toUpperCase();
			if (!data.containsKey(col)) throw new Exception("Sequence column "+col+" not present");
			seq = ((Integer)data.get(col)).intValue() ;
		}
		addToThreadCache(new DaoCacheEntry(keys, o, seq));

		logger.debug("Instantiated "+o.getClass().getName()+" with keys "+keys);

		return o;
	}

	protected UserDetails getCurrentUser() throws Exception
	{
		SecurityContext sc = SecurityContextHolder.getContext();
		if (sc==null) return null;
		Authentication auth = sc.getAuthentication();
		if (auth==null) return null;
		Object user = auth.getPrincipal();
		if (!(user instanceof UserDetails)) return null;
		return (UserDetails)user;
	}


	protected boolean isAccessCheckingEnabled()
	{
		if (isCurrentUserInRole(RoleConstants.ROLE_SYS_ADMIN)) return false;
		if (isCurrentUserInRole(RoleConstants.ROLE_SUPER_ADMIN)) return false;
		if (isCurrentUserInRole(RoleConstants.ROLE_RUN_AS_SERVER)) return false;
		return true;
	}


	private boolean isCurrentUserInRole(String role) 
	{
		SecurityContext sc = SecurityContextHolder.getContext();
		if (sc==null) return false; 
		Authentication auth = sc.getAuthentication();
		if (auth==null) return false; 
		GrantedAuthority[] auths = auth.getAuthorities();
		for (int i=0; i<auths.length; i++) 
			if (role.equals(auths[i].getAuthority())) return true;
		return false;
	}

	private void setProperty(Object o, String propName, Object value) throws Exception
	{
		Method m = findSetterMethod(o.getClass(), propName, value==null?null:value.getClass());
		if (m==null) throw new NoSuchMethodException("No suitable setter method for "+propName+" for "+o.getClass());
		m.setAccessible(true);
		invokeSetterMethod(m, o, value);
		return;
	}

	private void invokeSetterMethod(Method method, Object o, Object value) throws Exception
	{
		logger.debug("Invoking "+method+" with value ["+value+"]");

		Class argType = (method .getParameterTypes())[0];

		if (value==null) {
			if (argType.isPrimitive()) throw new Exception("Cannot assign null to property of primitive type. ("+method+")");
			method.invoke(o, new Object[] {value});
			return;
		}

		if (argType==boolean.class && value instanceof String) {
			if (value.equals(PSEUDO_BOOLEAN_TRUE_VALUE))
				method.invoke(o, new Object[] { Boolean.TRUE });
			else if (value.equals(PSEUDO_BOOLEAN_FALSE_VALUE))
				method.invoke(o, new Object[] { Boolean.FALSE });
			else
				throw new Exception("Invalid column value "+value+" for boolean property. ("+method+")");
			return;
		}
		method.invoke(o, new Object[] {value});
	}


	private Map<String, Method> _methodMap = new HashMap<String, Method>(89);

	private Method findSetterMethod(Class type, String propName, Class vc) throws Exception
	{
		Method method = (Method)_methodMap.get(type.getName()+"."+propName);  
		if (method!=null) return method;

		logger.debug("Looking for property "+propName+" in "+type);

		List<Method> found = new ArrayList<Method>(2);

		String methodName = "set"+propName.substring(0,1).toUpperCase()+propName.substring(1);
		for (Class c = type; c!=null; c=c.getSuperclass()) {
			Method[] methods = c.getDeclaredMethods();
			for (int n=0; n<methods.length; n++) {
				Method m = methods[n];
				if (!m.getName().equalsIgnoreCase(methodName)) continue;
				Class[] params = m.getParameterTypes();
				if (params.length!=1) continue;
				found.add(m);
			}
		}

		if (found.size()>1)
			throw new NoSuchMethodException("Duplicate method "+methodName+" for "+type);

		if (found.isEmpty()) return null;

		method = (Method)found.get(0);
		_methodMap.put(type.getName()+"."+propName, method);
		return method;
	}


	protected final String implode(Object[] list, String sep)
	{
		if (list==null) return "";
		StringBuffer buf = new StringBuffer(128);
		for (int n=0; n<list.length; n++) {
			if (n>0) buf.append(sep);
			buf.append(list[n]);
		}
		return buf.toString();
	}

	protected Object getEntity(String sQuery, Object... params) throws Exception
	{
		try {
			return getJdbcTemplate().queryForObject(sQuery, params, m_rowMapper);
		}
		catch (IncorrectResultSizeDataAccessException x) {
			if (x.getActualSize()>0) throw x;
			throw new ElementNotFoundException("Get with keys ["+implode(params, "|")+"]");
		}
		catch (Exception x) {
			for (Exception e=x; e!=null; e=(Exception)e.getCause()) {
				if (e instanceof AccessDeniedException) 
					throw (AccessDeniedException)e;
			}
			throw x;
		}
	}

	@SuppressWarnings("unchecked")
	protected <T> T getBean(String sQuery, Object[] params, Class<T> type) throws Exception
	{
		return getBean(sQuery, type, params);
	}

	@SuppressWarnings("unchecked")
	protected <T> T getBean(String sQuery, Class<T> type, Object... params) throws Exception
	{
		try {
			return (T)getJdbcTemplate().queryForObject(sQuery, params, getRowMapperForType(type));
		}
		catch (IncorrectResultSizeDataAccessException x) {
			if (x.getActualSize()>0) throw x;
			throw new ElementNotFoundException("Get "+type+" with keys ["+implode(params, "|")+"]");
		}
	}

	protected <T> List<T> getBeanList(String sQuery, Class<T> type, Object... params) throws Exception
	{
		return getBeanList(sQuery, type, false, params);
	}

	protected <T> List<T> getBeanList(String sQuery, Object[] params, Class<T> type) throws Exception
	{
		return getBeanList(sQuery, type, params);
	}

	@SuppressWarnings("unchecked")
	protected <T> List<T> getBeanList(String sQuery, Class<T> type, boolean cache, Object... params) throws Exception
	{
		String cacheKey = cache ? getCacheKey(sQuery, params, type) : null;
		if (cacheKey!=null) {
			Object o = getThreadCache().get(cacheKey);
			if (o!=null) {
				logger.info("Cache hit for "+cacheKey);
				return (List<T>)o;
			}
		}
		try {
			List<T> list = getJdbcTemplate().query(sQuery, params, getRowMapperForType(type));
			if (cacheKey!=null) {
				list = Collections.unmodifiableList(list);
				getThreadCache().put(cacheKey, list);
			}
			return list;
		}
		catch (IncorrectResultSizeDataAccessException x) {
			logger.error("", x);
			if (x.getActualSize()>0) throw x;
			throw new ElementNotFoundException("Get with keys ["+implode(params, "|")+"]");
		}
	}

	protected List getEntityList(String sQuery, Object... params) throws Exception
	{
		try {
			return getJdbcTemplate().query(sQuery, params, m_rowMapper);
		}
		catch (IncorrectResultSizeDataAccessException x) {
			if (x.getActualSize()>0) throw x;
			throw new ElementNotFoundException("Get with keys ["+implode(params, "|")+"]");
		}
	}

	protected QueryScroller getQueryScroller(String query, int i) throws Exception
	{
		return getQueryScroller(query, new Object[] { new Integer(i) }, null);
	}

	protected QueryScroller getQueryScroller(String query, int i, Class type) throws Exception
	{
		return getQueryScroller(query, new Object[] { new Integer(i) }, type);
	}

	protected QueryScroller getQueryScroller(String query, Class type, Object... params) throws Exception
	{
		return new Db2QueryScroller(this, type, query, params);
	}

	protected QueryScroller getQueryScroller(String query, boolean nQuery, Class type, Object... params) throws Exception
	{
		return new Db2QueryScroller(this, nQuery, type, query, params);
	}

	protected QueryScroller getQueryScroller(String query, Object[] params, Class type) throws Exception
	{
		return new Db2QueryScroller(this, type, query, params);
	}

	RowMapper getRowMapperForType(Class type) 
	{
		return getRowMapperForType(type, null);
	}

	RowMapper getRowMapperForType(Class type, Object enclosingInstance) 
	{
		if (type==null) 
			return m_rowMapper;

		if (getTypeMap().get(type)!=null)
			return m_rowMapper;

		return m_daoManager.getRowMapperForType(type, enclosingInstance);
	}

	protected Object getEntityWhere(String whereClause, int id) throws Exception
	{
		return getEntity(getSelectSql(whereClause), new Object[] { new Integer(id)});
	}

	protected Object getEntityWhere(String whereClause, Object... keys) throws Exception
	{
		return getEntity(getSelectSql(whereClause), keys);
	}

	void populateValues(Object bean, String[] props, Map data) throws Exception
	{
		for (int n=0; n<props.length; n++ ) {
			String prop = props[n];
			String col = prop.toUpperCase();
			if (!data.containsKey(col)){
				throw new Exception("Column "+col+" not found in column data but is required by "+bean.getClass());
			}
			setProperty(bean, prop, data.get(col));
		}
	}

	protected void populatePrimaryKeys(Object o, Object[] keyValues) throws Exception
	{
		String[] props = getKeyProperties();
		for (int n=0; n<props.length; n++) {
			setProperty(o, props[n], keyValues[n]);
		}
	}

	private synchronized Map getColumnTypes() throws Exception
	{
		if (m_columnTypes!=null) return m_columnTypes;

		final String sql = "select * from "+getTableName()+" limit 1";

		Map typeMap = (Map)getJdbcTemplate().execute(new ConnectionCallback() {
			public Object doInConnection(Connection conn) throws SQLException, DataAccessException
			{
				PreparedStatement prep = conn.prepareStatement(sql);
				ResultSetMetaData meta = prep.getMetaData();
				Map<String, Integer> map = new HashMap<String, Integer>(meta.getColumnCount()*2-1);
				for (int n=1; n<=meta.getColumnCount(); n++) {
					//handling of BLOB type for mySql
					if (meta.getColumnType(n) == -4) {
						map.put(meta.getColumnName(n).toUpperCase(), new Integer(2004));
					} else {
						map.put(meta.getColumnName(n).toUpperCase(), new Integer(meta.getColumnType(n)));
					}
				}
				prep.close();
				return map;
			}
		});

		return m_columnTypes = typeMap; 
	}

	private synchronized Map getColumnLengths() throws Exception
	{
		if (m_columnLengths!=null) return m_columnLengths;

		final String sql = "select * from "+getTableName()+" limit 1";

		Map lengthMap = (Map)getJdbcTemplate().execute(new ConnectionCallback() {
			public Object doInConnection(Connection conn) throws SQLException, DataAccessException
			{
				PreparedStatement prep = conn.prepareStatement(sql);
				ResultSetMetaData meta = prep.getMetaData();
				Map<String, Integer> map = new HashMap<String, Integer>(meta.getColumnCount()*2-1);
				for (int n=1; n<=meta.getColumnCount(); n++)
					map.put(meta.getColumnName(n), new Integer(meta.getColumnDisplaySize(n)));
				prep.close();
				return map;
			}
		});

		return m_columnLengths = lengthMap; 
	}

	private boolean haveSingleIntegerKey() throws Exception
	{
		String[] keys = getKeyProperties();
		if (keys.length>1) return false;
		int[] types = getSqlTypes(keys);
		return types[0] == Types.INTEGER;
	}

	static class DaoCacheEntry
	{
		Object[] keys;
		Object object;
		int sequence;

		DaoCacheEntry(Object[] keys, Object object, int sequence)
		{
			this.keys = keys;
			this.object = object;
			this.sequence = sequence;
		}

	}

	private Cache getThreadCache() throws Exception
	{
		return m_transactionContextHolder.getContext().getDaoCache();
	}

	DaoCacheEntry findInThreadCache(Object o) throws Exception
	{
		logger.debug("Looking for "+o+" in thread cache");
		Cache map = getThreadCache();
		return (DaoCacheEntry)map.get(o);
	}

	DaoCacheEntry findInThreadCache(Object[] keys) throws Exception
	{
		logger.debug("Looking for ["+implode(keys, "|")+"] in thread cache");
		Cache map = getThreadCache();
		DaoCacheEntry e = (DaoCacheEntry)map.get(getCacheKey(keys));
		if (e==null) return null;
		map.put(e.object, e); //May have been dropped from LRU cache while keys entry has not
		return e; 
	}

	void addToThreadCache(DaoCacheEntry e) throws Exception
	{
		Cache map = getThreadCache();
		if (e.keys!=null) map.put(getCacheKey(e.keys), e);
		map.put(e.object, e);
		logger.debug("Added "+e.object+" to thread cache");
	}

	void removeFromThreadCache(Object o) throws Exception
	{
		Cache map = getThreadCache();
		DaoCacheEntry e = (DaoCacheEntry)map.get(o);
		if (e==null) throw new Exception("Object "+o+" not found in thread cache");
		if (e.keys!=null) map.remove(getCacheKey(e.keys));
		map.remove(e.object);
	}

	private String getCacheKey(Object... keys) throws Exception
	{
		return getTableName()+"::"+implode(keys, ".");
	}

	private String getCacheKey(String sQuery, Object[] params, Class<?> type) throws Exception
	{
		StringBuilder buf = new StringBuilder(512);

		buf.append(sQuery);
		buf.append("::");
		buf.append(type.getCanonicalName());
		for (Object p : params) {
			if (p instanceof String || p instanceof Number || p instanceof Date) {
				buf.append("::");
				buf.append(p.toString());
			}
			else {
				return null;
			}
		}

		return buf.toString();
	}

	private void appendKeyClause(StringBuffer buf)
	{
		String[] keys = getKeyProperties();
		for (int n=0; n<keys.length; n++) {
			if (n>0) buf.append(" and ");
			buf.append(getColumnNamePrefix());
			buf.append(keys[n]);
			buf.append(" = ?");
		}
	}

	private void appendColumnListForUpdate(StringBuffer buf, String[] cols)
	{
		for (int n=0; n<cols.length; n++) {
			if (n>0) buf.append(", ");
			buf.append(getColumnNamePrefix());
			buf.append(cols[n]);
			buf.append("=?");
		}
	}

	private void appendColumnList(StringBuffer buf, String[] cols)
	{
		for (int n=0; n<cols.length; n++) {
			if (n>0) buf.append(", ");
			buf.append(getColumnNamePrefix());
			buf.append(cols[n]);
		}
	}

	private String getDeleteSql()
	{
		StringBuffer buf = new StringBuffer(1024);
		buf.append("delete from ");
		buf.append(getTableName());
		buf.append(" where ");
		appendKeyClause(buf);
		return buf.toString();
	}

	private String getInsertSql(Class type)
	{
		String[] props = getAllPersistedProperties(type);

		StringBuffer buf = new StringBuffer(1024);
		buf.append("insert into ");
		buf.append(getTableName());

		buf.append("(");
		appendColumnList(buf, props);

		if (isPolymorphic()) {
			buf.append(", ");
			buf.append(getDiscriminatorColumnName());
		}

		buf.append(") values (");

		for (int n=0; n<props.length; n++) {
			if (n>0) buf.append(", ");
			buf.append("?");
		}

		if (isPolymorphic()) buf.append(", ?");

		buf.append(")");

		return buf.toString();
	}

	private String getUpdateSql(Class type)
	{
		return getUpdateSql(type, getNonKeyProperties(type));
	}

	private String getUpdateSql(Class type, String[] cols)
	{
		StringBuffer buf = new StringBuffer(1024);

		buf.append("update ");
		buf.append(getTableName());

		buf.append(" set ");
		appendColumnListForUpdate(buf, cols);
		buf.append(" where ");

		appendKeyClause(buf);

		if (isSequencingEnabled()) {
			buf.append(" and ");
			buf.append(getSequenceColumnName());
			buf.append(" = ?");
		}

		return buf.toString();
	}

	private String getSelectSql() 
	{
		return getSelectSql(null, null, true);
	}

	private String getSelectSql(String whereClause) 
	{
		return getSelectSql(null, whereClause, true);
	}

	protected final String getSelectSql(String[] props, String whereClause, boolean includeSysColumns) 
	{
		StringBuffer buf = new StringBuffer(1024);

		buf.append("select ");

		if (props==null) {
			String[] keyProps = getKeyProperties();
			for (int k=0; k<keyProps.length; k++) {
				if (k>0) buf.append(", ");
				buf.append(getColumnNamePrefix());
				buf.append(keyProps[k]);
			}
			Iterator iter = getAllPersistedPropertyNames().iterator();
			while (iter.hasNext()) {
				buf.append(", ");
				buf.append(getColumnNamePrefix());
				buf.append(iter.next().toString());
			}
		}
		else {
			for (int n=0; n<props.length; n++) {
				if (n>0) buf.append(", ");
				buf.append(getColumnNamePrefix());
				buf.append(props[n]);
			}
		}

		if (m_optimisticLockingEnabled && includeSysColumns) {
			buf.append(", ");
			buf.append(m_sequenceColumnName);
		}

		if (m_createdTimestampProperty!=null && includeSysColumns) {
			buf.append(", ");
			buf.append(getColumnNamePrefix());
			buf.append(m_createdTimestampProperty);
		}

		/*if (m_ModifiedTimestampProperty!=null && includeSysColumns) {
			buf.append(", ");
			buf.append(getColumnNamePrefix());
			buf.append(m_ModifiedTimestampProperty);
		}*/

		if (m_polymorphic && includeSysColumns) {
			buf.append(", ");
			buf.append(getDiscriminatorColumnName());
		}

		buf.append(" from ");
		buf.append(getTableName());

		buf.append(" where ");
		if (whereClause==null)  appendKeyClause(buf);
		else buf.append(whereClause);
		String st = buf.toString();
		return st;
	}

	private String getSelectSql(String selectClause, String whereClause) 
	{
		StringBuffer buf = new StringBuffer(1024);

		buf.append("select ");

		buf.append(selectClause);

		buf.append(" from ");
		buf.append(getTableName());

		buf.append(" where ");
		if (whereClause==null)  appendKeyClause(buf);
		else buf.append(whereClause);

		return buf.toString();
	}

	private Object[] getKeyValuesForMap(Map data) throws Exception
	{ 
		String[] props = getKeyProperties();
		Object[] values = new Object[props.length];
		for (int n=0; n<props.length; n++) {
			String col = props[n].toUpperCase();
			if (!data.containsKey(col)) throw new Exception("Column "+col+" not present");
			values[n] = data.get(col); 
		}
		return values;
	}

	private Object[] getKeyValuesForObject(Object o) throws Exception
	{
		String[] props = getKeyProperties();
		Object[] values = new Object[props.length];
		for (int n=0; n<props.length; n++) {
			values[n] =PropertyUtils.getSimpleProperty(o,props[n]);
		}
		return values;
	}

	private final String[] getNonKeyProperties(Class type)
	{
		TypeMapEntry e = getTypeMap().get(type);
		return e.getPersistedProperties();
	}

	private final String[] getAllPersistedProperties(Class type)
	{
		String[] keys = getKeyProperties();
		String[] cols = getNonKeyProperties(type);
		String[] a = new String[keys.length+cols.length];
		int n=0;
		for (;n<keys.length; n++) a[n] = keys[n];
		for (;n<a.length;n++) a[n] = cols[n-keys.length];
		return a;
	}

	private final Collection getAllPersistedPropertyNames()
	{
		return getTypeMap().getAllPropertyNames();
	}

	private final String[] concatArays(String[] k, String[] c)
	{
		String[] a = new String[k.length+c.length];
		int n=0;
		for (;n<k.length; n++) a[n] = k[n];
		for (;n<a.length;n++) a[n] = c[n-k.length];
		return a;
	}

	private final int[] getSqlTypes(String[] props) throws Exception
	{
		Map map = getColumnTypes();

		int[] sqltypes = new int[props.length];

		for (int n=0; n<props.length; n++) {
			String s = (getColumnNamePrefix()+props[n]).toUpperCase();
			Integer t = (Integer)map.get(s);
			if (t==null) throw new Exception("Unknown column "+s);
			sqltypes[n] = t.intValue(); 
		}

		return sqltypes;
	}

	private Object[] getSqlValuesForProperties(Object o, String[] props) throws Exception 
	{
		Object[] values = new Object[props.length];
		for (int n=0; n<props.length; n++) {
			Object value;
			try {
				value =PropertyUtils.getSimpleProperty(o,props[n]);
			}
			catch (NoSuchMethodException x) {
				throw new Exception("Class "+o.getClass().getName()+" does not have a public getter method for ["+props[n]+"]");
			}
			if (value instanceof Boolean) {
				value = ((Boolean)value).booleanValue()?PSEUDO_BOOLEAN_TRUE_VALUE:PSEUDO_BOOLEAN_FALSE_VALUE;
			}
			values[n] = value;
			logger.debug(props[n]+"::::"+(values[n]==null?"null":values[n].getClass().getName()) );
		}
		return values;
	}

	public final void setTypeMap(TypeMap typeMap)
	{
		m_typeMap = typeMap;
	}

	protected final TypeMap getTypeMap()
	{
		return m_typeMap;
	}

	public final void setKeyProperties(String[] a)  
	{
		m_keyProperties = new String[a.length];
		for (int n=0; n<a.length; n++) {
			String prop = a[n];
			m_keyProperties[n] = prop.substring(0, 1).toLowerCase()+prop.substring(1);
		}
	}

	protected final String[] getKeyProperties()
	{
		return m_keyProperties;
	}

	public final String getColumnNamePrefix()
	{
		return m_columnNamePrefix;
	}

	public final void setColumnNamePrefix(String columnNamePrefix)
	{
		m_columnNamePrefix = columnNamePrefix;
	}

	protected final String getTableName()
	{
		return m_tableName;
	}

	public final void setTableName(String tableName)
	{
		m_tableName = tableName;
	}

	protected final String getDiscriminatorColumnName()
	{
		return m_discriminatorColumnName;
	}

	public final void setDiscriminatorColumnName(String discriminatorColumn)
	{
		m_discriminatorColumnName = discriminatorColumn;
	}

	protected final String getSequenceColumnName()
	{
		return m_sequenceColumnName;
	}

	public final void setSequenceColumnName(String sequenceColumn)
	{
		m_sequenceColumnName = sequenceColumn;
	}

	public void setServletContext(ServletContext servletContext)
	{
		m_servletContext = servletContext;
	}

	public void setApplicationContext(ApplicationContext arg0) throws BeansException
	{
		m_applicationContext = arg0;
	}

	protected final ApplicationContext getApplicationContext()
	{
		return m_applicationContext;
	}

	public void setAutowireMode(int autowireMode) {
		m_autowireMode = autowireMode;
	}

	public final void setAutowire(boolean autowire)
	{
		m_autowire = autowire;
	}

	public final void setOptimisticLockingEnabled(boolean sequencingEnabled)
	{
		m_optimisticLockingEnabled = sequencingEnabled;
	}

	public final boolean isSequencingEnabled()
	{
		return m_optimisticLockingEnabled;
	}

	public void setCreatedTimestampProperty(String createdTimestampColumnName)
	{
		m_createdTimestampProperty = createdTimestampColumnName;
	}

	/*public void setModifiedTimestampProperty(String modifiedTimestampColumnName)
	{
		m_ModifiedTimestampProperty = modifiedTimestampColumnName;
	}*/

	public final boolean isPolymorphic()
	{
		return m_polymorphic;
	}

	public final void setPolymorphic(boolean polymorphic)
	{
		m_polymorphic = polymorphic;
	}

	public final void setKeyGenerator(KeyGenerator keyGenerator)
	{
		m_keyGenerator = keyGenerator;
	}

	public final void setTransactionContextHolder(TransactionContextHolder transactionContextHolder)
	{
		m_transactionContextHolder = transactionContextHolder;
	}

	EntityRowMapper getRowMapper()
	{
		return m_rowMapper;
	}

	public void setDaoManager(JdbcDaoManager daoManager)
	{
		m_daoManager = daoManager;
	}

	public final TransactionContextHolder getTransactionContextHolder()
	{
		return m_transactionContextHolder;
	}
}