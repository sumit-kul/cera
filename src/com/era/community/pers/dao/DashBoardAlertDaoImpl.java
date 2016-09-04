package com.era.community.pers.dao; 

public class DashBoardAlertDaoImpl extends com.era.community.pers.dao.generated.DashBoardAlertDaoBaseImpl implements DashBoardAlertDao
{
	@Override
	public DashBoardAlert getDashBoardAlertForUserId(int id) throws Exception {
		DashBoardAlert alert = (DashBoardAlert)getEntityWhere(" UserId = ? ", id);
		return alert;
	}
}