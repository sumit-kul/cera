package com.era.community.admin.ui.action;

import java.io.InputStream;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

import support.community.database.BlobData;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.upload.dao.ImageFinder;

public class UpdateThumbnailTask extends AbstractCommandAction
{ 
	protected Log logger = LogFactory.getLog(getClass()); 
	protected String linkPrefix;
	protected UserFinder userFinder;
	protected CommunityFinder communityFinder;
	protected ImageFinder imageFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command)data;

		List<User> users = userFinder.getAllUser();
		int count  = 1;
		for (User user : users) {
			user = userFinder.getUserEntity(user.getId());
			try {
				if (user.isPhotoPresent()) {
					BlobData photoData = user.readPhoto();
					InputStream is = photoData.getStream();
					user.storePhoto(is);
					is.close();
				}
				if (user.isCoverPresent()) {
					BlobData coverData = user.readCover();
					InputStream isCover = coverData.getStream();
					user.storeCover(isCover);
					isCover.close();
				}

				System.out.println("^^^^^^^^^^ User Name : " + user.getFullName() + " Count : " +count);
			} catch (Exception e) {
				System.out.println("############## Failed User Name : " + user.getFullName() + " Count : " +count);
				e.printStackTrace();
			}
			count++;
		}

		List<Community> comms = communityFinder.getAllCommunities();

		int count2  = 1;
		for (Community com : comms) {
			com = communityFinder.getCommunityForId(com.getId());
			try {
				if (com.isLogoPresent()) {
					BlobData logoData = com.getCommunityLogo();
					InputStream is = logoData.getStream();
					com.storeCommunityLogo(is);
					is.close();
				}
				if (com.isBannerPresent()) {
					BlobData coverData = com.getCommunityBanner();
					InputStream isCover = coverData.getStream();
					com.storeCommunityBanner(isCover);
					isCover.close();
				}

				System.out.println("^^^^^^^^^^ Community Name : " + com.getName() + " Count : " +count);
			} catch (Exception e) {
				System.out.println("############## Failed Community Name : " + com.getName() + " Count : " +count);
				e.printStackTrace();
			}
			count2++;
		}

		/*List<Integer> lst = imageFinder.selectImagesForBlogEntry();
		int count  = 1;
		for(Integer ii : lst){
			Image img = imageFinder.getImageForId(ii);
			try {
				BlobData file = img.getFile();
				InputStream is = file.getStream();
				if (is != null) {
					BufferedImage input = ImageIO.read(is);
					BufferedImage out = Scalr.resize(input, 400, 400);
					ByteArrayOutputStream encoderOutputStream = new ByteArrayOutputStream();
					ImageIO.write(out, "JPEG", encoderOutputStream);
					byte[] resizedImageByteArray = encoderOutputStream.toByteArray();
					img.storeBlobThumbnail(new ByteArrayInputStream(resizedImageByteArray), encoderOutputStream.size(), "image/jpeg", out.getWidth(), out.getHeight());
					out.flush(); encoderOutputStream.flush();
					is.close();
				}
				


				System.out.println("^^^^^^^^^^ Image id : " + img.getId() + " Count : " +count);
			} catch (Exception e) {
				System.out.println("############## Failed Image id : " + img.getId() + " Count : " +count);
				e.printStackTrace();
			}
			count++;
		}*/
		
		/*List<Long> lst = imageFinder.selectImagesForWikiEntry();
		int count  = 1;
		for(Long ii : lst){
			Image img = imageFinder.getImageForId(ii.intValue());
			try {
				BlobData file = img.getFile();
				InputStream is = file.getStream();
				if (is != null) {
					BufferedImage input = ImageIO.read(is);
					BufferedImage out = Scalr.resize(input, 300, 300);
					ByteArrayOutputStream encoderOutputStream = new ByteArrayOutputStream();
					ImageIO.write(out, "JPEG", encoderOutputStream);
					byte[] resizedImageByteArray = encoderOutputStream.toByteArray();
					img.storeBlobThumbnail(new ByteArrayInputStream(resizedImageByteArray), encoderOutputStream.size(), "image/jpeg", out.getWidth(), out.getHeight());
					out.flush(); encoderOutputStream.flush();
					is.close();
				}
				


				System.out.println("^^^^^^^^^^ Image id : " + img.getId() + " Count : " +count);
			} catch (Exception e) {
				System.out.println("############## Failed Image id : " + img.getId() + " Count : " +count);
				e.printStackTrace();
			}
			count++;
		}*/

		return null;
	}

	public static class Command extends CommandBeanImpl implements CommandBean
	{

	}

	public void setLogger(Log logger) {
		this.logger = logger;
	}

	public String getLinkPrefix() {
		return linkPrefix;
	}

	public void setLinkPrefix(String linkPrefix) {
		this.linkPrefix = linkPrefix;
	}

	public void setCommunityFinder(CommunityFinder communityFinder) {
		this.communityFinder = communityFinder;
	}

	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

	public ImageFinder getImageFinder() {
		return imageFinder;
	}

	public void setImageFinder(ImageFinder imageFinder) {
		this.imageFinder = imageFinder;
	}
}