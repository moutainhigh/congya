package com.chauncy.data.dto.app.advice.category.select;

import com.alibaba.fastjson.annotation.JSONField;
import com.chauncy.data.vo.app.advice.goods.SearchGoodsBaseListVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author cheng
 * @create 2019-10-08 22:10
 *
 * 百货中部广告信息
 */
@Data
@ApiModel(description = "百货中部广告信息")
@Accessors(chain = true)
public class BaiHuoMiddleAdviceVo {

    @ApiModelProperty(value = "图片选项卡广告信息")
    @JSONField(ordinal = 0)
    private TabAdviceVo tabAdviceVo;

    @ApiModelProperty(value = "百货中部广告选项卡需要显示的前6个商品")
    @JSONField(ordinal = 1)
    private List<SearchGoodsBaseListVo> goodsBaseListVos;
}
