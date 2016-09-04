package com.era.community.blog.ui.action;

import java.io.Writer;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.base.RunAsAsyncThread;
import com.era.community.blog.dao.BlogAuthor;
import com.era.community.blog.dao.BlogAuthorFinder;
import com.era.community.blog.dao.PersonalBlog;
import com.era.community.blog.dao.PersonalBlogFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;

/**
 *  @spring.bean name="/blog/addAuthors.ajx" 
 */
public class AddAuthorsAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager; 
	protected BlogAuthorFinder blogAuthorFinder;
	protected UserFinder userFinder;
	protected PersonalBlogFinder bpFinder;
	protected JavaMailSenderImpl mailSender;
	protected VelocityEngine velocityEngine;
	protected RunAsAsyncThread taskExecutor;

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
					int memberRole = Integer.parseInt(member[1]);

					BlogAuthor author = blogAuthorFinder.newBlogAuthor();
					author.setPersonalBlogId(cmd.getConsId());
					author.setUserId(memberId);
					author.setRole(memberRole);
					author.setActive(1);
					author.update();

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date now = new Date();
					String dt = sdf.format(now);
					Timestamp ts = Timestamp.valueOf(dt);
					mailToNewAuthor(author, currUser);

				}
			}
		}

		resp.setContentType("text/html");
		Writer out = resp.getWriter();
		out.write("");
		out.close();
		return null;
	}

	private void mailToNewAuthor(final BlogAuthor author, final User currentUser) throws Exception
	{
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					CommunityEraContext context = contextManager.getContext(); 
					User newAuthor = userFinder.getUserEntity(author.getUserId());
					if (!newAuthor.isSuperAdministrator()) {
						PersonalBlog pb = bpFinder.getPersonalBlogForId(author.getPersonalBlogId());

						MimeMessage message = mailSender.createMimeMessage();
						MimeMessageHelper helper = new MimeMessageHelper(message, true);
						//helper.setTo(subscriber.getEmailAddress());
						helper.setTo(newAuthor.getEmailAddress());
						helper.setFrom(new InternetAddress("support@jhapak.com") );
						helper.setSubject("[Jhapak] You have been added as blog author");
						helper.setSentDate(new Date());
						Map model = new HashMap();  
						String sLink = context.getContextUrl() + "/blog/viewBlog.do?bid=" + author.getPersonalBlogId();
						model.put("#authorName#", newAuthor.getFirstName());
						model.put("#ownerName#", currentUser.getFullName());
						model.put("#role#", author.getRole() == 1 ? "Owner" : "Author");
						model.put("#blogName#", pb.getName());
						model.put("#blogLink#", sLink);

						String text = VelocityEngineUtils.mergeTemplateIntoString(
								velocityEngine, "main/resources/velocity/NewBlogAuthor.vm", "UTF-8", model);
						helper.setText(text, true);
						mailSender.send(message);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public class Command extends CommandBeanImpl implements CommandBean
	{
		private int consId;

		public int getConsId() {
			return consId;
		}

		public void setConsId(int consId) {
			this.consId = consId;
		}

	}

	public void setBlogAuthorFinder(BlogAuthorFinder blogAuthorFinder) {
		this.blogAuthorFinder = blogAuthorFinder;
	}

	public void setContextManager(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}

	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public void setTaskExecutor(RunAsAsyncThread taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	public void setBpFinder(PersonalBlogFinder bpFinder) {
		this.bpFinder = bpFinder;
	}
}