package com.era.community.common.dao;


import com.era.community.base.CecAbstractEntity;
import com.era.community.pers.dao.generated.UserEntity;

/**
 * @entity name="ISGT" 
 */
public class Insight extends CecAbstractEntity
{    
    protected String screen;
    protected int timeSpent;
    protected int sessionId;
    protected int previousPageId;

    protected InsightDao dao;

    public void update() throws Exception
    {
        dao.store(this); 
    }

    public final void setDao(InsightDao dao)
    {
        this.dao = dao;
    }   

    public boolean isWriteAllowed(UserEntity user) throws Exception
    {
    	return true;
    }

    public boolean isReadAllowed(UserEntity user) throws Exception
    {
    	return true;
    }

	public String getScreen() {
		return screen;
	}

	public void setScreen(String screen) {
		this.screen = screen;
	}

	public int getTimeSpent() {
		return timeSpent;
	}

	public void setTimeSpent(int timeSpent) {
		this.timeSpent = timeSpent;
	}

	public int getSessionId() {
		return sessionId;
	}

	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}

	public int getPreviousPageId() {
		return previousPageId;
	}

	public void setPreviousPageId(int previousPageId) {
		this.previousPageId = previousPageId;
	}
}