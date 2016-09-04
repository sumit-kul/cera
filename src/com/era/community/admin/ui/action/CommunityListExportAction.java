package com.era.community.admin.ui.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.database.EntityWrapper;
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
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.communities.dao.generated.CommunityEntity;
import com.era.community.pers.dao.UserFinder;

/**
 * System Administrator action to exports local authory user counts to CSV or XML file for download
 * 
 * @spring.bean name="/admin/community-list-export.do"
 */
public class CommunityListExportAction extends AbstractCommandAction
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
        resp.setHeader("Content-Disposition", "attachment;filename=" +   ( "Community list") + ".csv");

        /*
         * There is an IE bug that prevents download when "nocache" is used so replace with "private". (Only happens if
         * the download url is typed into the address bar.)
         */
        resp.setHeader("Cache-Control", "private");

        final CSVPrint out = new ExcelCSVPrinter(resp.getWriter(), true, false);

        QueryScroller scroller = communityFinder.listActiveCommunitiesByName();
        scroller.setBeanClass(RowBean.class);
        
        writeTitleRow(out);
        writeHeaderRow(out);

        scroller.doForAllRows(new QueryScrollerCallback() {
            public void handleRow(Object data, int rowNum) throws Exception
            {
                RowBean rowbean = (RowBean) data;
                out.print(""+rowbean.getId());                                                                // Community Id
                out.print(rowbean.getName());                                                               // Community Name


                SimpleDateFormat fmt = new SimpleDateFormat("dd MMM yyyy");
                String dateAsString = fmt.format(rowbean.getCommunity().getCreated());
                out.print(dateAsString);
                out.print(rowbean.getCommunity().getCommunityType());                           // Community Type - Private or public
                out.print(""+rowbean.getCommunity().getMemberCount());

                if (rowbean.getCommunity().getParentId() != null) {                                 // Parent Community Id
                    out.print(""+rowbean.getCommunity().getParentId());
                }

                // newline
                out.println();

            }
        }, false);
    }

    private void writeTitleRow(CSVPrint out) throws Exception
    {

        String[] values = { "jhapak.com : Community list" };
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
        String[] values = { "Id", "Name", "Date Created", "Type", "Sub Communities", "Member count", "Location", "Lead Organisation", "Lead Admin", "Parent Community Id"};

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

     /*
      * N.B.: This RowBean class is NOT static as we refer to some of the injected objects
      */
    public static class RowBean extends CommunityEntity implements EntityWrapper
    {
     
        Community community;
        private int count;

     
        public final int getCount()
        {
            return count;
        }

        public final void setCount(int count)
        {
            this.count = count;
        }

        public Community getCommunity()
        {
            return community;
        }

        public void setCommunity(Community community)
        {
            this.community = community;
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