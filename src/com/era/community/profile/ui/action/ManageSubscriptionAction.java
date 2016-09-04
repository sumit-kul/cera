package com.era.community.profile.ui.action;

import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import support.community.database.EntityWrapper;
import support.community.database.IndexedScrollerPage;
import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;
import support.community.framework.AbstractFormAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandValidator;
import support.community.framework.IndexCommandBean;
import support.community.framework.IndexCommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.monitor.dao.Subscription;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 * @spring.bean name="/pers/manageSubscriptions.do"
 */
public class ManageSubscriptionAction extends AbstractFormAction 
{

	public static final String REQUIRES_AUTHENTICATION = "";

	protected CommunityEraContextManager contextManager;
	protected UserFinder userFinder;
	protected SubscriptionFinder subscriptionFinder;

	protected String getView()
	{
		return "/profile/manageSubscriptions";
	}

	protected QueryPaginator getScroller(IndexCommandBean bean) throws Exception
	{ 
		Command cmd = (Command) bean;
		CommunityEraContext context = contextManager.getContext();
		return null;
	}

	public static class Command extends IndexCommandBeanImpl implements CommandBean
	{
		private int[] subscriptionIds;
		private int[] subscriptionFreqs;
		private int[] selectedIds;

		private String buttonUpdateOptions;
		private String buttonRemoveSelected;
		private User user;

		public final int[] getSubscriptionFreqs()
		{
			return subscriptionFreqs;
		}

		public final void setSubscriptionFreqs(int[] subscriptionFreqs)
		{
			this.subscriptionFreqs = subscriptionFreqs;
		}
		public final int[] getSubscriptionIds()
		{
			return subscriptionIds;
		}
		public final void setSubscriptionIds(int[] subscriptionIds)
		{
			this.subscriptionIds = subscriptionIds;
		}
		public final int[] getSelectedIds()
		{
			return selectedIds;
		}
		public final void setSelectedIds(int[] selectedIds)
		{
			this.selectedIds = selectedIds;
		}
		public final String getButtonRemoveSelected()
		{
			return buttonRemoveSelected;
		}
		public final void setButtonRemoveSelected(String buttonRemoveSelected)
		{
			this.buttonRemoveSelected = buttonRemoveSelected;
		}
		public final String getButtonUpdateOptions()
		{
			return buttonUpdateOptions;
		}
		public final void setButtonUpdateOptions(String buttonUpdateOptions)
		{
			this.buttonUpdateOptions = buttonUpdateOptions;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}
	}
	public class Validator extends CommandValidator 
	{
	}

	@Override
	protected CommandValidator createValidator()
	{
		return new Validator();
	}

	@Override
	protected ModelAndView onSubmit(Object data) throws Exception
	{
		Command cmd = (Command)data;

		if (cmd.getButtonUpdateOptions()!=null) {

			for (int n=0; n<cmd.subscriptionIds.length; n++) {
				Subscription sub = subscriptionFinder.getSubscriptionForId(cmd.getSubscriptionIds()[n]);
				sub.setFrequency(cmd.getSubscriptionFreqs()[n]);
				sub.update();
			}
		}
		else if (cmd.getButtonRemoveSelected()!=null) {

			for (int n=0; cmd.selectedIds != null && n<cmd.selectedIds.length; n++) {
				Subscription sub = subscriptionFinder.getSubscriptionForId(cmd.getSelectedIds()[n]);
				sub.setFrequency(cmd.getSubscriptionFreqs()[n]);
				sub.delete();
			}
		}
		return null;
	}

	@Override
	protected void initializeCommand(HttpServletRequest request, Object command) throws Exception
	{
		Command cmd = (Command)command;

		int n = WebUtils.getParametersStartingWith(request, "subscriptionFreqs[").size();

		cmd.setSubscriptionIds(new int[n]);
		cmd.setSubscriptionFreqs(new int[n]);
	}
	
	/*@Override
    protected Map referenceData(Object data) throws Exception
    {
        IndexCommandBeanImpl cmd = (IndexCommandBeanImpl)data;
        
        
         * If the scroller page has already been set then return;
         
        if (cmd.getScrollerPage()!=null) getReferenceData(cmd); 
        
        QueryPaginator paginator =  getScroller(cmd);
        if (paginator != null) {
        	paginator.setPageSize(cmd.getPageSize());

        	if (paginator instanceof QueryScroller) {
        		QueryScroller scroller = (QueryScroller)paginator;
        		if (cmd.getPageKey()!=null)  
        			cmd.setScrollerPage(scroller.readPage(cmd.getPageKey()));
        		else
        			cmd.setScrollerPage(scroller.readPage(cmd.getPage()));
        	}
        	else {
        		cmd.setScrollerPage(paginator.readPage(cmd.getPage()));
        	}
        }

        return getReferenceData(cmd);
    }*/

