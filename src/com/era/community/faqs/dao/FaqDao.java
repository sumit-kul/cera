package com.era.community.faqs.dao; 

interface FaqDao extends com.era.community.faqs.dao.generated.FaqDaoBase, FaqFinder
{
	public boolean isFilePresent(Faq faq) throws Exception;
	public void clearFile(Faq faq) throws Exception;
}

