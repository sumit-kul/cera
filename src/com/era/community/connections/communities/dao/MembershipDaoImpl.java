package com.era.community.connections.communities.dao; 

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;

import com.era.community.communities.dao.CommunityRoleConstants;
import com.era.community.pers.dao.User;
import com.era.community.pers.ui.dto.MemberDto;

public class MembershipDaoImpl extends com.era.community.connections.communities.dao.generated.MembershipDaoBaseImpl implements MembershipDao
{
    public int getMembershipCount(MemberList mlist) throws Exception
    {
        String sql = "select count(*) from Membership where MemberListId = ?";
        Object o = getSimpleJdbcTemplate().queryForObject(sql, Integer.class, mlist.getId());
        return ((Integer)o).intValue();
    }
    
    public int getMembershipCountAt(MemberList mlist, Date date) throws Exception
    {
        String sql = "select count(*) from Membership where MemberListId = ? and DateJoined <= ?";
        Object o = getSimpleJdbcTemplate().queryForObject(sql, Integer.class, mlist.getId(), date  );
        return ((Integer)o).intValue();
    }

    /*
     * Returns a QueryScroller with the members of a Community Membership List
     * We want to display :
     * 
     * [Firstname, Lastname,  Organisation] from User table and 
     * [Role, Date Joined ] from Membership table
     * 
     * Users must enter a free text organisation name OR select a Local Authority
     * Where a Local Authority has been selected, we want to display that
     * 
     */
    private QueryScroller listMembers(MemberList mlist) throws Exception
    {
        String sQuery= "select U.*, M.Role, M.DateJoined" +
        
                                " from User U, Membership M" +
                                " where U.Id = M.UserId" +
                                "  and M.MemberListId = ?";

        // Set a default BeanClass here, which will be overwritten in the IndexAction, typically with the RowBean class
        // User doesn't have Role and DateJoined so is not what we want,
        // so in MemberIndexAction we use the RowBean
        QueryScroller scroller = getQueryScroller(sQuery, User.class, mlist.getId());

        return scroller;
    }


