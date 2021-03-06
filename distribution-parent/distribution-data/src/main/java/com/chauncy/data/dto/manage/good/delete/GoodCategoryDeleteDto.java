package com.chauncy.data.dto.manage.good.delete;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @Author zhangrt
 * @Date 2019/6/12 12:39
 **/
@Data
@ApiModel( description = "分类删除dto")
public class GoodCategoryDeleteDto {

    @NotEmpty
    @NeedExistConstraint(tableName = "pm_goods_category",isNeedExists = false,field = "parent_id"
    ,message = "删除失败：存在非末级的分类！")
    @NeedExistConstraint(tableName = "pm_goods_rel_attribute_category",isNeedExists = false,
    field = "goods_category_id",message = "删除失败：存在已经与属性相关联的分类！")
    @NeedExistConstraint(tableName = "pm_goods",isNeedExists = false,
    field = "goods_category_id",message = "删除失败：存在已经与商品关联的分类！")
    @ApiModelProperty(value = "分类删除id集合")
    List<Long> ids;
}
