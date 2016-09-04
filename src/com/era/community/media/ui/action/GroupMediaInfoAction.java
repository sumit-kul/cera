package com.era.community.media.ui.action;

import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.media.dao.Album;
import com.era.community.media.dao.AlbumFinder;
import com.era.community.media.dao.AlbumLinkFinder;
import com.era.community.media.dao.Photo;
import com.era.community.media.dao.PhotoFinder;
import com.era.community.media.ui.dto.AlbumDto;
import com.era.community.media.ui.dto.AlbumLinkDto;
import com.era.community.pers.dao.User;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 *  @spring.bean name="/pers/showGroupMediaInfo.ajx" 
 */
public class GroupMediaInfoAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager; 
	private PhotoFinder photoFinder;
	private AlbumFinder albumFinder;
	private AlbumLinkFinder albumLinkFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		Command command = (Command)data; 
		User currUser = context.getCurrentUser();
		HttpServletResponse resp = context.getResponse();
		if (command.getType() != null && "album".equalsIgnoreCase(command.getType())) {
			Album album = albumFinder.getAlbumForId(command.getGroupMediaId());
			List<AlbumDto> otherAlbums = albumFinder.listOtherAlbumsForUser(command.getOwnerId(), command.getGroupMediaId());
			List<Photo> photos = photoFinder.listPhotosForAlbum(command.getGroupMediaId());
			if (album != null) {
				JSONObject json = toJsonString(album, photos, otherAlbums, currUser);
				String jsonString = json.serialize();
				resp.setContentType("text/json");
				Writer out = resp.getWriter();
				out.write(jsonString);
				out.close();
			}
		}
		if (command.getType() != null && "albumOnly".equalsIgnoreCase(command.getType())) {
			Album album = albumFinder.getAlbumForId(command.getGroupMediaId());
			List<AlbumLinkDto> links = albumLinkFinder.getAlbumLinksForAlbum(command.getGroupMediaId());
			String contList = "";
			for (AlbumLinkDto link : links) {
				contList += link.getContributorId()+",";
			}
			if (album != null) {
				JSONObject json = toJsonString(album, currUser, links);
				json.put("contList", contList);
				String jsonString = json.serialize();
				resp.setContentType("text/json");
				Writer out = resp.getWriter();
				out.write(jsonString);
				out.close();
			}
		}
		return null;
	}

	private JSONObject toJsonString(Album album, List<Photo> albumPhotos, List<AlbumDto> albums, User currUser) throws Exception
	{
		JSONObject json = new JSONObject();
		json.put("albumId", album.getId());
		json.put("title", album.getTitle());
		json.put("description", album.getDescription());
		json.put("createdOn", getPostedOn(album.getCreated()));
		json.put("privacyLevel", album.getPrivacyLevel());
		json.put("coverPhotoId", album.getCoverPhotoId());
		json.put("isContributor", isContributor(currUser, album));
		json.put("isAlbumEditAllowed", isAlbumEditAllowed(currUser, album));
		JSONArray photos = new JSONArray();
		for (Photo photo : albumPhotos) {
			JSONObject row = new JSONObject();
			row.put("id", photo.getId());
			row.put("title", photo.getTitle());
			row.put("description", photo.getDescription());
			row.put("width", photo.getWidth());
			row.put("height", photo.getHeight());
			row.put("ownerId", photo.getUserId());
			row.put("albumId", photo.getAlbumId());
			row.put("currentProfile", photo.getCurrentProfile());
			row.put("privacyLevel", photo.getPrivacyLevel());
			row.put("isEditAllowed", isPhotoEditAllowed(currUser, photo));
			photos.add(row);
		}
		json.put("photos", photos);
		JSONArray othAlbms = new JSONArray();
		for (AlbumDto otherAlbum : albums) {
			JSONObject row = new JSONObject();
			row.put("id", otherAlbum.getId());
			row.put("title", otherAlbum.getTitle());
			row.put("description", otherAlbum.getDescription());
			row.put("ownerId", otherAlbum.getOwnerId());
			row.put("photoCount", otherAlbum.getPhotoCount());
			row.put("coverPhotoId", otherAlbum.getCoverPhotoId());
			row.put("privacyLevel", otherAlbum.getPrivacyLevel());
			row.put("modified", otherAlbum.getModified().toString());
			othAlbms.add(row);
		}
		json.put("otherAlbums", othAlbms);
		return json;
	}

	private JSONObject toJsonString(Album album, User currUser, List<AlbumLinkDto> albumlinks) throws Exception
	{
		JSONObject json = new JSONObject();
		json.put("albumId", album.getId());
		json.put("title", album.getTitle());
		json.put("description", album.getDescription());
		json.put("privacyLevel", album.getPrivacyLevel());
		json.put("isAlbumEditAllowed", isAlbumEditAllowed(currUser, album));
		JSONArray links = new JSONArray();
		for (AlbumLinkDto link : albumlinks) {
			JSONObject row = new JSONObject();
			row.put("contributorId", link.getContributorId());
			row.put("photoPresent", link.getPhotoPresent());
			row.put("displayName", link.getDisplayName());
			links.add(row);
		}
		json.put("links", links);
		return json;
	}
	
	public String getPostedOn(Date date) throws Exception
	{
		SimpleDateFormat fmter = new SimpleDateFormat("dd-MMM-yyyy");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat fmt = new SimpleDateFormat("HH:mm a");
		SimpleDateFormat fmt2 = new SimpleDateFormat("dd MMM, yy");
		Date today = new Date();
		String sToday = fmter.format(today);
		try {
			if (fmter.format(date).equals(sToday)) {
				return "Today " + fmt.format(date);
			}
			return fmt2.format(date);
		} catch (NullPointerException e) {
			return "";
		}
	}

	private boolean isContributor(User current, Album album) throws Exception {
		if (current == null) {
			return false;
		}
		try {
			albumLinkFinder.getAlbumLinkForContributor(album.getId(), current.getId());
			return true;
		} catch (ElementNotFoundException e) {
			return false;
		}
	}

	private boolean isAlbumEditAllowed(User current, Album album) {
		if (current != null && current.getId() == album.getOwnerId()){
			return true;		
		}
		return false;
	}

	private boolean isPhotoEditAllowed(User current, Photo photo) {
		if (current != null && current.getId() == photo.getUserId()){
			return true;		
		}
		return false;
	}

	public class Command extends CommandBeanImpl implements CommandBean
	{
		private int groupMediaId;
		private int ownerId;
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
		public int getOwnerId() {
			return ownerId;
		}
		public void setOwnerId(int ownerId) {
			this.ownerId = ownerId;
		}
	}

	public CommunityEraContextManager getContextManager() {
		return contextManager;
	}

	public void setContextManager(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}

	public void setPhotoFinder(PhotoFinder photoFinder) {
		this.photoFinder = photoFinder;
	}

	public void setAlbumFinder(AlbumFinder albumFinder) {
		this.albumFinder = albumFinder;
	}

	public void setAlbumLinkFinder(AlbumLinkFinder albumLinkFinder) {
		this.albumLinkFinder = albumLinkFinder;
	}
}