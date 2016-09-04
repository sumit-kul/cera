package com.era.community.customtag;

import support.community.framework.taglib.AbstractHtmlElementTag;
import support.community.framework.taglib.TagWriter;

import com.era.community.base.CommunityEraContext;

public class TaggingDisplayTag extends AbstractHtmlElementTag {

    private CommunityEraContext context;
	private String parentType;
    private String parentId;

	
	@Override
	protected int doStartTagInternal() throws Exception {
		
        TagWriter tagWriter = getTagWriter();
        
        CommunityEraContext context = getContext();
        	
        tagWriter.startTag("div");
        tagWriter.writeAttribute("class", "tag-set");
        tagWriter.startTag("form");
        tagWriter.writeAttribute("method", "post");
        tagWriter.writeAttribute("name", "tagSetForm");
        //tagWriter.writeAttribute("action", context.getCurrentCommunityUrl() + "/addTag.do");
        
        tagWriter.startTag("input");
        tagWriter.writeAttribute("type", "hidden");
        tagWriter.writeAttribute("name", "parentId");
        tagWriter.writeAttribute("class", "text");
        tagWriter.writeAttribute("value", getParentId());
        tagWriter.endTag();
        
        tagWriter.startTag("input");
        tagWriter.writeAttribute("type", "hidden");
        tagWriter.writeAttribute("name", "parentType");
        tagWriter.writeAttribute("value", getParentType());
        tagWriter.endTag();
        
        tagWriter.startTag("input");
        tagWriter.writeAttribute("type", "hidden");       
        tagWriter.writeAttribute("name", "backlink");
        tagWriter.writeAttribute("value", context.getRequestUrl());
        
        tagWriter.endTag();                       // end form
        	
		return EVAL_BODY_INCLUDE;
	}

        
    @Override
    protected int doEndTagInternal() throws Exception
    {
        TagWriter tagWriter = getTagWriter();
                
        // Output the 'Submit tags' button
        tagWriter.startTag("div");
        tagWriter.writeAttribute("style", "margin: 10px 0px;");
        tagWriter.writeAttribute("class", "left");
        
        tagWriter.startTag("a");
        tagWriter.writeAttribute("href", "javascript:void(0);");
        tagWriter.writeAttribute("onclick", "submitTagAction();");
        tagWriter.writeAttribute("class", "btnmain");
        tagWriter.writeAttribute("style", "float:left;");
        tagWriter.appendValue("Submit tags");
        tagWriter.endTag(); 
        
        tagWriter.endTag();       // End div tag     

        tagWriter.endTag();
        tagWriter.endTag(); 
        
        return EVAL_PAGE;
    }

    public CommunityEraContext getContext()
    {
        return context;
    }

    public void setContext(CommunityEraContext context)
    {
        this.context = context;
    }

    public String getParentType()
    {
        return parentType;
    }

    public void setParentType(String parentType)
    {
        this.parentType = parentType;
    }

    public String getParentId()
    {
        return parentId;
    }

    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }
}