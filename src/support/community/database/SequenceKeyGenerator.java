
package support.community.database;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * 
 */
public class SequenceKeyGenerator extends JdbcDaoSupport implements KeyGenerator
{
    private String tableName;
    
    public int nextSequence() throws Exception
    {
        String sql ="SELECT get_community_sequence('"+tableName+"')";
        return getJdbcTemplate().queryForInt(sql);
    }

    public final void setTableName(String tableName)
    {
    	this.tableName = tableName;
    }
}
