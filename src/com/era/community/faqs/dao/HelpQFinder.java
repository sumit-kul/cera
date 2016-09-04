package com.era.community.faqs.dao; 

import java.util.List;

import com.era.community.faqs.ui.dto.HelpQDto;

public interface HelpQFinder extends com.era.community.faqs.dao.generated.HelpQFinderBase
{
	public List<HelpQDto> listHelpQTitles(int parentId) throws Exception;
}