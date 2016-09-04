package com.era.community.pers.dao; 

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;

import com.era.community.pers.ui.dto.InterestDto;

public class InterestDaoImpl extends com.era.community.pers.dao.generated.InterestDaoBaseImpl implements InterestDao
{
	@SuppressWarnings("unchecked")
	public List getInterestList(int categoryId, int offset, int rows) throws Exception
	{             
		//if (categoryId > 0) {
			String query = "select * from Interest where Active = 1 and CategoryId = ? LIMIT ? , ?";
			return getBeanList(query, Interest.class, categoryId, offset - 1, rows); 
		//} else {
			//String query = "select IL.InterestId, count(*) from InterestLink IL group by IL.InterestId order by count desc, TG.InterestI LIMIT ? , ?";
			//return getBeanList(query, Interest.class, categoryId, offset - 1, rows); 
		//}
	}

	public List searchInterestsForInput(String input) throws Exception
	{
		String query = "select * from Interest where Active = 1 and Interest like '"+input+"%'";
		return getBeanList(query, Interest.class);
	}

	public List getInterestListForProfileId(int userId) throws Exception
	{
		String query = "select distinct I.* from Interest I, InterestLink IL where I.Active = 1 and I.ID = IL.InterestId and IL.ProfileId = ?";
		return getBeanList(query, InterestDto.class, userId);
	}

	public List getInterestListForProfileId(int profileId, int currentUserId) throws Exception
	{
		String query = "select distinct I.*, coalesce (ITL.Id, 0 ) Selected from Interest I " +
		" Join InterestLink IL ON I.ID = IL.InterestId " +
		" left outer join InterestLink ITL ON I.ID = ITL.InterestId and ITL.ProfileId = ? " +
		" where I.Active = 1 and IL.ProfileId = ? ";
		return getBeanList(query, InterestDto.class, currentUserId, profileId);
	}

	public int countForCommonInterest(int profileId, int currentUserId) throws Exception
	{
		String query = "select count(I.ID) from Interest I " +
		" inner Join InterestLink IL ON I.ID = IL.InterestId and IL.ProfileId = ? " + 
		" inner Join InterestLink ITL ON I.ID = ITL.InterestId and ITL.ProfileId = ? where I.Active = 1 ";
		return getSimpleJdbcTemplate().queryForInt(query, profileId, currentUserId); 
	}
	
	public int countInterestsPerCategory(int categoryId) throws Exception
	{
		//if (categoryId > 0) {
			String query = "select count(ID) from Interest where Active = 1 and CategoryId = ? ";
			return getSimpleJdbcTemplate().queryForInt(query, categoryId); 
		//} else {
		//	String query = "select count(ID) from Interest where Active = 1 ";
		//	return getSimpleJdbcTemplate().queryForInt(query); 
		//}
	}

	public QueryScroller getInterestsForProfileId(int profileId, int currentUserId) throws Exception
	{
		String query = "select distinct I.*, coalesce (ITL.Id, 0 ) Selected from Interest I " +
		" Join InterestLink IL ON I.ID = IL.InterestId " +
		" left outer join InterestLink ITL ON I.ID = ITL.InterestId and ITL.ProfileId = ? " +
		" where I.Active = 1 and IL.ProfileId = ? ";
		QueryScroller scroller = getQueryScroller(query, true, null, currentUserId, profileId);
		scroller.addScrollKey("STEMP.Created", "Created", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		return scroller;
	}
	
	public List<String> getInterestCategoryListForProfileId(int profileId) throws Exception
	{
		String query = "select distinct MD.Label from InterestLink IL, Interest I, MasterData MD " +
						"where IL.InterestId = I.Id and I.CategoryId = MD.ID and Category = 'InterestType' and IL.ProfileId = ? ";
		List data = getSimpleJdbcTemplate().queryForList(query, profileId);
		List<String> result = new ArrayList<String>(data.size());
		for (int n = 0; n < data.size(); n++) {
			Map m = (Map) data.get(n);
			String name = ((String) m.get("Label")).toString();
			result.add(name);
		}
		return result;
	}
}