package com.chauncy.data.vo.supplier;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author cheng
 * @create 2019-06-20 17:50
 *
 * 商品拥有的会员等级信息
 */
@Data
@ApiModel(description = "商品拥有的会员等级信息")
public class MemberLevelInfos {

    @ApiModelProperty("会员等级ID")
    private Long  memberLevelId;

    @ApiModelProperty("会员等级名称")
    private String levelName;
/*
    @ApiModelProperty("显示给前端是否包含")
    private Boolean isInclude;*/
}
