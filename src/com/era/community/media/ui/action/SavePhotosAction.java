package com.era.community.media.ui.action;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.media.dao.Album;
import com.era.community.media.dao.AlbumFinder;
import com.era.community.media.dao.AlbumLink;
import com.era.community.media.dao.AlbumLinkFinder;
import com.era.community.media.dao.Photo;
import com.era.community.media.dao.PhotoFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserActivity;
import com.era.community.pers.dao.UserActivityFinder;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 *  @spring.bean name="/pers/savePhotos.ajx" 
 */
public class SavePhotosAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager; 
	private PhotoFinder photoFinder;
	private AlbumFinder albumFinder;
	private AlbumLinkFinder albumLinkFinder;
	private UserActivityFinder userActivityFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		Command command = (Command)data; 
		User currUser = context.getCurrentUser();
		HttpServletResponse resp = context.getResponse();
		int albumId = command.getAlbumId();
		int coverPhotoId = 0;
		Album album = null;
		if (currUser != null) {
			if (command.getType() != null && "album".equalsIgnoreCase(command.getType())) {
				String title = command.getTitle();
				String description = command.getDescription();
				album = albumFinder.newAlbum();
				album.setTitle(title);
				album.setDescription(description);
				album.setOwnerId(currUser.getId());
				album.setPrivacyLevel(command.getPrivacyLevel());
				album.update();
				albumId = album.getId();
				album = albumFinder.getAlbumForId(albumId);
				
				StringTokenizer st = new StringTokenizer(command.getContributors(), ",");
				while (st.hasMoreTokens()) {
					String contributorId = st.nextToken().trim();
					AlbumLink albumLink = albumLinkFinder.newAlbumLink();
					albumLink.setAlbumId(albumId);
					albumLink.setAccessLevel(0);
					albumLink.setContributorId(Integer.parseInt(contributorId));
					albumLink.update();
				}
			}
			
			if (album == null && albumId > 0) {
				album = albumFinder.getAlbumForId(albumId);
			}

			List <CommonsMultipartFile> files = command.getFiles();
			List<Photo> addedPhotos = new ArrayList<Photo>();
			Photo lastPhoto = null;
			int grSeq = 0;
			UserActivity userActivity = null;
			try {
				userActivity = userActivityFinder.getMostRecentMediaGroupActivity(currUser.getId(), 
						albumId > 0 ? albumId : command.getAlbumId());
				//if (userActivity.getCreated().compareTo(new Date()) < 0) {
					grSeq = userActivity.getMediaGroupNumber()+1;
					userActivity = userActivityFinder.newUserActivity();
					userActivity.setUserId(currUser.getId());
					userActivity.setMediaGroupNumber(grSeq);
					userActivity.update();
			} catch (ElementNotFoundException e) {
				userActivity = userActivityFinder.newUserActivity();
				userActivity.setUserId(currUser.getId());
				userActivity.setMediaGroupNumber(1);
				userActivity.update();
				grSeq = 1;
			}
			for (CommonsMultipartFile file : files) {
				String fileName = file.getOriginalFilename();
				fileName = fileName.substring(0, fileName.lastIndexOf("."));
				if (fileName.length() > 20) {
					fileName = fileName.substring(0, 19);
				}
				Photo photo = photoFinder.newPhoto();
				photo.setFileName(fileName);
				photo.setPhotoLength((Long.valueOf(file.getSize()).intValue()));
				photo.setPhotoContentType(file.getContentType());
				photo.setUserId(currUser.getId());
				if (albumId > 0) {
					photo.setAlbumId(albumId);
				} else {
					photo.setAlbumId(command.getAlbumId());
				}
				photo.setCurrentProfile(0);
				photo.setPrivacyLevel(0);
				photo.setTitle(fileName);
				photo.setMediaGroupNumber(grSeq);
				photo.update();
				photo.storePhoto(file);
				addedPhotos.add(photo);
				lastPhoto = photo;
			}
			
			if(album == null && lastPhoto != null && userActivity != null){
				userActivity.setPhotoId(lastPhoto.getId());
				userActivity.update();
			}
			
			if (album != null && album.getCoverPhotoId() == 0 && lastPhoto != null) {
				coverPhotoId = lastPhoto.getId();
				album.setCoverPhotoId(coverPhotoId);
				album.update();
			}

			if (album != null) {
				JSONObject json = toJsonString(album, addedPhotos, currUser);
				String jsonString = json.serialize();
				resp.setContentType("text/json");
				Writer out = resp.getWriter();
				out.write(jsonString);
				out.close();
			}
		}
		return null;
	}

	private JSONObject toJsonString(Album album, List<Photo> addedPhotos, User currUser) throws Exception
	{
		JSONObject json = new JSONObject();
		json.put("albumId", album.getId());
		json.put("title", album.getTitle());
		json.put("description", album.getDescription());
		json.put("ownerId", album.getOwnerId());
		json.put("privacyLevel", album.getPrivacyLevel());
		json.put("coverPhotoId", album.getCoverPhotoId());
		JSONArray data = new JSONArray();
		for (Photo photo : addedPhotos) {
			JSONObject row = new JSONObject();
			row.put("id", photo.getId());
			row.put("title", photo.getTitle());
			row.put("description", photo.getDescription());
			row.put("ownerId", photo.getUserId());
			row.put("albumId", photo.getAlbumId());
			row.put("currentProfile", photo.getCurrentProfile());
			row.put("privacyLevel", photo.getPrivacyLevel());
			row.put("isEditAllowed", isEditAllowed(currUser, photo));
			data.add(row);
		}
		json.put("aData", data);
		return json;
	}
	
	private boolean isEditAllowed(User current, Photo photo) {
		if (current != null && current.getId() == photo.getUserId()){
			return true;		
		}
		return false;
	}

	public class Command extends CommandBeanImpl implements CommandBean
	{
		private String title;
		private String type;
		private String description;
		private int privacyLevel;
		private String contributors;
		private int albumId;

		private List files = 
			LazyList.decorate(
					new ArrayList(),
					FactoryUtils.instantiateFactory(CommonsMultipartResolver.class));

		public List getFiles() {
			return files;
		}

		public void setFiles(List list) {
			files = list;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public int getPrivacyLevel() {
			return privacyLevel;
		}

		public void setPrivacyLevel(int privacyLevel) {
			this.privacyLevel = privacyLevel;
		}

		public String getContributors() {
			return contributors;
		}

		public void setContributors(String contributors) {
			this.contributors = contributors;
		}

		public int getAlbumId() {
			return albumId;
		}

		public void setAlbumId(int albumId) {
			this.albumId = albumId;
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

	public void setUserActivityFinder(UserActivityFinder userActivityFinder) {
		this.userActivityFinder = userActivityFinder;
	}
}