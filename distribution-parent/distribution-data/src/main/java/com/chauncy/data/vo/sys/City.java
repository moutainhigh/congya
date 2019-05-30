package com.chauncy.data.vo.sys;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author huangwancheng
 * @create 2019-05-24 23:09
 */
@Data
public class City implements Serializable {

    String country;

    String province;

    String city;
}
