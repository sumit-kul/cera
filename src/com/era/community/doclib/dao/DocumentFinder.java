package com.era.community.doclib.dao; 

import java.util.List;

import support.community.database.QueryScroller;

import com.era.community.communities.dao.Community;
import com.era.community.doclib.ui.dto.DocumentHeaderDto;
import com.era.community.media.ui.dto.MediaInfoDto;

public interface DocumentFinder extends com.era.community.doclib.dao.generated.DocumentFinderBase
{
    public QueryScroller listAllDocuments() throws Exception;
    public int getDocumentCount(int groupId) throws Exception;
    public int getDocumentCountForCommunity(Community comm) throws Exception;
    public QueryScroller getDocumentsForPoster(int posterId, String searchString) throws Exception;
    public QueryScroller getDocumentsFromOtherCommunity(int posterId, int communityId, String searchString) throws Exception;
    public QueryScroller getDocumentsFromMyConnection(int posterId, String searchString) throws Exception;
    public List getDocumentForMediaPanel(int libraryId, int max) throws Exception;
    public int getMediaCountForCommunity(Community comm) throws Exception;
    public QueryScroller getImagesForLibrary(int communityId, int folderId) throws Exception;
    public MediaInfoDto extraInfoForMedia(int mediaId, int currUserId) throws Exception;
    public List<Document> listImagesForFolder(int folderId) throws Exception;
    public List<Document> listImagesForCommunity(int communityId) throws Exception;
    public Document getLatestImageInFolder(int folderId) throws Exception;
    public void deleteAllPhotosForFolder(int folderId) throws Exception;
    public Document getCurrentProfileLogo(int libId) throws Exception;
    public Document getCurrentProfileBanner(int libId) throws Exception;
    public QueryScroller getFilesForLibraryAndFolder(int communityId, int folderId) throws Exception;
    public DocumentHeaderDto getDocumentForHeader(int docId) throws Exception;
    public int countPhotoListForHeader(int libraryId, int folderId, int userId, int docGroupNumber) throws Exception;
    public List<DocumentHeaderDto> getPhotoListForHeader(int libraryId, int folderId, int userId, int docGroupNumber) throws Exception;
}