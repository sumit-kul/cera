package com.era.community.doclib.dao; 

import java.util.Date;
import java.util.List;

import support.community.database.QueryScroller;

import com.era.community.pers.dao.User;

interface DocumentLibraryDao extends com.era.community.doclib.dao.generated.DocumentLibraryDaoBase, DocumentLibraryFinder
{
	public QueryScroller listDocumentsByDate(DocumentLibrary lib, Class beanClass,  int themeId, boolean ignoreThemes) throws Exception;
	public QueryScroller listDocumentsByTitle(DocumentLibrary lib, Class beanClass, int themeId, boolean ignoreThemes) throws Exception;
	public QueryScroller listDocumentsByAuthor(DocumentLibrary lib,Class beanClass,  int themeId, boolean ignoreThemes) throws Exception;
	public QueryScroller listDocumentsForUser(DocumentLibrary lib, Class beanClass, User user, int themeId, boolean ignoreThemes)  throws Exception;

	public QueryScroller listDocumentsByRating(DocumentLibrary lib, Class beanClass, int themeId, boolean ignoreThemes) throws Exception;


	public Date getLatestPostDate(DocumentLibrary lib) throws Exception;

	// This method is used for the monitor action
	public List getItemsSince(DocumentLibrary lib, Date date) throws Exception;

	public int getUnThemedDocumentCount(DocumentLibrary documentLibrary) throws Exception;
}

