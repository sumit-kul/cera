package com.era.community.home.ui.action;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.faqs.dao.HelpQFinder;
import com.era.community.faqs.ui.dto.HelpQDto;

/**
 *  @spring.bean name="/help.do" 
 */
public class DisplayHelpCenterAction extends AbstractCommandAction
{
	private HelpQFinder helpQFinder;

	protected ModelAndView handle(Object data) throws Exception
	{            
		Command cmd = (Command)data;  
		List <HelpQDto> topHeaders = helpQFinder.listHelpQTitles(0);
		for (HelpQDto header : topHeaders){
			List <HelpQDto> subHeaders = getSubHeadersList(header.getId());
			header.setHeaders(subHeaders);
			header.setSubHeaderCnt(subHeaders.size());
		}
		cmd.setTopHeaders(topHeaders);
		return new ModelAndView("/helpCenter");
	}

	private List <HelpQDto> getSubHeadersList(int parentId) throws Exception{
		List <HelpQDto> subHeaders = helpQFinder.listHelpQTitles(parentId);
		for (HelpQDto header : subHeaders){
			List <HelpQDto> innerHeader = getSubHeadersList(header.getId());
			header.setHeaders(innerHeader);
		}
		return subHeaders;
	}

	public static class Command extends CommandBeanImpl implements CommandBean
	{            
		private List <HelpQDto> topHeaders;

		public List<HelpQDto> getTopHeaders() {
			return topHeaders;
		}

		public void setTopHeaders(List<HelpQDto> topHeaders) {
			this.topHeaders = topHeaders;
		}
	}

	public void setHelpQFinder(HelpQFinder helpQFinder) {
		this.helpQFinder = helpQFinder;
	}
}