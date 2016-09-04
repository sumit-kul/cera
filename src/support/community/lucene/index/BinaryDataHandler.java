package support.community.lucene.index;

import java.io.*;
import java.util.*;

import org.apache.commons.logging.*;

public class BinaryDataHandler
{
    protected Log logger = LogFactory.getLog(getClass());
    
    private Map textExtractors;

    public String extractText(InputStream stream, String contentType) throws Exception
    {
        // Putting this test in to reduce log file size
        if (contentType.contains("vnd.ms-powerpoint") || contentType.contains("vnd.ms-excel") || contentType.contains("octet-stream"))
                return "";
        
        TextExtractor e = (TextExtractor)textExtractors.get(contentType);
        if (e==null) { 
            logger.warn("No handler for type ["+contentType+"]");                    //application/vnd.ms-powerpoint
            return "";
        }
        try {
            return e.extractText(stream);
        }
        catch (Exception x) {
            logger.info(x.toString());
 
            return "";
        }
    }
    
    public boolean supports(String contentType) throws Exception
    {
        TextExtractor e = (TextExtractor)textExtractors.get(contentType);
        return e!=null;
    }
    
    public final void setTextExtractors(Map textExtractors)
    {
        this.textExtractors = textExtractors;
    }
}