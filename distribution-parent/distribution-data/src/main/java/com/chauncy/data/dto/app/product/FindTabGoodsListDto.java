package com.chauncy.data.dto.app.product;

import com.chauncy.common.enums.app.activity.group.GroupTypeEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/9/26 13:36
 */
@Data
@ApiModel(value = "FindTabGoodsListDto对象", description = "根据选项卡查找活动商品")
public class FindTabGoodsListDto  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "分组类型   \n1-满减   \n2-积分   \n")
    @EnumConstraint(target = GroupTypeEnum.class)
    @NotNull(message = "分组类型不能为空")
    private Integer groupType;

    @ApiModelProperty(value = "选项卡id   \n")
    private Long tabId;

}

