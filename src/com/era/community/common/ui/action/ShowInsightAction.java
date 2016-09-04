package com.era.community.common.ui.action;

import java.io.Writer;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.database.EntityWrapper;
import support.community.database.IndexedScrollerPage;
import support.community.database.QueryScroller;
import support.community.framework.AbstractCommandAction;
import support.community.framework.IndexCommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.common.dao.InsightFinder;
import com.era.community.common.dao.generated.InsightEntity;
import com.era.community.common.ui.action.ShowUserSessionsAction.RowBean;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 * @spring.bean name="/common/showInsight.do"
 * @spring.bean name="/common/showInsight.ajx" 
 * 
 */
public class ShowInsightAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager;
	protected InsightFinder insightFinder;

	@Override
	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		Command cmd = (Command)data;

		String pageNum = context.getRequest().getParameter("jPage");
		int pNumber = 0;
		if (pageNum != null && !"".equals(pageNum)) {
			pNumber = Integer.parseInt(pageNum);
		}
		
		String sId = context.getRequest().getParameter("sessionId");
		int sessionId = 0;
		if (sId != null && !"".equals(sId)) {
			sessionId = Integer.parseInt(sId);
		} else {
			sessionId = cmd.getSessionId();
		}

		QueryScroller scroller = null;
		scroller = insightFinder.viewInsightForSession(sessionId);

		scroller.setBeanClass(RowBean.class, this);
		scroller.setPageSize(500);
		//pagination
		cmd.setPageCount(scroller.readPageCount());

		if (pageNum != null) {
			HttpServletResponse resp = contextManager.getContext().getResponse();
			JSONObject json = null;
			if (pNumber > 0) {
				scroller.setBeanClass(RowBean.class, this);
				scroller.setPageSize(500);
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
			scroller.setPageSize(500);
			cmd.setPageCount(scroller.readPageCount());
			cmd.setRowCount(scroller.readRowCount());
			return new ModelAndView("common/showInsight");
		}
	}

	public class RowBean extends InsightEntity implements EntityWrapper
	{
		private int resultSetIndex;
		private String ip;
		private String previousPage;

		public boolean isEvenRow()
		{
			return resultSetIndex % 2 == 0;
		}

		public boolean isOddRow()
		{
			return resultSetIndex % 2 == 1;
		}

		public int getResultSetIndex() {
			return resultSetIndex;
		}

		public void setResultSetIndex(int resultSetIndex) {
			this.resultSetIndex = resultSetIndex;
		}

		public String getIp() {
			return ip;
		}

		public void setIp(String ip) {
			this.ip = ip;
		}

		public String getPreviousPage() {
			return previousPage;
		}

		public void setPreviousPage(String previousPage) {
			this.previousPage = previousPage;
		}
	}

	public class Command extends IndexCommandBeanImpl
	{
		private String sortByOption = "1";
		private int sessionId;

		public String getSortByOption() {
			return sortByOption;
		}

		public void setSortByOption(String sortByOption) {
			this.sortByOption = sortByOption;
		}

		public int getSessionId() {
			return sessionId;
		}

		public void setSessionId(int sessionId) {
			this.sessionId = sessionId;
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setInsightFinder(InsightFinder insightFinder) {
		this.insightFinder = insightFinder;
	}
}