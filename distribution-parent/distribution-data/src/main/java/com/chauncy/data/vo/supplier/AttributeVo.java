package com.chauncy.data.vo.supplier;

import com.chauncy.data.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author cheng
 * @create 2019-06-27 17:38
 *
 * 获取分类下的商品属性信息 typeList:商品类型；brandList:品牌；labelList:标签；platformServiceList:平台服务说明;
 * merchantServiceList:商家服务说明；paramList:商品参数；platformShipList:平台运费模版;merchantShipList:店铺运费模版
 */
@Data
@ApiModel("获取分类下的商品属性信息")
@Accessors(chain = true)
public class AttributeVo {

    @ApiModelProperty("商品类型")
    private List<String> typeList;

    @ApiModelProperty("品牌")
    private List<BaseVo> brandList;

    @ApiModelProperty("标签")
    private List<BaseVo> labelList;

    @ApiModelProperty("平台服务说明")
    private List<BaseVo> platformServiceList;

    @ApiModelProperty("商家服务说明")
    private List<BaseVo> merchantServiceList;

    @ApiModelProperty("商品参数")
    private List<BaseVo> paramList;

    @ApiModelProperty("平台运费模版")
    private List<BaseVo> platformShipList;

    @ApiModelProperty("店铺运费模版")
    private List<BaseVo> merchantShipList;

    @ApiModelProperty("分类名称")
    private  String categoryName;
}
