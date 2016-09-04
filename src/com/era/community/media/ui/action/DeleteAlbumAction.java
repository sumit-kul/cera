package com.era.community.media.ui.action;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.doclib.dao.DocumentFinder;
import com.era.community.doclib.dao.Folder;
import com.era.community.doclib.dao.FolderFinder;
import com.era.community.media.dao.Album;
import com.era.community.media.dao.AlbumFinder;
import com.era.community.media.dao.AlbumLinkFinder;
import com.era.community.media.dao.PhotoFinder;
import com.era.community.pers.dao.User;

/**
 *  @spring.bean name="/pers/deleteAlbum.ajx" 
 */
public class DeleteAlbumAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager; 
	private PhotoFinder photoFinder;
	private AlbumFinder albumFinder;
	private AlbumLinkFinder albumLinkFinder;
	private DocumentFinder documentFinder;
	private FolderFinder folderFinder;
	private CommunityFinder communityFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		Command command = (Command)data; 
		User currUser = context.getCurrentUser();
		try {
			if (command.getAlbumId() > 0) {
				Album album = albumFinder.getAlbumForId(command.getAlbumId());
				if (isEditAllowed(currUser, album)) {
					album.delete();
					photoFinder.deleteAllPhotosForAlbum(command.getAlbumId());
					albumLinkFinder.deleteAllLinksForAlbum(command.getAlbumId());
				}
			} else if (command.getFolderId() > 0) {
				Folder folder = folderFinder.getFolderForId(command.getFolderId());
				if (isEditAllowed(currUser, folder, command.getCommunityId())) {
					folder.delete();
					documentFinder.deleteAllPhotosForFolder(command.getFolderId());
				}
			}
		} catch (ElementNotFoundException e) {
		}
		return null;
	}


	private boolean isEditAllowed(User current, Album album) {
		if (current != null && current.getId() == album.getOwnerId()){
			return true;		
		}
		return false;
	}

	private boolean isEditAllowed(User current, Folder folder, int commId) throws Exception {
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

	public class Command extends CommandBeanImpl implements CommandBean
	{
		private int albumId;
		private int folderId;
		private int communityId;

		public int getAlbumId() {
			return albumId;
		}

		public void setAlbumId(int albumId) {
			this.albumId = albumId;
		}

		public int getFolderId() {
			return folderId;
		}

		public void setFolderId(int folderId) {
			this.folderId = folderId;
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


	public void setAlbumLinkFinder(AlbumLinkFinder albumLinkFinder) {
		this.albumLinkFinder = albumLinkFinder;
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