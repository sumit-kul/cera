package support.community.util;
import java.net.URLDecoder;
import java.util.*;

import javax.servlet.jsp.*;

/**
  This is a helper class to convert a string to a javascript 'string literal'.
  ie we escape quotes and backslashes.
*/
public class StringFormat 
{
	/**
		Static method to perform conversion to a literal.
	*/
	public static String toStringLiteral(String s) 
	{
		StringBuffer buf = new StringBuffer(s.length()*2);
		for (int n=0; n<s.length(); n++) {
			char c = s.charAt(n);
			switch(c) {
				case '"':
				case '\\':
					buf.append('\\');
					//fall through!
				default:
					buf.append(c);
			}
		}
		return buf.toString();
	}
   
   
   /**
     * Returns the specified string converted to a format suitable for
     * HTML. All signle-quote, double-quote, greater-than, less-than and
     * ampersand characters are replaces with their corresponding HTML
     * Character Entity code.
     *
     * @param in the String to convert
     * @return the converted String
     */
    public static String toXmlString(String in) {
        StringBuffer out = new StringBuffer();
        for (int i = 0; in != null && i < in.length(); i++) {
            char c = in.charAt(i);
            if (c == '\'') {
                out.append("&#39;");
            }
            else if (c == '\"') {
                out.append("&#34;");
            }
            else if (c == '<') {
                out.append("&lt;");
            }
            else if (c == '>') {
                out.append("&gt;");
            }
            else if (c == '&') {
                out.append("&amp;");
            }
            else {
                out.append(c);
            }
        }
        return out.toString();
    }

   
    /*
       Escape a string.
     */
    static public String escape (String s) throws JspException
    {
        try {
            return java.net.URLEncoder.encode(s, "UTF-8");
        }
        catch (Exception x) {
            throw new JspException(x);
        }
    }
   
    /*
       Unescape a string.
     */
     static public String unescape(String s) throws JspException
     {
         try {
             return URLDecoder.decode(s, "UTF-8");
         }
         catch (Exception x) {
             throw new JspException(x);
         }
     }
  
  
    /*
        Replace line breaks with <br /> tags.
      */
      static public String breakToBr(String s) throws Exception
      {
            StringBuffer sbuf = new StringBuffer(s.length()+100);
        
            for (int n=0; n<s.length(); n++) {
                char c = s.charAt(n);
                if (c=='\n') sbuf.append("<br />\n");
                else if (c=='\r') ;
                else sbuf.append(c);
            }
                
            return sbuf.toString();
      }
      
      
      static public String[] explode(String data, String sep)
      {
          if (data==null||data.trim().length()==0) return new String[0];
          List<String> list = new ArrayList<String>(16);
          StringTokenizer tok = new StringTokenizer(data, sep);
          while (tok.hasMoreTokens()) list.add(tok.nextToken());
          String[] result = new String[list.size()];
          for (int i=0; i<result.length; i++) result[i] = (String)list.get(i);
          return result;
      }
      
      static public String implode(String[] data, String sep)
      {
          StringBuffer buf = new StringBuffer(256);
          for (int i=0; data!=null && i<data.length; i++) {
              if (buf.length()>0) buf.append(sep);
              buf.append(data[i]);
          }
          return buf.toString();
      }
      
      
}
