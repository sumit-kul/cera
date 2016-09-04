package com.era.community.faqs.dao.generated; 

import com.era.community.faqs.dao.Faq;

public interface FaqDaoBase extends FaqFinderBase
{
	public void store(Faq o) throws Exception;
	public void deleteFaqForId(int id) throws Exception;
	public void delete(Faq o) throws Exception;
	public support.community.database.BlobData readFile(Faq o) throws Exception;
	public void storeFile(Faq o, java.io.InputStream data, int dataLength, String contentType) throws Exception;
	public void storeFile(Faq o, org.springframework.web.multipart.MultipartFile file) throws Exception;
	public void markFileRelocated(Faq o) throws Exception;
	public String getFileContentType(Faq o) throws Exception;
	public String getFileSearchText(Faq o) throws Exception;
	public void setFileSearchText(Faq o, String s) throws Exception;
}

