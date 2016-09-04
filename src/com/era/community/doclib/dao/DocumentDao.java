package com.era.community.doclib.dao; 

import java.util.Date;
import java.util.List;

interface DocumentDao extends com.era.community.doclib.dao.generated.DocumentDaoBase, DocumentFinder
{
	public Date getLatestPostDate(Document doc) throws Exception;
	public int getStarRating(Document doc) throws Exception;
	public int getNumberOfRaters(Document doc) throws Exception;
	public List getItemsSince(Document doc, Date date) throws Exception;
}