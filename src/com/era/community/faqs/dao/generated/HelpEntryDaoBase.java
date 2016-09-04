package com.era.community.faqs.dao.generated; 

import com.era.community.faqs.dao.HelpEntry;

public interface HelpEntryDaoBase extends HelpEntryFinderBase
{
	public void store(HelpEntry o) throws Exception;
	public void deleteHelpEntryForId(int id) throws Exception;
	public void delete(HelpEntry o) throws Exception;
}