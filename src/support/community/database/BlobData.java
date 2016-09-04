package support.community.database;

import java.io.*;
import java.sql.*;

/**
 * 
 */
public class BlobData
{
    private int length=0;
    private InputStream stream;
    private String contentType;

  public BlobData(Blob blob, int length, String contentType) throws Exception
  {
      this.length = length;
      this.contentType = contentType; 
      if (blob!=null) stream = blob.getBinaryStream();
  }

  public final String getContentType()
  {
    return contentType;
  }

    public boolean isEmpty()
    {
        return length==0;
    }
    
    public boolean isRelocated()
    {
        return length>0 && stream==null;
    }
    
    public final int getLength()
    {
        return length;
    }
    public final InputStream getStream()
    {
        return stream;
    }
}