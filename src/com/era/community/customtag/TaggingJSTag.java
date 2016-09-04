package com.era.community.customtag;

import support.community.framework.taglib.AbstractHtmlElementTag;
import support.community.framework.taglib.TagWriter;

import com.era.community.base.CommunityEraContext;

public class TaggingJSTag extends AbstractHtmlElementTag {	
	
	private CommunityEraContext context;
	
	protected int doStartTagInternal() throws Exception {
		TagWriter tagWriter = getTagWriter();
		tagWriter.startTag("script");	
		tagWriter.writeAttribute("language", "javascript");
		tagWriter.writeAttribute("type", "text/javascript");
		tagWriter.writeAttribute("src", "js/autocomplete/actb.js");
		tagWriter.forceBlock();		
		tagWriter.endTag();
		tagWriter.startTag("script");	
		tagWriter.writeAttribute("language", "javascript");
		tagWriter.writeAttribute("type", "text/javascript");
		tagWriter.writeAttribute("src", "js/autocomplete/common.js");
		tagWriter.forceBlock();
		tagWriter.endTag();
		tagWriter.startTag("script");	
		tagWriter.writeAttribute("language", "javascript");
		tagWriter.writeAttribute("type", "text/javascript");
		
		if (getContext().getCurrentCommunity() == null) {
			tagWriter.appendValue("\n" + getContext().getCurrentUser().getTagTypeAheadJSArray() + "\n");	
		}
		else {
			tagWriter.appendValue("\n" + getContext().getCurrentCommunity().getTagTypeAheadJSArray() + "\n");	
		}			
		
		tagWriter.appendValue(" function initTypeahead() " + "\n" 
												+ "{" + "\n"
												+ "	var acomp = new actb(document.getElementById('tags'), tagarray);" + "\n"
												+ "}" + "\n"
												+ "" + "\n"
												+ "window.onload=initTypeahead;" + "\n"	
												+ "" + "\n"
												+ "function addTag(tagName)" + "\n" 
												+ "{" + "\n"
												+ "	var toElm = document.getElementById('tags');" + "\n"
												+ "	var toArray = toElm.value.split(' ');" + "\n"
												+ "	var isAlreadyPresent = false;" + "\n"
												+ "	for (i=0; i<toArray.length; i++) { " + "\n"						
												+ "		if (toArray[i].replace( /^\\s+/g, \"\" ) == tagName) {" + "\n"
												+ "			isAlreadyPresent = true;" + "\n"
												+ "		}"  + "\n"
												+ "	}" + "\n"
												+ "	if (!isAlreadyPresent) {" + "\n"
												+ "		var sep = '';" + "\n"
												+ "		if (toElm.value != '') sep = ' ';" + "\n"
												+ "		toElm.value = toElm.value + sep + tagName;" + "\n"
												+ "	}" + "\n"
												+ "}" + "\n"); 
		
		tagWriter.endTag();
		tagWriter.startTag("style");
		tagWriter.writeAttribute("type", "text/css");
		tagWriter.appendValue("		#tat_table {"
													+ "	width: auto; "
													+ "}");		
		tagWriter.endTag();
				
		return EVAL_PAGE;
	}

	public CommunityEraContext getContext() {
		return context;
	}

	public void setContext(CommunityEraContext context) {
		this.context = context;
	}			
}