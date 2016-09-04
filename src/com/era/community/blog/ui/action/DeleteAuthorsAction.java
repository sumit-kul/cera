package com.era.community.blog.ui.action;

import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.IndexCommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.blog.dao.BlogAuthor;
import com.era.community.blog.dao.BlogAuthorFinder;
import com.era.community.pers.dao.User;
import com.sun.xml.internal.bind.v2.TODO;

/**
 * @spring.bean name="/blog/deleteAuthors.ajx"
 */
public class DeleteAuthorsAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager;
	protected BlogAuthorFinder blogAuthorFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		User currentUser = context.getCurrentUser();
		String returnString = "";
		Command cmd = (Command) data;
		
		String[] myJsonData = context.getRequest().getParameterValues("json[]");
		if(myJsonData != null && myJsonData.length > 0) {
			for (int i=0; i < myJsonData.length; ++i) {
				String entId = myJsonData[i];
				int authId = Integer.parseInt(entId);
				try {
					BlogAuthor blogAuthor = blogAuthorFinder.getBlogAuthorForId(authId);
					blogAuthor.delete();
					//TODO mail required
				} catch (ElementNotFoundException ex) {
				}
			}
		}

		HttpServletResponse resp = contextManager.getContext().getResponse();
		resp.setContentType("text/html");
		Writer out = resp.getWriter();
		out.write(returnString);
		out.close();
		return null;
	}

	public static class Command extends IndexCommandBeanImpl implements CommandBean
	{
	}

	public void setContextManager(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}

	public void setBlogAuthorFinder(BlogAuthorFinder blogAuthorFinder) {
		this.blogAuthorFinder = blogAuthorFinder;
	}
}