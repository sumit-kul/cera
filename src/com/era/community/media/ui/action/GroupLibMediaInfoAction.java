package com.era.community.media.ui.action;

import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.database.IndexedScrollerPage;
import support.community.database.QueryScroller;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.doclib.dao.Document;
import com.era.community.doclib.dao.DocumentFinder;
import com.era.community.doclib.dao.Folder;
import com.era.community.doclib.dao.FolderFinder;
import com.era.community.doclib.ui.dto.DocumentDto;
import com.era.community.doclib.ui.dto.FolderDto;
import com.era.community.pers.dao.User;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 *  @spring.bean name="/pers/showGroupLibMediaInfo.ajx" 
 */
public class GroupLibMediaInfoAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager; 
	private DocumentFinder documentFinder;
	private FolderFinder folderFinder;
	private CommunityFinder communityFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		Command command = (Command)data; 
		User currUser = context.getCurrentUser();
		HttpServletResponse resp = context.getResponse();
		if (command.getType() != null && "album".equalsIgnoreCase(command.getType())) {
			Folder folder = folderFinder.getFolderForId(command.getGroupMediaId());
			List<FolderDto> otherFolders = folderFinder.listOtherFoldersForLibrary(command.getLibraryId(), command.getGroupMediaId());
			List<Document> photos = documentFinder.listImagesForFolder(command.getGroupMediaId());
			if (folder != null) {
				JSONObject json = toJsonString(folder, photos, otherFolders, currUser, command.getCommunityId());
				String jsonString = json.serialize();
				resp.setContentType("text/json");
				Writer out = resp.getWriter();
				out.write(jsonString);
				out.close();
			}
		}

		if (command.getType() != null && "folderOnly".equalsIgnoreCase(command.getType())) {
			JSONObject json = new JSONObject();
			FolderDto folder = folderFinder.extraInfoForFolder(command.getGroupMediaId());
			json.put("title", folder.getTitle());
			json.put("description", folder.getDescription());
			json.put("ownerId", folder.getOwnerId());
			json.put("privacyLevel", folder.getPrivacyLevel());
			json.put("fileCount", folder.getPhotoCount());
			json.put("filePageCount", folder.getPhotoCount() == 0 ? 0 : ((int)((folder.getPhotoCount()-1)/2))+1);
			json.put("createdOn", folder.getCreatedOn());
			json.put("posterName", folder.getFirstName()+" "+folder.getLastName());
			json.put("lastUpdate", getlastUpdateOn(folder.getUpdatedOn()));
			String jsonString = json.serialize();
			resp.setContentType("text/json");
			Writer out = resp.getWriter();
			out.write(jsonString);
			out.close();
			return null;
		}
		
		if (command.getType() != null && "folder".equalsIgnoreCase(command.getType())) {
			String pageNum = context.getRequest().getParameter("jFile");
			int pNumber = 0;
			if (pageNum != null && !"".equals(pageNum)) {
				pNumber = Integer.parseInt(pageNum);
			}
			JSONObject json = null;
			QueryScroller scroller = documentFinder.getFilesForLibraryAndFolder(command.getCommunityId(), command.getGroupMediaId());
			scroller.setBeanClass(RowBean.class, this);
			if (pageNum != null) {
				if (pNumber > 0) {
					scroller.setPageSize(18);
					IndexedScrollerPage page = scroller.readPage(pNumber);
					json = page.toJsonStringForFiles(currUser != null ? currUser.getId(): 0);
				}
			}
			String jsonString = json.serialize();
			resp.setContentType("text/json");
			Writer out = resp.getWriter();
			out.write(jsonString);
			out.close();
			return null;
		}

		if (command.getType() != null && "albumOnly".equalsIgnoreCase(command.getType())) {
			Folder folder = folderFinder.getFolderForId(command.getGroupMediaId());
			if (folder != null) {
				JSONObject json = toJsonString(folder, currUser, command.getCommunityId());
				String jsonString = json.serialize();
				resp.setContentType("text/json");
				Writer out = resp.getWriter();
				out.write(jsonString);
				out.close();
			}
		}
		return null;
	}

	private JSONObject toJsonString(Folder folder, List<Document> folderPhotos, List<FolderDto> folders, User currUser, int commId) throws Exception
	{
		JSONObject json = new JSONObject();
		json.put("albumId", folder.getId());
		json.put("title", folder.getTitle());
		json.put("description", folder.getDescription());
		json.put("privacyLevel", folder.getPrivacyLevel());
		json.put("coverPhotoId", folder.getCoverPhotoId());
		json.put("isAlbumEditAllowed", isAlbumEditAllowed(currUser, folder, commId));
		JSONArray photos = new JSONArray();
		for (Document photo : folderPhotos) {
			JSONObject row = new JSONObject();
			row.put("id", photo.getId());
			row.put("title", photo.getTitle());
			row.put("description", photo.getDescription());
			row.put("width", photo.getWidth());
			row.put("height", photo.getHeight());
			row.put("ownerId", photo.getPosterId());
			row.put("albumId", photo.getFolderId());
			row.put("privacyLevel", photo.getPrivacyLevel());
			row.put("isEditAllowed", isPhotoEditAllowed(currUser, photo));
			row.put("folderId", photo.getFolderId());
			row.put("moveToAlbumAllowed", isMoveToFolderAllowed(photo.getFolderId()));
			photos.add(row);
		}
		json.put("photos", photos);
		JSONArray othAlbms = new JSONArray();
		for (FolderDto otherFolder : folders) {
			JSONObject row = new JSONObject();
			row.put("id", otherFolder.getId());
			row.put("title", otherFolder.getTitle());
			row.put("description", otherFolder.getDescription());
			row.put("ownerId", otherFolder.getOwnerId());
			row.put("photoCount", otherFolder.getPhotoCount());
			row.put("coverPhotoId", otherFolder.getCoverPhotoId());
			row.put("privacyLevel", otherFolder.getPrivacyLevel());
			row.put("modified", otherFolder.getModified().toString());
			othAlbms.add(row);
		}
		json.put("otherAlbums", othAlbms);
		return json;
	}
	
	private JSONObject toJsonString(Folder folder, User currUser, int commId) throws Exception
	{
		JSONObject json = new JSONObject();
		json.put("albumId", folder.getId());
		json.put("title", folder.getTitle());
		json.put("description", folder.getDescription());
		json.put("privacyLevel", folder.getPrivacyLevel());
		json.put("isAlbumEditAllowed", isAlbumEditAllowed(currUser, folder, commId));
		return json;
	}

	private boolean isMoveToFolderAllowed(int folderId) {
		boolean move = false;
		if (folderId > 0) {
			try {
				Folder folder = folderFinder.getFolderForId(folderId);
				if (!(folder.getBannerFolder() == 1 || folder.getProfileFolder() == 1)) {
					move = true;
				}
			} catch (Exception e) {
			}
		} else {
			move = true;
		}
		return move;
	}

	private boolean isAlbumEditAllowed(User current, Folder folder, int commId) throws Exception {
		if (current != null && current.getId() == folder.getOwnerId()){
			return true;		
		}
		try {
			Community community = communityFinder.getCommunityForId(commId);
			if (current != null && community.isAdminMember(current)) {
				return true;
			}
		} catch (ElementNotFoundException e) {
			return false;
		}
		return false;
	}

	private boolean isPhotoEditAllowed(User current, Document photo) {
		if (current != null && current.getId() == photo.getPosterId()){
			return true;		
		}
		return false;
	}
	
	public String getlastUpdateOn(String updateOn) throws Exception
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy HH:mm a");
		try {
			Date date = formatter.parse(updateOn);
			return fmt2.format(date);
		} catch (Exception e) {
			return updateOn;
		}
	}

	public class Command extends CommandBeanImpl implements CommandBean
	{
		private int groupMediaId;
		private int libraryId;
		private int communityId;
		private String type;

		public int getGroupMediaId() {
			return groupMediaId;
		}
		public void setGroupMediaId(int groupMediaId) {
			this.groupMediaId = groupMediaId;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public int getLibraryId() {
			return libraryId;
		}
		public void setLibraryId(int libraryId) {
			this.libraryId = libraryId;
		}
		public int getCommunityId() {
			return communityId;
		}
		public void setCommunityId(int communityId) {
			this.communityId = communityId;
		}
	}
	
	public class RowBean extends DocumentDto
	{
		public boolean isMoveToFolderAllowed(int folderId) {
			boolean move = false;
			if (folderId > 0) {
				try {
					Folder folder = folderFinder.getFolderForId(folderId);
					if (!(folder.getBannerFolder() == 1 || folder.getProfileFolder() == 1)) {
						move = true;
					}
				} catch (Exception e) {
				}
			} else {
				move = true;
			}
			return move;
		}
		
		public String getIconType() {
	    	if (getFileContentType() != null) {
	    		if (getFileContentType().equalsIgnoreCase("application/vnd.ms-excel") || getFileContentType().equalsIgnoreCase("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
	        		return "xls";
	    		} else if (getFileContentType().equalsIgnoreCase("application/x-tar") || getFileContentType().equalsIgnoreCase("application/zip")) {
	    			return "zip";
	    		} else if (getFileContentType().equalsIgnoreCase("application/pdf")) {
	    			return "pdf";
	    		} else if (getFileContentType().equalsIgnoreCase("application/xml")) {
	    			return "xml";
	    		} else if (getFileContentType().equalsIgnoreCase("image/jpeg")) {
	    			return "img";
	    		} else if (getFileContentType().equalsIgnoreCase("text/richtext") || getFileContentType().equalsIgnoreCase("application/msword") || getFileContentType().equalsIgnoreCase("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
	    			return "doc";
	    		} else if (getFileContentType().equalsIgnoreCase("application/vnd.ms-powerpoint") || getFileContentType().equalsIgnoreCase("application/vnd.openxmlformats-officedocument.presentationml.presentation")) {
	    			return "ppt";
	    		} 
	    		return "file";
			} else {
				return "file";
			}
	    }
	}

	public CommunityEraContextManager getContextManager() {
		return contextManager;
	}

	public void setContextManager(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}

	public void setDocumentFinder(DocumentFinder documentFinder) {
		this.documentFinder = documentFinder;
	}

	public void setFolderFinder(FolderFinder folderFinder) {
		this.folderFinder = folderFinder;
	}

	public void setCommunityFinder(CommunityFinder communityFinder) {
		this.communityFinder = communityFinder;
	}
}