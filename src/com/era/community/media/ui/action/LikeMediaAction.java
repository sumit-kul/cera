package com.era.community.media.ui.action;

import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.doclib.dao.DocumentCommentLike;
import com.era.community.doclib.dao.DocumentCommentLikeFinder;
import com.era.community.doclib.dao.DocumentLike;
import com.era.community.doclib.dao.DocumentLikeFinder;
import com.era.community.media.dao.PhotoCommentLike;
import com.era.community.media.dao.PhotoCommentLikeFinder;
import com.era.community.media.dao.PhotoLike;
import com.era.community.media.dao.PhotoLikeFinder;

/**
 * @spring.bean name="/pers/likeMedia.ajx"
 */
public class LikeMediaAction extends AbstractCommandAction 
{
	protected CommunityEraContextManager contextManager;
	protected PhotoLikeFinder photoLikeFinder;
	protected DocumentLikeFinder documentLikeFinder;
	protected PhotoCommentLikeFinder photoCommentLikeFinder;
	protected DocumentCommentLikeFinder documentCommentLikeFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		if (context.getCurrentUser() != null) {
			int currentUserId = context.getCurrentUser().getId();
			Command cmd = (Command) data;
			PhotoCommentLike photoCommentLike;
			DocumentCommentLike documentCommentLike;
			PhotoLike photoLike;
			DocumentLike documentLike;
			String returnString = "";
			String likeCount = "";
			if (cmd.getCommentId() > 0) {
				if (cmd.getMediaId() > 0) {
					try {
						documentCommentLike = documentCommentLikeFinder.getLikeForDocumentCommentAndUser(cmd.getMediaId(), cmd.getCommentId(), currentUserId);
						//If like exists, delete this like record...this is for unlike
						documentCommentLike.delete();
						int count = documentCommentLikeFinder.getLikeCountForDocumentComment(cmd.getMediaId(), cmd.getCommentId());
						likeCount = String.valueOf(count);
						if (count > 0) {
							returnString = "<span class='lkCnt' >" + likeCount;
							if (count > 1) {
								returnString = returnString + " likes";
							} else {
								returnString = returnString + " like";
							}
							returnString = returnString + " - ";
							returnString = returnString + "</span>";
						} else {
							returnString = "";
						}
						returnString = returnString+"<a href='javascript:void(0);' onclick='likeMedia("+cmd.getMediaId()+ ", "+cmd.getCommentId()+")' class='euInfoSelect' style='font-weight: bold;' ><i class='fa fa-thumbs-up' style='font-size: 18px;'></i></a>";
					} catch (ElementNotFoundException e) {
						//If no like exists, create a new like record
						documentCommentLike = documentCommentLikeFinder.newDocumentCommentLike();
						documentCommentLike.setDocumentId(cmd.getMediaId());
						documentCommentLike.setDocumentCommentId(cmd.getCommentId());
						documentCommentLike.setPosterId(currentUserId);
						documentCommentLike.update();     

						int count = documentCommentLikeFinder.getLikeCountForDocumentComment(cmd.getMediaId(), cmd.getCommentId());
						likeCount = String.valueOf(count);
						if (count > 0) {
							returnString = "<span class='lkCnt' >" + likeCount;
							if (count > 1) {
								returnString = returnString + " likes";
							} else {
								returnString = returnString + " like";
							}
							returnString = returnString + " - ";
							returnString = returnString + "</span>";
						} else {
							returnString = "";
						}
						returnString = returnString+"<a href='javascript:void(0);' onclick='likeMedia("+cmd.getMediaId()+ ", "+cmd.getCommentId()+")' class='euInfoSelect' style='font-weight: bold;' ><i class='fa fa-thumbs-down' style='font-size: 18px;'></i></a>";
					} 
				} else if (cmd.getPhotoId() > 0) {
					try {
						photoCommentLike = photoCommentLikeFinder.getLikeForPhotoCommentAndUser(cmd.getPhotoId(), cmd.getCommentId(), currentUserId);
						//If like exists, delete this like record...this is for unlike
						photoCommentLike.delete();
						int count = photoCommentLikeFinder.getLikeCountForPhotoComment(cmd.getPhotoId(), cmd.getCommentId());
						likeCount = String.valueOf(count);
						if (count > 0) {
							returnString = "<span class='lkCnt' >" + likeCount;
							if (count > 1) {
								returnString = returnString + " likes";
							} else {
								returnString = returnString + " like";
							}
							returnString = returnString + " - ";
							returnString = returnString + "</span>";
						} else {
							returnString = "";
						}
						returnString = returnString+"<a href='javascript:void(0);' onclick='likeMedia("+cmd.getPhotoId()+ ", "+cmd.getCommentId()+")' class='euInfoSelect' style='font-weight: bold;' ><i class='fa fa-thumbs-up' style='font-size: 18px;'></i></a>";
					} catch (ElementNotFoundException e) {
						//If no like exists, create a new like record
						photoCommentLike = photoCommentLikeFinder.newPhotoCommentLike();
						photoCommentLike.setPhotoId(cmd.getPhotoId());
						photoCommentLike.setPhotoCommentId(cmd.getCommentId());
						photoCommentLike.setPosterId(currentUserId);
						photoCommentLike.update();     
						int count = photoCommentLikeFinder.getLikeCountForPhotoComment(cmd.getPhotoId(), cmd.getCommentId());
						likeCount = String.valueOf(count);
						if (count > 0) {
							returnString = "<span class='lkCnt' >" + likeCount;
							if (count > 1) {
								returnString = returnString + " likes";
							} else {
								returnString = returnString + " like";
							}
							returnString = returnString + " - ";
							returnString = returnString + "</span>";
						} else {
							returnString = "";
						}
						returnString = returnString+"<a href='javascript:void(0);' onclick='likeMedia("+cmd.getPhotoId()+ ", "+cmd.getCommentId()+")' class='euInfoSelect' style='font-weight: bold;' ><i class='fa fa-thumbs-down' style='font-size: 18px;'></i></a>";
					} 
				} else {
					return null;
				}
			} else {
				if (cmd.getMediaId() > 0) {
					try {
						documentLike = documentLikeFinder.getLikeForDocumentAndUser(cmd.getMediaId(), currentUserId);
						//If like exists, delete this like record...this is for unlike
						documentLike.delete();
						int count = documentLikeFinder.getLikeCountForDocument(cmd.getMediaId());;
						likeCount = String.valueOf(count);
						if (count > 0) {
							returnString = "<span class='lkCnt' style='margin-right: 40px; margin-top: -4px;'>" + likeCount;
							if (count > 1) {
								returnString = returnString + " likes";
							} else {
								returnString = returnString + " like";
							}
							returnString = returnString + " - ";
							returnString = returnString + "</span>";
						} else {
							returnString = "";
						}
						returnString = returnString+"<a href='javascript:void(0);' onclick='likeMedia("+cmd.getMediaId()+ ", "+0+")' class='euInfoSelect' style='font-weight: bold;' ><i class='fa fa-thumbs-up' style='font-size: 18px;'></i></a>";
					} catch (ElementNotFoundException e) {
						//If no like exists, create a new like record
						documentLike = documentLikeFinder.newDocumentLike();
						documentLike.setDocumentId(cmd.getMediaId());
						documentLike.setPosterId(currentUserId);
						documentLike.update();     

						int count = documentLikeFinder.getLikeCountForDocument(cmd.getMediaId());;
						likeCount = String.valueOf(count);
						if (count > 0) {
							returnString = "<span class='lkCnt' style='margin-right: 40px; margin-top: -4px;'>" + likeCount;
							if (count > 1) {
								returnString = returnString + " likes";
							} else {
								returnString = returnString + " like";
							}
							returnString = returnString + " - ";
							returnString = returnString + "</span>";
						} else {
							returnString = "";
						}
						returnString = returnString+"<a href='javascript:void(0);' onclick='likeMedia("+cmd.getMediaId()+ ", "+0+")' class='euInfoSelect' style='font-weight: bold;' ><i class='fa fa-thumbs-down' style='font-size: 18px;'></i></a>";
					}
				} else if (cmd.getPhotoId() > 0){
					try {
						photoLike = photoLikeFinder.getLikeForPhotoAndUser(cmd.getPhotoId(), currentUserId);
						//If like exists, delete this like record...this is for unlike
						photoLike.delete();
						int count = photoLikeFinder.getLikeCountForPhoto(cmd.getPhotoId());
						likeCount = String.valueOf(count);
						if (count > 0) {
							returnString = "<span class='lkCnt' style='margin-right: 40px; margin-top: -4px;'>" + likeCount;
							if (count > 1) {
								returnString = returnString + " likes";
							} else {
								returnString = returnString + " like";
							}
							returnString = returnString + " - ";
							returnString = returnString + "</span>";
						} else {
							returnString = "";
						}
						returnString = returnString+"<a href='javascript:void(0);' onclick='likeMedia("+cmd.getPhotoId()+ ", "+0+")' class='euInfoSelect' style='font-weight: bold;' ><i class='fa fa-thumbs-up' style='font-size: 18px;'></i></a>";
					} catch (ElementNotFoundException e) {
						//If no like exists, create a new like record
						photoLike = photoLikeFinder.newPhotoLike();
						photoLike.setPhotoId(cmd.getPhotoId());
						photoLike.setPosterId(currentUserId);
						photoLike.update();     

						int count = photoLikeFinder.getLikeCountForPhoto(cmd.getPhotoId());
						likeCount = String.valueOf(count);
						if (count > 0) {
							returnString = "<span class='lkCnt' style='margin-right: 40px; margin-top: -4px;'>" + likeCount;
							if (count > 1) {
								returnString = returnString + " likes";
							} else {
								returnString = returnString + " like";
							}
							returnString = returnString + " - ";
							returnString = returnString + "</span>";
						} else {
							returnString = "";
						}
						returnString = returnString+"<a href='javascript:void(0);' onclick='likeMedia("+cmd.getPhotoId()+ ", "+0+")' class='euInfoSelect' style='font-weight: bold;' ><i class='fa fa-thumbs-down' style='font-size: 18px;'></i></a>";
					}
				} else {
					return null;
				}
			}

			HttpServletResponse resp = contextManager.getContext().getResponse();
			resp.setContentType("text/html");
			Writer out = resp.getWriter();
			out.write(returnString);
			out.close();
			return null;
		}
		return null;
	}

	public static class Command extends CommandBeanImpl implements CommandBean
	{
		private int commentId;
		private int mediaId;
		private int photoId;

		public int getCommentId() {
			return commentId;
		}

		public void setCommentId(int commentId) {
			this.commentId = commentId;
		}

		public int getMediaId() {
			return mediaId;
		}

		public void setMediaId(int mediaId) {
			this.mediaId = mediaId;
		}

		public int getPhotoId() {
			return photoId;
		}

		public void setPhotoId(int photoId) {
			this.photoId = photoId;
		}
	}

	/**
	 * @param contextManager the contextManager to set
	 */
	public void setContextHolder(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}

	public void setPhotoLikeFinder(PhotoLikeFinder photoLikeFinder) {
		this.photoLikeFinder = photoLikeFinder;
	}

	public void setDocumentLikeFinder(DocumentLikeFinder documentLikeFinder) {
		this.documentLikeFinder = documentLikeFinder;
	}

	public void setPhotoCommentLikeFinder(
			PhotoCommentLikeFinder photoCommentLikeFinder) {
		this.photoCommentLikeFinder = photoCommentLikeFinder;
	}

	public void setDocumentCommentLikeFinder(
			DocumentCommentLikeFinder documentCommentLikeFinder) {
		this.documentCommentLikeFinder = documentCommentLikeFinder;
	}
}