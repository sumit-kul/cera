package com.era.community.tagging.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TreeMap;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CecAbstractEntity;
import com.era.community.tagging.dao.generated.TagEntity;
import com.era.community.tagging.ui.dto.TagDto;
/**
 * Superclass for entities that are tagged. Contains basic methods for tag 
 * storage and retrieval.
 */
public abstract class TaggedEntity extends CecAbstractEntity
{
	protected TagFinder tagFinder; 

	public void clearAllTags() throws Exception 
	{
		tagFinder.clearTagsForParentId(getId());
	}

	public void clearTagsForUser(int userId) throws Exception 
	{
		tagFinder.clearTagsForParentIdUserId(getId(), userId);
	}

	public void setTags(String tagString) throws Exception
	{		

		CommunityEraContext context = getCommunityEraContext();

		clearTagsForUser(context.getCurrentUser().getId());            // Clear the tags for this entity and user

		/* Parse tag string */
		StringTokenizer st = new StringTokenizer(tagString, " ");

		while (st.hasMoreTokens()) {
			/* Create new tag */
			String tag = st.nextToken().trim().toLowerCase();
			Tag newTag = tagFinder.newTag();
			if (context.getCurrentCommunity() != null) {
				newTag.setCommunityId(context.getCurrentCommunity().getId());
			}
			newTag.setTagText(tag);			
			newTag.setPosterId(context.getCurrentUser().getId());
			newTag.setParentId(getId());
			newTag.setParentType(this.getClass().getSimpleName());  // e.g. WikiEntry, Document, BlogEntry
			newTag.update();			
		}			

	}

	public TreeMap getPopularTags() throws Exception 
	{
		TreeMap<String, TagDto> tm = new TreeMap<String, TagDto>();       
		List tagList = tagFinder.getTagsForParentIdByPopularity(getId(), 15);        
		int maxPopularity  = 0;
		int minPopularity = 0;
		int noOfSets = 5;

		for (int i=0; i<tagList.size(); i++) {
			TagDto tag = (TagDto) tagList.get(i);
			if (tag.getCount() > maxPopularity) {
				maxPopularity = tag.getCount().intValue();
			}
			if (tag.getCount() < minPopularity || minPopularity == 0) {
				minPopularity = tag.getCount().intValue();
			}                       
		}

		int setSize = (int)Math.round( (double)(maxPopularity - minPopularity) / noOfSets);
		if (setSize == 0)   {
			setSize = 1;
		}

		for (int i=0; i<tagList.size(); i++) {
			TagDto tag = (TagDto) tagList.get(i);
			int cloudSet = Math.min(noOfSets,((tag.getCount().intValue() / setSize) - minPopularity) + 1);
			tag.setCloudSet(cloudSet);
			tm.put(tag.getTagText(), tag);
		}       
		return tm;
	}

	public String[] getTagsAsArray() throws Exception 
	{
		CommunityEraContext context = getCommunityEraContext();
		List tagList;
		if (context.getCurrentUser() != null) {
			tagList = tagFinder.getTagsForParentIdUserId(getId(), context.getCurrentUser().getId());
		} else {
			tagList = tagFinder.getTagsForParentId(getId());
		}

		String[] retVal = new String[tagList.size()];
		for (int i=0; i<tagList.size(); i++) {
			retVal[i] = ((TagDto) tagList.get(i)).getTagText();
		}
		return retVal;
	}
	
	public String getKeywords() throws Exception 
	{
		CommunityEraContext context = getCommunityEraContext();
		List tagList;
		String keywords = "";
		if (context.getCurrentUser() != null) {
			tagList = tagFinder.getTagsForParentIdUserId(getId(), context.getCurrentUser().getId());
		} else {
			tagList = tagFinder.getTagsForParentId(getId());
		}

		for (Iterator iterator = tagList.iterator(); iterator.hasNext();) {
			TagDto tb = (TagDto) iterator.next();
			String tag = tb.getTagText();
			keywords += tag;
			if (iterator.hasNext())keywords += " , ";
		}
		return keywords;
	}

	public String getTags() throws Exception 
	{
		String[] tags = getTagsAsArray();
		StringBuffer tagBuf = new StringBuffer();        
		List<String> existing = new ArrayList<String>();                

		for (int i=0; i<tags.length; i++) {
			String tag = tags[i];
			if (!existing.contains(tag)) {
				if (tagBuf.length() > 0) tagBuf.append(" ");
				tagBuf.append(tag);
				existing.add(tag);    
			}            		
		}
		return tagBuf.toString();
	}

	/* Used by Spring to inject reference */
	public void setTagFinder(TagFinder tagFinder) throws Exception 
	{
		this.tagFinder = tagFinder;
	}			

	public void delete() throws Exception
	{
		clearAllTags();
	}

}
