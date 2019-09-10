package com.chauncy.data.vo.app.advice.gift;

import com.chauncy.data.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-09-09 22:43
 *
 * 分页查询购买经验包信息
 */
@Data
@ApiModel(description = "分页查询购买经验包信息")
@Accessors(chain = true)
public class SearchTopUpGiftVo {

    @ApiModelProperty("礼包id")
    private Long giftId;

    @ApiModelProperty("礼包名称")
    private String giftName;

    @ApiModelProperty("礼包图片")
    private String picture;

    @ApiModelProperty("购买金额")
    private BigDecimal purchasePrice;

    @ApiModelProperty("购物券")
    private BigDecimal vouchers;

    @ApiModelProperty("经验值")
    private BigDecimal experience;

    @ApiModelProperty("积分")
    private BigDecimal integrals;

    @ApiModelProperty("图文详情")
    private String detailHtml;

    @ApiModelProperty("礼包关联的优惠券")
    private List<BaseVo> couponList;

}
