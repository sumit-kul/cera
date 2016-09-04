package com.era.community.pers.dao; 

import java.util.List;

import com.era.community.pers.ui.dto.UserExpertiseDto;

public class UserExpertiseLinkDaoImpl extends com.era.community.pers.dao.generated.UserExpertiseLinkDaoBaseImpl implements UserExpertiseLinkDao
{
	public List getExpertiseBeansForUserId(int id) throws Exception
	{
		String sql="select * from UserExpertise E where " +
		"exists ( select * from UserExpertiseLink L where L.UserId=? and L.UserExpertiseId=E.id)";

		List lis=getBeanList(sql, UserExpertiseDto.class, id);

		return lis; 

	}

	public int[] getExpertiseIdsForUserId(int id) throws Exception
	{
		List lis=getExpertiseBeansForUserId(id);
		int[] res = new int[lis.size()];
		for (int n=0; n<res.length; n++) {
			UserExpertiseDto bean=(UserExpertiseDto)lis.get(n);
			res[n] = bean.getId();
		}
		return res;
	}

	public void clearExpertise(int userId) throws Exception
	{
		String sql="delete from " + getTableName() + " where UserId=?";
		getSimpleJdbcTemplate().update(sql, userId);
	}
}