package com.era.community.pers.ui.action;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;
import support.community.util.StringFormat;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityActivity;
import com.era.community.communities.dao.CommunityActivityFinder;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.doclib.dao.Document;
import com.era.community.doclib.dao.DocumentFinder;
import com.era.community.doclib.dao.DocumentLibrary;
import com.era.community.doclib.dao.DocumentLibraryFeature;
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
 * @spring.bean name="/pers/addPhotoInAlbum.img"
 */
public class AddPhotoInAlbumAction extends AbstractCommandAction 
{
	protected CommunityEraContextManager contextManager;
	protected AlbumFinder albumFinder;
	protected PhotoFinder photoFinder;
	protected FolderFinder folderFinder;
	protected DocumentFinder documentFinder;
	protected CommunityFinder communityFinder;
	protected DocumentLibraryFeature doclibFeature;
	protected CommunityActivityFinder communityActivityFinder;
	protected UserActivityFinder userActivityFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command)data;
		CommunityEraContext context = contextManager.getContext();

		if (context.getCurrentUser() == null ) {
			String reqUrl = context.getRequestUrl();
			if(reqUrl != null) {
				context.getRequest().getSession().setAttribute("url_prior_login", reqUrl);
			}
			return new ModelAndView("redirect:/login.do?redirect=" + StringFormat.escape(context.getRequestUrl()), "command", cmd);
		}
		User currentUser =  context.getCurrentUser();
		// Handle the user photo file attachment
		if (cmd.getFile() != null && !cmd.getFile().isEmpty() && currentUser != null) {
			if ("profile".equalsIgnoreCase(cmd.getType())) {
				Album album = null;
				try {
					if(cmd.getImgType() != null && "Cover".equalsIgnoreCase(cmd.getImgType())) {
						album = albumFinder.getCoverAlbumForUser(currentUser.getId());
					}else {
						album = albumFinder.getProfileAlbumForUser(currentUser.getId());
					}
				} catch (ElementNotFoundException e) {
					album = albumFinder.newAlbum();
					if(cmd.getImgType() != null && "Cover".equalsIgnoreCase(cmd.getImgType())) {
						album.setTitle("Cover Photos");
						album.setDescription("Cover Photos");
						album.setCoverAlbum(1);
					} else {
						album.setTitle("Profile Photos");
						album.setDescription("Profile Photos");
						album.setProfileAlbum(1);
					}
				}
				album.setOwnerId(currentUser.getId());
				album.setPrivacyLevel(0);
				album.setShared(0);
				album.update();

				try {
					Photo cpPhoto = null;
					if(cmd.getImgType() != null && "Cover".equalsIgnoreCase(cmd.getImgType())) {
						cpPhoto = photoFinder.getCurrentCoverPhoto(currentUser.getId());
					} else {
						cpPhoto = photoFinder.getCurrentProfilePhoto(currentUser.getId());
					}
					cpPhoto.setCurrentProfile(0);
					cpPhoto.update();
				} catch (ElementNotFoundException e) {
				}
				
				int grSeq = 0;
				try {
					UserActivity userActivity = userActivityFinder.getMostRecentMediaGroupActivity(currentUser.getId(), album.getId());
					if (userActivity.getCreated().compareTo(new Date()) < 0) {
						grSeq = userActivity.getMediaGroupNumber()+1;
						userActivity = userActivityFinder.newUserActivity();
						userActivity.setUserId(currentUser.getId());
						userActivity.setMediaGroupNumber(grSeq);
						userActivity.update();
					} else {
						grSeq = userActivity.getMediaGroupNumber();
					}
				} catch (ElementNotFoundException e) {
					UserActivity uActivity = userActivityFinder.newUserActivity();
					uActivity.setUserId(currentUser.getId());
					uActivity.setMediaGroupNumber(1);
					uActivity.update();
					grSeq = 1;
				}

				Photo photo = null;
				try {
					if(cmd.getPhotoId() > 0) {
						photo = photoFinder.getPhotoForId(cmd.getPhotoId());
						if(cmd.getImgType() != null && "Cover".equalsIgnoreCase(cmd.getImgType())) {
							photo.setCurrentProfile(2);
						} else {
							photo.setCurrentProfile(1);
						}
						photo.update();
					} else {
						photo = photoFinder.newPhoto();
						photo.setFileName(cmd.getFileName());
						photo.setPhotoLength((Long.valueOf(cmd.getFile().getSize()).intValue()));
						photo.setPhotoContentType(cmd.getFile().getContentType());
						photo.setUserId(currentUser.getId());
						photo.setMediaGroupNumber(grSeq);
						photo.setAlbumId(album.getId());
						if(cmd.getImgType() != null && "Cover".equalsIgnoreCase(cmd.getImgType())) {
							photo.setCurrentProfile(2);
						} else {
							photo.setCurrentProfile(1);
						}
						photo.setPrivacyLevel(0);
						photo.setTitle(cmd.getFileName());
						photo.update();
						photo.storePhoto(cmd.getFile());
					}
				} catch (ElementNotFoundException e) {
					photo = photoFinder.newPhoto();
					photo.setFileName(cmd.getFileName());
					photo.setPhotoLength((Long.valueOf(cmd.getFile().getSize()).intValue()));
					photo.setPhotoContentType(cmd.getFile().getContentType());
					photo.setUserId(currentUser.getId());
					photo.setMediaGroupNumber(grSeq);
					photo.setAlbumId(album.getId());
					if(cmd.getImgType() != null && "Cover".equalsIgnoreCase(cmd.getImgType())) {
						photo.setCurrentProfile(2);
					} else {
						photo.setCurrentProfile(1);
					}
					photo.setPrivacyLevel(0);
					photo.setTitle(cmd.getFileName());
					photo.update();
					photo.storePhoto(cmd.getFile());
				}
				album.setCoverPhotoId(photo.getId());
				album.update();
			} else if ("community".equalsIgnoreCase(cmd.getType())) {
				Folder folder = null;
				Community currComm = null;
				DocumentLibrary lib = null;
				try {
					try {
						currComm = communityFinder.getCommunityForId(cmd.getCommunityId());
						lib = (DocumentLibrary)doclibFeature.getFeatureForCommunity(currComm);
					} catch (ElementNotFoundException e) {
						return null;
					}

					if(cmd.getImgType() != null && "Banner".equalsIgnoreCase(cmd.getImgType())) {
						folder = folderFinder.getCommunityAlbumForBanner(lib.getId());
					}else {
						folder = folderFinder.getCommunityAlbumForLibrary(lib.getId());
					}
				} catch (ElementNotFoundException e) {
					folder = folderFinder.newFolder();
					if(cmd.getImgType() != null && "Banner".equalsIgnoreCase(cmd.getImgType())) {
						folder.setTitle("Community Banners");
						folder.setDescription("Community Banners");
						folder.setBannerFolder(1);
					} else {
						folder.setTitle("Community Logos");
						folder.setDescription("Community Logos");
						folder.setProfileFolder(1);
					}
					folder.setOwnerId(currentUser.getId());
					folder.setLibraryId(lib.getId());
					folder.setPrivacyLevel(0);
					folder.update();
				}
				
				int grSeq = 0;
				try {
					CommunityActivity communityActivity = communityActivityFinder.getMostRecentDocumentGroupActivity(cmd.getCommunityId(), folder.getId());
					if (communityActivity.getCreated().compareTo(new Date()) < 0) {
						grSeq = communityActivity.getDocGroupNumber()+1;
						communityActivity = communityActivityFinder.newCommunityActivity();
						communityActivity.setFolderId(folder.getId());
						communityActivity.setDocGroupNumber(grSeq);
						communityActivity.setUserId(currentUser.getId());
						communityActivity.setCommunityId(cmd.getCommunityId());
						communityActivity.update();
						
						UserActivity uActivity = userActivityFinder.newUserActivity();
						uActivity.setUserId(currentUser.getId());
						uActivity.setCommunityActivityId(communityActivity.getId());
						uActivity.update();
					} else {
						grSeq = communityActivity.getDocGroupNumber();
					}
				} catch (ElementNotFoundException e) {
					CommunityActivity communityActivity = communityActivityFinder.newCommunityActivity();
					communityActivity.setFolderId(folder.getId());
					communityActivity.setDocGroupNumber(1);
					communityActivity.setUserId(currentUser.getId());
					communityActivity.setCommunityId(cmd.getCommunityId());
					communityActivity.update();
					
					UserActivity uActivity = userActivityFinder.newUserActivity();
					uActivity.setUserId(currentUser.getId());
					uActivity.setCommunityActivityId(communityActivity.getId());
					uActivity.update();
					grSeq = 1;
				}

				try {
					Document cpLogo = null;
					if(cmd.getImgType() != null && "Banner".equalsIgnoreCase(cmd.getImgType())) {
						cpLogo = documentFinder.getCurrentProfileBanner(lib.getId());
					} else {
						cpLogo = documentFinder.getCurrentProfileLogo(lib.getId());
					}
					cpLogo.setCurrentProfile(0);
					cpLogo.update();
				} catch (ElementNotFoundException e) {
				}

				Document logo = null;
				try {
					if(cmd.getPhotoId() > 0) {
						logo = documentFinder.getDocumentForId(cmd.getPhotoId());
						if(cmd.getImgType() != null && "Banner".equalsIgnoreCase(cmd.getImgType())) {
							logo.setCurrentProfile(2);
						} else {
							logo.setCurrentProfile(1);
						}
						logo.update();
					} else {
						logo = documentFinder.newDocument();
						logo.setFileName(cmd.getFileName());
						logo.setFileLength((Long.valueOf(cmd.getFile().getSize()).intValue()));
						logo.setFileContentType(cmd.getFile().getContentType());
						logo.setPosterId(currentUser.getId());
						logo.setDocGroupNumber(grSeq);
						logo.setFolderId(folder.getId());
						if(cmd.getImgType() != null && "Banner".equalsIgnoreCase(cmd.getImgType())) {
							logo.setCurrentProfile(2);
						} else {
							logo.setCurrentProfile(1);
						}
						logo.setPrivacyLevel(0);
						logo.setTitle(cmd.getFileName());
						logo.setLibraryId(lib.getId());
						logo.update();
						logo.storeFile(cmd.getFile());

						CommunityActivity cActivity = communityActivityFinder.newCommunityActivity();
						cActivity.setCommunityId(context.getCurrentCommunity().getId());
						cActivity.setDocumentId(logo.getId());
						cActivity.update();

						UserActivity uActivity = userActivityFinder.newUserActivity();
						uActivity.setCommunityActivityId(cActivity.getId());
						uActivity.update();
					}
				} catch (ElementNotFoundException e) {
					logo = documentFinder.newDocument();
					logo.setFileName(cmd.getFileName());
					logo.setFileLength((Long.valueOf(cmd.getFile().getSize()).intValue()));
					logo.setFileContentType(cmd.getFile().getContentType());
					logo.setPosterId(currentUser.getId());
					logo.setDocGroupNumber(grSeq);
					logo.setFolderId(folder.getId());
					if(cmd.getImgType() != null && "Banner".equalsIgnoreCase(cmd.getImgType())) {
						logo.setCurrentProfile(2);
					} else {
						logo.setCurrentProfile(1);
					}
					logo.setPrivacyLevel(0);
					logo.setTitle(cmd.getFileName());
					logo.setLibraryId(lib.getId());
					logo.update();
					logo.storeFile(cmd.getFile());

					CommunityActivity cActivity = communityActivityFinder.newCommunityActivity();
					cActivity.setCommunityId(context.getCurrentCommunity().getId());
					cActivity.setDocumentId(logo.getId());
					cActivity.setFolderId(folder.getId());
					cActivity.setUserId(logo.getPosterId());
					cActivity.update();

					UserActivity uActivity = userActivityFinder.newUserActivity();
					uActivity.setCommunityActivityId(cActivity.getId());
					uActivity.setUserId(logo.getPosterId());
					uActivity.update();
				}
				folder.setCoverPhotoId(logo.getId());
				folder.update();
			}
		}
		return null;
	}

	public static class Command extends CommandBeanImpl implements CommandBean
	{
		private int photoId = 0;
		private MultipartFile file;
		private String fileName;
		private String type;
		private int communityId;
		private String imgType;

		public MultipartFile getFile() {
			return file;
		}
		public void setFile(MultipartFile file) {
			this.file = file;
		}
		public String getFileName() {
			return fileName;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public int getCommunityId() {
			return communityId;
		}
		public void setCommunityId(int communityId) {
			this.communityId = communityId;
		}
		public int getPhotoId() {
			return photoId;
		}
		public void setPhotoId(int photoId) {
			this.photoId = photoId;
		}
		public String getImgType() {
			return imgType;
		}
		public void setImgType(String imgType) {
			this.imgType = imgType;
		}
	}

	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setAlbumFinder(AlbumFinder albumFinder) {
		this.albumFinder = albumFinder;
	}

	public void setPhotoFinder(PhotoFinder photoFinder) {
		this.photoFinder = photoFinder;
	}

	public void setFolderFinder(FolderFinder folderFinder) {
		this.folderFinder = folderFinder;
	}

	public void setDocumentFinder(DocumentFinder documentFinder) {
		this.documentFinder = documentFinder;
	}

	public void setDoclibFeature(DocumentLibraryFeature doclibFeature) {
		this.doclibFeature = doclibFeature;
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