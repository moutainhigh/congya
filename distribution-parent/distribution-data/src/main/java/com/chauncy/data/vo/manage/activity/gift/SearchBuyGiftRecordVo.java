package com.chauncy.data.vo.manage.activity.gift;

import com.chauncy.data.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-07-22 22:07
 *
 * 查询购买礼包条件
 */
@Data
@ApiModel(description = "查询购买礼包条件")
@Accessors(chain = true)
public class SearchBuyGiftRecordVo {

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("用户名称")
    private String userName;

    @ApiModelProperty("领取时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "礼包ID",hidden = true)
    private Long giftId;

    @ApiModelProperty("礼包名称")
    private String giftName;

    @ApiModelProperty("购买记录id/订单ID")
    private Long orderId;

    @ApiModelProperty("经验值")
    private Integer experience;

    @ApiModelProperty("购物券")
    private Integer vouchers;

    @ApiModelProperty("积分")
    private Integer integrals;

    @ApiModelProperty("礼包图片")
    private String picture;

    @ApiModelProperty("图文详情")
    private String detailHtml;

    @ApiModelProperty("优惠券数量")
    private Integer num = 0;

    @ApiModelProperty("优惠券信息")
    private List<BaseVo> couponList;
}
