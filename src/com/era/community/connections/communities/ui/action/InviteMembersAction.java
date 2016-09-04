package com.era.community.connections.communities.ui.action;

import java.io.Writer;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.connections.communities.dao.MemberInvitation;
import com.era.community.connections.communities.dao.MemberInvitationFinder;
import com.era.community.pers.dao.User;

/**
 *  @spring.bean name="/connections/inviteMembers.ajx" 
 */
public class InviteMembersAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager; 
	protected MemberInvitationFinder memberInvitationFinder;
	protected JavaMailSenderImpl mailSender;
	protected VelocityEngine velocityEngine;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command)data; 
		CommunityEraContext context = contextManager.getContext();
		HttpServletResponse resp = context.getResponse();
		User currUser = context.getCurrentUser();

		String[] myJsonData = context.getRequest().getParameterValues("json[]");
		if(myJsonData != null && myJsonData.length > 0) {
			for (int i=0; i < myJsonData.length; ++i) {
				String usrInfo = myJsonData[i];
				if (usrInfo != null && !"".equals(usrInfo)) {
					String[] member = usrInfo.split("#", 2);
					int memberId = Integer.parseInt(member[0]);

					MemberInvitation memberInvitation;
					try {
						memberInvitation= memberInvitationFinder.getMemberInvitationForUserAndCommunity(memberId, cmd.getCommunityId());
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date now = new Date();
						String dt = sdf.format(now);
						Timestamp ts = Timestamp.valueOf(dt);
						memberInvitation.setRequestDate(ts);
						memberInvitation.setInvitorId(currUser.getId());
						memberInvitation.update();
						//mailToJoinee(comm, user, cmd);
					} catch (ElementNotFoundException e) {
						memberInvitation = memberInvitationFinder.newMemberInvitation();
						memberInvitation.setUserId(memberId);
						memberInvitation.setInvitorId(currUser.getId());
						memberInvitation.setCommunityId(cmd.getCommunityId());
						memberInvitation.setStatus(0);
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date now = new Date();
						String dt = sdf.format(now);
						Timestamp ts = Timestamp.valueOf(dt);
						memberInvitation.setRequestDate(ts);
						memberInvitation.update();            
					}
					//mailToJoinee(comm, user, cmd);
				}
			}
		}

		resp.setContentType("text/html");
		Writer out = resp.getWriter();
		out.write("");
		out.close();
		return null;
	}

	public class Command extends CommandBeanImpl implements CommandBean
	{
		private int communityId;

		public int getCommunityId() {
			return communityId;
		}

		public void setCommunityId(int communityId) {
			this.communityId = communityId;
		}
	}

	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public void setContextManager(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}

	public void setMemberInvitationFinder(MemberInvitationFinder memberInvitationFinder) {
		this.memberInvitationFinder = memberInvitationFinder;
	}
}