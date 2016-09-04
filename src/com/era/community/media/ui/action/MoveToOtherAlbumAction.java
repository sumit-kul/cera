package com.era.community.media.ui.action;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
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

/**
 *  @spring.bean name="/pers/moveToOtherAlbum.ajx" 
 */
public class MoveToOtherAlbumAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager; 
	private PhotoFinder photoFinder;
	private AlbumFinder albumFinder;
	private DocumentFinder documentFinder;
	private FolderFinder folderFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		Command command = (Command)data; 
		if (command.getFolderId() > 0) {
			Document document = null;
			try {
				document = documentFinder.getDocumentForId(command.getPhotoId());
				document.setFolderId(command.getFolderId());
				document.update();

				Folder folder = folderFinder.getFolderForId(command.getFolderId());
				try {
					Document latest = documentFinder.getLatestImageInFolder(command.getFolderId());
					folder.setCoverPhotoId(latest.getId());
				} catch (ElementNotFoundException e) {
					folder.setCoverPhotoId(0);
				}
				folder.update();
			} catch (ElementNotFoundException e) {
			}
		} else if (command.getAlbumId() > 0){
			Photo photo = null;
			try {
				photo = photoFinder.getPhotoForId(command.getPhotoId());
				photo.setAlbumId(command.getAlbumId());
				photo.update();

				Album album = albumFinder.getAlbumForId(command.getAlbumId());
				try {
					Photo latest = photoFinder.getLatestPhotoInAlbum(command.getAlbumId());
					album.setCoverPhotoId(latest.getId());
				} catch (ElementNotFoundException e) {
					album.setCoverPhotoId(0);
				}
				album.update();
			} catch (ElementNotFoundException e) {
			}
		}

		return null;
	}

	public class Command extends CommandBeanImpl implements CommandBean
	{
		private int photoId;
		private int albumId;
		private int folderId;

		public int getPhotoId() {
			return photoId;
		}
		public void setPhotoId(int photoId) {
			this.photoId = photoId;
		}
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