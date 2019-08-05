package com.chauncy.message.easemob.service.impl;

import com.chauncy.message.easemob.comm.EasemobAPI;
import com.chauncy.message.easemob.comm.OrgInfo;
import com.chauncy.message.easemob.comm.ResponseHandler;
import com.chauncy.message.easemob.comm.TokenUtil;
import com.chauncy.message.easemob.service.SendMessageAPI;
import io.swagger.client.ApiException;
import io.swagger.client.api.MessagesApi;
import io.swagger.client.model.Msg;

public class EasemobSendMessage implements SendMessageAPI {
    private ResponseHandler responseHandler = new ResponseHandler();
    private MessagesApi api = new MessagesApi();
    @Override
    public Object sendMessage(final Object payload) {
        return responseHandler.handle(new EasemobAPI() {
            @Override
            public Object invokeEasemobAPI() throws ApiException {
                return api.orgNameAppNameMessagesPost(OrgInfo.ORG_NAME,OrgInfo.APP_NAME, TokenUtil.getAccessToken(), (Msg) payload);
            }
        });
    }
}
