package com.chauncy.data.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author huangwancheng
 * @create 2019-05-24 19:07
 */
@Data
public class SearchVo implements Serializable {

    private String startDate;

    private String endDate;
}
