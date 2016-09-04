package com.era.community.tagging.ui.action;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.blog.dao.BlogEntryFinder;
import com.era.community.doclib.dao.DocumentFinder;
import com.era.community.events.dao.EventFinder;
import com.era.community.forum.dao.ForumItemFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.wiki.dao.WikiEntryFinder;

/**
 * 
 * Display all items in this community with the specified tag
 * 
 * Steps to follow to enable tagging for a new item type:
 * 
 * - Change Entity from CecAbstractEntity to TaggedEntity.
 * - Ensure command bean implements Taggable interface.
 * - Add <cop:taggingJS context="${communityEraContext}"/> to head of JSP page where tags will be edited.
 * - Add <cop:taggingSelection path="tags" context="${communityEraContext}" maxTags="15" /> where you would like tag editing control to appear in JSP.
 * - Add code to handle new item type in command bean methods getItemTitle(), etc.
 * 
 * @spring.bean name="/cid/[cec]/show-tagged.do"
 */
public class TagCommunitySearchAction extends AbstractCommandAction {

	/* Injected */
	protected CommunityEraContextManager contextManager;
	protected DocumentFinder documentFinder;	
	protected WikiEntryFinder wikiEntryFinder;
	protected BlogEntryFinder blogEntryFinder;
	protected EventFinder eventFinder;
	protected UserFinder userFinder;
    protected ForumItemFinder forumItemFinder;
	
	@Override	
	protected ModelAndView handle(Object data) throws Exception {
		
		/* Search for tags in current community */
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();
		List results = context.getCurrentCommunity().getItemsForTag(cmd.getSelectedTag());
		cmd.setResults( results );
		cmd.setResultCount( results.size() );	
        
		cmd.setDocumentFinder(documentFinder);
		cmd.setBlogEntryFinder(blogEntryFinder);
		cmd.setEventFinder(eventFinder);
		cmd.setWikiEntryFinder(wikiEntryFinder);
        cmd.setForumItemFinder(forumItemFinder);        
		cmd.setUserFinder(userFinder);
        
		return new ModelAndView("tagging/tag-search-results", "command", cmd);
	}

	public static class Command extends CommandBeanImpl implements CommandBean
	{
		final String DOCUMENT_TYPE = "Document";
		final String WIKI_TYPE = "WikiEntry";
		final String EVENT_TYPE = "Event";
		final String BLOG_TYPE = "BlogEntry";
        final String FORUM_TOPIC = "ForumTopic";
        final String FORUM_RESPONSE = "ForumResponse";
        
		private List results;
		private String selectedTag;
		private String order;
		private int resultCount;
		
		private int curItemId;
		private String curItemType;
		
		private DocumentFinder documentFinder;
		private WikiEntryFinder wikiEntryFinder;
		private BlogEntryFinder blogEntryFinder;
		private EventFinder eventFinder;
        private ForumItemFinder forumItemFinder;
		private UserFinder userFinder;
									
		public int getResultCount() {
			return resultCount;
		}

		public void setResultCount(int resultCount) {
			this.resultCount = resultCount;
		}

		public List getResults() {
			return results;
		}

		public void setResults(List results) {
			this.results = results;
		}

		public String getOrder() {
			return order;
		}
		
		public void setOrder(String order) 
		{
			this.order = order;
		}
		
		public String getSelectedTag() 
		{
			return selectedTag;
		}
		
		public void setSelectedTag(String selectedTag) 
		{
			this.selectedTag = selectedTag;
		}

		public int getCurItemId() 
		{
			return curItemId;
		}

		public void setCurItemId(int curItemId) 
		{
			this.curItemId = curItemId;
		}		
					
		public String getCurItemType() 
		{
			return curItemType;
		}

		public void setCurItemType(String curItemType) 
		{
			this.curItemType = curItemType;
		}

		public String getItemTitle() throws Exception 
		{
			
			if (getCurItemType().equalsIgnoreCase(DOCUMENT_TYPE)) {
				return documentFinder.getDocumentForId(getCurItemId()).getTitle();
			}
			
			if (getCurItemType().equalsIgnoreCase(WIKI_TYPE)) {
				return wikiEntryFinder.getWikiEntryForId(getCurItemId()).getTitle();
			}
			
			if (getCurItemType().equalsIgnoreCase(EVENT_TYPE)) {
				return eventFinder.getEventForId(getCurItemId()).getName();
			}
			
			if (getCurItemType().equalsIgnoreCase(BLOG_TYPE)) {
				return blogEntryFinder.getBlogEntryForId(getCurItemId()).getTitle();
			}
			
             if (getCurItemType().equalsIgnoreCase(FORUM_TOPIC) ||  getCurItemType().equalsIgnoreCase(FORUM_RESPONSE)) {
                return forumItemFinder.getForumItemForId(getCurItemId()).getSubject();
            }
             
			return "";
			
		}
		
