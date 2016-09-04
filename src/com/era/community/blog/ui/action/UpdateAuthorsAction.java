package com.era.community.blog.ui.action;

import java.io.Writer;

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
import com.era.community.blog.dao.BlogAuthor;
import com.era.community.blog.dao.BlogAuthorFinder;

/**
 *  @spring.bean name="/blog/updateAuthors.ajx" 
 */
public class UpdateAuthorsAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager; 
	protected BlogAuthorFinder blogAuthorFinder;
	protected JavaMailSenderImpl mailSender;
	protected VelocityEngine velocityEngine;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command)data; 
		CommunityEraContext context = contextManager.getContext();
		HttpServletResponse resp = context.getResponse();
		String returnString = "";
		if (cmd.getAuthId() > 0) {
			try {
				BlogAuthor author = blogAuthorFinder.getBlogAuthorForId(cmd.getAuthId());
				author.setRole(cmd.getRole());
				author.update();
				if (cmd.getRole() == 1) {
					returnString = "<span class='dynaDropDown' title='pers/connectionOptions.ajx?authId="+cmd.getAuthId()+"&authRole="+cmd.getRole()+"'>Owner</span>";
				} else if (cmd.getRole() == 2) {
					returnString = "<span class='dynaDropDown' title='pers/connectionOptions.ajx?authId="+cmd.getAuthId()+"&authRole="+cmd.getRole()+"'>Author</span>";
				}
			} catch (ElementNotFoundException e) {
			}
		} 
		//TODO mail required
		resp.setContentType("text/html");
		Writer out = resp.getWriter();
		out.write(returnString);
		out.close();
		return null;
	}

	public class Command extends CommandBeanImpl implements CommandBean
	{
		private int authId;
		private int role;
		public int getAuthId() {
			return authId;
		}
		public void setAuthId(int authId) {
			this.authId = authId;
		}
		public int getRole() {
			return role;
		}
		public void setRole(int role) {
			this.role = role;
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

	public void setBlogAuthorFinder(BlogAuthorFinder blogAuthorFinder) {
		this.blogAuthorFinder = blogAuthorFinder;
	}
}