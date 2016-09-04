package com.era.community.communities.ui.action;

import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

import support.community.database.BlobData;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.doclib.dao.Document;
import com.era.community.doclib.dao.DocumentFinder;
import com.era.community.doclib.dao.DocumentLibrary;
import com.era.community.doclib.dao.DocumentLibraryFeature;

/**
 * 
 * @spring.bean name="/commLogoDisplay.img"
 */
public class CommunityLogoDisplayAction extends AbstractCommandAction
{   
	protected Log logger = LogFactory.getLog(getClass());
	
	protected CommunityEraContextManager contextManager;
	protected CommunityFinder communityFinder;
	protected DocumentFinder documentFinder;
	protected DocumentLibraryFeature doclibFeature;

	protected ModelAndView handle(Object data) throws Exception
	{
		logger.error("Inside CommunityLogoDisplayAction");
		Command cmd = (Command)data;
		CommunityEraContext context = contextManager.getContext();
		int commId = cmd.getCommunityId();
		if (commId> 0) {
			Community comm = communityFinder.getCommunityForId(commId);
			
			if (context.getRequest().getParameter("showType") != null 
					&& !"".equals(context.getRequest().getParameter("showType"))) {
				cmd.setShowType(context.getRequest().getParameter("showType"));
			}

			try {
				BlobData logo = null;
				if ("editThimbnail".equalsIgnoreCase(cmd.getType())) {
					DocumentLibrary lib = (DocumentLibrary)doclibFeature.getFeatureForCommunity(comm);

					if ("Banner".equalsIgnoreCase(cmd.getImgType())) {
						Document doc = documentFinder.getCurrentProfileBanner(lib.getId());
						logo = doc.getFile();
					} else {
						Document doc = documentFinder.getCurrentProfileLogo(lib.getId());
						logo = doc.getFile();
					}
				} else {
					if ("Banner".equalsIgnoreCase(cmd.getImgType())) {
						if ("m".equals(cmd.getShowType())) {
							logo = comm.getCommunityBanner();
						} else {
							logo = comm.getCommunityBannerThumb();
						}
					} else {
						if ("m".equals(cmd.getShowType())) {
							logo = comm.getCommunityLogo();
						} else {
							logo = comm.getCommunityLogoThumb();
						}
					}
				}
				if (logo.isEmpty()) return null;

				InputStream is = logo.getStream();

				HttpServletResponse response = contextManager.getContext().getResponse();

				String type = logo.getContentType();
				response.setContentType(type==null?"image/jpeg":type);
				response.setContentLength((int)logo.getLength());

				OutputStream out = response.getOutputStream();      
				logger.error("out OutputStream");
				int i;
				byte[] buf = new byte[4096];
				while ((i=is.read(buf, 0, buf.length))>0) out.write(buf,0,i);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.error("out CommunityLogoDisplayAction");
		return null;
	}

	public static class Command extends CommandBeanImpl implements CommandBean
	{
		private int Id;
		private int communityId;
		private String type;
		private String imgType;
		private String showType;

		public final int getId()
		{
			return Id;
		}
		public final void setId(int id)
		{
			Id = id;
		}
		/**
		 * @return the communityId
		 */
		public int getCommunityId() {
			return communityId;
		}
		/**
		 * @param communityId the communityId to set
		 */
		public void setCommunityId(int communityId) {
			this.communityId = communityId;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getImgType() {
			return imgType;
		}
		public void setImgType(String imgType) {
			this.imgType = imgType;
		}
		public String getShowType() {
			return showType;
		}
		public void setShowType(String showType) {
			this.showType = showType;
		}
	}

	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public final void setCommunityFinder(CommunityFinder communityFinder)
	{
		this.communityFinder = communityFinder;
	}

	public void setDocumentFinder(DocumentFinder documentFinder) {
		this.documentFinder = documentFinder;
	}

	public void setDoclibFeature(DocumentLibraryFeature doclibFeature) {
		this.doclibFeature = doclibFeature;
	}
}