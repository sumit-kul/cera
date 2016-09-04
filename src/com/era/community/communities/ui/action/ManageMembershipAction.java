package com.era.community.communities.ui.action;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractFormAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;
import support.community.framework.CommandValidator;
import support.community.framework.RunAsServerCallback;
import support.community.util.StringFormat;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.communities.dao.CommunityRoleConstants;
import com.era.community.communities.ui.validator.CommunityValidator;
import com.era.community.connections.communities.dao.MemberInvitationFinder;
import com.era.community.connections.communities.dao.MemberListFeature;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;

/**
 * @spring.bean name="/cid/[cec]/connections/manageMembership.do"
 * TODO need to add later
 */
public class ManageMembershipAction extends AbstractFormAction
{
    protected CommunityEraContextManager contextManager;
    protected CommunityFinder communityFinder;
    protected UserFinder userFinder;
    protected MemberListFeature memberListFeature;
    protected MemberInvitationFinder memberInvitationFinder;
    
    protected String getView()
    {
        return "community/manageMembership";
    }
  
    protected void onDisplay(Object data) throws Exception
    {
        Command cmd = (Command)data;        
        
        CommunityEraContext context = contextManager.getContext();
        Community comm = context.getCurrentCommunity();
        
        if (!context.isUserCommunityAdmin())
            throw new Exception("Not authorized");
        
        cmd.setPrivateCommunity(comm.isPrivate());
        /*if (comm.getParentId()!=null) {
            cmd.setChildCommunity(true);
            cmd.setParentCommunityPrivate(comm.getParentCommunity().isPrivate());
        }
        cmd.setIncludeParentMembers(comm.isIncludeParentMembers());*/
        cmd.setAdministrators(comm.getAdminMemberEmailAddresses());
        int invCount = memberInvitationFinder.countMemberInvitationsForCommunity(comm != null ? comm.getId() : 0);
		cmd.setInvitationCount(invCount);
		cmd.setCommunity(comm);
		cmd.setMember(isMember(comm));
		cmd.setAdminMember(isAdminMember(comm));
    }
    
    private boolean isAdminMember(Community community) throws Exception
	{
		if (contextManager.getContext().getCurrentUser() == null ) return false;
		if (contextManager.getContext().isUserAuthenticated() == false) return false;
		return community.isAdminMember(contextManager.getContext().getCurrentUser());
	}
	
	private boolean isMember(Community community) throws Exception
	{
		if (contextManager.getContext().getCurrentUser() == null ) return false;
		if (contextManager.getContext().isUserAuthenticated() == false) return false;
		int currentUser = contextManager.getContext().getCurrentUser().getId();
		return community.isMember(currentUser);
	}

    protected ModelAndView onSubmit(Object data) throws Exception
    {
        final Command cmd = (Command)data;
        
        final CommunityEraContext context = contextManager.getContext();
        final Community comm = context.getCurrentCommunity();

        if (!context.isUserCommunityAdmin())
            throw new Exception("Not authorized");
        
        getRunServerAsTemplate().execute(new RunAsServerCallback() {
            public Object doInSecurityContext() throws Exception
            {
                comm.setIncludeParentMembers(cmd.isIncludeParentMembers());
                comm.update();
                
                comm.setAdminMemberEmailAddresses(cmd.getAdministrators());
                
                List list = cmd.getAdditionalMemberList();
                for (int n=0; n<list.size(); n++) {
                    String s = (String)list.get(n);
                    User user = userFinder.getUserForEmailAddress(s);
                    comm.getMemberList().addMember(user, CommunityRoleConstants.MEMBER);
                }
                                
                return null;
            }
        });
                
        return new ModelAndView("redirect:"+contextManager.getContext().getCurrentCommunityUrl()+"/connections/showConnections.do");
    }
    

