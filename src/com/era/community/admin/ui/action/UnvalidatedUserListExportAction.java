package com.era.community.admin.ui.action;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.database.QueryScroller;
import support.community.database.QueryScrollerCallback;
import support.community.framework.AbstractCommandAction;
import support.community.framework.AppRequestContextHolder;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;
import support.community.util.CSVPrint;
import support.community.util.ExcelCSVPrinter;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.pers.dao.generated.UserEntity;

/**
 * @spring.bean name="/admin/unvalidateduser-list-export.do"
 */
public class UnvalidatedUserListExportAction extends AbstractCommandAction
{
	public static final String REQUIRES_AUTHENTICATION = "";
	private AppRequestContextHolder requestcontextManager;
	protected CommunityEraContextManager contextManager;
	private UserFinder userFinder;
	private CommunityFinder communityFinder;

	@Override
	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command) data;

		CommunityEraContext context = contextManager.getContext();

		if (!context.isUserSysAdmin())
			throw new Exception("You are not authorized to view this information");

		export();

		return null;
	}

	private void export() throws Exception
	{
		HttpServletResponse resp = requestcontextManager.getRequestContext().getResponse();
		resp.setContentType("text/csv");
		resp.setHeader("Content-Disposition", "attachment;filename=" +   ( "Unvalidated user list") + ".csv");

		/*
		 * There is an IE bug that prevents download when "nocache" is used so replace with "private". (Only happens if
		 * the download url is typed into the address bar.)
		 */
		resp.setHeader("Cache-Control", "private");

		final CSVPrint out = new ExcelCSVPrinter(resp.getWriter(), true, false);

		QueryScroller scroller;

		scroller = userFinder.listUnvalidatedUsers();

		scroller.setBeanClass(RowBean.class,this);

		writeTitleRow(out);
		writeHeaderRow(out);

		scroller.doForAllRows(new QueryScrollerCallback() {
			public void handleRow(Object data, int rowNum) throws Exception
			{
				RowBean rowbean = (RowBean) data;
				out.print(""+rowbean.getFirstName() +" "+rowbean.getLastName());                                                                
				out.print(rowbean.getEmailAddress());                                                              

				if(rowbean.getValidated()) {                                                                      
					out.print("Yes");
				}
				else{
					out.print("No");
				}


				SimpleDateFormat fmt = new SimpleDateFormat("dd MMM yyyy");
				String dateRegistered = fmt.format(rowbean.getDateRegistered());                    
				String dateLastVisit = fmt.format(rowbean.getDateLastVisit());                          

				out.print(dateRegistered);
				String sTemp = dateLastVisit.toString();
				if (dateLastVisit.toString().contains("0001")) {
					out.print("unknown");
				}
				else {
					out.print(dateLastVisit);
				}
				// newline
				out.println();
			}
		}, false);
	}

	private void writeTitleRow(CSVPrint out) throws Exception
	{

		String[] values = { "jhapak.com : Unvalidated user list" };
		out.writeln(values);
		out.writeln("");

		Date today = new Date();
		SimpleDateFormat fmt = new SimpleDateFormat("dd MMM yyyy");
		String dateAsString = fmt.format(today);

		out.writeln(dateAsString);
		out.writeln("");
	}

	/*
	 * Write the header row
	 */
	private void writeHeaderRow(CSVPrint out) throws Exception
	{
		String[] values = { "User", "Email", "Job title", "Organisation", "Local authority","Region", "Validated", "Registered", "Last visit"};

		out.writeln(values);
	}

	public class Command extends CommandBeanImpl implements CommandBean
	{
		private String format = "";


		public final String getFormat()
		{
			return format;
		}

		public final void setFormat(String format)
		{
			this.format = format;
		}
	}

	public class RowBean extends UserEntity     
	{
		User user;

		private int resultSetIndex;

		private String lastVisitDate;

		public String getLastVisitDate()
		{
			return this.getDateLastVisit();
		}

		public void setLastVisitDate(String lastVisitDate)
		{
			this.lastVisitDate = lastVisitDate;
		}

		public RowBean()
		{

		}

		public boolean isEvenRow()
		{
			return resultSetIndex % 2 == 0;
		}

		public boolean isOddRow()
		{
			return resultSetIndex % 2 == 1;
		}

		public boolean isNewUser()
		{
			if (this.getDateRegistered() == null)
				return false;
			GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
			DateFormat df = DateFormat.getDateInstance();
			try {
				cal.setTime(df.parse(this.getDateRegistered()));
			} catch (ParseException e) {
				return false;
			}
			cal.roll(Calendar.DAY_OF_YEAR, 7);
			return cal.after(Calendar.getInstance());
		}

		public final int getResultSetIndex()
		{
			return resultSetIndex;
		}

		public final void setResultSetIndex(int resultSetIndex)
		{
			this.resultSetIndex = resultSetIndex;
		}

		public User getUser()
		{
			return user;
		}

		public void setUser(User user)
		{
			this.user = user;
		}
	}

	public final void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}

	public static String getREQUIRES_AUTHENTICATION()
	{
		return REQUIRES_AUTHENTICATION;
	}

	public CommunityEraContextManager getContextHolder()
	{
		return contextManager;
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public AppRequestContextHolder getRequestcontextManager()
	{
		return requestcontextManager;
	}

	public void setRequestcontextManager(AppRequestContextHolder requestcontextManager)
	{
		this.requestcontextManager = requestcontextManager;
	}

	public UserFinder getUserFinder()
	{
		return userFinder;
	}

	public CommunityFinder getCommunityFinder()
	{
		return communityFinder;
	}

	public void setCommunityFinder(CommunityFinder communityFinder)
	{
		this.communityFinder = communityFinder;
	}
}