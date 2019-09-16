package com.chauncy.common.third.easemob.service;

import io.swagger.client.ApiException;

public interface AuthTokenAPI{
	/**
	 * Request an Authentication Token
	 * @return String
	 * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
	 */
	Object getAuthToken();
}
