package com.era.community.pers.dao;

public class LoginSNDaoImpl extends com.era.community.pers.dao.generated.LoginSNDaoBaseImpl implements LoginSNDao
{
	public LoginSN getLoginSNForEmailAddress(String address) throws Exception
	{
		return (LoginSN) getEntityWhere("ucase(emailAddress) = ? ", address.toUpperCase());
	}
	
	public LoginSN getLoginSNForLoginIdAndType(String loginId, int snType) throws Exception
	{
		return (LoginSN) getEntityWhere("LoginId = ? and SnType = ? ", loginId, snType);
	}
	
	public LoginSN getLoginSNForLogoutAction(int userId) throws Exception
	{
		return (LoginSN) getEntityWhere("UserId = ? and Login = 'Y' ", userId);
	}
	
	public LoginSN getLoginSNForType(int snType) throws Exception
	{
		String query = "select * from LoginSN where snType = ? order by Created asc LIMIT 1";
		return getBean(query, LoginSN.class, snType);
	}
	
	public int getLoginSNForTypeCount(int snType) throws Exception
	{
		String query = "select count(*) from LoginSN where snType = ? ";
		return getSimpleJdbcTemplate().queryForInt(query, snType);
	}
}