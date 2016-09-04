package com.era.community.faqs.dao; 

import support.community.database.QueryScroller;

public interface FaqFinder extends com.era.community.faqs.dao.generated.FaqFinderBase
{
	public QueryScroller listAllFaqs() throws Exception;
}