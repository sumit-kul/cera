package com.era.community.media.ui.action;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityActivityFinder;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.doclib.dao.Document;
import com.era.community.doclib.dao.DocumentFinder;
import com.era.community.doclib.dao.Folder;
import com.era.community.doclib.dao.FolderFinder;
import com.era.community.media.dao.Album;
import com.era.community.media.dao.AlbumFinder;
import com.era.community.media.dao.Photo;
import com.era.community.media.dao.PhotoFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserActivity;
import com.era.community.pers.dao.UserActivityFinder;

/**
 *  @spring.bean name="/pers/deletePhotos.ajx" 
 */
public class DeletePhotosAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager; 
	private PhotoFinder photoFinder;
	private AlbumFinder albumFinder;
	private DocumentFinder documentFinder;
	private FolderFinder folderFinder;
	private CommunityFinder communityFinder;
	protected CommunityActivityFinder communityActivityFinder;
	protected UserActivityFinder userActivityFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		Command command = (Command)data; 
		User currUser = context.getCurrentUser();
		if (currUser != null) {
			try {
				if (command.getPhotoId() > 0) {
					Photo photo = photoFinder.getPhotoForId(command.getPhotoId());
					if (isEditAllowed(currUser, photo.getUserId())) {
						Album album = null;
						Photo latest = null;
						photo.delete();
						try {
							album = albumFinder.getAlbumForId(photo.getAlbumId());
							latest = photoFinder.getLatestPhotoInAlbum(album.getId());
							
							album.setCoverPhotoId(latest.getId());
							album.update();
						} catch (ElementNotFoundException e) {
							if (album != null && latest == null) {
								album.setCoverPhotoId(0);
								album.update();
							}
						}
						try {
							int count = photoFinder.countPhotoListForHeader(photo.getAlbumId(), photo.getUserId(), photo.getMediaGroupNumber());
							if (count == 1) {
								userActivityFinder.clearUserActivityForPhoto(photo.getId(), photo.getMediaGroupNumber(), photo.getAlbumId());
							} 							
						} catch (ElementNotFoundException e) {
						}
					}
				} else if (command.getMediaId() > 0 && command.getCommunityId() > 0) {
					Document media = documentFinder.getDocumentForId(command.getMediaId());
					Community community = communityFinder.getCommunityForId(command.getCommunityId());
					if (isEditAllowed(currUser, media.getPosterId()) || community.isAdminMember(currUser)) {
						Folder folder = null;
						Document latest = null;
						
						try {
							folder = folderFinder.getFolderForId(media.getFolderId());
							latest = documentFinder.getLatestImageInFolder(folder.getId());
							
							folder.setCoverPhotoId(latest.getId());
							folder.update();
						} catch (ElementNotFoundException e) {
							if (folder != null && latest == null) {
								folder.setCoverPhotoId(0);
								folder.update();
							}
						}
						media.delete();
					}
				}
			} catch (ElementNotFoundException e) {
				// TODO: handle exception
			}
		}
		return null;
	}


	private boolean isEditAllowed(User current, int userId) {
		if (current != null && current.getId() == userId){
			return true;		
		}
		return false;
	}
	
	public class Command extends CommandBeanImpl implements CommandBean
	{
		private int photoId;
		private int mediaId;
		private int communityId;

		public int getPhotoId() {
			return photoId;
		}

		public void setPhotoId(int photoId) {
			this.photoId = photoId;
		}

		public int getMediaId() {
			return mediaId;
		}

		public void setMediaId(int mediaId) {
			this.mediaId = mediaId;
		}

		public int getCommunityId() {
			return communityId;
		}

		public void setCommunityId(int communityId) {
			this.communityId = communityId;
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


	public void setDocumentFinder(DocumentFinder documentFinder) {
		this.documentFinder = documentFinder;
	}


	public void setFolderFinder(FolderFinder folderFinder) {
		this.folderFinder = folderFinder;
	}


	public void setCommunityFinder(CommunityFinder communityFinder) {
		this.communityFinder = communityFinder;
	}


	public void setCommunityActivityFinder(
			CommunityActivityFinder communityActivityFinder) {
		this.communityActivityFinder = communityActivityFinder;
	}


	public void setUserActivityFinder(UserActivityFinder userActivityFinder) {
		this.userActivityFinder = userActivityFinder;
	}
}