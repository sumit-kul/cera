package com.era.community.faqs.dao.generated; 

import com.era.community.faqs.dao.HelpQ;

public interface HelpQDaoBase extends HelpQFinderBase
{
	public void store(HelpQ o) throws Exception;
	public void deleteHelpQForId(int id) throws Exception;
	public void delete(HelpQ o) throws Exception;
}