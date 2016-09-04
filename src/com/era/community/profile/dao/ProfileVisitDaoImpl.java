package com.era.community.profile.dao; 

import java.util.List;

import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;

import com.era.community.pers.dao.User;
import com.era.community.pers.ui.dto.UserDto;

public class ProfileVisitDaoImpl extends com.era.community.profile.dao.generated.ProfileVisitDaoBaseImpl implements ProfileVisitDao
{
	public ProfileVisit getProfileVisit(int profileUserId, int visitingUserId) throws Exception
	{
		ProfileVisit profileVisit= (ProfileVisit)getEntityWhere(" ProfileUserId = ? and VisitingUserId = ? ", profileUserId, visitingUserId);
		return profileVisit;
	}

	public void deleteAllProfileVisitsForUser(int profileUserId) throws Exception
	{
		String sql="delete from profileVisit where ProfileUserId = ?";
		getSimpleJdbcTemplate().update(sql, profileUserId);       
	}
	
	public void deleteProfileVisitsForUserAndId(int profileUserId, int profileVisitId) throws Exception
	{
		String sql="delete from profileVisit where ProfileUserId = ? and ID = ?";
		getSimpleJdbcTemplate().update(sql, profileUserId, profileVisitId);       
	}
	
	public void markAllVisitingAsSeen(int profileUserId) throws Exception
	{
		String sql="update profileVisit set Status = 1 where ProfileUserId = ?";
		getSimpleJdbcTemplate().update(sql, profileUserId);       
	}

	public int getUnseenProfileVisitCountForCurrentUser(int profileUserId) throws Exception 
	{
		String query = "select count(*) from ProfileVisit where ProfileUserId = ? "; //and Status = 0 ";
		return getSimpleJdbcTemplate().queryForInt(query, profileUserId );
	}

	public QueryScroller getUnseenProfileVisitorListForCurrentUser(int profileUserId, String filterBy, String sortBy) throws Exception 
	{
		String typeBy = "";
		if("All Entries".equalsIgnoreCase(filterBy)) {
			typeBy = " and (P.Status = 0 or P.Status = 1)";
		} else if ("Seen Requests".equalsIgnoreCase(filterBy)) {
			typeBy = " and P.Status = 1";
		} else {
			typeBy = " and P.Status = 0";
		}
		String query = "select distinct P.Id ProfileVisitId, P.Status, U.* from ProfileVisit P, User U  where P.VISITINGUSERID = U.ID and P.ProfileUserId = ? " 
			+ typeBy; //and Status = 0 ";
		QueryScroller scroller = getQueryScroller(query, UserDto.class, profileUserId);

		if ("Name".equalsIgnoreCase(sortBy)) {
			scroller.addScrollKey("STEMP.FirstName", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
		} else {
			scroller.addScrollKey("STEMP.Modified", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
			scroller.addScrollKey("STEMP.Created", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		}
		return scroller;
	}
	
	public List getProfileVisitorsForUser(int profileUserId, int currentUserId, int max) throws Exception
	{
		String query = "select distinct P.Id ProfileVisitId, P.Status, U.* from ProfileVisit P, User U  "
			+ " where P.VISITINGUSERID = U.ID and P.ProfileUserId = ? and P.VisitingUserId <> ? order by P.Created desc ";

		if (max != 0) {
			query += " LIMIT " + max + " ";
		}
		
		return getBeanList(query, User.class, profileUserId, currentUserId);
	}
}