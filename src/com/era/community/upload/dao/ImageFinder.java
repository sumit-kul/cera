package com.era.community.upload.dao; 

import java.util.List;

public interface ImageFinder extends com.era.community.upload.dao.generated.ImageFinderBase
{
	public void deleteImagesForWikiEntry(int wikiEntryId) throws Exception;
	public void deleteImagesForAllWikiEntryVersions(int entryId) throws Exception;
	public Image getImageForItemType(int itemId, String type) throws Exception;
	public void deleteImagesForItemType(int itemId, String type) throws Exception;
	public void markImagesForDeletion(int itemId, String type) throws Exception;
	public int countImageForItemType(int itemId, String type) throws Exception;
	
	public List selectImagesForBlogEntry() throws Exception;
	public List selectImagesForWikiEntry() throws Exception;
}