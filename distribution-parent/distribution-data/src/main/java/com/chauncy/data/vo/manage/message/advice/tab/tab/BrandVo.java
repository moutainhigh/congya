package com.chauncy.data.vo.manage.message.advice.tab.tab;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author cheng
 * @create 2019-06-17 15:27
 *
 * 品牌基本信息Vo,只包含ID和name
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "品牌基本信息Vo,只包含ID和name")
public class BrandVo {

    @ApiModelProperty("品牌ID")
    @JSONField(ordinal = 7)
    private Long brandId;

    @ApiModelProperty("品牌名称")
    @JSONField(ordinal = 8)
    private String brandName;

    @ApiModelProperty("品牌和选项卡关联ID")
    @JSONField(ordinal = 9)
    private String relTabBrandId;

    @ApiModelProperty("品牌关联的轮播图广告")
    @JSONField(ordinal = 10)
    private List<BrandShufflingVo> brandShufflingVos;
}
