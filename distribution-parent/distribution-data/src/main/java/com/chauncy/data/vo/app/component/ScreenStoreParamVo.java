package com.chauncy.data.vo.app.component;

import com.chauncy.data.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author yeJH
 * @since 2019/9/19 17:37
 */
@Data
@ApiModel(value = "ScreenStoreParamVo", description =  "店铺筛选参数")
public class ScreenStoreParamVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id是为了满足一对多的查询将品牌，标签，类目分组")
    private Integer id;

    @ApiModelProperty(value = "店铺标签")
    private List<BaseVo> labelList;

    @ApiModelProperty(value = "店铺分类")
    private List<BaseVo> categoryList;

    @ApiModelProperty(value = "优惠活动")
    private List<BaseVo> activityList ;

}