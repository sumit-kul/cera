package com.era.community.admin.ui.action;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.era.community.pers.dao.UserFinder;

/**
 * 
 * THIS IS NOT FINISHED
 * System Administrator action to export the number of registered users by region and month to excel file
 * 
 * @spring.bean name="/admin/usercount-export.do"
 */
public class UserCountByMonthAndRegionExport extends AbstractCommandAction
{

    /*
     * Access markers.
     */
    public static final String REQUIRES_AUTHENTICATION = "";

    private AppRequestContextHolder requestcontextManager;
    protected CommunityEraContextManager contextManager;
    private UserFinder userFinder;

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
        resp.setHeader("Content-Disposition", "attachment;filename=" +   ( "Monthly-Registered-Users") + ".csv");

        /*
         * There is an IE bug that prevents download when "nocache" is used so replace with "private". (Only happens if
         * the download url is typed into the address bar.)
         */
        resp.setHeader("Cache-Control", "private");

        final CSVPrint out = new ExcelCSVPrinter(resp.getWriter(), true, false);

        QueryScroller  scroller = userFinder.listUserCountsByMonthAndRegion();  // 3 cols :  count, region, monthcreated

        scroller.setBeanClass(RowBean.class);
        
        writeTitleRow(out);
        writeHeaderRow(out);

        scroller.doForAllRows(new QueryScrollerCallback() {
            public void handleRow(Object data, int rowNum) throws Exception
            {
                RowBean rowbean = (RowBean) data;
                
            
                
                out.print(""+rowbean.getUserCount());
                out.print(""+rowbean.getRegion());
                out.print(""+rowbean.getYear());
                out.print(""+rowbean.getMonth());
                       
                out.println();  // newline

            }
        }, false);
        
       
    }

    /*
     * Write the title row for the CSV output
     */
    private void writeTitleRow(CSVPrint out) throws Exception
    {

        String[] values = { "jhapak.com : User count by region and month" };
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
        String[] values = { "Registered Users", "Region", "Year", "Month"};

        out.writeln(values);
    }

    

    public class Command extends CommandBeanImpl implements CommandBean
    {
 
    }

    /*
     * The row bean is populated with result data from the query
     */
    public static class RowBean  
    {
     
        private int userCount;
        private String region;
        private int month;
        private int year;


        public final int getMonth()
        {
            return month;
        }

        public final void setMonth(int month)
        {
            this.month = month;
        }

        public final int getYear()
        {
            return year;
        }

        public final void setYear(int year)
        {
            this.year = year;
        }

        public final String getRegion()
        {
            return region;
        }

        public final void setRegion(String region)
        {
            this.region = region;
        }

        public final int getUserCount()
        {
            return userCount;
        }

        public final void setUserCount(int userCount)
        {
            this.userCount = userCount;
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

}