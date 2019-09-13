package com.chauncy.data.vo.app.order.cart;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author cheng
 * @create 2019-07-05 13:59
 *
 * 购物车显示
 */
@ApiModel("查看购物车返回的信息")
@Data
public class CartVo {

    @ApiModelProperty("店铺ID")
    @JSONField(ordinal = 0)
    private Long storeId;

    @ApiModelProperty("店铺名称")
    @JSONField(ordinal = 1)
    private String storeName;

    @ApiModelProperty("店铺logo")
    @JSONField(ordinal = 2)
    private String logoImage;

    @ApiModelProperty("店铺下的商品")
    @JSONField(ordinal = 3)
    private List<StoreGoodsVo> storeGoodsVoList;

}
