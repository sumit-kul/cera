package com.era.community.library.ui.action;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.database.IndexedScrollerPage;
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
import com.era.community.doclib.ui.dto.DocumentDto;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/** 
 * @spring.bean name="/cid/[cec]/library/showLibraryItems.do"
 */
public class ShowLibraryItemsAction extends AbstractCommandAction
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
		QueryScroller scroller = null;
		//QueryScroller myDocScroller = null;

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

		boolean ignoreThemes = true;

		if (cmd.getSortByOption() == null || cmd.getSortByOption().equalsIgnoreCase("Most Recent")) {
			scroller = lib.listDocumentsByDate(0, RowBean.class, ignoreThemes); 
		}
		else if (cmd.getSortByOption().equalsIgnoreCase("Title") ) {
			scroller = lib.listDocumentsByTitle(0, RowBean.class, ignoreThemes); 
		}
		/*else if (context.getCurrentUser() != null && cmd.getSortByOption().equalsIgnoreCase("mydocs")) {
            myDocScroller = lib.listDocumentsForCurrentUser(context.getCurrentUser(),RowBean.class,0, ignoreThemes); 
            }*/
		else if (cmd.getSortByOption().equalsIgnoreCase("Author")) {
			scroller = lib.listDocumentsByAuthor(0, RowBean.class, ignoreThemes);
		}
		else if (cmd.getSortByOption().equalsIgnoreCase("Rating")) {
			scroller = lib.listDocumentsByRating(0,RowBean.class, ignoreThemes);
		}
		else {
			throw new Exception("Invalid order "+cmd.getSortByOption());
		}
		if(scroller != null){
			scroller.setBeanClass(RowBean.class, this);
			cmd.setTotalDocCount( scroller.readRowCount());
		}

		/* Return different scroller depending upon view selected by user */
		/*if (cmd.getOrder() != null) {
            if (cmd.getOrder().equalsIgnoreCase("mydocs")) {
                myDocScroller.setBeanClass(RowBean.class, this);
                allScroller = lib.listDocumentsByDate(0, RowBean.class, ignoreThemes); 
                cmd.setTotalDocCount( allScroller.readRowCount() );
                return myDocScroller;
            }        
            }*/

		if (pageNum != null) {
			HttpServletResponse resp = contextManager.getContext().getResponse();
			JSONObject json = null;
			if (pNumber > 0) {
				scroller.setBeanClass(RowBean.class, this);
				scroller.setPageSize(24);
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
			int mediaCount = documentFinder.getMediaCountForCommunity(context.getCurrentCommunity());
			cmd.setMediaCount(mediaCount);
			scroller.setPageSize(24);
			cmd.setPageCount(scroller.readPageCount());
			cmd.setRowCount(scroller.readRowCount());
			cmd.setScrollerPage(scroller.readPage(1));
			cmd.setSearchType("File");
			cmd.setQueryText(lib.getName());
			
			int folderCount = folderFinder.countFoldersForLibrary(lib.getId());
			cmd.setFolderCount(folderCount);
			return new ModelAndView("library/showLibraryItems");
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
		private String filterTagList = "";

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

		/**
		 * @return the sortByOption
		 */
		public String getSortByOption() {
			return sortByOption;
		}

		/**
		 * @param sortByOption the sortByOption to set
		 */
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

		public String getFilterTagList() {
			return filterTagList;
		}

		public void setFilterTagList(String filterTagList) {
			this.filterTagList = filterTagList;
		}

	}

	public class RowBean extends DocumentDto
	{
		private int resultSetIndex;  
		private int documentCommentCount;

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
			return resultSetIndex % 3 == 0;
		}

		public boolean isOddRow()
		{
			return resultSetIndex % 3 != 0;
		}

		public int getDocumentCommentCount() throws Exception
		{
			return documentCommentFinder.getDocumentCommentCountForDocument(getId());
		}

		public void setDocumentCommentCount(int documentCommentCount)
		{
			this.documentCommentCount = documentCommentCount;
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