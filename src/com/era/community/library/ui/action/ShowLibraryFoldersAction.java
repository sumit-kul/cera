package com.era.community.library.ui.action;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.database.IndexedScrollerPage;
import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;
import support.community.framework.AbstractCommandAction;
import support.community.framework.IndexCommandBean;
import support.community.framework.IndexCommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.doclib.dao.DocumentCommentFinder;
import com.era.community.doclib.dao.DocumentFinder;
import com.era.community.doclib.dao.DocumentLibrary;
import com.era.community.doclib.dao.DocumentLibraryFeature;
import com.era.community.doclib.dao.FolderFinder;
import com.era.community.doclib.ui.dto.FolderDto;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/** 
 * @spring.bean name="/cid/[cec]/library/showLibraryFolders.do"
 * @spring.bean name="/cid/[cec]/library/showLibraryFolders.ajx"
 */
public class ShowLibraryFoldersAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager;
	protected DocumentLibraryFeature doclibFeature;
	protected SubscriptionFinder subscriptionFinder;
	protected DocumentCommentFinder documentCommentFinder;
	protected DocumentFinder documentFinder;
	protected FolderFinder folderFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();
		DocumentLibrary lib = (DocumentLibrary)doclibFeature.getFeatureForCommunity(context.getCurrentCommunity());

		String pageNum = context.getRequest().getParameter("jPage");
		int pNumber = 0;
		if (pageNum != null && !"".equals(pageNum)) {
			pNumber = Integer.parseInt(pageNum);
		}

		if (context.getRequest().getParameter("sortByOption") != null 
				&& !"".equals(context.getRequest().getParameter("sortByOption"))) {
			cmd.setSortByOption(context.getRequest().getParameter("sortByOption"));
		} else {
			cmd.setSortByOption("Most Recent");
		}
		
		QueryScroller scroller = folderFinder.listFoldersForLibrary(lib.getId());
		scroller.addScrollKey("STEMP.Created", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);

		if (pageNum != null) {
			HttpServletResponse resp = contextManager.getContext().getResponse();
			JSONObject json = null;
			if (pNumber > 0) {
				int userId = context.getCurrentUser() == null ? 0 : context.getCurrentUser().getId();
				scroller.setBeanClass(RowBean.class, this);
				scroller.setPageSize(18);
				IndexedScrollerPage page = scroller.readPage(pNumber);
				json = page.toJsonString(pNumber);
			} else {
				json = new JSONObject();
				json.put("pageNumber", pNumber);
				JSONArray jData = new JSONArray();
				json.put("aData", jData);
			}
			String jsonString = json.serialize();
			resp.setContentType("text/json");
			Writer out = resp.getWriter();
			out.write(jsonString);
			out.close();
			return null;
		} else {
			cmd.setPageCount(scroller.readPageCount());
			cmd.setRowCount(scroller.readRowCount());
			cmd.setSearchType("File");
			cmd.setQueryText(lib.getName());
			return new ModelAndView("library/showLibraryFolders");
		}
	}

	public class Command extends IndexCommandBeanImpl implements IndexCommandBean
	{
		private int totalDocCount;
		private int mediaCount;
		private int numberOfThemes;
		private String sortByOption;
		private int typeId;
		private int folderCount;
		private int folderPageCount;

		public boolean isMember() throws Exception
		{
			if (contextManager.getContext().getCurrentUser() == null ) return false;
			if (contextManager.getContext().isUserAuthenticated() == false) return false;
			Community community = contextManager.getContext().getCurrentCommunity();
			if (community == null) return false;
			int currentUser = contextManager.getContext().getCurrentUser().getId();
			return community.isMember(currentUser);
		}

		public List getSortByOptionOptions() throws Exception
		{
			List sortByOptionList = new ArrayList();
			sortByOptionList.add("Most Recent");
			sortByOptionList.add("Title");
			sortByOptionList.add("Author");
			sortByOptionList.add("Rating");
			return sortByOptionList;
		}

		public DocumentLibrary getDocumentLibrary() throws Exception
		{
			return (DocumentLibrary)doclibFeature.getFeatureForCurrentCommunity();
		}

		public boolean isCurrentUserSubscribed() throws Exception
		{
			try {
				if (contextManager.getContext().getCurrentUser() == null) return false; 
				subscriptionFinder.getDocLibSubscriptionForUser(getDocumentLibrary().getId(), contextManager.getContext().getCurrentUser().getId());
				return true;
			}
			catch (ElementNotFoundException x) {
				return false;
			}
		}

		public int getTotalDocCount()
		{
			return totalDocCount;
		}

		public void setTotalDocCount(int totalDocCount)
		{
			this.totalDocCount = totalDocCount;
		}

		public final int getNumberOfThemes()
		{
			return numberOfThemes;
		}

		public final void setNumberOfThemes(int numberOfThemes)
		{
			this.numberOfThemes = numberOfThemes;
		}

		public String getSortByOption() {
			return sortByOption;
		}

		public void setSortByOption(String sortByOption) {
			this.sortByOption = sortByOption;
		}

		public int getTypeId() {
			return typeId;
		}

		public void setTypeId(int typeId) {
			this.typeId = typeId;
		}

		public void setMediaCount(int mediaCount) {
			this.mediaCount = mediaCount;
		}

		public int getMediaCount() {
			return mediaCount;
		}

		public int getFolderCount() {
			return folderCount;
		}

		public void setFolderCount(int folderCount) {
			this.folderCount = folderCount;
		}

		public int getFolderPageCount() {
			int fCount = this.getFolderCount();
		    return fCount == 0 ? 0 : ((int)((fCount-1)/20))+1;
		}

		public void setFolderPageCount(int folderPageCount) {
			this.folderPageCount = folderPageCount;
		}

	}

	public class RowBean extends FolderDto
	{
		private int resultSetIndex;  

		public int getResultSetIndex()
		{
			return resultSetIndex;
		}

		public void setResultSetIndex(int resultSetIndex)
		{
			this.resultSetIndex = resultSetIndex;
		}

		public boolean isEvenRow()
		{
			return resultSetIndex % 4 == 0;
		}

		public boolean isOddRow()
		{
			return resultSetIndex % 4 != 0;
		}
	}

	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public final void setDoclibFeature(DocumentLibraryFeature doclibFeature)
	{
		this.doclibFeature = doclibFeature;
	}

	public void setSubscriptionFinder(SubscriptionFinder subscriptionFinder)
	{
		this.subscriptionFinder = subscriptionFinder;
	}

	public void setDocumentCommentFinder(DocumentCommentFinder documentCommentFinder)
	{
		this.documentCommentFinder = documentCommentFinder;
	}

	public void setDocumentFinder(DocumentFinder documentFinder) {
		this.documentFinder = documentFinder;
	}

	public FolderFinder getFolderFinder() {
		return folderFinder;
	}

	public void setFolderFinder(FolderFinder folderFinder) {
		this.folderFinder = folderFinder;
	}
}