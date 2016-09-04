package com.era.community.faqs.dao.generated; 

import com.era.community.faqs.dao.HelpEntry;

public interface HelpEntryFinderBase
{
	public HelpEntry getHelpEntryForId(int id) throws Exception;
	public HelpEntry newHelpEntry() throws Exception;
}