		public String getItemAuthor() throws Exception 
		{
			
			if (getCurItemType().equalsIgnoreCase(DOCUMENT_TYPE)) {
				return documentFinder.getDocumentForId(getCurItemId()).getPoster().getFullName();
			}
			
			if (getCurItemType().equalsIgnoreCase(WIKI_TYPE)) {
				User user = userFinder.getUserEntity(wikiEntryFinder.getWikiEntryForId(getCurItemId()).getPosterId());
				return user.getFullName();
			}
			
			if (getCurItemType().equalsIgnoreCase(EVENT_TYPE)) {
				User user = userFinder.getUserEntity(eventFinder.getEventForId(getCurItemId()).getPosterId()); 
				return user.getFullName();
			}
			
			if (getCurItemType().equalsIgnoreCase(BLOG_TYPE)) {
				User user = userFinder.getUserEntity(blogEntryFinder.getBlogEntryForId(getCurItemId()).getPosterId());
				return user.getFullName();
			}
            
            if (getCurItemType().equalsIgnoreCase(FORUM_TOPIC) ||  getCurItemType().equalsIgnoreCase(FORUM_RESPONSE)) {
                User user = userFinder.getUserEntity(forumItemFinder.getForumItemForId(getCurItemId()).getAuthorId());
                return user.getFullName();
            }
			
			return "";
			
		}
		
		public String getItemModified() throws Exception 
		{
		
			SimpleDateFormat sd = new SimpleDateFormat("dd MMM yyyy");
			
			if (getCurItemType().equalsIgnoreCase(DOCUMENT_TYPE)) {
				return sd.format( documentFinder.getDocumentForId(getCurItemId()).getModified() );
			}
			
			if (getCurItemType().equalsIgnoreCase(WIKI_TYPE)) {
				return sd.format( wikiEntryFinder.getWikiEntryForId(getCurItemId()).getModified() );
			}
			
			if (getCurItemType().equalsIgnoreCase(EVENT_TYPE)) {
				return sd.format( eventFinder.getEventForId(getCurItemId()).getModified() );
			}
			
			if (getCurItemType().equalsIgnoreCase(BLOG_TYPE)) {
				return sd.format( blogEntryFinder.getBlogEntryForId(getCurItemId()).getModified() );
			}
            
            if (getCurItemType().equalsIgnoreCase(FORUM_TOPIC) ||  getCurItemType().equalsIgnoreCase(FORUM_RESPONSE)) {
                return sd.format( forumItemFinder.getForumItemForId(getCurItemId()).getModified() );
            }
			
			return "";
			
		}
		
		public String getItemURL() {
		
			if (getCurItemType().equalsIgnoreCase(DOCUMENT_TYPE)) {
				return "library/documentdisplay.do?backlink=ref&amp;id=" + getCurItemId();
			}
			
			if (getCurItemType().equalsIgnoreCase(WIKI_TYPE)) {
				return "wiki/wikiDisplay.do?backlink=ref&amp;id=" + getCurItemId();
			}
			
			if (getCurItemType().equalsIgnoreCase(EVENT_TYPE)) {
				return "event/showEventEntry.do?backlink=ref&amp;id=" + getCurItemId();
			}
			
			if (getCurItemType().equalsIgnoreCase(BLOG_TYPE)) {
				return "blog/blogEntry.do?backlink=ref&amp;id=" + getCurItemId();
			}
            
            if (getCurItemType().equalsIgnoreCase(FORUM_TOPIC)) {
                return "forum/forumThread.do?backlink=ref&amp;id=" + getCurItemId();
            }
			
            if (getCurItemType().equalsIgnoreCase(FORUM_RESPONSE)) {
                return "forum/forumThread.do?backlink=ref&amp;id=" + getCurItemId();
            }
            
			return "";
			
		}
		
		public String getItemType() {
			
			if (getCurItemType().equalsIgnoreCase(DOCUMENT_TYPE)) {
				return "Document";
			}
			
			if (getCurItemType().equalsIgnoreCase(WIKI_TYPE)) {
				return "Wiki entry";
			}
			
			if (getCurItemType().equalsIgnoreCase(EVENT_TYPE)) {
				return "Event";
			}
			
			if (getCurItemType().equalsIgnoreCase(BLOG_TYPE)) {
				return "Blog entry";
			}
                                    
            if (getCurItemType().equalsIgnoreCase(FORUM_TOPIC)) {
                return "Forum Item";
            }
            
            if (getCurItemType().equalsIgnoreCase(FORUM_RESPONSE)) {
                return "Forum Response";
            }
            
			return "";
			
		}

		public void setBlogEntryFinder(BlogEntryFinder blogEntryFinder) {
			this.blogEntryFinder = blogEntryFinder;
		}

		public void setDocumentFinder(DocumentFinder documentFinder) {
			this.documentFinder = documentFinder;
		}

		public void setEventFinder(EventFinder eventFinder) {
			this.eventFinder = eventFinder;
		}

		public void setWikiEntryFinder(WikiEntryFinder wikiEntryFinder) {
			this.wikiEntryFinder = wikiEntryFinder;
		}

		public void setUserFinder(UserFinder userFinder) {
			this.userFinder = userFinder;
		}

        public ForumItemFinder getForumItemFinder()
        {
            return forumItemFinder;
        }

        public void setForumItemFinder(ForumItemFinder forumItemFinder)
        {
            this.forumItemFinder = forumItemFinder;
        }
									
	}

	public void setContextHolder(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}

	public void setDocumentFinder(DocumentFinder documentFinder) {
		this.documentFinder = documentFinder;
	}

	public void setBlogEntryFinder(BlogEntryFinder blogEntryFinder) {
		this.blogEntryFinder = blogEntryFinder;
	}

	public void setEventFinder(EventFinder eventFinder) {
		this.eventFinder = eventFinder;
	}

	public void setWikiEntryFinder(WikiEntryFinder wikiEntryFinder) {
		this.wikiEntryFinder = wikiEntryFinder;
	}

	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

    public void setForumItemFinder(ForumItemFinder forumItemFinder)
    {
        this.forumItemFinder = forumItemFinder;
    }				    
    
}
