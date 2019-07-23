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
 * @create 2019-07-22 21:12
 *
 * 查询新人礼包领取条件
 */
@Data
@ApiModel("查询新人礼包领取条件")
@Accessors(chain = true)
public class SearchReceiveGiftRecordVo {

    @ApiModelProperty("领取记录id")
    private Long id;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("用户名称")
    private String userName;

    @ApiModelProperty("领取时间")
    private LocalDateTime receiveTime;

    @ApiModelProperty(value = "礼包ID",hidden = true)
    private Long giftId;

    @ApiModelProperty("礼包名称")
    private String giftName;

    @ApiModelProperty("经验值")
    private Integer experience;

    @ApiModelProperty("购物券")
    private Integer vouchers;

    @ApiModelProperty("积分")
    private Integer integrals;

    @ApiModelProperty("优惠券数量")
    private Integer num = 0;

    @ApiModelProperty("优惠券信息")
    private List<BaseVo> couponList;

}
