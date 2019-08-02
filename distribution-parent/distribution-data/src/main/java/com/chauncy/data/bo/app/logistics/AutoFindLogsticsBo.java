package com.chauncy.data.bo.app.logistics;

import lombok.Data;

/**
 * @Author cheng
 * @create 2019-08-02 22:20
 *
 * 接收通过快递100智能判断接口获取的数据
 */
@Data
public class AutoFindLogsticsBo {

    private String comCode;

    private String id;

    private String noCount;

    private String noPre;

    private String startTime;
}
