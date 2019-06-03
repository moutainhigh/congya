package com.chauncy.data.domain.po.store;

    import java.math.BigDecimal;
    import com.baomidou.mybatisplus.annotation.TableName;
    import com.baomidou.mybatisplus.annotation.TableField;
    import java.io.Serializable;
    import io.swagger.annotations.ApiModel;
    import io.swagger.annotations.ApiModelProperty;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

/**
* <p>
    * 
    * </p>
*
* @author huangwancheng
* @since 2019-06-03
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("sm_store")
    @ApiModel(value="SmStorePo对象", description="")
    public class SmStorePo implements Serializable {

    private static final long serialVersionUID = 1L;

            @ApiModelProperty(value = "id")
    private Long id;

            @ApiModelProperty(value = "店铺名称")
    private String name;

            @ApiModelProperty(value = "店铺描述")
    private String describe;

            @ApiModelProperty(value = "店铺账号")
    private String userName;

            @ApiModelProperty(value = "是否展示在前端 0 不展示 1 展示")
    private Boolean showStatus;

            @ApiModelProperty(value = "店铺类型标签id（pm_goods_attribute主键）")
    private Long storeTypeLabelId;

            @ApiModelProperty(value = "店铺分类id（pm_goods_attribute主键）")
    private Long storeCategoryId;

            @ApiModelProperty(value = "商家类型（公司，品牌，分店）")
    private String type;

            @ApiModelProperty(value = "所属店铺Id")
    private Long parentId;

            @ApiModelProperty(value = "排序数字")
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
    private Integer serviceAttitude

Score;

            @ApiModelProperty(value = "物流服务评分")
    private Integer logisticsService
Score;


}