    public QueryScroller listMembersByName(MemberList mlist) throws Exception 
    {
        QueryScroller scroller = listMembers(mlist);
        scroller.addScrollKey("STEMP.FirstName", "FirstName", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
        scroller.addScrollKey("STEMP.LastName", "LastName", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
        return scroller;
    }
    
    public QueryScroller listMembersByRole(MemberList mlist) throws Exception 
    {
        QueryScroller scroller = listMembers(mlist);
        scroller.addScrollKey("STEMP.Role", "FullNameUC", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
        return scroller;
    }
    
    public QueryScroller listMembersByOrganisation(MemberList mlist) throws Exception 
    {               
        /*
         * This query is to support sorting by Organisation (where organisation can be a Local Authority or a free text organisation)
         */
        
        String sQuery = "select U.Id, U.Region, U.LocalAuthorityId, U.FirstName, U.LastName, U.FullNameUC, coalesce(L.OrgShortName, U.Organisation) As Organisation, "+
        " M.Role, M.DateJoined, M.MemberListId from User U inner join Membership M on U.Id = M.UserId  " +
        " left outer join Extdata.LocalAuthorities L on U.LocalAuthorityId = L.OrgId "+
        " left outer join Organisation O on U.LocalAuthorityId = O.Id " +
        " where M.MemberListId = ?";
        
        QueryScroller scroller = getQueryScroller(sQuery, null, mlist.getId());
        
        // Set a default BeanClass here, which will be overwritten in the IndexAction, typically with the RowBean class
        
        // User doesn't have Role and DateJoined so is not what we want,
        // so in MemberIndexAction we use the RowBean
        scroller.setBeanClass(User.class);
        
        
        // N.B. You need one "addScrollKey" for each key you want to sort by
        // The scroller can support max two scroll keys
        scroller.addScrollKey("coalesce(L.OrgShortName, U.Organisation)", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
        scroller.addScrollKey("STEMP.FullNameUC", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);

        return scroller;
    }

    public QueryScroller listMembersByDateJoined(MemberList mlist) throws Exception
    {
        QueryScroller scroller = listMembers(mlist);
        scroller.addScrollKey("STEMP.DateJoined", "DateJoined", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
        return scroller;
    }

    /**
     */
    public Membership getMembershipForUserId(MemberList mlist, int userId) throws Exception
    {
         return (Membership)getEntityWhere("UserId=? and MemberListId=?", userId, mlist.getId());
    } 
    
    
   public void deleteAllMembershipsForUser(int userId) throws Exception
    {
        String sql="delete from Membership M where M.UserId = ?";
      
        getSimpleJdbcTemplate().update(sql, userId);       
    }
    
    public List getAdminMembers(MemberList mlist) throws Exception
    {
        String query = "select U.* from User U, Membership M where U.Id = M.UserId and M.MemberListId = ? and ( M.Role = '"+CommunityRoleConstants.ADMIN+"' or M.Role = '"+CommunityRoleConstants.ADMIN+"' ) ";
        return getBeanList(query, User.class, mlist.getId());
    }
    
    public List getMostRecentMembers(MemberList mlist) throws Exception
    {
          String query = "select U.*, M.DateJoined from User U, Membership M where U.Id = M.UserId and M.MemberListId = ? order by M.DateJoined desc LIMIT 20 ";
          return getBeanList(query, MemberDto.class, mlist.getId());
    }
    
    public List getMostRecentMemberNames(MemberList mlist) throws Exception
    {
          String query = "select CONCAT_WS(' ',FirstName,LastName) as MemberName from User U, Membership M where U.Id = M.UserId and M.MemberListId = ? order by M.DateJoined desc LIMIT 20 ";
        List data = getSimpleJdbcTemplate().queryForList(query, mlist.getId());
		List<String> result = new ArrayList<String>(data.size());
		for (int n = 0; n < data.size(); n++) {
			Map m = (Map) data.get(n);
			String name = ((String) m.get("MemberName")).toString();
			result.add(name);
		}
		return result;
    }
    
    public List getMembersByDateJoined(MemberList mlist) throws Exception
    {
          String query = "select U.* from User U, Membership M where U.Id = M.UserId and M.MemberListId = ? order by M.DateJoined asc";
        return getBeanList(query, User.class, mlist.getId());
    }
    
    public List getAdminByName(MemberList mlist) throws Exception
    {
          String query = "select U.* from User U, Membership M where U.Id = M.UserId and M.MemberListId = ? and M.Role = '"
              + CommunityRoleConstants.ADMIN
              +"' order by U.FirstName DESC";
        return getBeanList(query, User.class, mlist.getId());
    }

    /**
     * Return the number of users who belong to at least one community in a given group. 
     * 
     */
    public int getMemberCount(int groupId) throws Exception
    {
        return getMemberCountAt(new Date(), groupId);
    }

    /**
     * Return the number of users who belong to at least one community in a given group
     * and joined no later than a given date. 
     * 
     */
    public int getMemberCountAt(Date date, int groupId) throws Exception
    {
        if (groupId>0) {
            String query = "select count(*) from User U where exists (" +
               "select * from Membership M, MemberList T, CommunityGroupLink L " +
               "where M.UserId = U.Id and T.Id = M.MemberListId " +
               "and L.CommunityId = T.CommunityId and L.CommunityGroupId = ? "+
               "and M.DateJoined <= ?" +
             ")";
            return getSimpleJdbcTemplate().queryForInt(query,  groupId, date );
        }
        else {
            String query = "select count(*) from User U where exists (select * from Membership M where M.UserId = U.Id and M.DateJoined <= ?)";
            return getSimpleJdbcTemplate().queryForInt(query, date );
        }
    }

    /**
     * Return the number of membership records for the communities in a given group. 
     */
    public int getMembershipCount(int groupId) throws Exception
    {
        if (groupId>0) {
            String query = "select count(*) from Membership M, MemberList T, CommunityGroupLink L " +
               "where T.Id = M.MemberListId " +
               "and L.CommunityId = T.CommunityId and L.CommunityGroupId = ? ";
            return getSimpleJdbcTemplate().queryForInt(query, groupId );
        }
        else {
            String query = "select count(*) from Membership";
            return getJdbcTemplate().queryForInt(query);
        }
    }
    
    public Membership getMembershipForUserAndMemberList(int userId, int listId) throws Exception
    {
        String query = "select M.* from Membership M where M.UserId = ? and M.MemberListId = ?";
        return (Membership)getEntity(query, new Object[] { userId, listId });
    }
    
    /**
     * Return the number of common membership count... to check if a user is a member of a community of second user's communities... 
     */
    public int getCommonMembershipCount(int user1, int user2) throws Exception
    {
            String query = "select count(distinct MS2.ID) from MemberList ML, Membership MS1, Membership MS2 " +
            		" where MS1.MemberListId = ML.Id and MS2.MemberListId = ML.Id and MS1.UserId = ? and MS2.UserId = ? and ML.Inactive = 'N'";
            return getSimpleJdbcTemplate().queryForInt(query, user1, user2);
    }
}