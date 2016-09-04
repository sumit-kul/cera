package com.era.community.faqs.dao.generated; 

import com.era.community.faqs.dao.Faq;

public interface FaqFinderBase
{
	public Faq getFaqForId(int id) throws Exception;
	public Faq newFaq() throws Exception;
}