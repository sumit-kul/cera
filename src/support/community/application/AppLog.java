package support.community.application;

import org.apache.commons.logging.*;

/**

*/
public class AppLog 
{
	static Log logger = LogFactory.getLog("support.community.applicationLog");
  
	public static void write( String s ) 
	{
		logger.info(s);
	}

	public static void write( Throwable t ) 
	{
        logger.error(t.toString(), t);
     }

}
