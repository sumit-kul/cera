package com.era.community.faqs.dao; 

import java.util.List;

public class HelpEntryDaoImpl extends com.era.community.faqs.dao.generated.HelpEntryDaoBaseImpl implements HelpEntryDao
{
	public List<HelpEntry> listHelpEntrys(int parentId) throws Exception
	{        
		String query ="select H.* from HelpEntry H where ParentId = ? order by Sequence asc";
		List <HelpEntry> list = getBeanList(query, HelpEntry.class, parentId);
		return list;
	}
}