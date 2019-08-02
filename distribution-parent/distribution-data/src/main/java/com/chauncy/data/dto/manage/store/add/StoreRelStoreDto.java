package com.chauncy.data.dto.manage.store.add;

import com.chauncy.data.valid.group.IUpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/7/8 2:05
 */
@Data
@ApiModel(value = "StoreRelStoreDto对象", description = "店铺绑定店铺基本信息")
public class StoreRelStoreDto  implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "店铺id(sm_store)")
    @NotNull(groups = IUpdateGroup.class,message = "编辑时店铺id不能为空！")
    private Long storeId;

    @ApiModelProperty(value = "绑定的店铺id")
    @NotNull(message = "绑定的店铺id不能为空！")
    private Long parentId;

    @ApiModelProperty(value = "默认1  1->产品代理  2->团队合作")
    @NotNull(message = "绑定的关系类型不能为空！")
    private Integer type;
}
