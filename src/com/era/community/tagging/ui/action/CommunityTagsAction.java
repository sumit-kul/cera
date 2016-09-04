package com.era.community.tagging.ui.action;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.tagging.dao.TagFinder;

/**
 * 
 * Display all tags within this community as a list
 * 
 * @spring.bean name="/cid/[cec]/communityTags.do"
 */
public class CommunityTagsAction extends AbstractCommandAction {

	/* Injected */
	protected CommunityEraContextManager contextManager;
	protected TagFinder tagFinder;

	@Override
	protected ModelAndView handle(Object data) throws Exception {
		Command cmd = (Command) data;		
		CommunityEraContext context = contextManager.getContext();
		List tags = tagFinder.getTagsAlphaForCommunityId( context.getCurrentCommunity().getId(), 0 );

		cmd.setTagList(tags);
		return new ModelAndView("tag/communityTags", "command", cmd);
	}

	public static class Command extends CommandBeanImpl implements CommandBean
	{
		private List tagList;
		private int parentId;

		public List getTagList() {
			return tagList;
		}

		public void setTagList(List tagList) {
			this.tagList = tagList;
		}

		public int getParentId()
		{
			return parentId;
		}

		public void setParentId(int parentId)
		{
			this.parentId = parentId;
		}

	}

	public void setContextHolder(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}

	public void setTagFinder(TagFinder tagFinder) {
		this.tagFinder = tagFinder;
	}			

}
