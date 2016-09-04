package support.community.database;

import org.acegisecurity.userdetails.*;

public interface CecSecuredEntity 
{   
    public boolean isReadAllowed(UserDetails user) throws Exception; 
    public boolean isWriteAllowed(UserDetails user) throws Exception;
}