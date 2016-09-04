package com.era.community.pers.dao; 

import java.util.List;

public class SocialLinkDaoImpl extends com.era.community.pers.dao.generated.SocialLinkDaoBaseImpl implements SocialLinkDao
{
	public List<SocialLink> getSocialLinksByUserId(int userId) throws Exception {
		String query = "select * from SocialLink where UserId = ?";
        return getBeanList(query, SocialLink.class, userId);
	}
	
	public SocialLink getSocialLinkByNameAndUserId(String name, int userId) throws Exception {
		return (SocialLink) getEntityWhere(" Name = ? and UserId = ? ", name, userId);
	}
}