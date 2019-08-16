package com.chauncy.data.vo.manage.message.advice;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cheng
 * @create 2019-08-16 11:46
 *
 * 条件分页查询广告信息
 */
@Data
@ApiModel(description = "条件分页查询广告信息")
@Accessors(chain = true)
public class SearchAdvicesVo {

    @ApiModelProperty("广告ID")
    @JSONField(ordinal = 0)
    private Long adviceId;

    @ApiModelProperty("广告位置(首页有店+店铺分类详情)")
    @JSONField(ordinal = 1)
    private String location;

    @ApiModelProperty("广告名称")
    @JSONField(ordinal = 2)
    private String name;

    @ApiModelProperty("发广告对应的详情")
    @JSONField(ordinal = 3)
    private Object detail;

}

