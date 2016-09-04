package com.era.community.doclib.ui.action;

import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.database.BlobData;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.doclib.dao.Document;
import com.era.community.doclib.dao.DocumentFinder;
import com.era.community.pers.dao.User;
import com.ibm.json.java.JSONObject;

/**
 *  @spring.bean name="/library/shareDocument.ajx" 
 */
public class ShareFileAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager; 
	private DocumentFinder documentFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command)data; 
		CommunityEraContext context = contextManager.getContext();
		HttpServletResponse resp = context.getResponse();
		User currUser = context.getCurrentUser();
		if (currUser != null) {
			String[] myJsonData = context.getRequest().getParameterValues("json[]");
			if(myJsonData != null && myJsonData.length > 0) {
				for (int i=0; i < myJsonData.length; ++i) {
					String fId = myJsonData[i];
					int fileId = Integer.parseInt(fId);
					Document doc = documentFinder.getDocumentForId(fileId);
					
					BlobData file = doc.getFile();
					
					Document newDocument = documentFinder.newDocument();
					newDocument.setFileName(doc.getFileName());
					newDocument.setTitle(doc.getTitle());
					newDocument.setPosterId(currUser.getId());
					newDocument.setLibraryId(cmd.getLibraryId());
					newDocument.setFolderId(cmd.getFolderId());
					newDocument.setFileContentType(file.getContentType());
					newDocument.update();
					
					newDocument.storeFile(file.getStream(), file.getLength(), file.getContentType());
				}
			}

			JSONObject json = new JSONObject();
			String jsonString = json.serialize();
			resp.setContentType("text/json");
			Writer out = resp.getWriter();
			out.write(jsonString);
			out.close();
		}
		return null;
	}

	public class Command extends CommandBeanImpl implements CommandBean
	{
		private int fileId;
		private int libraryId;
		private int folderId;

		public int getFileId() {
			return fileId;
		}

		public void setFileId(int fileId) {
			this.fileId = fileId;
		}

		public int getLibraryId() {
			return libraryId;
		}

		public void setLibraryId(int libraryId) {
			this.libraryId = libraryId;
		}

		public int getFolderId() {
			return folderId;
		}

		public void setFolderId(int folderId) {
			this.folderId = folderId;
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setContextManager(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}

	public DocumentFinder getDocumentFinder() {
		return documentFinder;
	}

	public void setDocumentFinder(DocumentFinder documentFinder) {
		this.documentFinder = documentFinder;
	}
}