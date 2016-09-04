package com.era.community.doclib.dao; 

import java.util.List;

import com.era.community.communities.dao.Community;

public interface DocumentLibraryFinder extends com.era.community.doclib.dao.generated.DocumentLibraryFinderBase
{
	public DocumentLibrary getDocumentLibraryForCommunity(Community comm) throws Exception;
	public DocumentLibrary getDocumentLibraryForCommunityId(int commId) throws Exception;
	public Community getCommunityForDoclib(DocumentLibrary doclib) throws Exception;
	public List getLibraryThemeOptionList(int communityId) throws Exception;
}