    protected CommandValidator createValidator()
    {
        return new Validator(); 
    }
   
    
    public static class Command extends CommandBeanImpl implements CommandBean
    {       
        private boolean privateCommunity = false;
        private boolean childCommunity = false;
        private boolean parentCommunityPrivate = false;
        private boolean includeParentMembers = false;
        private String administrator1 = "";
        private String administrator2 = "";
        private String administrator3 = "";
        private String administrator4 = "";
        private String administrator5 = "";
        private String administrator6 = "";
        private String additionalMembers = "";
        private String membershipDomains = "";
        
        private int numberOfRequests;
        private int invitationCount;
		private boolean adminMember = false;
		private boolean member = false;
		private Community community;


        public String[] getMembershipDomainList()
        {
            return StringFormat.explode(membershipDomains, ", ");
        }
        
        public void setMembershipDomainList(String[] list)
        {
            membershipDomains = StringFormat.implode(list, ",");
        }
        
        public void setAdministrators(String[] addresses) throws Exception
        {          
            if (addresses.length>0) administrator1 = addresses[0];
            if (addresses.length>1) administrator2 = addresses[1];
            if (addresses.length>2) administrator3 = addresses[2];
            if (addresses.length>3) administrator4 = addresses[3];
            if (addresses.length>4) administrator5 = addresses[4];
            if (addresses.length>5) administrator6 = addresses[5];
        }
        
        public String[]  getAdministrators() throws Exception
        {
            List<String> list = new ArrayList<String>(6);
            if (administrator1.trim().length()>0) list.add(administrator1);
            if (administrator2.trim().length()>0) list.add(administrator2);
            if (administrator3.trim().length()>0) list.add(administrator3);
            if (administrator4.trim().length()>0) list.add(administrator4);
            if (administrator5.trim().length()>0) list.add(administrator5);
            if (administrator6.trim().length()>0) list.add(administrator6);
            String[] addresses = new String[list.size()];
            for (int n=0; n<addresses.length; n++) addresses[n] = (String)list.get(n);
            return addresses;
        }
        
        public String getAdditionalMembers()
        {
            return additionalMembers;
        }
        public void setAdditionalMembers(String additionalMembers)
        {
            this.additionalMembers = additionalMembers;
        }

        public String getAdministrator2()
        {
            return administrator2;
        }
        public void setAdministrator2(String administrator2)
        {
            this.administrator2 = administrator2;
        }
        public String getAdministrator3()
        {
            return administrator3;
        }
        public void setAdministrator3(String administrator3)
        {
            this.administrator3 = administrator3;
        }
        public String getAdministrator4()
        {
            return administrator4;
        }
        public void setAdministrator4(String administrator4)
        {
            this.administrator4 = administrator4;
        }
        public String getMembershipDomains()
        {
            return membershipDomains;
        }
        public void setMembershipDomains(String allowedDomains)
        {
            this.membershipDomains = allowedDomains;
        }

        
        public List getAdditionalAdministrators() throws Exception
        {
            List<String> list = new ArrayList<String>(3);
            if (administrator2.trim().length()>0) list.add(administrator2.trim());
            if (administrator3.trim().length()>0) list.add(administrator3.trim());
            if (administrator4.trim().length()>0) list.add(administrator4.trim());
            return list;
        }
        
        public List getAdditionalMemberList() throws Exception
        {
            List<String> list = new ArrayList<String>(50);
            StringTokenizer tok = new StringTokenizer(additionalMembers, " ,\n\r", false);
            while (tok.hasMoreTokens()) list.add(tok.nextToken());
            return list;
        }
        public final String getAdministrator1()
        {
            return administrator1;
        }
        public final void setAdministrator1(String administrator1)
        {
            this.administrator1 = administrator1;
        }
        public final String getAdministrator5()
        {
            return administrator5;
        }
        public final void setAdministrator5(String administrator5)
        {
            this.administrator5 = administrator5;
        }
        public final String getAdministrator6()
        {
            return administrator6;
        }
        public final void setAdministrator6(String administrator6)
        {
            this.administrator6 = administrator6;
        }
        public final boolean isPrivateCommunity()
        {
            return privateCommunity;
        }
        public final void setPrivateCommunity(boolean privateCommunity)
        {
            this.privateCommunity = privateCommunity;
        }
        public final boolean isChildCommunity()
        {
            return childCommunity;
        }
        public final void setChildCommunity(boolean childCommunity)
        {
            this.childCommunity = childCommunity;
        }
        public final boolean isIncludeParentMembers()
        {
            return includeParentMembers;
        }
        public final void setIncludeParentMembers(boolean includeParentMembers)
        {
            this.includeParentMembers = includeParentMembers;
        }
        public final boolean isParentCommunityPrivate()
        {
            return parentCommunityPrivate;
        }
        public final void setParentCommunityPrivate(boolean parentCommunityPrivate)
        {
            this.parentCommunityPrivate = parentCommunityPrivate;
        }

