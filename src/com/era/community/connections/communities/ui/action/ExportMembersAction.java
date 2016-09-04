package com.era.community.connections.communities.ui.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import support.community.database.EntityWrapper;
import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;
import support.community.framework.AbstractIndexAction;
import support.community.framework.IndexCommandBean;
import support.community.framework.IndexCommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.connections.communities.dao.MemberListFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.ui.dto.UserDto;

/** 
 * 
 * list of members for this community in Excel sheet
 * 
 * @spring.bean name="/cid/[cec]/communityMembers/exportList.do"
 * @spring.bean name="/exportList.do"
 */
public class ExportMembersAction extends AbstractIndexAction
{
    protected CommunityEraContextManager contextManager;
    protected CommunityFinder commFinder;
    protected MemberListFinder memberListFinder;
    
    public static final String REQUIRES_AUTHENTICATION = "";
    
    @Override
    protected QueryPaginator getScroller(IndexCommandBean bean) throws Exception
    {        
        Command cmd = (Command) bean;
        CommunityEraContext context = contextManager.getContext();
        QueryScroller scroller = null;
        
        /* Display whole list */
        bean.setPageSize(999999);
        
        if (context.getCurrentCommunity() != null && context.isUserCommunityAdmin()) {
            Community comm = null;
            if (context.getCurrentCommunity() == null) {
                comm = commFinder.getCommunityForId(cmd.getCommId());
            }
            else {
                comm = context.getCurrentCommunity(); 
            }            
            cmd.setCommName(comm.getName());
            scroller = memberListFinder.getMemberListForCommunity(comm).listMembersByName();
            scroller.setBeanClass(RowBean.class, this);         
        }
        return scroller;
    }

    @Override
    protected String getView(IndexCommandBean bean) throws Exception
    {
        return "communityMembers/exportMembers";
    }
        
    public static class Command extends IndexCommandBeanImpl implements IndexCommandBean
    {             
        private int commId;
        private String commName;

        public int getCommId()
        {
            return commId;
        }

        public void setCommId(int commId)
        {
            this.commId = commId;
        }

        public String getCommName()
        {
            return commName;
        }

        public void setCommName(String commName)
        {
            this.commName = commName;
        }                        
    }
    
    public class RowBean extends UserDto implements EntityWrapper
    {      
        private Date dateJoined;
        private String role;
        private String localAuthority;
        private User user;
        private String lastVisitDate;

        public String getLastVisitDate()
        {
        	SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        	try {
                
        		Date date = formatter.parse(getDateLastVisit());
        		return formatter.format(date);
         
        	} catch (ParseException e) {
        		return getLastVisitDate();
        	}
        }

        public void setLastVisitDate(String lastVisitDate)
        {
            this.lastVisitDate = lastVisitDate;
        }
         
        public User getUser()
        {
            return user;
        }

        public void setUser(User user)
        {
            this.user = user;
        }

        public String getLocalAuthority()
        {
            return localAuthority;
        }

        public void setLocalAuthority(String localAuthority)
        {
            this.localAuthority = localAuthority;
        }

        public Date getDateJoined()
        {
            return dateJoined;
        }
        
        public void setDateJoined(Date dateJoined)
        {
            this.dateJoined = dateJoined;
        }
        
        public String getRole()
        {
            return role;
        }
        
        public void setRole(String role)
        {
            this.role = role;
        }
        
    }
    
    public void setContextHolder(CommunityEraContextManager contextManager)
    {
        this.contextManager = contextManager;
    }

    public void setMemberListFinder(MemberListFinder memberListFinder)
    {
        this.memberListFinder = memberListFinder;
    }

    public void setCommFinder(CommunityFinder commFinder)
    {
        this.commFinder = commFinder;
    }     
    
}
