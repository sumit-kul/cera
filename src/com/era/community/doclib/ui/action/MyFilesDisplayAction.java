package com.era.community.doclib.ui.action;

import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.database.EntityWrapper;
import support.community.database.IndexedScrollerPage;
import support.community.database.QueryScroller;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContextManager;
import com.era.community.doclib.dao.DocumentFinder;
import com.era.community.doclib.dao.generated.DocumentEntity;
import com.era.community.pers.dao.User;
import com.ibm.json.java.JSONObject;

/**
 *  @spring.bean name="/library/myFilesDisplay.ajx" 
 */
public class MyFilesDisplayAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager; 
	private DocumentFinder documentFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command)data; 
		HttpServletResponse resp = contextManager.getContext().getResponse();
		User currentUser = contextManager.getContext().getCurrentUser();
		if (currentUser != null) {
			String pageNum = contextManager.getContext().getRequest().getParameter("mPage");
			int pNumber = 0;
			if (pageNum != null && !"".equals(pageNum)) {
				pNumber = Integer.parseInt(pageNum);
			}
			QueryScroller scroller = null;
			if(cmd.getTypeId() == 1 && cmd.getCommunityId() > 0){
				scroller = documentFinder.getDocumentsFromOtherCommunity(currentUser.getId(), cmd.getCommunityId(), cmd.getSearchString());
			} else if(cmd.getTypeId() == 2){
				scroller = documentFinder.getDocumentsFromMyConnection(currentUser.getId(), cmd.getSearchString());
			} else if(cmd.getTypeId() == 3){
				
			} else{
				scroller = documentFinder.getDocumentsForPoster(currentUser.getId(), cmd.getSearchString());
			}
			
			if (cmd.getSearchString() != null && !"".equals(cmd.getSearchString())) {
				cmd.setPageSize(99);
			} else {
				cmd.setPageSize(20);
			}
			
			if (pageNum != null) {
				JSONObject json = null;
				if (pNumber > 0) {
					scroller.setBeanClass(RowBean.class, this);
					scroller.setPageSize(cmd.getPageSize());
					IndexedScrollerPage page = scroller.readPage(pNumber);
					json = page.toJsonString(pNumber);
					json.put("totalPage", scroller.readPageCount());
					cmd.setPage(cmd.getPage() + 1);
				} 
				String jsonString = json.serialize();
				resp.setContentType("text/json");
				Writer out = resp.getWriter();
				out.write(jsonString);
				out.close();
				return null;
			}
		} else {
			JSONObject json = new JSONObject();
			resp.setContentType("text/json");
			Writer out = resp.getWriter();
			out.write(json.serialize());
			out.close();
		}
		return null;
	}
	
	public class RowBean extends DocumentEntity implements EntityWrapper
	{
		private int resultSetIndex;
		private int level = 1;

	}

	public class Command extends CommandBeanImpl implements CommandBean
	{
		private int typeId;
		private int communityId;
		private String searchString;
		private IndexedScrollerPage scrollerPage;

		public int getTypeId() {
			return typeId;
		}

		public void setTypeId(int typeId) {
			this.typeId = typeId;
		}

		public IndexedScrollerPage getScrollerPage() {
			return scrollerPage;
		}

		public void setScrollerPage(IndexedScrollerPage scrollerPage) {
			this.scrollerPage = scrollerPage;
		}

		public int getCommunityId() {
			return communityId;
		}

		public void setCommunityId(int communityId) {
			this.communityId = communityId;
		}

		public String getSearchString() {
			return searchString;
		}

		public void setSearchString(String searchString) {
			this.searchString = searchString;
		}

	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setDocumentFinder(DocumentFinder documentFinder) {
		this.documentFinder = documentFinder;
	}

}