package com.chauncy.data.vo.manage.activity.coupon;

import com.chauncy.data.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author cheng
 * @create 2019-07-19 11:08
 *
 * 为新建的优惠券添加对应的满足条件的商品的返回结果
 */
@Data
@ApiModel(description = "为新建的优惠券添加对应的满足条件的商品的返回结果")
@Accessors(chain = true)
public class SaveCouponResultVo {

    @ApiModelProperty("成功条数")
    private Integer successCount;

    @ApiModelProperty("失败条数")
    private Integer failCount;

    @ApiModelProperty("失败记录")
    private List<BaseVo> failList;
}
