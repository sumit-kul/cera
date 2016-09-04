package com.era.community.media.ui.action;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
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
import com.era.community.media.dao.Album;
import com.era.community.media.dao.AlbumFinder;
import com.era.community.media.dao.AlbumLink;
import com.era.community.media.dao.AlbumLinkFinder;
import com.era.community.media.dao.Photo;
import com.era.community.media.dao.PhotoFinder;
import com.era.community.media.ui.dto.AlbumLinkDto;
import com.era.community.pers.dao.User;

/**
 *  @spring.bean name="/pers/updateMediaInfo.ajx" 
 */
public class UpdateMediaInfoAction extends AbstractCommandAction
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
		Command cmd = (Command)data; 
		CommunityEraContext context = contextManager.getContext();
		User currentUser = context.getCurrentUser();

		try {
			if ( cmd.getMediaId() > 0 ) {
				Photo media = photoFinder.getPhotoForId(cmd.getMediaId());
				if (isPhotoEditAllowed(currentUser, media.getUserId())) {
					media.setTitle(cmd.getTitle());
					media.setDescription(cmd.getDescription());
					media.update();
				}
			}
			if ( cmd.getDocId() > 0 ) {
				Document doc = documentFinder.getDocumentForId(cmd.getDocId());
				if (isPhotoEditAllowed(currentUser, doc.getPosterId(), cmd.getCommunityId())) {
					doc.setTitle(cmd.getTitle());
					doc.setDescription(cmd.getDescription());
					doc.update();
				}
			}
			if ( cmd.getFolderId() > 0 ) {
				Folder folder = folderFinder.getFolderForId(cmd.getFolderId());
				if (isAlbumEditAllowed(currentUser, folder.getOwnerId(), cmd.getCommunityId())) {
					folder.setTitle(cmd.getTitle());
					folder.setDescription(cmd.getDescription());
					folder.update();
				}
			}
			if ( cmd.getAlbumId() > 0 ) {
				Album album = albumFinder.getAlbumForId(cmd.getAlbumId());
				if (isAlbumEditAllowed(currentUser, album.getOwnerId())) {
					album.setTitle(cmd.getTitle());
					album.setDescription(cmd.getDescription());
					album.update();

					List<AlbumLinkDto> links = albumLinkFinder.getAlbumLinksForAlbum(album.getId());
					String [] contributors = cmd.getContList().split(",");
					for(AlbumLinkDto link : links) {
						if (contributors.length > 0) {
							boolean found = false;
							for (int i = 0; i < contributors.length; i++) {
								String cId = contributors[i].trim();
								if(!"".equals(cId)){
									int contId = Integer.parseInt(cId);
									if (contId == link.getContributorId()) {
										found = true;
										break;
									}
								}
							}
							if (!found) {
								albumLinkFinder.deleteAlbumLinkForId(link.getId());
							}
						}
					}
					if (contributors.length > 0) {
						for (int i = 0; i < contributors.length; i++) {
							String cId = contributors[i].trim();
							if(!"".equals(cId)){
								boolean found = false;
								int contId = Integer.parseInt(cId);
								for(AlbumLinkDto link : links) {
									if (contId == link.getContributorId()) {
										found = true;
										break;
									}
								}
								if (!found) {
									AlbumLink lnk = albumLinkFinder.newAlbumLink();
									lnk.setAlbumId(cmd.getAlbumId());
									lnk.setContributorId(contId);
									lnk.update();
								}
							}
						}
					}
				}

			}
		} catch (ElementNotFoundException e) {
		}
		return null;
	}

	private boolean isPhotoEditAllowed(User current, int userId) {
		if (current != null && current.getId() == userId){
			return true;		
		}
		return false;
	}
	
	private boolean isPhotoEditAllowed(User current, int userId, int communityId) throws Exception {
		if (current != null && current.getId() == userId){
			return true;		
		}
		try {
			Community community = communityFinder.getCommunityForId(communityId);
			if (current != null && community.isAdminMember(current)) {
				return true;
			}
		} catch (ElementNotFoundException e) {
			return false;
		}
		return false;
	}
	
	private boolean isAlbumEditAllowed(User current, int ownerId) throws Exception {
		if (current != null && current.getId() == ownerId){
			return true;		
		}
		return false;
	}

	private boolean isAlbumEditAllowed(User current, int ownerId, int communityId) throws Exception {
		if (current != null && current.getId() == ownerId){
			return true;		
		}
		try {
			Community community = communityFinder.getCommunityForId(communityId);
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
		private String title;
		private String description;
		private int mediaId;
		private int albumId;
		private String contList;
		private int docId;
		private int folderId;
		private int communityId;

		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public int getMediaId() {
			return mediaId;
		}
		public void setMediaId(int mediaId) {
			this.mediaId = mediaId;
		}
		public int getAlbumId() {
			return albumId;
		}
		public void setAlbumId(int albumId) {
			this.albumId = albumId;
		}
		public String getContList() {
			return contList;
		}
		public void setContList(String contList) {
			this.contList = contList;
		}
		public int getDocId() {
			return docId;
		}
		public void setDocId(int docId) {
			this.docId = docId;
		}
		public int getFolderId() {
			return folderId;
		}
		public void setFolderId(int folderId) {
			this.folderId = folderId;
		}
		public void setCommunityId(int communityId) {
			this.communityId = communityId;
		}
		public int getCommunityId() {
			return communityId;
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
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