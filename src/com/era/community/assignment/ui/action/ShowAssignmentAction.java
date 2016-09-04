package com.era.community.assignment.ui.action;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.TreeMap;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.IndexCommandBean;
import support.community.util.StringHelper;

import com.era.community.assignment.dao.Assignment;
import com.era.community.assignment.dao.AssignmentFeature;
import com.era.community.assignment.dao.AssignmentFinder;
import com.era.community.assignment.dao.generated.AssignmentEntity;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.base.Taggable;
import com.era.community.forum.ui.validator.ForumItemValidator;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.tagging.dao.TagFinder;
import com.era.community.tagging.dao.generated.TagEntity;
import com.era.community.tagging.ui.dto.TagDto;

/**
 * @spring.bean name="/cid/[cec]/assignment/showAssignment.do"
 */
public class ShowAssignmentAction extends AbstractCommandAction
{
	public static final String REQUIRES_AUTHENTICATION = "";

	protected AssignmentFeature assignmentFeature;
	protected AssignmentFinder assignmentFinder;
	protected UserFinder userFinder;
	protected TagFinder tagFinder;

	protected CommunityEraContextManager contextManager;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command) data;
		Assignment assignment = assignmentFinder.getAssignmentForId(cmd.getId());
		User user = userFinder.getUserEntity(assignment.getCreatorId());
		cmd.setPhotoPresent(user.isPhotoPresent()? "Y" : "N");
		cmd.setAssigner(user.getFullName());
		cmd.setTitle(assignment.getTitle());
		cmd.setCreatorId(assignment.getCreatorId());
		cmd.setDatePosted(assignment.getDatePosted());
		cmd.setBody(assignment.getBody());
		cmd.setDueDate(assignment.getDueDate());
		getAssignmentEntries(cmd, assignment);
		cmd.setMetaForDescription(cmd.getBody());
		Date today = new Date();
		if (assignment.getDueDate().before(today)) {
			cmd.setPassedDue(true);
		}
		cmd.setSearchType("Assignment");
		cmd.setQueryText(assignment.getTitle());
		return new ModelAndView("/assignment/showAssignment");
	}
	
	private void getAssignmentEntries(Command bean, Assignment assignment) throws Exception
	{
		List items = assignmentFinder.getEntryBeans(bean.getId());
		bean.setItems(items);
		bean.setEntryCount(Long.valueOf(bean.getItems().size()));
	}

	public String getPostedOn(String datePosted) throws Exception
	{
		SimpleDateFormat fmter = new SimpleDateFormat("dd-MMM-yyyy");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat fmt = new SimpleDateFormat("HH:mm a");
		SimpleDateFormat fmt2 = new SimpleDateFormat("dd MMM, yy");
		Date today = new Date();
		String sToday = fmter.format(today);

		try {

			Date date = formatter.parse(datePosted);
			if (fmter.format(date).equals(sToday)) {
				return "Today " + fmt.format(date);
			}
			return fmt2.format(date);

		} catch (ParseException e) {
			return datePosted;
		}
	}

	public String getPostedOnHover(String datePosted) throws Exception
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy HH:mm a");
		try {
			Date date = formatter.parse(datePosted);
			return fmt2.format(date);
		} catch (ParseException e) {
			return datePosted;
		}
	}

	public class Command extends AssignmentEntity implements CommandBean, Taggable
	{
		private String tags;        
		private List m_items;
		private int isRoot;
		private String Assigner;
		private String PhotoPresent;
		private boolean passedDue;
		private String ddate;
		
		private int entryCount;
		
		private String keywords;
		private String metaForDescription;
		
		public final int getEntryCount()
		{
			return entryCount;
		}

		public final void setEntryCount(Long entryCount)
		{
			this.entryCount = entryCount.intValue();
		}
		
		public String getIsoPostedOn() throws Exception
		{
			TimeZone tz = TimeZone.getTimeZone("UTC");
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
			df.setTimeZone(tz);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = formatter.parse(getDatePosted());
			String nowAsISO = df.format(date);
			return nowAsISO;
		}
		
		public String getDisplayBody()
		{
			if ( (this.getBody()==null) || (this.getBody().length()==0)) return "";

			String sBody = this.getBody();
			//sBody = Jsoup.parse(sBody).text();
			//sBody = StringHelper.escapeHTML(sBody);
			return sBody;
		}

		public String getTaggedKeywords(){
			String taggedKeywords = "";
			try {
				List tags = tagFinder.getTagsForParentTypeByPopularity(this.getId(), 0, 20, "Assignment");
				for (Iterator iterator = tags.iterator(); iterator.hasNext();) {
					TagDto tb = (TagDto) iterator.next();
					String tag = tb.getTagText().trim().toLowerCase();
					taggedKeywords += "<a href='"+contextManager.getContext().getCurrentCommunityUrl()+"/search/searchByTagInCommunity.do?filterTag="+tag+"' class='euInfoSelect normalTip' style='display: inline;' title='Click to filter by tag &#39;"+tag+"&#39;'>"+tag+"</a>";
					if (iterator.hasNext())taggedKeywords += ", ";
				}
			} catch (Exception e) {
				return taggedKeywords;
			}
			return taggedKeywords;
		}

		public String getTags()
		{
			return tags;
		}

		public void setTags(String tags)
		{
			this.tags = tags;
		}

		public TreeMap getPopularTags()
		{
			return null;
		}

		public List getItems()
		{
			return m_items;
		}

		public void setItems(List items)
		{
			m_items = items;
		}

		public String getPostedOn() throws Exception
		{
			SimpleDateFormat fmter = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat fmt = new SimpleDateFormat("HH:mm a");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yy");
			Date today = new Date();
			String sToday = fmter.format(today);
			try {
				Date date = formatter.parse(getDatePosted());
				if (fmter.format(date).equals(sToday)) {
					return "Today " + fmt.format(date);
				}
				return fmt2.format(date);
			} catch (Exception e) {
				return getCreated();
			}
		}
		
		public String getDueOn() throws Exception
		{
			SimpleDateFormat fmter = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat fmt = new SimpleDateFormat("HH:mm a");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yy");
			Date today = new Date();
			String sToday = fmter.format(today);
			try {
				Date date = formatter.parse(getDueDate());
				if (fmter.format(date).equals(sToday)) {
					return "Today " + fmt.format(date);
				}
				return fmt2.format(date);
			} catch (Exception e) {
				return getDueDate();
			}
		}
		
		public String getDueOnHover() throws Exception
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy HH:mm a");
			try {
				
				Date date = formatter.parse(getDueDate());
				return fmt2.format(date);
			} catch (Exception e) {
				return getDueDate();
			}
		}
		
		public String getPostedOnHover() throws Exception
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy HH:mm a");
			try {
				
				Date date = formatter.parse(getDatePosted());
				return fmt2.format(date);
			} catch (Exception e) {
				return getCreated();
			}
		}

		public String getKeywords(){
			String keywords = "";
			try {
				List tags = tagFinder.getTagsForParentTypeByPopularity(this.getId(), 0, 20, "Assignment");
				
				for (Iterator iterator = tags.iterator(); iterator.hasNext();) {
					TagEntity tb = (TagEntity) iterator.next();
					String tag = tb.getTagText().trim().toLowerCase();
					keywords += tag;
					if (iterator.hasNext())keywords += " , ";
				}
			} catch (Exception e) {
				return keywords;
			}
			return keywords;
		}

		public void setKeywords(String keywords) {
			this.keywords = keywords;
		}

		public String getMetaForDescription() {
			return metaForDescription;
		}

		public void setMetaForDescription(String metaForDescription) {
			this.metaForDescription = StringHelper.escapeHTML(metaForDescription);
		}

		public String getAssigner() {
			return Assigner;
		}

		public void setAssigner(String assigner) {
			Assigner = assigner;
		}

		public String getPhotoPresent() {
			return PhotoPresent;
		}

		public void setPhotoPresent(String photoPresent) {
			PhotoPresent = photoPresent;
		}

		public int getIsRoot() {
			return isRoot;
		}

		public void setIsRoot(int isRoot) {
			this.isRoot = isRoot;
		}

		public boolean isPassedDue() {
			return passedDue;
		}

		public void setPassedDue(boolean passedDue) {
			this.passedDue = passedDue;
		}

		public String getDdate() {
			return ddate;
		}

		public void setDdate(String ddate) {
			this.ddate = ddate;
		}
	}

	public class Validator extends ForumItemValidator
	{
		public String validateSubject(Object value, CommandBean cmd)
		{
			if (value.toString().length() > 200) {
				return "The maximum length of the title is 200 characters, please shorten your title";
			}
			return null;
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	protected String getView(IndexCommandBean bean) throws Exception
	{
		return null;
	}

	public void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}

	public final void setTagFinder(TagFinder tagFinder)
	{
		this.tagFinder = tagFinder;
	}

	public void setContextManager(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}

	public void setAssignmentFeature(AssignmentFeature assignmentFeature) {
		this.assignmentFeature = assignmentFeature;
	}

	public void setAssignmentFinder(AssignmentFinder assignmentFinder) {
		this.assignmentFinder = assignmentFinder;
	}
}