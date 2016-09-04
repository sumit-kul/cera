package com.era.community.customtag;

import java.util.List;

import javax.servlet.jsp.JspException;

import support.community.framework.taglib.AbstractHtmlElementTag;
import support.community.framework.taglib.TagWriter;

import com.era.community.base.CommunityEraContext;
import com.era.community.tagging.ui.dto.TagDto;

public class TaggingSelectionTag extends AbstractHtmlElementTag  {

	private boolean showPicker = true;
	private boolean showLabel = true;
	private CommunityEraContext context;
	private int maxTags = 20;
	private String fieldLabel;
	private String tags;
	private String parentType;
	private String parentId;

	protected int doStartTagInternal() throws Exception {


		TagWriter tagWriter = getTagWriter();

		tagWriter.startTag("div");
		tagWriter.writeAttribute("class", "tagOutLine");
		if (this.getParentType().equals("Blog entry") || this.getParentType().equalsIgnoreCase("Library entry")) {
			tagWriter.writeAttribute("style", "width:600px;");
		}
		tagWriter.writeAttribute("id", "box-2fields");

		tagWriter.startTag("h4");
		tagWriter.writeAttribute("style", "margin-left:4px;");
		tagWriter.appendValue("Add tags for this "+this.getParentType());
		tagWriter.endTag();  //  </h4>

		tagWriter.startTag("p");
		if (this.getParentType().equalsIgnoreCase("community")) {
			tagWriter.appendValue("A tag is a keyword / one word label which members can assign to a community / any items in the community to categorize it. A tag make it easier to find related communities / items. Popular tags appear in larger text in the tag cloud.<br /><strong>Use space to separate tags. Special characters like &, #, @, etc are not allowed.</strong>");
		} else {
			tagWriter.appendValue("A tag is a keyword / one word label which members can assign to any items in the community to categorize it. A tag make it easier to find related items. Popular tags appear in larger text in the tag cloud.<br /><strong>Use space to separate tags. Special characters like &, #, @, etc are not allowed.</strong>");
		}

		tagWriter.endTag();      // </p>

		tagWriter.appendValue("<label for=\"txtKeywords\" class=\"tagLeft\">Add Tags:</label>\n");

		/* Write input tag */                
		tagWriter.startTag("input");      
		tagWriter.writeAttribute("id", "tags");
		tagWriter.writeAttribute("name", "tags");
		tagWriter.writeAttribute("type", "text");
		tagWriter.writeAttribute("class", "text");
		/* Not W3C standard, but there is no W3C standard for this at the current time! */
		tagWriter.writeAttribute("autocomplete", "off");
		tagWriter.writeAttribute("value", getTags().length()==0?"":getTags());              
		tagWriter.endTag();

		/* Write out tag picker */
		if (showPicker) {           
			renderTagPicker(tagWriter);
		}

		tagWriter.endTag();
		return EVAL_PAGE;
	}

	public void renderTagPicker(TagWriter tagWriter) throws JspException
	{
		/* Output links to add 10 most popular tags */
		try {
			List tags = null;
			if (getContext().getCurrentCommunity() == null) {
            	if (getParentType() != null && getParentId() != null && !"".equals(getParentId())) {
            		if (getParentType().equalsIgnoreCase("Blog entry")) {
            			tags = getContext().getCurrentUser().getMostPopularTagsForParent("BlogEntry", Integer.parseInt(getParentId()), getMaxTags());
					}else {
		                tags = getContext().getCurrentUser().getMostPopularTags(getMaxTags());
					}
				} else {
                tags = getContext().getCurrentUser().getMostPopularTags(getMaxTags());
				}
            }
            else {
                tags = getContext().getCurrentCommunity().getMostPopularTags(getMaxTags()); 
            }

			if (tags.size() > 0 ) {

				tagWriter.startTag("h4");
				if (this.getParentType().equalsIgnoreCase("community")) {
					tagWriter.appendValue("Popular tags assigned to other communities");
				} else if (this.getParentType().equalsIgnoreCase("Blog entry")){
					tagWriter.appendValue("Popular tags assigned to communities this entry published to");
				} else if (getContext().getCurrentCommunity() != null) {
					tagWriter.appendValue("Popular tags for '"+getContext().getCurrentCommunity().getName()+"' community");
				}
				tagWriter.endTag(); // </h4> 

				tagWriter.startTag("p");
				tagWriter.writeAttribute("class", "keywords");
				tagWriter.appendValue("Popular tags can also be reused. Just click to add to the tag list");
				tagWriter.endTag();  // </p>

				tagWriter.startTag("p");
				tagWriter.writeAttribute("class", "keywords");


				for (int i=0; i<tags.size(); i++) {
					String tagText = ((TagDto) tags.get(i)).getTagText(); 
					tagWriter.startTag("a");

					tagWriter.writeAttribute("onmouseover", "tip(this,&quot;Click to add tag &#39;" + tagText + "&#39; to the tag list&quot;)");
        			tagWriter.writeAttribute("href", "javascript:void(0)");
        			tagWriter.writeAttribute("onclick", "addTag('" +  tagText + "')");
					tagWriter.appendValue(tagText);

					tagWriter.endTag();
					tagWriter.appendValue(" ");
				}

				tagWriter.endTag();  // </p>

			}
		}
		catch (Exception ex) {
			/* Problem getting popular tags - do nothing */
		}


	}

	public boolean isShowLabel() {
		return showLabel;
	}

	public void setShowLabel(boolean showLabel) {
		this.showLabel = showLabel;
	}

	public boolean isShowPicker() {
		return showPicker;
	}

	public void setShowPicker(boolean showPicker) {
		this.showPicker = showPicker;
	}

	public int getMaxTags() {
		return maxTags;
	}

	public String getFieldLabel()
	{
		return fieldLabel;
	}

	public void setFieldLabel(String fieldLabel)
	{
		this.fieldLabel = fieldLabel;
	}

	public void setMaxTags(int maxTags) {
		this.maxTags = maxTags;
	}

	public String getTags()
	{
		return tags;
	}

	public void setTags(String tags)
	{
		this.tags = tags;
	}

	public CommunityEraContext getContext() {
		return context;
	}

	public void setContext(CommunityEraContext context) {
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

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
}