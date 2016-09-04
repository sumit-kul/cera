
package support.community.lucene.index;

import java.io.*;

/**
 * 
 */
public interface TextExtractor
{
    public String extractText(InputStream is) throws Exception;
}
