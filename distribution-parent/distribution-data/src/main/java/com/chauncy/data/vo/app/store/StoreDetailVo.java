package com.chauncy.data.vo.app.store;

import com.alibaba.fastjson.annotation.JSONField;
import com.chauncy.common.util.serializer.LongJsonSerializer;
import com.chauncy.data.vo.manage.store.label.SmStoreLabelVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @author yeJH
 * @since 2019/7/17 23:46
 */
@Data
@ApiModel(value = "StoreDetailVo", description =  "店铺详情")
public class StoreDetailVo  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "店铺id")
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long storeId;

    @ApiModelProperty(value = "店铺名称")
    private String storeName;

    @ApiModelProperty(value = "店铺标签")
    @JSONField(serialize=false)
    private String storeLabels;

    @ApiModelProperty(value = "店铺标签")
    private List<String> storeLabelList;

    @ApiModelProperty(value = "店铺logo")
    private String logoImage;

    @ApiModelProperty(value = "收藏量/粉丝数")
    private Integer collectionNum;

    @ApiModelProperty(value = "点赞数")
    private Integer likedSum;

    @ApiModelProperty(value = "是否关注")
    private Boolean isFocus;

    @ApiModelProperty(value = "店铺描述")
    private String storeDescribe;

   /* @ApiModelProperty(value = "店铺综合体验评分")
    private BigDecimal totalScore;*/

    @ApiModelProperty(value = "宝贝描述评分")
    private BigDecimal babyDescribeScore;

    @ApiModelProperty(value = "服务态度评分")
    private BigDecimal serviceAttitudeScore;

    @ApiModelProperty(value = "物流服务评分")
    private BigDecimal logisticsServiceScore;

    @ApiModelProperty(value = "主理人姓名")
    private String ownerName;

    @ApiModelProperty(value = "公司地址")
    private String companyAddr;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private LocalDate createDate;

    @ApiModelProperty(value = "经营资质，营业执照 多张图片需转为list",hidden = true)
    @JSONField(serialize = false)
    private String businessLicense;

    @ApiModelProperty(value = "工商执照多张图片")
    private List<String> businessLicenseList;
}
