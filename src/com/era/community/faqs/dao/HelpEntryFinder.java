package com.era.community.faqs.dao; 

import java.util.List;

public interface HelpEntryFinder extends com.era.community.faqs.dao.generated.HelpEntryFinderBase
{
	public List<HelpEntry> listHelpEntrys(int parentId) throws Exception;
}