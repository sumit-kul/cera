package com.era.community.pers.dao; 

import java.util.List;

public interface SocialLinkFinder extends com.era.community.pers.dao.generated.SocialLinkFinderBase
{      
	public List<SocialLink> getSocialLinksByUserId(int userId) throws Exception;
	public SocialLink getSocialLinkByNameAndUserId(String name, int userId) throws Exception;
}