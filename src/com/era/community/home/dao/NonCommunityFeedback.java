package com.era.community.home.dao;

import com.era.community.base.CecAbstractEntity;
import com.era.community.pers.dao.generated.UserEntity;

/**
 * @entity name="NCFD" 
 */
public class NonCommunityFeedback extends CecAbstractEntity
{
	protected String Subject;
	protected String Name;
	protected String EmailAddress;
	protected String Body = "";
	protected int Type;
	protected int SubmitterId;

	protected NonCommunityFeedbackDao dao;
	protected NonCommunityFeedbackFinder feedbackFinder;

	public void update() throws Exception
	{
		dao.store(this); 
	}

	public final void setDao(NonCommunityFeedbackDao dao)
	{
		this.dao = dao;
	}  

	public boolean isReadAllowed(UserEntity user) throws Exception
	{
		return true;
	}

	public boolean isWriteAllowed(UserEntity user) throws Exception
	{
		return true;
	}

	public final String getBody()
	{
		return Body;
	}
	public final void setBody(String body)
	{
		Body = body;
	}

	public final String getName()
	{
		return Name;
	}

	public final void setName(String name)
	{
		Name = name;
	}

	public final String getEmailAddress()
	{
		return EmailAddress;
	}

	public final void setEmailAddress(String emailAddress)
	{
		EmailAddress = emailAddress;
	}

	public final void setFeedbackFinder(NonCommunityFeedbackFinder feedbackFinder)
	{
		this.feedbackFinder = feedbackFinder;
	}

	public String getSubject() {
		return Subject;
	}

	public void setSubject(String subject) {
		Subject = subject;
	}

	public int getType() {
		return Type;
	}

	public void setType(int type) {
		Type = type;
	}

	public int getSubmitterId() {
		return SubmitterId;
	}

	public void setSubmitterId(int submitterId) {
		SubmitterId = submitterId;
	}
}