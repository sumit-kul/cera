package com.era.community.common.ui.action;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.doclib.dao.Document;
import com.era.community.doclib.dao.DocumentFinder;
import com.era.community.media.dao.Photo;
import com.era.community.media.dao.PhotoFinder;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 * @spring.bean name="/common/mediaPannel.ajx"
 */
public class MediaPannelAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager;
	protected DocumentFinder documentFinder;
	protected PhotoFinder photoFinder;

	@Override
	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();

		HttpServletResponse resp = context.getResponse();
		//User user = context.getCurrentUser();

		JSONObject json = new JSONObject();
		JSONArray aData = new JSONArray();
		List medias = new ArrayList();
		if (cmd.getCommunityId() != 0){
			medias = documentFinder.getDocumentForMediaPanel(cmd.getCommunityId(), 9);
			for (Iterator iterator = medias.iterator(); iterator.hasNext();) {
				Document document = (Document) iterator.next();
				aData.add(toJsonStringForMedia(document.getFileName(), document.getId()));
			}
		} else if (cmd.getProfileId() != 0) {
			medias = photoFinder.getPhotoForMediaPanel(cmd.getProfileId(), 9);
			for (Iterator iterator = medias.iterator(); iterator.hasNext();) {
				Photo photo = (Photo) iterator.next();
				aData.add(toJsonStringForMedia(photo.getFileName(), photo.getId()));
			}
		}

		json.put("aData", aData);
		String jsonString = json.serialize();
		resp.setContentType("text/json");
		Writer out = resp.getWriter();
		out.write(jsonString);
		out.close();
		return null;
	}

	private JSONObject toJsonStringForMedia(String name, int mediaId) throws Exception
	{
		JSONObject row = new JSONObject();
		row.put("name", name);
		row.put("mediaId", mediaId);
		return row;
	}

	public static class Command extends CommandBeanImpl implements CommandBean
	{
		private int communityId;
		private int profileId;

		public int getCommunityId() {
			return communityId;
		}

		public void setCommunityId(int communityId) {
			this.communityId = communityId;
		}

		public int getProfileId() {
			return profileId;
		}

		public void setProfileId(int profileId) {
			this.profileId = profileId;
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setDocumentFinder(DocumentFinder documentFinder) {
		this.documentFinder = documentFinder;
	}

	public void setPhotoFinder(PhotoFinder photoFinder) {
		this.photoFinder = photoFinder;
	}
}