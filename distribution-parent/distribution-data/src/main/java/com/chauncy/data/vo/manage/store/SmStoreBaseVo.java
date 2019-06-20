package com.chauncy.data.vo.manage.store;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.chauncy.common.enums.store.StoreTypeEnum;
import com.chauncy.common.util.serializer.LongJsonDeserializer;
import com.chauncy.common.util.serializer.LongJsonSerializer;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 店铺列表分页查询结果
 * @Author: xiaoye
 * @Date: 2019/6/11 16:50
 */
@Data
public class SmStoreBaseVo implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "id")
    @JsonSerialize(using = LongJsonSerializer.class)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    private Long id;

    @ApiModelProperty(value = "店铺名称")
    private String name;

    @ApiModelProperty(value = "商家类型（1.推广店铺，2.商品店铺）")
    private Integer type;

    @ApiModelProperty(value = "商家类型")
    private String typeName;

    @ApiModelProperty(value = "所属店铺Id")
    private Long parentId;

    @ApiModelProperty(value = "所属店铺名称")
    private String parentStoreName;

    @ApiModelProperty(value = "公司地址")
    private String companyAddr;

    @ApiModelProperty(value = "本店营业额")
    private BigDecimal storeTurnover;

    @ApiModelProperty(value = "本店会员数量")
    private Integer storeMemberNum;

    @ApiModelProperty(value = "旗下店铺数量")
    private Integer storeSubNum;

    @ApiModelProperty(value = "本店商品总数")
    private Integer goodsNum;

    @ApiModelProperty(value = "主理人联系电话")
    private String ownerMobile;

    @ApiModelProperty(value = "排序数值")
    private Integer sort;

    @ApiModelProperty(value = "是否启用 1-是 0-否 默认为1")
    private Boolean enabled;

}