	@Override
    protected final Map referenceData(Object data) throws Exception
    {       
		CommunityEraContext context = contextManager.getContext();
		Command cmd = (Command)data;
		//return new HashMap();
		
		String pageNum = context.getRequest().getParameter("jPage");
		int pNumber = 0;
		if (pageNum != null && !"".equals(pageNum)) {
			pNumber = Integer.parseInt(pageNum);
		}
		
		User user = context.getCurrentUser();
		if (user != null) {
		cmd.setUser(user);
		QueryScroller scroller = user.listUserSubscriptions();
		//scroller.setBeanClass( RowBean.class );
		
		
		if (pageNum != null) {
			HttpServletResponse resp = contextManager.getContext().getResponse();
			JSONObject json = null;
			if (pNumber > 0) {
				scroller.setBeanClass(RowBean.class, this);
				scroller.setPageSize(cmd.getPageSize());
				IndexedScrollerPage page = scroller.readPage(pNumber);
				cmd.setScrollerPage(page);
				json = page.toJsonString(pNumber);
				json.put("userSysAdmin", Boolean.toString(context.isUserSysAdmin()));
			} else {
				json = new JSONObject();
				json.put("pageNumber", pNumber);
		        JSONArray jData = new JSONArray();
		        json.put("aData", jData);
			}
			List subscriptions = cmd.getScrollerPage();
			int[] sids = new int[subscriptions.size()];
			int[] freqs = new int[subscriptions.size()];
			for (int n=0; n<subscriptions.size(); n++) {
				RowBean sub = (RowBean)subscriptions.get(n);
				sids[n] = sub.getId();
				freqs[n] = sub.getFrequency();
			}
			cmd.setSubscriptionIds(sids);
			cmd.setSubscriptionFreqs(freqs);
			String jsonString = json.serialize();
			resp.setContentType("text/json");
			Writer out = resp.getWriter();
			out.write(jsonString);
			out.close();
			return null;
		} else {
			scroller.setBeanClass(RowBean.class, this);
			scroller.setPageSize(cmd.getPageSize());
			IndexedScrollerPage page = scroller.readPage(1);
			cmd.setScrollerPage(page);
			List subscriptions = cmd.getScrollerPage();
			int[] sids = new int[subscriptions.size()];
			int[] freqs = new int[subscriptions.size()];
			for (int n=0; n<subscriptions.size(); n++) {
				RowBean sub = (RowBean)subscriptions.get(n);
				sids[n] = sub.getId();
				freqs[n] = sub.getFrequency();
			}
			cmd.setSubscriptionIds(sids);
			cmd.setSubscriptionFreqs(freqs);
			scroller.setPageSize(cmd.getPageSize());
			cmd.setPageCount(scroller.readPageCount());
			cmd.setRowCount(scroller.readRowCount());
			return new HashMap();
		}
		}
		return new HashMap();
	}

	public static class RowBean implements EntityWrapper
	{  
		private Subscription sub ; 
		private int resultSetIndex;      
		private String communityName;
		private String resultSetIndex2; 
		private String resultSetIndex3;

		public String getCommunityName()
		{
			return communityName;
		}

		public void setCommunityName(String communityName)
		{
			this.communityName = communityName;
		}

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
			return resultSetIndex%2==0;
		}

		public boolean isOddRow()
		{
			return resultSetIndex%2==1;
		}

		public Integer getCommunityId()
		{
			return sub.getCommunityId();
		}

		public String getItemName() throws Exception
		{
			try {
				return sub.getItemName();
			} catch (Exception e) {
				return "";
			}            
		}

		public String getItemType() throws Exception
		{
			return sub.getItemType();
		}

		public String getItemUrl() throws Exception
		{
			return sub.getItemUrl();
		}

		public String getItemLastUpdateDate() throws Exception
		{

			SimpleDateFormat fmter = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat fmt = new SimpleDateFormat("HH:mm a");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy HH:mm a");
			Date today = new Date();
			String sToday = fmter.format(today);

			try {
				if(sub.getItemLastUpdateDate() == null)return "";
				Date date = formatter.parse(sub.getItemLastUpdateDate().toString());
				if (fmter.format(date).equals(sToday)) {
					return "Today " + fmt.format(date);
				}
				return fmt2.format(date);

			} catch (ParseException e) {
				return sub.getItemLastUpdateDate().toString();
			}
		}

		public Integer getUserId()
		{
			return sub.getUserId();
		}
		public int getId()
		{
			return sub.getId();
		}
		public int getFrequency()
		{
			return sub.getFrequency();
		}
		public final void setSub(Subscription sub)
		{
			this.sub = sub;
		}

		public String getResultSetIndex2() {
			//return "<form:hidden path='subscriptionFreqs["+Integer.toString(getResultSetIndex() - 1)+"]' />";
			return "<input type='hidden' name='subscriptionIds["+Integer.toString(getResultSetIndex() - 1)+"]' name='subscriptionIds["+Integer.toString(getResultSetIndex() - 1)+"]' value='"+sub.getId()+"'/>";
			//return "<form:hidden path='subscriptionIds["+Integer.toString(getResultSetIndex() - 1)+"]' value='"+sub.getId+"'/>";
			//return "";
		}

		public void setResultSetIndex2(String resultSetIndex2) {
			this.resultSetIndex2 = resultSetIndex2;
		}

		public String getResultSetIndex3() {
			String str = "<select name='subscriptionFreqs["+Integer.toString(getResultSetIndex() - 1)+"]' id='subscriptionFreqs["+Integer.toString(getResultSetIndex() - 1)+"]'>";
			if (sub.getFrequency() == 0) {
				str = str + "<option value='0' selected='"+sub.getFrequency()+"'>Immediate</option><option value='1'>Daily</option><option value='2'>Weekly</option></select>";
			}
			if (sub.getFrequency() == 1) {
				str = str + "<option value='0' >Immediate</option><option value='1' selected='"+sub.getFrequency()+"'>Daily</option><option value='2'>Weekly</option></select>";
			}if (sub.getFrequency() == 2) {
				str = str + "<option value='0' >Immediate</option><option value='1'>Daily</option><option value='2' selected='"+sub.getFrequency()+"'>Weekly</option></select>";
			}
			return str;
		}

		public void setResultSetIndex3(String resultSetIndex3) {
			this.resultSetIndex3 = resultSetIndex3;
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public final void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}

	public final void setSubscriptionFinder(SubscriptionFinder subscriptionFinder)
	{
		this.subscriptionFinder = subscriptionFinder;
	}

	@Override
	protected void onDisplay(Object data) throws Exception {
	}
}