package com.era.community.pers.dao; 


public interface LoginSNFinder extends com.era.community.pers.dao.generated.LoginSNFinderBase
{
    public LoginSN getLoginSNForEmailAddress(String address) throws Exception;
    public LoginSN getLoginSNForLoginIdAndType(String loginId, int snType) throws Exception;
    public LoginSN getLoginSNForLogoutAction(int userId) throws Exception;
    public LoginSN getLoginSNForType(int snType) throws Exception;
    public int getLoginSNForTypeCount(int snType) throws Exception;
}