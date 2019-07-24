package com.chauncy.data.vo.manage.activity;

import com.chauncy.data.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cheng
 * @create 2019-07-23 21:05
 *
 * 查找活动对应的商品分类信息 ID、name、所属分类
 */
@Data
@ApiModel(description = "查找活动对应的商品分类信息 ID、name、所属分类")
@Accessors(chain = true)
public class SearchGoodsCategoryVo extends BaseVo {

    @ApiModelProperty("所属分类")
    private String categoryName;
}
