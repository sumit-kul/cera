package com.era.community.upload.dao.generated; 

import com.era.community.upload.dao.Image;

public interface ImageFinderBase
{
    public Image getImageForId(int id) throws Exception;
    public Image newImage() throws Exception;
}