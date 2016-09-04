package com.era.community.forum.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

import support.community.application.ElementNotFoundException;

import com.era.community.pers.dao.User;

public class ForumResponse extends ForumItem
{
	protected Integer TopicId;
	protected User currentUser;

	public Integer getTopicId()
	{
		return TopicId;
	}
	public void setTopicId(Integer topicId)
	{
		TopicId = topicId;
	}

	public ForumTopic getTopic() throws Exception
	{
		return (ForumTopic)dao.getForumItemForId(getTopicId().intValue());
	}
	
	public String getRepliedOn() throws Exception
	{
		SimpleDateFormat fmter = new SimpleDateFormat("dd-MMM-yyyy");
		SimpleDateFormat fmt = new SimpleDateFormat("HH:mm a");
		SimpleDateFormat fmt2 = new SimpleDateFormat("dd MMM, yy");
		Date today = new Date();
		String sToday = fmter.format(today);

		try {

			Date date = getDatePosted();
			if (fmter.format(date).equals(sToday)) {
				return "Today " + fmt.format(date);
			}
			return fmt2.format(date);

		} catch (Exception e) {
			return getDatePosted().toString();
		}
	}
	
	public String getLikeString(){
		try {
			if (currentUser == null) {
				return "Like";
			} else {
				try {
					ForumItemLike forumItemLike = forumItemLikeFinder.getLikeForForumItemAndUser(this.getId(), currentUser.getId());
					return "Unlike";
				}
				catch (ElementNotFoundException e) {
					return "Like";
				}
			}
		} catch (Exception e) {
			return "Like";
		}
	}
	
	public int getLikeCount(){
		try {
			int count = forumItemLikeFinder.getLikeCountForForumItem(this.getId());
			return count;
		} catch (Exception e) {
			return 0;
		}
	}
	
	public User getCurrentUser() {
		return currentUser;
	}
	
	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
}