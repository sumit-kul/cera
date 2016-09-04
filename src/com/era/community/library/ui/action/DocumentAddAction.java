package com.era.community.library.ui.action;

import java.io.Writer;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandValidator;
import support.community.util.StringFormat;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.base.Taggable;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityActivity;
import com.era.community.communities.dao.CommunityActivityFinder;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.config.dao.MailMessageConfig;
import com.era.community.doclib.dao.DocGroupFinder;
import com.era.community.doclib.dao.Document;
import com.era.community.doclib.dao.DocumentFinder;
import com.era.community.doclib.dao.DocumentLibrary;
import com.era.community.doclib.dao.DocumentLibraryFeature;
import com.era.community.doclib.dao.DocumentLibraryFinder;
import com.era.community.doclib.dao.Folder;
import com.era.community.doclib.dao.FolderFinder;
import com.era.community.doclib.ui.dto.DocumentDto;
import com.era.community.doclib.ui.validator.DocumentValidator;
import com.era.community.monitor.dao.DocLibSubscription;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserActivity;
import com.era.community.pers.dao.UserActivityFinder;
import com.era.community.pers.dao.UserFinder;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 * @spring.bean name="/cid/[cec]/library/addDocument.do"
 * @spring.bean name="/cid/[cec]/library/addDocument.ajx"
 */
