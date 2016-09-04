package com.era.community.doclib.dao.generated;

import com.era.community.doclib.dao.DocGroup;

public interface DocGroupFinderBase
{
	public DocGroup getDocGroupForId(int id) throws Exception;
	public DocGroup newDocGroup() throws Exception;
}