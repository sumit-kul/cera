package com.era.community.doclib.ui.action;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.CommunityActivityFinder;
import com.era.community.doclib.dao.Document;
import com.era.community.doclib.dao.DocumentFinder;
import com.era.community.doclib.dao.DocumentLibrary;
import com.era.community.doclib.dao.DocumentLibraryFinder;
import com.era.community.doclib.ui.dto.DocumentDto;

/**
 *
 * @spring.bean name="/cid/[cec]/library/deleteDocument.do"
 * @spring.bean name="/cid/[cec]/library/deleteDocument.ajx"
 */
public class DocumentDeleteAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager;
	private DocumentFinder documentFinder; 
	protected CommunityActivityFinder communityActivityFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();
		try {
			Document doc = documentFinder.getDocumentForId(cmd.getId());
			if ( doc != null && doc.isWriteAllowed( context.getCurrentUserDetails() )) {
				int docCount = documentFinder.countPhotoListForHeader(doc.getLibraryId(), doc.getFolderId(), doc.getPosterId(), doc.getDocGroupNumber());
				if (docCount == 1) {
					communityActivityFinder.clearCommunityActivityForDocument(doc.getId(), doc.getFolderId(), context.getCurrentCommunity().getId());
				}
				doc.delete();
				return new ModelAndView("doclib/doc-delete-confirm");
			}
			else {
				return null;
			}
		} catch (ElementNotFoundException e) {
			return null;
		}
	}

	public static class Command extends DocumentDto implements CommandBean
	{
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}
	public void setDocumentFinder(DocumentFinder documentFinder)
	{
		this.documentFinder = documentFinder;
	}
	public void setCommunityActivityFinder(
			CommunityActivityFinder communityActivityFinder) {
		this.communityActivityFinder = communityActivityFinder;
	}
}