public class DocumentAddAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager;
	protected DocumentLibraryFeature doclibFeature;
	protected MailSender mailSender;
	protected MailMessageConfig mailMessageConfig;
	protected CommunityActivityFinder communityActivityFinder;
	protected UserActivityFinder userActivityFinder;
	protected DocGroupFinder docGroupFinder;
	protected DocumentLibraryFinder doclibFinder;
	protected SubscriptionFinder subscriptionFinder;
	protected DocumentFinder documentFinder;
	protected FolderFinder folderFinder;
	protected UserFinder userFinder;
	protected CommunityFinder communityFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		try {
			Command cmd = (Command) data;
			CommunityEraContext context = contextManager.getContext();
			User currUser = context.getCurrentUser();
			HttpServletResponse resp = context.getResponse();
			int folderId = cmd.getFolderId();
			int coverPhotoId = 0;
			if (context.getCurrentUser() == null ) {
				String reqUrl = context.getRequestUrl();
				if(reqUrl != null) {
					context.getRequest().getSession().setAttribute("url_prior_login", reqUrl);
				}
				return new ModelAndView("redirect:/login.do?redirect=" + StringFormat.escape(context.getRequestUrl()), "command", cmd);
			} else {
				DocumentLibrary lib = doclibFinder.getDocumentLibraryForCommunity(context.getCurrentCommunity());
				Folder folder = null;
				if (cmd.getType() != null && "folder".equalsIgnoreCase(cmd.getType())) {
					String title = cmd.getTitle();
					String description = cmd.getDescription();
					folder = folderFinder.newFolder();
					folder.setTitle(title);
					folder.setDescription(description);
					folder.setOwnerId(context.getCurrentUser().getId());
					folder.setPrivacyLevel(0);
					folder.setLibraryId(cmd.getLibraryId());
					folder.update();
					folderId = folder.getId();
					folder = folderFinder.getFolderForId(folderId);
				}

				if (folder == null && folderId > 0) {
					folder = folderFinder.getFolderForId(folderId);
				}
				
				int grSeq = 0;
				CommunityActivity communityActivity = null;
				try {
					communityActivity = communityActivityFinder.getMostRecentDocumentGroupActivity(context.getCurrentCommunity().getId()
							,folderId > 0 ? folderId : cmd.getFolderId());
						grSeq = communityActivity.getDocGroupNumber()+1;
						communityActivity = communityActivityFinder.newCommunityActivity();
						communityActivity.setCommunityId(context.getCurrentCommunity().getId());
						communityActivity.setFolderId(folder == null ? 0 : folder.getId());
						communityActivity.setDocGroupNumber(grSeq);
						communityActivity.setUserId(context.getCurrentUser().getId());
						communityActivity.update();
						
						UserActivity uActivity = userActivityFinder.newUserActivity();
						uActivity.setCommunityActivityId(communityActivity.getId());
						uActivity.setUserId(communityActivity.getUserId());
						uActivity.update();
				} catch (ElementNotFoundException e) {
					communityActivity = communityActivityFinder.newCommunityActivity();
					communityActivity.setCommunityId(context.getCurrentCommunity().getId());
					communityActivity.setFolderId(folder == null ? 0 : folder.getId());
					communityActivity.setDocGroupNumber(1);
					communityActivity.setUserId(context.getCurrentUser().getId());
					communityActivity.update();
					
					UserActivity uActivity = userActivityFinder.newUserActivity();
					uActivity.setCommunityActivityId(communityActivity.getId());
					uActivity.setUserId(communityActivity.getUserId());
					uActivity.update();
					grSeq = 1;
				}

				List <CommonsMultipartFile> files = cmd.getFiles();
				List<Document> addedFiles = new ArrayList<Document>();
				Document lastPhoto = null;
				for (CommonsMultipartFile file : files) {
					String fileName = file.getOriginalFilename();
					String title = fileName.substring(0, fileName.lastIndexOf("."));
					String ext = fileName.substring(fileName.lastIndexOf("."));
					if (title.length() > 120) {
						title = title.substring(0, 119).concat("...");
					}
					if (fileName.length() > 20) {
						fileName = fileName.substring(0, 19).concat(ext);
					}
					Document doc = documentFinder.newDocument();
					doc.setFileName(fileName);
					doc.setTitle(title);
					doc.setFileLength((Long.valueOf(file.getSize()).intValue()));
					doc.setFileContentType(file.getContentType());
					doc.setPosterId(context.getCurrentUser().getId());
					doc.setDocGroupNumber(grSeq);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date now = new Date();
					String dt = sdf.format(now);
					Timestamp ts = Timestamp.valueOf(dt);
					doc.setModified(ts);
					if (folderId > 0) {
						doc.setFolderId(folderId);
					} else {
						doc.setFolderId(cmd.getFolderId());
					}
					
					doc.setLibraryId(lib.getId());
					doc.setPrivacyLevel(0);
					doc.update();
					try {
						doc.storeFile(file);
					} catch (Exception e) {
						e.printStackTrace();
					}
					addedFiles.add(doc);
					lastPhoto = doc;
				}
				
				if(folder == null && lastPhoto != null && communityActivity != null){
					communityActivity.setDocumentId(lastPhoto.getId());
					communityActivity.update();
				}
				
				if (folder != null && lastPhoto != null) {
					if (folder.getCoverPhotoId() == 0) {
						coverPhotoId = lastPhoto.getId();
						folder.setCoverPhotoId(coverPhotoId);
						folder.update();
					} else {
						try {
							Document media = documentFinder.getDocumentForId(folder.getCoverPhotoId());
						} catch (Exception e) {
							coverPhotoId = lastPhoto.getId();
							folder.setCoverPhotoId(coverPhotoId);
							folder.update();
						}
					}
				}

				if (folder != null && "createFolder".equals(cmd.getAction())) {
					JSONObject json = toJsonString(folder, addedFiles, currUser, cmd.getCommunityId());
					String jsonString = json.serialize();
					resp.setContentType("text/json");
					Writer out = resp.getWriter();
					out.write(jsonString);
					out.close();
				}

				mailSubscribers(addedFiles, context);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private JSONObject toJsonString(Folder folder, List<Document> addedPhotos, User currUser, int communityId) throws Exception
	{
		JSONObject json = new JSONObject();
		json.put("albumId", folder.getId());
		json.put("title", folder.getTitle());
		json.put("description", folder.getDescription());
		json.put("ownerId", folder.getOwnerId());
		json.put("privacyLevel", folder.getPrivacyLevel());
		json.put("coverPhotoId", folder.getCoverPhotoId());
		JSONArray data = new JSONArray();
		for (Document photo : addedPhotos) {
			JSONObject row = new JSONObject();
			row.put("id", photo.getId());
			row.put("title", photo.getTitle());
			row.put("description", photo.getDescription());
			row.put("ownerId", photo.getPosterId());
			row.put("albumId", photo.getFolderId());
			row.put("privacyLevel", photo.getPrivacyLevel());
			row.put("isEditAllowed", isEditAllowed(currUser, photo.getPosterId(), communityId));
			data.add(row);
		}
		json.put("aData", data);
		return json;
	}

	private boolean isEditAllowed(User current, int userId, int commId) throws Exception {
		if (current != null && current.getId() == userId){
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

	protected String getView()
	{
		return "library/addDocument";
	}

	public class Command extends DocumentDto implements CommandBean, Taggable
	{
		private String type;
		private String action;
		private int communityId;

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

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getAction() {
			return action;
		}

		public void setAction(String action) {
			this.action = action;
		}

		public int getCommunityId() {
			return communityId;
		}

		public void setCommunityId(int communityId) {
			this.communityId = communityId;
		}
	}

	public class Validator extends DocumentValidator
	{
		public String validateUpload(Object value, CommandBean cmd)
		{
			String err = "Please include an attachment for your document";

			if (value == null)
				return err;

			if (!(value instanceof MultipartFile))
				return "Value is " + value.getClass().getName()
				+ " when a MutipartFile is expected. The enclosing form tag may not be set to multipart";

			MultipartFile file = (MultipartFile) value;
			if (file.isEmpty())
				return err;

			return null;
		}

	}

	private void mailSubscribers(List<Document> addedFiles, CommunityEraContext context) throws Exception
	{
		Document doc = addedFiles.get(0);
		List subs = subscriptionFinder.getSubscriptionsForDocLib(doc.getLibraryId());

		Iterator it = subs.iterator();

		/* Loop thru the list of Library Subscriptions */
		while (it.hasNext()) {
			DocLibSubscription sub = (DocLibSubscription) it.next();

			if (sub.getFrequency() == 0) {       // Only email the subscribers who want immediate alerts

				/*
				 * Email the doclib subscribers who have set immediate to alert that a new document has been posted
				 * Skip the current user
				 */
				int uid = sub.getUserId();
				if (uid == context.getCurrentUser().getId())
					continue;
				User subscriber = userFinder.getUserEntity(uid);

				/*
				 * Parameters to substitute into the body text of the Email.
				 */
				Map<String, String> params = new HashMap<String, String>(11);

				String sLink = context.getCurrentCommunityUrl() + "/library/documentdisplay.do?id=" + doc.getId();
				String sUnSubscribe = context.getContextUrl() + "reg/watch.do";

				params.put("#communityName#", context.getCurrentCommunity().getName());
				params.put("#docLink#", sLink);
				params.put("#docTitle#", doc.getTitle());
				params.put("#sUnSubscribe#", sUnSubscribe);


				/*
				 * Create and send the mail message.
				 */
				SimpleMailMessage msg = mailMessageConfig.createMailMessage("new-document-alert", params);
				msg.setTo(subscriber.getEmailAddress());
				try {
					if (!subscriber.isSuperAdministrator()) {
						mailSender.send(msg);
					}
				} catch (RuntimeException e) {
					e.printStackTrace();
				}
			}

		}
	}

	protected CommandValidator createValidator()
	{
		return new Validator();
	}

	public final void setDocumentFinder(DocumentFinder documentFinder)
	{
		this.documentFinder = documentFinder;
	}

	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public final MailSender getMailSender()
	{
		return mailSender;
	}

	public final void setMailSender(MailSender mailSender)
	{
		this.mailSender = mailSender;
	}

	public final void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}

	public final void setSubscriptionFinder(SubscriptionFinder subscriptionFinder)
	{
		this.subscriptionFinder = subscriptionFinder;
	}

	public final void setMailMessageConfig(MailMessageConfig mailMessageConfig)
	{
		this.mailMessageConfig = mailMessageConfig;
	}

	public final void setDoclibFinder(DocumentLibraryFinder doclibFinder)
	{
		this.doclibFinder = doclibFinder;
	}

	public final DocumentLibraryFinder getDoclibFinder()
	{
		return doclibFinder;
	}

	public final void setDoclibFeature(DocumentLibraryFeature doclibFeature)
	{
		this.doclibFeature = doclibFeature;
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