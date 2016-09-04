package support.community.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.PreparedStatementSetter;

/**
 * 
 * 
 */
public class PreparedStatmentSetter implements PreparedStatementSetter
{
    private Object[] values;
    
    public PreparedStatmentSetter(Object[] values)
    {
        this.values = values;
    }
    
    /*
     * 
     */
    public void setValues(PreparedStatement prep) throws SQLException
    {

        
    }

}