		public int getNumberOfRequests() {
			return numberOfRequests;
		}

		public void setNumberOfRequests(int numberOfRequests) {
			this.numberOfRequests = numberOfRequests;
		}

		public int getInvitationCount() {
			return invitationCount;
		}

		public void setInvitationCount(int invitationCount) {
			this.invitationCount = invitationCount;
		}

		public boolean isAdminMember() {
			return adminMember;
		}

		public void setAdminMember(boolean adminMember) {
			this.adminMember = adminMember;
		}

		public boolean isMember() {
			return member;
		}

		public void setMember(boolean member) {
			this.member = member;
		}

		public Community getCommunity() {
			return community;
		}

		public void setCommunity(Community community) {
			this.community = community;
		}
    }
    
    public class Validator extends CommunityValidator
    {
        public String validateAdministratorAddress(Object value, CommandBean cmd) throws Exception
        {
            if (value==null||value.toString().trim().length()==0) return null;
            try {
                userFinder.getUserForEmailAddress(value.toString().trim());
                return null;
            }
            catch (ElementNotFoundException e) {
                return "There is no registered user with email address "+value;
            }
        }
        public String validateAdministrator1(Object value, CommandBean cmd) throws Exception
        {
            return validateAdministratorAddress(value, cmd);
        }
        public String validateAdministrator2(Object value, CommandBean cmd) throws Exception
        {
            return validateAdministratorAddress(value, cmd);
        }
        public String validateAdministrator3(Object value, CommandBean cmd) throws Exception
        {
            return validateAdministratorAddress(value, cmd);
        }
        public String validateAdministrator4(Object value, CommandBean cmd) throws Exception
        {
            return validateAdministratorAddress(value, cmd);
        }
        public String validateAdministrator5(Object value, CommandBean cmd) throws Exception
        {
            return validateAdministratorAddress(value, cmd);
        }
        public String validateAdministrator6(Object value, CommandBean cmd) throws Exception
        {
            return validateAdministratorAddress(value, cmd);
        }
        
        public String validateAdditionalMembers(Object value, CommandBean bean) throws Exception
        {
            StringBuffer buf = new StringBuffer(1024);
            Command cmd = (Command)bean;
            List list = cmd.getAdditionalMemberList();
            for (int n=0; n<list.size(); n++) {
                String s = (String)list.get(n);
                try {
                    userFinder.getUserForEmailAddress(s);
                }
                catch (ElementNotFoundException e) {
                    if (buf.length()>0) buf.append(",");
                    buf.append(s);
                }
            }
            if (buf.length()==0) return null;
            
            return "No registered user was found for the following addresses: "+buf.toString();
        }
        
    }

    public void setCommunityFinder(CommunityFinder communityFinder)
    {
        this.communityFinder = communityFinder;
    }


    public void setContextHolder(CommunityEraContextManager contextManager)
    {
        this.contextManager = contextManager;
    }
    public void setMemberListFeature(MemberListFeature memberListFeature)
    {
        this.memberListFeature = memberListFeature;
    }
    public void setUserFinder(UserFinder userFinder)
    {
        this.userFinder = userFinder;
    }

	public void setMemberInvitationFinder(
			MemberInvitationFinder memberInvitationFinder) {
		this.memberInvitationFinder = memberInvitationFinder;
	}


}
