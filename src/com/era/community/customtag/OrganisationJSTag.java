package com.era.community.customtag;

import support.community.framework.taglib.AbstractHtmlElementTag;
import support.community.framework.taglib.TagWriter;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.ReferenceData;

public class OrganisationJSTag extends AbstractHtmlElementTag {	
	
	private CommunityEraContext context;
    private String organisation;
    private ReferenceData referenceData;
	
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
		if (getContext().getCurrentUser() != null) {
            tagWriter.appendValue("\n" + getContext().getCurrentUser().getOrganisationTypeAheadJSArray(getOrganisation()) + "\n");
        } 
        else if (getReferenceData() != null) {
            tagWriter.appendValue("\n" + getReferenceData().getOrganisationTypeAheadJSArray(getOrganisation()) + "\n");      
        }       
		
		tagWriter.appendValue(" function initTypeahead() " + "\n" 
												+ "{" + "\n"
												+ "	var acomp = new actb(document.getElementById('organisation'), organisationarray);" + "\n"
												+ "}" + "\n"
												+ "" + "\n"
												+ "window.onload=initTypeahead;" + "\n"	
												+ "" + "\n"
												+ "function addTag(tagName)" + "\n" 
												+ "{" + "\n"
												+ "	var toElm = document.getElementById('organisation');" + "\n"
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
												+ "		toElm.value = sep + tagName;" + "\n"
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

    public String getOrganisation()
    {
        return organisation;
    }

    public void setOrganisation(String organisation)
    {
        this.organisation = organisation;
    }
    
	public CommunityEraContext getContext() {
		return context;
	}

	public void setContext(CommunityEraContext context) {
		this.context = context;
	}	
    
    public ReferenceData getReferenceData() {
        return referenceData;
    }

    public void setReferenceData(ReferenceData referenceData) {
        this.referenceData = referenceData;
    }   
}
