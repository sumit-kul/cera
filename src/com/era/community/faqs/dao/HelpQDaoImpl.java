package com.era.community.faqs.dao; 

import java.util.List;

import com.era.community.faqs.ui.dto.HelpQDto;

public class HelpQDaoImpl extends com.era.community.faqs.dao.generated.HelpQDaoBaseImpl implements HelpQDao
{
	public List<HelpQDto> listHelpQTitles(int parentId) throws Exception
	{        
		String query ="select H.* from HelpQ H where ParentId = ? order by Sequence asc";
		List <HelpQDto> list = getBeanList(query, HelpQDto.class, parentId);
		return list;
	}
}