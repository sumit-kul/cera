package com.era.community.customtag;

import support.community.framework.taglib.AbstractHtmlElementTag;
import support.community.framework.taglib.TagWriter;

import com.era.community.base.CommunityEraContext;

public class OrganisationSelectionTag extends AbstractHtmlElementTag  
{
    private boolean showLabel = true;
    private CommunityEraContext context;   
    private String fieldLabel;
    private String organisation;

    protected int doStartTagInternal() throws Exception {

        TagWriter tagWriter = getTagWriter();

        tagWriter.startTag("p");
        tagWriter.appendValue("<label for=\"organisation\" class=\"left\">Organisation<sup>*</sup></label>\n");

        /* Write input tag */                
        tagWriter.startTag("input");      
        tagWriter.writeAttribute("id", "organisation");
        tagWriter.writeAttribute("name", "organisation");
        tagWriter.writeAttribute("type", "text");
        tagWriter.writeAttribute("class", "text");
        /* Not W3C standard, but there is no W3C standard for this at the current time! */
        tagWriter.writeAttribute("autocomplete", "off");
        tagWriter.writeAttribute("value", getOrganisation().length()==0?"":getOrganisation());              
        tagWriter.endTag();
        tagWriter.endTag();  // </p>

        return EVAL_PAGE;
    }

    public boolean isShowLabel() {
        return showLabel;
    }

    public void setShowLabel(boolean showLabel) {
        this.showLabel = showLabel;
    }

    public String getFieldLabel()
    {
        return fieldLabel;
    }

    public void setFieldLabel(String fieldLabel)
    {
        this.fieldLabel = fieldLabel;
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
}
