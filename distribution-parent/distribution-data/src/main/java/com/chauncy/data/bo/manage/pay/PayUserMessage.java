package com.chauncy.data.bo.manage.pay;

import com.chauncy.common.util.LoggerUtil;
import lombok.Data;
import lombok.ToString;

/**
 * 查询用户所有上级和直接子级
 * @Author zhangrt
 * @Date 2019/7/29 16:37
 **/

@Data
@ToString
public class PayUserMessage implements Cloneable {

    private Long userId;

    private Integer level;

    @Override
    public Object clone(){
        PayUserMessage payUserMessage = null;
        try{
            payUserMessage = (PayUserMessage)super.clone();
        }catch(CloneNotSupportedException e) {
            LoggerUtil.error(e);
        }
        return payUserMessage;
    }
}
