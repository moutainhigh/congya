package com.chauncy.common.third.easemob.service.impl;


import com.chauncy.common.third.easemob.comm.TokenUtil;
import com.chauncy.common.third.easemob.service.AuthTokenAPI;

public class EasemobAuthToken implements AuthTokenAPI {

	@Override
	public Object getAuthToken(){
		return TokenUtil.getAccessToken();
	}
}
