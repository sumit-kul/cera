package support.community.util;


/**

*/
public class HtmlString 
{
	/*


	*/
	public static String valueOf(String s) 
	{
		StringBuffer buf = new StringBuffer(s.length()+64);
		for (int n=0; n<s.length(); n++) {
			char c = s.charAt(n);
			if (c=='\'') buf.append("&#39;");
			else if (c=='\"') buf.append("&#34;");
			else if (c=='&') buf.append("&amp;");
			else if (c=='<') buf.append("&lt;");
			else if (c=='>') buf.append("&gt;");
			else buf.append(c);
		} 
		return buf.toString();
	}
}
