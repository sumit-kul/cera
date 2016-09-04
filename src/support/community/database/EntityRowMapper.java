package support.community.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EntityRowMapper extends ColumnMapRowMapper
{
	protected Log logger = LogFactory.getLog(getClass()); 
	AbstractJdbcDaoSupport m_dao;

	EntityRowMapper(AbstractJdbcDaoSupport dao) 
	{
		m_dao = dao;
	}

	public Object mapRow(ResultSet res, int i) throws SQLException
	{
		logger.debug("begin map entity");

		Map map = (Map) super.mapRow(res, i);

		try {
			return m_dao.instantiateEntity(map); 
		}
		catch (SQLException x) {
			logger.error("", x);
			throw x;
		}
		catch (Exception x) {
			logger.error("Row mapper error", x);
			SQLException s = new SQLException(x.toString());
			s.initCause(x);
			throw s;
		}

	}

	protected String getColumnKey(String s)
	{
		logger.debug("Column name: "+s);
		s = s.toUpperCase();
		if (s.startsWith(m_dao.getColumnNamePrefix()))
			s = s.substring(m_dao.getColumnNamePrefix().length());
		logger.debug("Column key: "+s);
		return s;
	}

	protected Object getColumnValue(ResultSet res, int i)  throws SQLException
	{
		logger.debug("Column value:"+super.getColumnValue(res, i));
		return super.getColumnValue(res, i);
	}

	public String toString() 
	{
		return getClass().getName()+"::"+m_dao;
	}

}