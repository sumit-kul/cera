package com.era.community.upload.dao.generated; 

import com.era.community.upload.dao.Upload;

public interface UploadFinderBase
{
	public Upload getUploadForId(int id) throws Exception;
	public Upload newUpload() throws Exception;
}