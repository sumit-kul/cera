
package support.community.lucene.parse.html;

import support.community.lucene.index.*;

import java.io.*;

import au.id.jericho.lib.html.*;

/*********************************************
 * parser for mime type text/html.
 * 
 *********************************************/

public class HtmlExtractor implements TextExtractor  
{
    public String extractText(InputStream is) throws Exception 
    {
      Source source=new Source(is);
      return extractText(source);
  }

    public String extractText(Reader reader) throws Exception 
    {
      Source source=new Source(reader);
      return extractText(source);
  }

  private String extractText(Source source) throws Exception 
  {
    source.setLogWriter(new OutputStreamWriter(System.out)); 
    source.fullSequentialParse();

    String title=getTitle(source);
    String description=getMetaValue(source,"description");
    String keywords=getMetaValue(source,"keywords");

    /*
    List linkElements=source.findAllElements(HTMLElementName.A);
    for (Iterator i=linkElements.iterator(); i.hasNext();) {
        Element linkElement=(Element)i.next();
        String href=linkElement.getAttributeValue("href");
        if (href==null) continue;
        // A element can contain other tags so need to extract the text from it:
        String label=linkElement.getContent().extractText();
    } 
    */

    Element bodyElement = source.findNextElement(0,HTMLElementName.BODY);
    Segment contentSegment = (bodyElement==null) ? source : bodyElement.getContent();
    return contentSegment.extractText(true);
}

private static String getTitle(Source source) 
{
    Element titleElement=source.findNextElement(0,HTMLElementName.TITLE);
    if (titleElement==null) return null;
    // TITLE element never contains other tags so just decode it collapsing whitespace:
    return CharacterReference.decodeCollapseWhiteSpace(titleElement.getContent());
}

private static String getMetaValue(Source source, String key) 
{
    for (int pos=0; pos<source.length();) {
        StartTag startTag=source.findNextStartTag(pos,"name",key,false);
        if (startTag==null) return null;
        if (startTag.getName()==HTMLElementName.META)
            return startTag.getAttributeValue("content"); // Attribute values are automatically decoded
        pos=startTag.getEnd();
    }
    return null;
}

}
