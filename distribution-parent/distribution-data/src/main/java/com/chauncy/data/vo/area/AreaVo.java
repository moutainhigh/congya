package com.chauncy.data.vo.area;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author cheng
 * @create 2019-06-28 17:00
 *
 * 省市区街道Vo
 */
@ApiModel(description = "省市区街道Vo")
@Data
public class AreaVo {

    @ApiModelProperty("地址ID")
    @JSONField(ordinal = 0)
    private Long id;

    @ApiModelProperty("行政区划id")
    @JSONField(ordinal = 1)
    private String cityCode;

    @ApiModelProperty("父级id")
    @JSONField(ordinal = 2)
    private String parentCode;

    @ApiModelProperty("行政区划全称")
    @JSONField(ordinal = 3)
    private String name;

    @ApiModelProperty("省市区全称聚合")
    @JSONField(ordinal = 4)
    private String mergerName;

    @ApiModelProperty("邮编")
    @JSONField(ordinal = 5)
    private String zipCode;

    @ApiModelProperty("行政区划级别country:国家,province:省份,city:市,district:区县,street:街道")
    @JSONField(ordinal = 6)
    private String level;

    @ApiModelProperty("级别 0.国家，1.省(直辖市) 2.市 3.区(县),4.街道")
    @JSONField(ordinal = 7)
    private Integer levelType;

    @ApiModelProperty("邮编")
    @JSONField(ordinal = 8)
    private List<AreaVo> children;
}
