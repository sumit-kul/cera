package com.era.community.pers.dao; 

import java.util.List;

import support.community.framework.Option;

public class UserExpertiseDaoImpl extends com.era.community.pers.dao.generated.UserExpertiseDaoBaseImpl implements UserExpertiseDao
{

	@SuppressWarnings("unchecked")
	public List getExpertiseOptionList() throws Exception
	{             

		/* This double query enables Other to be at the end of the list 
		 * whilst preserving alpha ordering otherwise
		 * 
		 * */ 
		String sql="select name as label, id as value from " + getTableName() + " where id != 5 order by 1";        
		List optionList=getBeanList(sql, Option.class);        

		sql="select name as label, id as value from " + getTableName() + " where id = 5 order by 1";
		List other=getBeanList(sql, Option.class);

		optionList.addAll(other);

		return optionList;        
	}


}

