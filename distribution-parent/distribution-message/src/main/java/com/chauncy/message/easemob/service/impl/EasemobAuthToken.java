package com.chauncy.message.easemob.service.impl;


import com.chauncy.message.easemob.comm.TokenUtil;
import com.chauncy.message.easemob.service.AuthTokenAPI;

public class EasemobAuthToken implements AuthTokenAPI {

	@Override
	public Object getAuthToken(){
		return TokenUtil.getAccessToken();
	}
}
