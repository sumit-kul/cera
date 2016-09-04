package com.era.community.upload.dao; 

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ImageDaoImpl extends com.era.community.upload.dao.generated.ImageDaoBaseImpl implements ImageDao
{
	public void deleteImagesForWikiEntry(int wikiEntryId) throws Exception
	{
		String sql="delete from " + getTableName() + " where WikiEntryId = ?";
		getSimpleJdbcTemplate().update(sql, wikiEntryId);        
	}
	
	public void deleteImagesForAllWikiEntryVersions(int entryId) throws Exception
	{
		String sql="delete from Image where WikiEntryId in ( select W.ID from WikiEntry W where W.EntryId =  ? ) ";
		getSimpleJdbcTemplate().update(sql, entryId);        
	}
	
	public Image getImageForItemType(int itemId, String type) throws Exception
	{
		String query = "";
		if ("BlogEntry".equalsIgnoreCase(type)) {
			query = "select * from Image where MarkDeleted = 0 and BlogEntryId = ? order by Id asc LIMIT 1";
		} else if ("WikiEntry".equalsIgnoreCase(type)) {
			query = "select * from Image where MarkDeleted = 0 and WikiEntryId = ? order by Id asc LIMIT 1";
		} else if ("WikiEntrySection".equalsIgnoreCase(type)) {
			query = "select * from Image where MarkDeleted = 0 and WikiEntrySectionId = ? order by Id asc LIMIT 1";
		} else if ("ForumItem".equalsIgnoreCase(type)) {
			query = "select * from Image where MarkDeleted = 0 and ForumItemId = ? order by Id asc LIMIT 1";
		} else if ("BlogEntryComment".equalsIgnoreCase(type)) {
			query = "select * from Image where MarkDeleted = 0 and BlogEntryCommentId = ? order by Id asc LIMIT 1";
		} else if ("WikiEntryComment".equalsIgnoreCase(type)) {
			query = "select * from Image where MarkDeleted = 0 and WikiEntryCommentId = ? order by Id asc LIMIT 1";
		} else if ("CommunityDescription".equalsIgnoreCase(type)) {
			query = "select * from Image where MarkDeleted = 0 and CommunityDescriptionId = ? order by Id asc LIMIT 1";
		} else if ("AboutMe".equalsIgnoreCase(type)) {
			query = "select * from Image where MarkDeleted = 0 and AboutMeId = ? order by Id asc LIMIT 1";
		} else if ("Message".equalsIgnoreCase(type)) {
			query = "select * from Image where MarkDeleted = 0 and MessageId = ? order by Id asc LIMIT 1";
		} else if ("WikiEntrySectionAll".equalsIgnoreCase(type)) {
			query = "select I.* from Image I, WikiEntrySection S, WikiEntry W where S.ID = I.WikiEntrySectionId and S.WikiEntryId = W.Id " +
					" and W.EntryId = ? and W.entrySequence = " + Integer.MAX_VALUE + " and I.MarkDeleted = 0 order by I.ID asc limit 1 ";
		} else if ("Assignment".equalsIgnoreCase(type)) {
			query = "select * from Image where MarkDeleted = 0 and AssignmentId = ? order by Id asc LIMIT 1";
		} 
		
		return getBean(query, Image.class, itemId);
	}
	
	public int countImageForItemType(int itemId, String type) throws Exception
	{
		String query = "";
		if ("BlogEntry".equalsIgnoreCase(type)) {
			query = "select count(*) from Image where MarkDeleted = 0 and BlogEntryId = ? ";
		} else if ("WikiEntry".equalsIgnoreCase(type)) {
			query = "select count(*) from Image where MarkDeleted = 0 and WikiEntryId = ? ";
		} else if ("WikiEntrySection".equalsIgnoreCase(type)) {
			query = "select count(*) from Image where MarkDeleted = 0 and WikiEntrySectionId = ? ";
		} else if ("ForumItem".equalsIgnoreCase(type)) {
			query = "select count(*) from Image where MarkDeleted = 0 and ForumItemId = ? ";
		} else if ("BlogEntryComment".equalsIgnoreCase(type)) {
			query = "select count(*) from Image where MarkDeleted = 0 and BlogEntryCommentId = ? ";
		} else if ("WikiEntryComment".equalsIgnoreCase(type)) {
			query = "select count(*) from Image where MarkDeleted = 0 and WikiEntryCommentId = ? ";
		} else if ("CommunityDescription".equalsIgnoreCase(type)) {
			query = "select count(*) from Image where MarkDeleted = 0 and CommunityDescriptionId = ? ";
		} else if ("AboutMe".equalsIgnoreCase(type)) {
			query = "select count(*) from Image where MarkDeleted = 0 and AboutMeId = ? ";
		} else if ("Message".equalsIgnoreCase(type)) {
			query = "select count(*) from Image where MarkDeleted = 0 and MessageId = ? ";
		} else if ("WikiEntrySectionAll".equalsIgnoreCase(type)) {
			query = "select count(I.Id) from Image I, WikiEntrySection S, WikiEntry W where S.ID = I.WikiEntrySectionId and S.WikiEntryId = W.Id " +
			" and W.EntryId = ? and W.entrySequence = " + Integer.MAX_VALUE + " and I.MarkDeleted = 0 ";
		} else if ("Assignment".equalsIgnoreCase(type)) {
			query = "select count(*) from Image where MarkDeleted = 0 and AssignmentId = ? ";
		}
		return getSimpleJdbcTemplate().queryForInt(query, itemId);
	}
	
	public void deleteImagesForItemType(int itemId, String type) throws Exception
	{
		String query = "";
		if ("BlogEntry".equalsIgnoreCase(type)) {
			query = "delete from Image where MarkDeleted = 1 and BlogEntryId  =  ? ";
		} else if ("WikiEntry".equalsIgnoreCase(type)) {
			query = "delete from Image where MarkDeleted = 1 and WikiEntryId  =  ? ";
		} else if ("WikiEntrySection".equalsIgnoreCase(type)) {
			query = "delete from Image where MarkDeleted = 1 and WikiEntrySectionId  =  ? ";
		} else if ("ForumItem".equalsIgnoreCase(type)) {
			query = "delete from Image where MarkDeleted = 1 and ForumItemId  =  ? ";
		} else if ("BlogEntryComment".equalsIgnoreCase(type)) {
			query = "delete from Image where MarkDeleted = 1 and BlogEntryCommentId  =  ? ";
		} else if ("WikiEntryComment".equalsIgnoreCase(type)) {
			query = "delete from Image where MarkDeleted = 1 and WikiEntryCommentId  =  ? ";
		} else if ("CommunityDescription".equalsIgnoreCase(type)) {
			query = "delete from Image where MarkDeleted = 1 and CommunityDescriptionId  =  ? ";
		} else if ("AboutMe".equalsIgnoreCase(type)) {
			query = "delete from Image where MarkDeleted = 1 and AboutMeId  =  ? ";
		} else if ("Message".equalsIgnoreCase(type)) {
			query = "delete from Image where MarkDeleted = 1 and MessageId  =  ? ";
		} else if ("Assignment".equalsIgnoreCase(type)) {
			query = "delete from Image where MarkDeleted = 1 and AssignmentId  =  ? ";
		}
		
		getSimpleJdbcTemplate().update(query, itemId);
	}
	
	public void markImagesForDeletion(int itemId, String type) throws Exception
	{
		String query = "";
		if ("BlogEntry".equalsIgnoreCase(type)) {
			query = "update Image set MarkDeleted = 1 where BlogEntryId  =  ? ";
		} else if ("WikiEntry".equalsIgnoreCase(type)) {
			query = "update Image set MarkDeleted = 1 where WikiEntryId  =  ? ";
		} else if ("WikiEntrySection".equalsIgnoreCase(type)) {
			query = "update Image set MarkDeleted = 1 where WikiEntrySectionId  =  ? ";
		} else if ("ForumItem".equalsIgnoreCase(type)) {
			query = "update Image set MarkDeleted = 1 where ForumItemId  =  ? ";
		} else if ("BlogEntryComment".equalsIgnoreCase(type)) {
			query = "update Image set MarkDeleted = 1 where BlogEntryCommentId  =  ? ";
		} else if ("WikiEntryComment".equalsIgnoreCase(type)) {
			query = "update Image set MarkDeleted = 1 where WikiEntryCommentId  =  ? ";
		} else if ("CommunityDescription".equalsIgnoreCase(type)) {
			query = "update Image set MarkDeleted = 1 where CommunityDescriptionId  =  ? ";
		} else if ("AboutMe".equalsIgnoreCase(type)) {
			query = "update Image set MarkDeleted = 1 where AboutMeId  =  ? ";
		} else if ("Message".equalsIgnoreCase(type)) {
			query = "update Image set MarkDeleted = 1 where MessageId  =  ? ";
		} else if ("Assignment".equalsIgnoreCase(type)) {
			query = "update Image set MarkDeleted = 1 where AssignmentId  =  ? ";
		} 
		
		getSimpleJdbcTemplate().update(query, itemId);
	}
	
	public List selectImagesForBlogEntry() throws Exception{
		String sql = "select min(ID) as ID from Image  where BlogEntryId > 0 group by BlogEntryId ";
		List data = getSimpleJdbcTemplate().queryForList(sql);
		List<Integer> result = new ArrayList<Integer>(data.size());
		for (int n = 0; n < data.size(); n++) {
			Map m = (Map) data.get(n);
			Integer id = (Integer) m.get("ID");
			result.add(id);
		}
		return result;
	}
	
	public List selectImagesForWikiEntry() throws Exception{
		String sql = "select min(ID) as ID from Image  where ( WikiEntryId > 0 or WikiEntrySectionId > 0 ) group by WikiEntryId , WikiEntrySectionId";
		List data = getSimpleJdbcTemplate().queryForList(sql);
		List<Long> result = new ArrayList<Long>(data.size());
		for (int n = 0; n < data.size(); n++) {
			Map m = (Map) data.get(n);
			Long id = (Long) m.get("ID");
			result.add(id);
		}
		return result;
	}
}