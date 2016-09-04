package support.community.database;

import java.sql.*;
import java.util.*;
import org.springframework.jdbc.support.JdbcUtils;


/**
 * RowMapper implementation that creates a Map
 * for each row, representing all columns as key-value pairs: one
 * entry for each column, with the column name as key.
 * This is a variant of the equivalent Spring class that adds checking
 * for duplicate column names in the result set data. 
 */
public class ColumnMapRowMapper extends org.springframework.jdbc.core.ColumnMapRowMapper 
{

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException 
	{
		// if (rowNum==1) { issue with mysql
		if (rowNum==0) {
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			List<String> columnNames = new ArrayList<String>(columnCount);
			for (int i = 1; i <= columnCount; i++) {
				String key = rsmd.getColumnLabel(i).toUpperCase();
				if ("".equals(key.trim())) {
					key = rsmd.getColumnName(i).toUpperCase();
				}
				if (columnNames.contains(key))
					throw new SQLException("Duplicate column name ["+key+"] found in result set");
				columnNames.add(key);
			}
		}
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		Map mapOfColValues = createColumnMap(columnCount);
		for(int i = 1; i <= columnCount; i++)
		{
			String name = rsmd.getColumnLabel(i).toUpperCase();
			if ("".equals(name.trim())) {
				name = rsmd.getColumnName(i).toUpperCase();
			}
			String key = getColumnKey(name);
			Object obj = getColumnValue(rs, i);
			mapOfColValues.put(key, obj);
		} 
		return mapOfColValues;
	}

}
