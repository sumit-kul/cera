package support.community.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.jsp.JspException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

public class Db2QueryScroller implements QueryScroller
{
   private String m_queryText = null;
   private Vector<Object> m_queryParameters = new Vector<Object>(2);
   private Vector<ScrollKey> m_scrollKeys = new Vector<ScrollKey>(2);
   private Class m_beanClass = null;
   
   private int m_pageSize = 16; 
   private boolean m_strict = false; 
   private boolean m_reverse = false;  
   private boolean m_endOfData = false;
   
   private String[] m_beanMatchedColumnNames = null;
   private String[] m_columnNamePrefixes = null;
   
   private AbstractJdbcDaoSupport m_dao;
   private boolean m_native;
   private int m_totalRows = 0;
   JdbcTemplate m_jdbcTemplate;
   protected RowMapper m_rowMapper = null;
   
   private KeyedScrollerPageImpl m_data; 
  
   Log logger = LogFactory.getLog(getClass());

   Db2QueryScroller(AbstractJdbcDaoSupport dao, Class beanClass, String query, Object[] params)
   {
       m_dao = dao;
       m_jdbcTemplate = dao.getJdbcTemplate();
       
       setBeanClass(beanClass);
       
       m_queryText = query;
       
       for (int n=0; params!=null&&n<params.length; n++)
           m_queryParameters.add(params[n]);
   }
   
   Db2QueryScroller(AbstractJdbcDaoSupport dao, boolean nQuery, Class beanClass, String query, Object[] params)
   {
       m_dao = dao;
       m_jdbcTemplate = dao.getJdbcTemplate();
       
       setBeanClass(beanClass);
       setM_native(nQuery);
       
       m_queryText = query;
       
       for (int n=0; params!=null&&n<params.length; n++)
           m_queryParameters.add(params[n]);
   }
   
   public void addQueryParameter(Object o) throws Exception
   {
      m_queryParameters.addElement(o);
   }

