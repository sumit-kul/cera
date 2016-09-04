package com.era.community.media.ui.action;

import java.io.Writer;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.doclib.dao.Document;
import com.era.community.doclib.dao.DocumentFinder;
import com.era.community.doclib.dao.Folder;
import com.era.community.doclib.dao.FolderFinder;
import com.era.community.media.dao.Album;
import com.era.community.media.dao.AlbumFinder;
import com.era.community.media.dao.Photo;
import com.era.community.media.dao.PhotoFinder;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 *  @spring.bean name="/pers/showMediaList.ajx" 
 */
public class MediaListAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager; 
	private PhotoFinder photoFinder;
	private DocumentFinder documentFinder;
	private FolderFinder folderFinder;
	private AlbumFinder albumFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command)data; 
		CommunityEraContext context = contextManager.getContext();
		HttpServletResponse resp = context.getResponse();
		try {
			if (cmd.getOwnerId() > 0) {
				List<Photo> items = photoFinder.listPhotosForUser(cmd.getOwnerId());
				int rowCount = items.size();
				int pgCount = rowCount==0 ? 0 : ((int)((rowCount-1)/10))+2;
				JSONObject json = toJsonString(items);
				json.put("pgCount", pgCount);
				String jsonString = json.serialize();
				resp.setContentType("text/json");
				Writer out = resp.getWriter();
				out.write(jsonString);
				out.close();
			}
			if (cmd.getCommunityId() > 0) {
				List<Document> items = documentFinder.listImagesForCommunity(cmd.getCommunityId());
				int rowCount = items.size();
				int pgCount = rowCount==0 ? 0 : ((int)((rowCount-1)/10))+2;
				JSONObject json = toJsonStringDocument(items);
				json.put("pgCount", pgCount);
				String jsonString = json.serialize();
				resp.setContentType("text/json");
				Writer out = resp.getWriter();
				out.write(jsonString);
				out.close();
			}
		} catch (Exception e) {
		}
		return null;
	}

	private JSONObject toJsonString(List<Photo> photos) throws Exception
	{
		JSONObject json = new JSONObject();
		JSONArray data = new JSONArray();
		for (Photo photo : photos) {
			JSONObject row = new JSONObject();
			row.put("id", photo.getId());
			row.put("title", photo.getTitle());
			row.put("width", photo.getWidth());
			row.put("height", photo.getHeight());
			row.put("description", photo.getDescription());
			row.put("userId", photo.getUserId());
			row.put("folderId", photo.getAlbumId());
			row.put("moveToAlbumAllowed", isMoveToFolderAllowed(photo.getAlbumId()));
			data.add(row);
		}
		json.put("aData", data);
		return json;
	}

	private JSONObject toJsonStringDocument(List<Document> photos) throws Exception
	{
		JSONObject json = new JSONObject();
		JSONArray data = new JSONArray();
		for (Document photo : photos) {
			JSONObject row = new JSONObject();
			row.put("id", photo.getId());
			row.put("title", photo.getTitle());
			row.put("width", photo.getWidth());
			row.put("height", photo.getHeight());
			row.put("description", photo.getDescription());
			row.put("userId", photo.getPosterId());
			row.put("folderId", photo.getFolderId());
			row.put("moveToAlbumAllowed", isMoveToFolderAllowed(photo.getFolderId()));
			data.add(row);
		}
		json.put("aData", data);
		return json;
	}
	
	private boolean isMoveToAlbumAllowed(int albumId) {
		boolean move = false;
		if (albumId > 0) {
			try {
				Album album = albumFinder.getAlbumForId(albumId);
				if (!(album.getProfileAlbum() == 1 || album.getCoverAlbum() == 1)) {
					move = true;
				}
			} catch (Exception e) {
			}
		} else {
			move = true;
		}
		return move;
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

	public class Command extends CommandBeanImpl implements CommandBean
	{
		private int ownerId;
		private String type;
		private int communityId;

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public int getOwnerId() {
			return ownerId;
		}

		public void setOwnerId(int ownerId) {
			this.ownerId = ownerId;
		}

		public int getCommunityId() {
			return communityId;
		}

		public void setCommunityId(int communityId) {
			this.communityId = communityId;
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public PhotoFinder getPhotoFinder() {
		return photoFinder;
	}

	public void setPhotoFinder(PhotoFinder photoFinder) {
		this.photoFinder = photoFinder;
	}

	public void setDocumentFinder(DocumentFinder documentFinder) {
		this.documentFinder = documentFinder;
	}

	public void setFolderFinder(FolderFinder folderFinder) {
		this.folderFinder = folderFinder;
	}

	public void setAlbumFinder(AlbumFinder albumFinder) {
		this.albumFinder = albumFinder;
	}
}