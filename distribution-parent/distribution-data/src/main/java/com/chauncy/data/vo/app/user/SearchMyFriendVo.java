package com.chauncy.data.vo.app.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @Author cheng
 * @create 2019-09-18 09:38
 *
 * 分页查询我的粉丝条件
 */
@Data
@ApiModel(description = "分页查询我的粉丝条件")
@Accessors(chain = true)
public class SearchMyFriendVo {

    @ApiModelProperty("用户ID、用户IM账号")
    private Long userId;

    @ApiModelProperty("用户头像")
    private String photo;

    @ApiModelProperty("用户名称")
    private String userName;

    @ApiModelProperty("综合分")
    private BigDecimal comprehensive;

    @ApiModelProperty("会员等级图标")
    private String actorImage;

}
