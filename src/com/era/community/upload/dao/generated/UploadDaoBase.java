package com.era.community.upload.dao.generated; 

import com.era.community.upload.dao.Upload;

public interface UploadDaoBase extends UploadFinderBase
{
	public void store(Upload o) throws Exception;
	public void deleteUploadForId(int id) throws Exception;
	public void delete(Upload o) throws Exception;
	public support.community.database.BlobData readData(Upload o) throws Exception;
	public void storeData(Upload o, java.io.InputStream data, int dataLength, String contentType) throws Exception;
	public void storeData(Upload o, org.springframework.web.multipart.MultipartFile file) throws Exception;
	public void markDataRelocated(Upload o) throws Exception;
	public String getDataContentType(Upload o) throws Exception;
	public String getDataSearchText(Upload o) throws Exception;
	public void setDataSearchText(Upload o, String s) throws Exception;
}