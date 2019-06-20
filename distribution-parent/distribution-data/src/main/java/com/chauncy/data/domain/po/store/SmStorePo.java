package com.chauncy.data.domain.po.store;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
    import java.time.LocalDateTime;

    import com.chauncy.common.util.serializer.LongJsonDeserializer;
    import com.chauncy.common.util.serializer.LongJsonSerializer;
    import com.fasterxml.jackson.annotation.JsonFormat;
    import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
    import com.fasterxml.jackson.databind.annotation.JsonSerialize;
    import io.swagger.annotations.ApiModel;
    import io.swagger.annotations.ApiModelProperty;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
    import org.springframework.format.annotation.DateTimeFormat;

/**
*
* @author huangwancheng
* @since 2019-06-03
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sm_store")
@ApiModel(value="SmStorePo对象", description="店铺信息表")
public class SmStorePo implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "id")
    @TableId(value = "id",type = IdType.ID_WORKER)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long id;

    @ApiModelProperty(value = "店铺名称")
    private String name;

    @ApiModelProperty(value = "店铺描述")
    private String storeDescribe;

    @ApiModelProperty(value = "店铺账号")
    private String userName;

    @ApiModelProperty(value = "是否展示在前端 0 不展示 1 展示")
    private Boolean showStatus;

    @ApiModelProperty(value = "店铺类型标签id（sm_store_label主键）")
    private Long storeLabelId;

    @ApiModelProperty(value = "店铺分类id（sm_store_category主键）")
    private Long storeCategoryId;

    @ApiModelProperty(value = "商家类型（推广店铺，商品店铺）")
    private Integer type;

    @ApiModelProperty(value = "所属店铺Id")
    private Long parentId;

    @ApiModelProperty(value = "排序数值")
    private Integer sort;

    @ApiModelProperty(value = "主理人姓名")
    private String ownerName;

    @ApiModelProperty(value = "主理人联系电话")
    private String ownerMobile;

    @ApiModelProperty(value = "店铺logo")
    private String logoImage;

    @ApiModelProperty(value = "店铺缩略图，展示用")
    private String storeImage;

    @ApiModelProperty(value = "店铺背景图")
    private String backgroundImage;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "公司地址")
    private String companyAddr;

    @ApiModelProperty(value = "联系电话")
    private String companyMobile;

    @ApiModelProperty(value = "邮箱")
    private String companyEmail;

    @ApiModelProperty(value = "法人")
    private String legalPerson;

    @ApiModelProperty(value = "税号")
    private String taxNumber;

    @ApiModelProperty(value = "持卡人姓名")
    private String cardholder;

    @ApiModelProperty(value = "开户行")
    private String openingBank;

    @ApiModelProperty(value = "收款账户")
    private String account;

    @ApiModelProperty(value = "银行预留电话")
    private String bankReserveMobile;

    @ApiModelProperty(value = "货款账单结算周期")
    private Integer paymentBillSettlementCycle;

    @ApiModelProperty(value = "收入账单结算周期")
    @TableField("income_bill_Settlement_cycle")
    private Integer incomeBillSettlementCycle;

    @ApiModelProperty(value = "收入配置比例")
    private BigDecimal incomeRate;

    @ApiModelProperty(value = "经营资质，营业执照")
    private String businessLicense;

    @ApiModelProperty(value = "是否启用 1-是 0-否 默认为1")
    private Boolean enabled;

    @ApiModelProperty(value = "店铺综合体验评分")
    private Integer totalScore;

    @ApiModelProperty(value = "宝贝描述评分")
    private Integer babyDescribeScore;

    @ApiModelProperty(value = "服务态度评分")
    private Integer serviceAttitudeScore;

    @ApiModelProperty(value = "物流服务评分")
    private Integer logisticsServiceScore;

    @ApiModelProperty(value = "本店营业额")
    private BigDecimal storeTurnover;

    @ApiModelProperty(value = "本店会员数量")
    private Integer storeMemberNum;

    @ApiModelProperty(value = "旗下店铺数量")
    private Integer storeSubNum;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "删除标志 默认0")
    @TableLogic
    private Boolean delFlag;
}
