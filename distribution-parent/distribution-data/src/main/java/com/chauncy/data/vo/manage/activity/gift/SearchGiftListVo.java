package com.chauncy.data.vo.manage.activity.gift;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.chauncy.data.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-07-22 17:25
 *
 * 分页获取的礼包信息
 */
@Data
@ApiModel(description = "分页获取的礼包信息")
@Accessors(chain = true)
public class SearchGiftListVo {

    @ApiModelProperty(value = "id")
    @JSONField(ordinal = 0)
    private Long id;

    @ApiModelProperty(value = "创建时间")
    @JSONField(ordinal = 1)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "是否启用 1-启用，0-禁用")
    @JSONField(ordinal = 2)
    private Boolean enable;

    @ApiModelProperty(value = "礼包名称")
    @JSONField(ordinal = 3)
    private String name;

    @ApiModelProperty(value = "经验值")
    @JSONField(ordinal = 4)
    private BigDecimal experience;

    @ApiModelProperty(value = "购物券")
    @JSONField(ordinal = 5)
    private BigDecimal vouchers;

    @ApiModelProperty(value = "积分")
    @JSONField(ordinal = 6)
    private BigDecimal integrals;

    @ApiModelProperty(value = "购买金额")
    @JSONField(ordinal = 7)
    private BigDecimal purchasePrice;

    @ApiModelProperty(value = "图片")
    @JSONField(ordinal = 8)
    private String picture;

    @ApiModelProperty(value = "图文详情")
    @JSONField(ordinal = 9)
    private String detailHtml;

    @ApiModelProperty("已经关联的优惠券集合")
    @JSONField(ordinal = 11)
    private List<BaseVo> associationedList;

    @ApiModelProperty("已经关联的优惠券的数量")
    @JSONField(ordinal = 10)
    private Integer num = 0;

    @ApiModelProperty("未关联的优惠券集合")
    @JSONField(ordinal = 12)
    private List<BaseVo> associationList;

}
