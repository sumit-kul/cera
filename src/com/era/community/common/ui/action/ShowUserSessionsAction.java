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
import com.era.community.common.dao.UserSessionFinder;
import com.era.community.common.dao.generated.UserSessionEntity;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 * @spring.bean name="/common/showUserSessions.do"
 * @spring.bean name="/common/showUserSessions.ajx" 
 * 
 */
public class ShowUserSessionsAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager;
	protected UserSessionFinder userSessionFinder;

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

		QueryScroller scroller = null;
		scroller = userSessionFinder.findAllUserSessions(cmd.getSortByOption());

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
			int cnt = userSessionFinder.gettotalForIP();
			cmd.setTotalIP(cnt);
			scroller.setPageSize(500);
			cmd.setPageCount(scroller.readPageCount());
			cmd.setRowCount(scroller.readRowCount());
			return new ModelAndView("common/showUserSessions");
		}
	}

	public class RowBean extends UserSessionEntity implements EntityWrapper
	{
		private int resultSetIndex;
		private double stayTime;
		private int screenCounts;
		private String userName;

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

		public double getStayTime() {
			return stayTime;
		}

		public void setStayTime(BigDecimal stayTime) {
			if (stayTime == null) {
				this.stayTime =  0.0;
			} else {
				this.stayTime = stayTime.doubleValue();
			}
		}

		public int getScreenCounts() {
			return screenCounts;
		}

		public void setScreenCounts(Long screenCounts) {
			this.screenCounts = screenCounts.intValue();
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}
	}

	public class Command extends IndexCommandBeanImpl
	{
		private String sortByOption = "1";
		private int totalIP;

		public String getSortByOption() {
			return sortByOption;
		}

		public void setSortByOption(String sortByOption) {
			this.sortByOption = sortByOption;
		}

		public int getTotalIP() {
			return totalIP;
		}

		public void setTotalIP(int totalIP) {
			this.totalIP = totalIP;
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setUserSessionFinder(UserSessionFinder userSessionFinder) {
		this.userSessionFinder = userSessionFinder;
	}
}