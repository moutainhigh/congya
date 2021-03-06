package com.chauncy.data.haiguan.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.LongCodec;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author zhangrt
 * @Date 2019/10/15 22:35
 * @Description  返回海关的参数
 *
 * @Update
 *
 * @Param
 * @return
 **/



@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ReturnCustomsVo {

    private String code;

    private String message;

    @JSONField(serializeUsing = LongCodec.class)
    private Long serviceTime;
}
