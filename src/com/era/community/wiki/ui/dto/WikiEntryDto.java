package com.era.community.wiki.ui.dto; 

import java.util.List;

import com.era.community.wiki.dao.WikiEntry;
import com.era.community.wiki.dao.WikiEntryFinder;

public class WikiEntryDto extends com.era.community.wiki.dao.generated.WikiEntryEntity 
{    
	private String Body;
	private String LastEditedBy;
	private String photoPresent;
	private String logoPresent;
	private List wikiEntrySections;

	private WikiEntryFinder wikiEntryFinder;        

	public boolean isCurrentVersion()
	{
		if (getEntrySequence() == Integer.MAX_VALUE) {
			return true;
		}
		else {
			return false;
		}      
	}


	public void setWikiEntryFinder(WikiEntryFinder wikiEntryFinder) throws Exception
	{
		this.wikiEntryFinder = wikiEntryFinder;
		WikiEntry entry;

		/*if (getRelatedId1() != 0) {
			entry = wikiEntryFinder.getWikiEntryForId(getRelatedId1());
			setRelatedEntryLink1("/wiki/wikiDisplay.do?entryId=" + getRelatedId1());
			setRelatedEntryTitle1(entry.getTitle());
		}*/
	}

	public final String getBody()
	{
		return Body;
	}

	public final void setBody(String body)
	{
		Body = body;
	}

	public final String getLastEditedBy()
	{
		return LastEditedBy;
	}

	public final void setLastEditedBy(String lastEditedBy)
	{
		LastEditedBy = lastEditedBy;
	}

	public String getPhotoPresent() {
		return photoPresent;
	}

	public void setPhotoPresent(String photoPresent) {
		this.photoPresent = photoPresent;
	}


	public List getWikiEntrySections() {
		return wikiEntrySections;
	}


	public void setWikiEntrySections(List wikiEntrySections) {
		this.wikiEntrySections = wikiEntrySections;
	}


	public String getLogoPresent() {
		return logoPresent;
	}


	public void setLogoPresent(String logoPresent) {
		this.logoPresent = logoPresent;
	}
}