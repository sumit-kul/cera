package com.era.community.faqs.dao; 

import support.community.database.QueryScroller;

public class FaqDaoImpl extends com.era.community.faqs.dao.generated.FaqDaoBaseImpl implements FaqDao
{
	public QueryScroller listAllFaqs() throws Exception
	{        
		QueryScroller scroller = getQueryScroller("select F.* from Faq F", null);
		scroller.addScrollKey("Sequence", QueryScroller.DIRECTION_ASCENDING, QueryScroller.TYPE_INTEGER);
		return scroller;
	}

	public boolean isFilePresent(Faq faq) throws Exception
	{
		Integer v = (Integer) getValue(faq, "case when File is null then 0 else 1 end ", Integer.class);
		return v.intValue() == 1;
	}


	public void clearFile(Faq faq) throws Exception
	{
		setColumn(faq, "File", null);
		setColumn(faq, "FileContentType", null);
		setColumn(faq, "FileName", null);
		setColumn(faq, "FileLength", 0);
	}
}

