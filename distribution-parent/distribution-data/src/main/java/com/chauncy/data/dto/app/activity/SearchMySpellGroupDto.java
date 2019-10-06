package com.chauncy.data.dto.app.activity;

import com.chauncy.common.enums.app.activity.MySpellGroupTypeEnum;
import com.chauncy.data.dto.base.BasePageDto;
import com.chauncy.data.valid.annotation.EnumConstraint;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/10/6 23:00
 */
@Data
@ApiModel(value = "SearchMySpellGroupDto对象", description = "查询我的拼团信息")
public class SearchMySpellGroupDto  extends BasePageDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "我的拼团类型    \n1：我发起的    \n2：我参与的    \n")
    @NotNull(message = "goodsId参数不能为空")
    @EnumConstraint(target = MySpellGroupTypeEnum.class)
    private Integer mySpellGroupType;

    @ApiModelProperty(value = "APP用户id")
    @JsonIgnore
    private Long userId;

}