   private PreparedStatement buildQuery(Connection conn) throws Exception
   {
      /*
       * In the text passed up by the enclosed <query> tag remove and tab characters.
       */
      String s = m_queryText.replace('\t',' ');

      /*
       * If scroll key tags present the text must not include order by.
       */
      if (!m_scrollKeys.isEmpty() && s.indexOf(" order by ")>-1) 
         throw new JspException("Do not specifiy 'order by' in sql query when scrollKey tag is present");
      
      /*
       * If scroll key tags present the query text must not include reference to the scroll keys
       * in the where clause since this will more often than not break the scrolling logic.
       */
      for (int n=0; n<m_scrollKeys.size(); n++) {
         ScrollKey skey = (ScrollKey)m_scrollKeys.elementAt(n);
         if (checkForInvalidRefrence(s, skey.c_name))
            throw new JspException("Cannot not refer to a scroll key column in this way in the where clause of a query that is to be scrolled. Use value and base attributes of the scrollKey tag instead.");
      }
      
      /* 
       * Initialize the query buffer.
       */
      StringBuffer sbuf = new StringBuffer(s.length()+100);
      sbuf.append(s);
      
      /*
       * It gets a bit too complex to be worthwhile to support more than two scroll keys since
       * we have no actual need for more than two at present.
       */
      if (m_scrollKeys.size()>2) 
         throw new JspException("No more than 2 scroll keys can be specified");

      /*
       * If scroll keys present then add in any base clauses. A base attribute on the
       * scrollKey tag allows scrolling to be restricted to a final segment of the 
       * data set defined by the query.
       */
      for (int n=0; n<m_scrollKeys.size(); n++) {
         ScrollKey skey = (ScrollKey)m_scrollKeys.elementAt(n);
         if (skey.c_baseKey!=null) {
            if (sbuf.toString().indexOf(" where ")>-1) sbuf.append(" and ");
               else sbuf.append(" where ");
            sbuf.append("(");
            sbuf.append(skey.c_name);
            sbuf.append(" >= ?");
            sbuf.append(") ");
         }
      }

      /*
       * If scroll keys present then add in the scroll start clause. This is simple for
       * a single key but more complex for two keys, eg: (k1 > s1) or (k1 = s1 and k2 > s2). 
       */
      if (m_scrollKeys.size()>0) {

         ScrollKey skey = (ScrollKey)m_scrollKeys.elementAt(0);
         if (skey.c_positionKey!=null) {
         
            if (sbuf.toString().indexOf(" where ")>-1) sbuf.append(" and ");
               else sbuf.append(" where ");
            sbuf.append("(");
            sbuf.append(skey.c_name);
            sbuf.append(skey.getComparator(m_strict, m_reverse));

            if (m_scrollKeys.size()>1) {
               ScrollKey skey2 = (ScrollKey)m_scrollKeys.elementAt(1);
               if (skey2.c_positionKey!=null) {
                  sbuf.append(" or (");
                  sbuf.append(skey.c_name);
                  sbuf.append(" = ? and ");
                  sbuf.append(skey2.c_name);
                  sbuf.append(skey2.getComparator(m_strict, m_reverse));
                  sbuf.append(")");
                  /* 
                   * Add this redundant clause to help the optimizer. This makes it obvious that 
                   * everything in the resultset has skey >= (or<= if reverse) the key value so
                   * the optimizer will use a start key on an index scan not a full index scan.   
                   */
                  sbuf.append(" and ");
                  sbuf.append(skey.c_name);
                  if (skey.c_direction.equals("asc")) sbuf.append(m_reverse?" <= ?":" >= ?");
                     else sbuf.append(m_reverse?" >= ?":" <= ?");
               }
            }  
            
            sbuf.append(")");
         }
      }  

      /*
       * Add in the order by clause.
       */
      for (int n=0; n<m_scrollKeys.size(); n++) {
         ScrollKey skey = (ScrollKey)m_scrollKeys.elementAt(n);
         sbuf.append(n==0?" order by ":", ");
         sbuf.append(skey.c_name);
         if (skey.c_direction.equals("asc")) sbuf.append(m_reverse?" desc":" asc");
            else sbuf.append(m_reverse?" asc":" desc");
      }

      /*
       * Add in the clauses to optimize for the page size. We add 1 to the page
       * size when scrolling froward in order to be able to look ahead and detect end of data.
       */
      if (m_pageSize>0 && s.indexOf(" optimize for ")<0 ) {
         int i = m_reverse ? m_pageSize : m_pageSize+1;
         sbuf.append(" LIMIT ");
         sbuf.append(String.valueOf(i));
         sbuf.append(" ");
         sbuf.append(" optimize for ");
         sbuf.append(String.valueOf(i));
         sbuf.append(" rows");
      }

     logger.debug(sbuf.toString());
      /*
       * Get a connection and prepare the statement that has been built.
       */
      PreparedStatement prep = conn.prepareStatement(sbuf.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      
      /*
       * Set the specified query parameters into the statement.
       */
      int i = 1;
      Enumeration e = m_queryParameters.elements();
      while (e.hasMoreElements()) prep.setObject(i++, e.nextElement());

      /*
       * If scroll keys present then add in any base key values. 
       */
      for (int n=0; n<m_scrollKeys.size(); n++) {
         ScrollKey skey = (ScrollKey)m_scrollKeys.elementAt(n);
         if (skey.c_baseKey!=null) prep.setObject(i++, skey.c_baseKey);
      }

      /*
       * If there are scroll keys then set the start or end values into the statement.
       */
      if (m_scrollKeys.size()>0) {
         ScrollKey skey = (ScrollKey)m_scrollKeys.elementAt(0);
         if (skey.c_positionKey!=null) {
            prep.setObject(i++, skey.c_positionKey);
            if (m_scrollKeys.size()>1) {
               ScrollKey skey2 = (ScrollKey)m_scrollKeys.elementAt(1);
               if (skey2.c_positionKey!=null) {
                  prep.setObject(i++, skey.c_positionKey);
                  prep.setObject(i++, skey2.c_positionKey);
                  prep.setObject(i++, skey.c_positionKey);
               }
            }
         }
      }
         
      /*
       * Return the built and prepared statement.
       */
      return prep;      
    }

   protected final PreparedStatement buildCountQuery(Connection conn) throws Exception
   {
	   /*
	    * In the text passed up by the enclosed <query> tag remove and tab characters.
	    */
	   String s = m_queryText.replace('\t',' ');

	   /* 
	    * Initialize the query buffer.
	    */
	   StringBuffer sbuf = new StringBuffer(s.length()+100);    

	   if (isM_native()) {
		   if (s.contains(") as T")) {
			   sbuf.append("select count(*) from ( ");
			   sbuf.append(s);
			   sbuf.append(" ) as T2");
		   } else {
		   sbuf.append(s.substring(0, 7));
		   sbuf.append(" count(*) ");
		   sbuf.append(s.substring(s.toLowerCase().indexOf(" from ", 1)));
		   }
	   } else {
		   if (s.startsWith("select * from (")) {
			   sbuf.append(s.replace("select * from (", "select count(*) from ( "));
		   } else {
		   sbuf.append(s.substring(0, findSelectListStart(s)));
		   sbuf.append(" count(*) ");
		   sbuf.append(s.substring(findSelectListEnd(s)));
		   }
	   }

	   logger.debug(sbuf.toString());
	   PreparedStatement prep = conn.prepareStatement(sbuf.toString());

	   int i = 1;
	   Enumeration e = m_queryParameters.elements();
	   while (e.hasMoreElements()) prep.setObject(i++, e.nextElement());

	   return prep;      
   }

   private PreparedStatement buildPagedQuery(Connection conn, int start, int end, boolean nQuery) throws Exception
   {
      /*
       * In the text passed up by the enclosed <query> tag remove and tab characters.
       */
      String s = m_queryText.replace('\t',' ');

      /*
       * If scroll key tags present the text must not include order by.
       */
      if (m_scrollKeys.isEmpty() && !nQuery) 
         throw new JspException("You must specifiy at least one scroll key");
      
      /*
       * The text must not include order by.
       */
      if (!nQuery && s.indexOf(" order by ")>-1) 
         throw new JspException("Do not specifiy 'order by' in sql query when scrollKey tag is present");
          
      /* 
       * Initialize the query buffer.
       */
      StringBuffer sbuf = new StringBuffer(s.length()+100);    

      /*
       * Find the point in the quey at which to insert the row number clause.
       */
      if (!nQuery) {
      int scs = findSelectClauseStart(s);
      int ip = findSelectListEndForPagination(s);
      String slectString = s.substring(scs, ip);
      
      /*sbuf.append(s.substring(0, scs));
      sbuf.append("SELECT * FROM (");
      sbuf.append(s.substring(scs, ip));
      sbuf.append(", rownumber() OVER (");*/
      /*
       * Add in the order by clause.
       */
      /*writeOrderByClause(sbuf);
      sbuf.append(") AS ROWNUM ");
      sbuf.append(s.substring(ip));
      sbuf.append(") AS SCROLLER_TEMP WHERE ROWNUM BETWEEN ? AND ?");*/
      
      /*sbuf.append("SELECT SCROLLER_TEMP.* FROM ( SELECT @i:=@i + 1 as ROWNUM , ");
      sbuf.append(slectString.substring(7));
      sbuf.append(" FROM (select @i:=0) temp , ");
      sbuf.append(s.substring(ip+5));
      sbuf.append(" " +addOriteOrderByClause());
	  sbuf.append(" ) AS SCROLLER_TEMP WHERE SCROLLER_TEMP.ROWNUM BETWEEN ? AND ?");*/
      
      sbuf.append("select * from ( ");
      //sbuf.append("select STEMP.*, @rownum := @rownum + 1 AS ROWNUM from ( ");
      sbuf.append(s);
      //sbuf.append(" " +addOriteOrderByClause()+ " ");
      //sbuf.append(" ) as STEMP, (SELECT @rownum := 0) r ");
      sbuf.append(" ) as STEMP ");
      sbuf.append(" " +addOriteOrderByClause()+ " ");
      sbuf.append(" LIMIT ?, ? ");
	  //sbuf.append(") AS SCROLLER_TEMP WHERE SCROLLER_TEMP.ROWNUM BETWEEN ? AND ? ");
      } else {
    	  /*sbuf.append("SELECT SCROLLER_TEMP.* FROM ( SELECT ROWNUMBER() OVER ( ");
    	  sbuf.append(addOriteOrderByClause());
    	  sbuf.append(" ) AS ROWNUM, ");
    	  sbuf.append(s.substring(7));
		  sbuf.append(" ) AS SCROLLER_TEMP WHERE SCROLLER_TEMP.ROWNUM BETWEEN ? AND ?");*/
    	  
    	  /*sbuf.append("SELECT SCROLLER_TEMP.* FROM ( SELECT @i:=@i + 1 as ROWNUM , ");
    	  sbuf.append(s.substring(7, s.toLowerCase().indexOf("from ")));
    	  sbuf.append(" FROM (select @i:=0) temp , ");
    	  sbuf.append(s.substring(s.toLowerCase().indexOf("from ") + 4));
    	  sbuf.append(" " +addOriteOrderByClause());
		  sbuf.append(" ) AS SCROLLER_TEMP WHERE SCROLLER_TEMP.ROWNUM BETWEEN ? AND ?");*/
    	  
    	  sbuf.append("select * from ( ");
          //sbuf.append("select STEMP.*, @rownum := @rownum + 1 AS ROWNUM from ( ");
          sbuf.append(s);
          //sbuf.append(" " +addOriteOrderByClause()+ " ");
          //sbuf.append(" ) as STEMP, (SELECT @rownum := 0) r ");
          sbuf.append(" ) as STEMP ");
          sbuf.append(" " +addOriteOrderByClause()+ " ");
          sbuf.append(" LIMIT ?, ? ");
    	  //sbuf.append(") AS SCROLLER_TEMP WHERE SCROLLER_TEMP.ROWNUM BETWEEN ? AND ? ");
      }

    
     logger.debug("Executing query: "+sbuf.toString());
     
      /*
       * Get a connection and prepare the statement that has been built.
       */
//     PreparedStatement prep = conn.prepareStatement(sbuf.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
     PreparedStatement prep = conn.prepareStatement(sbuf.toString());
      
      /*
       * Set the specified query parameters into the statement.
       */
      int i = 1;
      Enumeration e = m_queryParameters.elements();
      while (e.hasMoreElements()) {
          Object o = e.nextElement();
          logger.debug("Setting query parameter:"+o);
          prep.setObject(i++, o);
      }

      /*
       * Set the begin and end indexes into the statement.
       */
      logger.debug("Setting begin="+start+", end="+end);
      prep.setInt(i++, start);
      prep.setInt(i++, end);
         
      /*
       * Return the built and prepared statement.
       */
      return prep;      
    }

   private void writeOrderByClause(StringBuffer sbuf)
   {
       for (int n=0; n<m_scrollKeys.size(); n++) {
           ScrollKey skey = (ScrollKey)m_scrollKeys.elementAt(n);
           sbuf.append(n==0?" ORDER BY ":", ");
           sbuf.append(skey.c_name);
           if (skey.c_direction.equals("asc")) sbuf.append(m_reverse?" desc":" asc");
              else sbuf.append(m_reverse?" asc":" desc");
        }
   }
   
   private StringBuffer addOriteOrderByClause()
   {
	   StringBuffer ordBy = new StringBuffer();
       for (int n=0; n<m_scrollKeys.size(); n++) {
           ScrollKey skey = (ScrollKey)m_scrollKeys.elementAt(n);
           ordBy.append(n==0?" ORDER BY ":", ");
           ordBy.append(skey.c_name);
           if (skey.c_direction.equals("asc")) ordBy.append(m_reverse?" desc":" asc");
              else ordBy.append(m_reverse?" asc":" desc");
        }
       return ordBy;
   }
   
   private int findSelectListEndForPagination(String query)
   {
       int fromCount = 0;
       int insertionFromIndex = 0;
       boolean inExistClasue = false;
       
       StringTokenizer tok = new StringTokenizer(query, " (),\r\n", false);
       while (tok.hasMoreTokens()) {
           String s = tok.nextToken();
           if (s.equalsIgnoreCase("exists")) inExistClasue = true;
           if (s.equalsIgnoreCase("from")) {
               fromCount++;
               if (!inExistClasue) insertionFromIndex = fromCount;
                 else inExistClasue = false;
           }
       }
       
       int i = 0;
       for (int j=0; j<insertionFromIndex; j++ ) {
           i = query.toLowerCase().indexOf(" from ", i+1);
       }
       
       return i; //to remove 'from' in the final query
   }
   
   private int findSelectListEnd(String query)
   {
       int fromCount = 0;
       int insertionFromIndex = 0;
       boolean inExistClasue = false;
       
       StringTokenizer tok = new StringTokenizer(query, " (),\r\n", false);
       while (tok.hasMoreTokens()) {
           String s = tok.nextToken();
           if (s.equalsIgnoreCase("exists")) inExistClasue = true;
           if (s.equalsIgnoreCase("from")) {
               fromCount++;
               if (!inExistClasue) insertionFromIndex = fromCount;
                 else inExistClasue = false;
           }
       }
       
       int i = 0;
       for (int j=0; j<insertionFromIndex; j++ ) {
           i = query.toLowerCase().indexOf(" from ", i+1);
       }
       
       return i;
   }
   
   private int findSelectClauseStart(String query)
   {
       int fromCount = 0;
       int insertionFromIndex = 0;
       boolean inExistClasue = false;
       int selectNestLevel = 0;
       
       StringTokenizer tok = new StringTokenizer(query, " (),\r\n", false);
       while (tok.hasMoreTokens()) {
           String s = tok.nextToken();
           if (s.equalsIgnoreCase("exists")) { 
               inExistClasue = true;
           }
           else if (s.equalsIgnoreCase("from")) { 
               selectNestLevel--;
               inExistClasue = false;
           }
           else if (s.equalsIgnoreCase("select")) {
               fromCount++;
               selectNestLevel++;
               if (inExistClasue) continue;
               if (selectNestLevel>1) continue;     
               insertionFromIndex = fromCount;
           }
       }
       
       int i = -1;
       for (int j=0; j<insertionFromIndex; j++ ) {
           i = query.toLowerCase().indexOf("select ", i+1);
       }
       
       return i;
   }
   
   private int findFromClauseStart(String query)
   {
       int fromCount = 0;
       int insertionFromIndex = 0;
       boolean inExistClasue = false;
       int selectNestLevel = 0;
       
       StringTokenizer tok = new StringTokenizer(query, " (),\r\n", false);
       while (tok.hasMoreTokens()) {
           String s = tok.nextToken();
           if (s.equalsIgnoreCase("from")) { 
               selectNestLevel--;
               inExistClasue = false;
           }
           else if (s.equalsIgnoreCase("select")) {
               fromCount++;
               selectNestLevel++;
               if (inExistClasue) continue;
               if (selectNestLevel>1) continue;     
               insertionFromIndex = fromCount;
           }
       }
       
       int i = -1;
       for (int j=0; j<insertionFromIndex; j++ ) {
           i = query.toLowerCase().indexOf("select ", i+1);
       }
       
       return i;
   }
   
   private int findSelectListStart(String query)
   {
       return findSelectClauseStart(query)+7;
   }
   
   private boolean checkForInvalidRefrence(String sQuery, String sKeyName) throws Exception
   {
      String s = sQuery.toLowerCase();
      String sName = sKeyName.toLowerCase();
      int j = s.indexOf(" where ");
      if (j<0) return false;
      if (s.indexOf(sName+">", j)>-1) return true;
      if (s.indexOf(sName+" >", j)>-1) return true;
      if (s.indexOf(sName+"<", j)>-1) return true;
      if (s.indexOf(sName+" <", j)>-1) return true;
      return false;
   }

   /**
    * Specify a scroll key for the scroller.
    * 
    * @param sName  The column name of the scroll key.
    * @param sDir  The direction "asc" or "desc".
    * @param sType The data type of the column "text", "int" or "date".
    * @throws Exception
    */
   public void addScrollKey(String sName, String sDir, String sType) throws Exception
   {
       m_scrollKeys.add(new ScrollKey(sName, sName, sDir, sType, false, null));
   }

   /**
    * Specify a scroll key for the scroller.
    * 
    * @param sName  The column name of the scroll key.
    * @param sDir  The direction "asc" or "desc".
    * @param sType The data type of the column "text", "int" or "date".
    * @param bUpperCase  True if the column values are upper case only.
    * @throws Exception
    */
   public void addScrollKey(String sName, String sDir, String sType, boolean bUpperCase) throws Exception
   {
       m_scrollKeys.add(new ScrollKey(sName, sName, sDir, sType, bUpperCase, null));
   }

   /**
    * Specify a scroll key for the scroller.
    * 
    * @param sName  The column name of the scroll key.
    * @param sResultSetName  The name of the column as it appears in the query resultset
    * @param sDir  The direction "asc" or "desc".
    * @param sType The data type of the column "text", "int" or "date".
    * @throws Exception
    */
   public void addScrollKey(String sName, String sResultSetName, String sDir, String sType) throws Exception
   {
       m_scrollKeys.add(new ScrollKey(sName, sResultSetName, sDir, sType, false, null));
   }

   /**
    * Specify a scroll key for the scroller.
    * 
    * @param sName  The column name of the scroll key.
    * @param sResultSetName  The name of the column as it appears in the query resultset
    * @param sDir  The direction "asc" or "desc".
    * @param sType The data type of the column "text", "int" or "date".
    * @param bUpperCase  True if the (text) column is upper case only.
    * @throws Exception
    */
   public void addScrollKey(String sName, String sResultSetName, String sDir, String sType, boolean bUpperCase) throws Exception
   {
       m_scrollKeys.add(new ScrollKey(sName, sResultSetName, sDir, sType, bUpperCase, null));
   }

   /**
    * Specify a scroll key for the scroller.
    * 
    * @param sName  The column name of the scroll key.
    * @param sResultSetName  The name of the column as it appears in the query resultset
    * @param sDir  The direction "asc" or "desc".
    * @param sType The data type of the column "text", "int" or "date".
    * @param bUpperCase  True if the (text) column is upper case only.
    * @param oBase  Specify a key value here to limit the result to rows with the scroll key value greater than this.
    * @throws Exception
    */
   public void addScrollKey(String sName, String sResultSetName, String sDir, String sType, boolean bUpperCase, Object oBase) throws Exception
   {
      m_scrollKeys.add(new ScrollKey(sName, sResultSetName, sDir, sType, bUpperCase, oBase));
   }

   /**
   .
*/
public KeyedScrollerPage readPage(Object skey1, Object skey2, boolean bStrict, boolean bReverse) throws Exception
{
    /*
     * Fill in the scroll key values.
     */
    if (m_scrollKeys.size()>0) {
        ScrollKey sk = (ScrollKey)m_scrollKeys.elementAt(0);
        sk.setValue(skey1);
    }
    if (m_scrollKeys.size()>1) {
        ScrollKey sk = (ScrollKey)m_scrollKeys.elementAt(1);
        sk.setValue(skey2);
    }

    /*
     * Determine whether start key is strict or equality allowed.
     */
     m_strict = bStrict;

     /*
      * 
      */
     m_reverse = bReverse;
     
    /*
     * Run the query and process the result set.
     */
     m_jdbcTemplate.execute(new ConnectionCallback() {
         public Object doInConnection(Connection conn) {
             try {
                 processRows(conn);
             }
             catch (Exception x) {
                 logger.error("",x);
                 //throw new DataRetrievalFailureException(x.toString());
             }
             return null;
         }
     });
     
   /*
    * Set up the javascript variables used by the scrolling functions. These contain the
    * key values for the first and last rows on the page in general but there are some
    * special cases. If end of data is reached (so there is no next page) then the LastKey
    * variable is set to hold the Start position key so that a scroll to next page has the 
    * effect of redisplaying the current (last) page.
    */
   if (!m_scrollKeys.isEmpty()) {
       
      for (int n=0; n<m_scrollKeys.size(); n++) {
         ScrollKey skey = (ScrollKey)m_scrollKeys.elementAt(n);
         if (skey.c_firstKey!=null)
             m_data.setPrevPageStartKey(n, skey.c_firstKey);  
         else  
             m_data.setPrevPageStartKey(n, skey.c_positionKey);  
      }
      
      for (int n=0; n<m_scrollKeys.size(); n++) {
         ScrollKey skey = (ScrollKey)m_scrollKeys.elementAt(n);
         if (m_endOfData) 
             m_data.setNextPageStartKey(n, skey.c_positionKey); 
         else 
             m_data.setNextPageStartKey(n, skey.c_lastKey); 
      }
      
   }
               
   return m_data;
}

public int readPageCount() throws Exception
{
    int rowCount = readRowCount();
    setM_totalRows(rowCount);
    return rowCount==0 ? 0 : ((int)((rowCount-1)/m_pageSize))+1;
}


/**
.
*/
public IndexedScrollerPage readPage(Object key) throws Exception
{
    int p = readPageNumberForKey(key);
    logger.debug("Page number for key:"+p);
    return readPage(p);
}
  
/**
.
*/
protected int readPageNumberForKey(final Object key) throws Exception
{
    /*
     *
     */
     Integer rowsBefore = (Integer)m_jdbcTemplate.execute(new ConnectionCallback() {
         public Object doInConnection(Connection conn) {
             try {
                 PreparedStatement prep = buildKeyToPageQuery(conn, key);
                 try {
                    ResultSet res = prep.executeQuery();
                    if (!res.next()) return new Integer(0);
                    return new Integer(res.getInt(1));
                 }
                 finally {
                     prep.close();
                 }
             }
             catch (Exception x) {
                 logger.error("",x);
                 //throw new DataRetrievalFailureException(x.toString());
                 return new Integer(0);
             }
         }
     });
  
     logger.debug("Rows before key:"+rowsBefore);
     
     int rows  = rowsBefore.intValue()+1;   
     if (rows==1) return 1;
    
     return (((rows-1)/m_pageSize)) + 1;
}


private PreparedStatement buildKeyToPageQuery(Connection conn, Object key) throws Exception
{
    /*
     * In the text passed up by the enclosed <query> tag remove and tab characters.
     */
    String s = m_queryText.replace('\t',' ');

    /*
     * If scroll key tags present the text must not include order by.
     */
    if (!m_scrollKeys.isEmpty() && s.indexOf(" order by ")>-1) 
       throw new JspException("Do not specifiy 'order by' in sql query when scrollKey tag is present");
    
    /*
     * If scroll key tags present the query text must not include reference to the scroll keys
     * in the where clause since this will more often than not break the scrolling logic.
     */
    for (int n=0; n<m_scrollKeys.size(); n++) {
       ScrollKey skey = m_scrollKeys.elementAt(n);
       if (checkForInvalidRefrence(s, skey.c_name))
          throw new JspException("Cannot not refer to a scroll key column in this way in the where clause of a query that is to be scrolled. Use value and base attributes of the scrollKey tag instead.");
    }
    
    if (m_scrollKeys.size()==0) 
        throw new JspException("No scroll keys specified");

    /* 
     * Initialize the query buffer.
     */
    StringBuffer sbuf = new StringBuffer(s.length()+100);
    
    sbuf.append("with TEMP as (");
    
    sbuf.append(s);
    
    /*
     * If scroll keys present then add in any base clauses. A base attribute on the
     * scrollKey tag allows scrolling to be restricted to a final segment of the 
     * data set defined by the query.
     */
    for (int n=0; n<m_scrollKeys.size(); n++) {
       ScrollKey skey = m_scrollKeys.elementAt(n);
       if (skey.c_baseKey!=null) {
          if (sbuf.toString().indexOf(" where ")>-1) sbuf.append(" and ");
             else sbuf.append(" where ");
          sbuf.append("(");
          sbuf.append(skey.c_name);
          sbuf.append(" >= ?");
          sbuf.append(") ");
       }
    }

    sbuf.append(") select count(*) from TEMP where ");
    
       ScrollKey skey = m_scrollKeys.elementAt(0);
       sbuf.append(skey.c_name);
       sbuf.append(" < ?");

   logger.debug(sbuf.toString());
 
    /*
     * Get a connection and prepare the statement that has been built.
     */
    PreparedStatement prep = conn.prepareStatement(sbuf.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    
    /*
     * Set the specified query parameters into the statement.
     */
    int i = 1;
    Enumeration e = m_queryParameters.elements();
    while (e.hasMoreElements()) prep.setObject(i++, e.nextElement());

    /*
     * If scroll keys present then add in any base key values. 
     */
    for (int n=0; n<m_scrollKeys.size(); n++) {
       skey = m_scrollKeys.elementAt(n);
       if (skey.c_baseKey!=null) prep.setObject(i++, skey.c_baseKey);
    }

    /*
     * Set the key value into the statement.
     */
    ScrollKey skey1 = m_scrollKeys.elementAt(0);
    ScrollKey sktemp =  new ScrollKey(skey1.c_name, skey1.c_rsname , skey1.c_direction, skey1.c_type, skey1.c_upperCase, skey1.c_baseKey);
    sktemp.setValue(key);
    logger.debug("Page key:"+sktemp.c_positionKey);
    prep.setObject(i++, sktemp.c_positionKey);
       
    /*
     * Return the built and prepared statement.
     */
    return prep;      
 }


/**
.
*/
public IndexedScrollerPage readPage(int pageNumber) throws Exception
{
    Assert.isTrue(pageNumber>0, "Invalid page number ["+pageNumber+"]. Must be > 0.");

    
    int rowCount = m_native && m_totalRows > 0 ? m_totalRows : readRowCount();
    int pageCount = rowCount==0 ? 0 : ((int)((rowCount-1)/m_pageSize))+1;;
    
    if (pageNumber>pageCount&&pageCount>0) pageNumber = pageCount;
    
      final int s =  (pageNumber-1)*m_pageSize;
      final int e = m_pageSize;
        
     /*
      * Run the query and process the result set.
      */
      IndexedScrollerPageImpl data = (IndexedScrollerPageImpl)m_jdbcTemplate.execute(new ConnectionCallback() {
          public Object doInConnection(Connection conn) {
              try {
                  return processPageRows(conn, s, e, m_native);
              }
              catch (Exception x) {
                  logger.error("",x);
                  //throw new DataRetrievalFailureException(x.toString());
                  return new IndexedScrollerPageImpl(0);
              }
          }
      });
      
      data.setRowCount(rowCount);
      data.setPageCount(pageCount);
      data.setCurrentPageNumber(pageNumber);
      data.setScrollerPageSize(m_pageSize);
      
      return data;
}
  
/**
.
*/
public int readRowCount() throws Exception
{    
     /*
      * Run the row count query.
      */
      Integer rows = (Integer)m_jdbcTemplate.execute(new ConnectionCallback() {
          public Object doInConnection(Connection conn) {
              try {
                  PreparedStatement prep = buildCountQuery(conn);
                  try {
                     ResultSet res = prep.executeQuery();
                     if (!res.next()) return new Integer(0);
                     return new Integer(res.getInt(1));
                  }
                  finally {
                      prep.close();
                  }
              }
              catch (Exception x) {
                  logger.error("",x);
                  x.printStackTrace();
                  //throw new DataRetrievalFailureException(x.toString());
                  return new Integer(0);
              }
          }
      });
   
    return rows.intValue();
}

/**
 * Iterate over all the rows returned by the query for this scroller calling a handler
 * for each row in turn.
*/
public void doForAllRows(final QueryScrollerCallback callback, boolean commitAfterEachPage) throws Exception
{
    if (!commitAfterEachPage) {
        doForAllRows(callback);
        return;
    }
    
    for (int page=1; true ;page++) {
        if (page>readPageCount()) break;
        for (Object o : readPage(page)) {
            callback.handleRow(o, 0);
        }
        if (commitAfterEachPage) m_dao.getTransactionContextHolder().commit();
    }
}

/**
 * Iterate over all the rows returned by the query for this scroller calling a handler
 * for each row in turn.
*/
public void doForAllRows(final QueryScrollerCallback callback) throws Exception
{
    StringBuffer buf = new StringBuffer(512);
    buf.append(m_queryText);
    writeOrderByClause(buf);
    String sQuery = buf.toString();
    
    logger.debug("Executing query: "+sQuery);
    
    m_jdbcTemplate.query(sQuery, m_queryParameters.toArray(), new RowCallbackHandler() {
        int iCount = 0;
        public void processRow(ResultSet rs) throws SQLException {
            Object o = m_rowMapper.mapRow(rs, ++iCount);
            try {
                callback.handleRow(o, iCount);
            }
            catch (Exception e) {
                logger.error("", e);
                e.printStackTrace();
                throw new SQLException(e.toString());
            }
        }
    });
}

    /**
    .
    */
   protected void processRows(Connection conn) throws Exception
   {
      if (m_queryText==null) throw new Exception("No query");
      
      m_data = new KeyedScrollerPageImpl(m_pageSize+1);
      
      PreparedStatement prep = buildQuery(conn);

      try {

         ResultSet res = prep.executeQuery();
         
         /*
          * If we are scrolling to a prev page and there is not a page full of content
          * because we have hit start of data then instead display the first page of data.
          */
         if (m_pageSize>0 && m_reverse&&!res.absolute(m_pageSize)) {
            
            prep.close();
            for (int n=0; n<m_scrollKeys.size(); n++) {
               ScrollKey skey = (ScrollKey)m_scrollKeys.elementAt(n);
               skey.c_positionKey = skey.getImpliedBaseKey();
            }
            m_reverse = false;
            prep = buildQuery(conn);
            res = prep.executeQuery();
            
         }
         
         if (m_reverse) 
            processRowsReverse(res);
         else
            processRowsForward(res);

      }
      finally {      
         prep.close();
      }

   }

private void processRowsForward(ResultSet res) throws Exception
{
      if (!res.first()) {
         m_endOfData = true;
         return;    
      }
      
      Enumeration f = m_scrollKeys.elements();
      while (f.hasMoreElements()) {
         ScrollKey skey = (ScrollKey)f.nextElement();
         skey.setFirstKey(res);
      }

      int iCount = 0;      
      do {
         f = m_scrollKeys.elements();
         while (f.hasMoreElements()) {
            ScrollKey skey = (ScrollKey)f.nextElement();
            skey.setLastKey(res);
         }
         m_data.add(m_rowMapper.mapRow(res, ++iCount));
         
      } while (res.next() && (m_pageSize==0 || iCount<m_pageSize));
      
      if (res.isAfterLast()) m_endOfData = true;
}


/**
.
*/
protected IndexedScrollerPage processPageRows(Connection conn, int start, int end, boolean nQuery) throws Exception
{
    if (m_queryText==null) throw new Exception("No query");
    
    IndexedScrollerPage data = new IndexedScrollerPageImpl(m_pageSize+1);
    
    PreparedStatement prep = buildPagedQuery(conn, start, end, nQuery);

    try {

       ResultSet res = prep.executeQuery();
       int iCount = 0;
       while (res.next()) {
         data.add(m_rowMapper.mapRow(res, ++iCount));
      } 
    
       if (m_pageSize==0 || iCount<m_pageSize)
           m_endOfData = true; ////??????
       
       return data;
       
    } 
    finally {      
       prep.close();
    }
    
}

    public void setBeanClass(Class beanClass)
    {
        m_beanClass = beanClass;
        m_rowMapper = m_dao.getRowMapperForType(beanClass);
    }

    public void setBeanClass(Class beanClass, Object enclosingInstance)
    {
        m_beanClass = beanClass;
        m_rowMapper = m_dao.getRowMapperForType(beanClass, enclosingInstance);
    }


   /**
      .
   */
   private void processRowsReverse(ResultSet res) throws Exception
   {
         if (!res.last()) return;    
         
         Enumeration f = m_scrollKeys.elements();
         while (f.hasMoreElements()) {
            ScrollKey skey = (ScrollKey)f.nextElement();
            skey.setFirstKey(res);
         }
      
         int num = 0;
         
         do {
            
            f = m_scrollKeys.elements();
            while (f.hasMoreElements()) {
               ScrollKey skey = (ScrollKey)f.nextElement();
               skey.setLastKey(res);
            }
            m_data.add(m_rowMapper.mapRow(res, ++num));
            
         } while (res.previous());
         
   }

   private int getScrollKeyCount()
   {
      return m_scrollKeys.size();
   }

   /**
    * 
    */
   private static class ScrollKey 
   {
      String c_name;
      String c_rsname;
      String c_direction;
      String c_type;
      Object c_positionKey;
      boolean c_upperCase;
      Object c_baseKey;

      Object c_firstKey;
      Object c_lastKey;

      ScrollKey(String sName, String sName2, String sDir, String sType, boolean bUpperCase, Object oBase)
      {
         c_name = sName;
         c_rsname = sName2;
         c_direction = sDir;
         c_type = sType;
         c_baseKey = oBase;
         c_upperCase = bUpperCase;
      }

      void setFirstKey(ResultSet res) throws Exception
      {
         c_firstKey = res.getObject(c_rsname);
      }
      
      void setLastKey(ResultSet res) throws Exception
      {
         c_lastKey = res.getObject(c_rsname);
      }
      
      void setValue(Object o) throws Exception
      {
         if (o==null)
            c_positionKey = getImpliedBaseKey();
         else if (c_type.equals("text")) {
             c_positionKey = c_upperCase ? o.toString().toUpperCase() : o.toString() ;
         } 
         else if (c_type.equals("integer")||c_type.equals("int")) { 
             c_positionKey =  o instanceof Integer ? o : Integer.valueOf(o.toString());
         }
         else if (c_type.equals("timestamp")) {
             c_positionKey = o instanceof Timestamp ? o : Timestamp.valueOf(o.toString());
         }
      }
      
      Object getImpliedBaseKey() throws Exception
      {
         if (c_baseKey!=null) return c_baseKey;
         
         if (c_direction.equalsIgnoreCase("desc")) {
            if (c_type.equals("text")) return "\0xff";
            if (c_type.equals("integer")||c_type.equals("int")) return new Integer(Integer.MAX_VALUE);
            if (c_type.equals("timestamp")) return Timestamp.valueOf("9999-12-31 23:59:59.999");
         }
         else {
            if (c_type.equals("text")) return "";
            if (c_type.equals("integer")||c_type.equals("int")) return new Integer(0);
            if (c_type.equals("timestamp")) return new Timestamp(0);
         }
         
         throw new Exception("Invalid scroll key type "+c_type);
      }

      String getComparator(boolean bStrict, boolean bReverse) 
      {      
          String lt = bStrict ? " < ?" : " <= ?";
          String gt = bStrict ? " > ?" : " >= ?";
          if (c_direction.equals("asc")) return bReverse ? lt : gt;
             else return bReverse ? gt : lt ;
      }

   }
       
    public final int getPageSize()
    {
        return m_pageSize;
    }
    public final void setPageSize(int pageSize)
    {
        m_pageSize = pageSize;
    }
    
    public Class getBeanClass()
    {
        return m_beanClass;
    }

	/**
	 * @return the m_native
	 */
	public boolean isM_native() {
		return m_native;
	}

	/**
	 * @param m_native the m_native to set
	 */
	public void setM_native(boolean m_native) {
		this.m_native = m_native;
	}

	/**
	 * @return the m_totalRows
	 */
	public int getM_totalRows() {
		return m_totalRows;
	}

	/**
	 * @param rows the m_totalRows to set
	 */
	public void setM_totalRows(int rows) {
		m_totalRows = rows;
	}
}