package com.chauncy.data.domain.po.store.information.category;

    import java.math.BigDecimal;
    import com.baomidou.mybatisplus.annotation.TableName;
    import java.time.LocalDateTime;
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
* @since 2019-06-25
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("im_information_category")
    @ApiModel(value="ImInformationCategoryPo对象", description="")
    public class ImInformationCategoryPo implements Serializable {

    private static final long serialVersionUID = 1L;

            @ApiModelProperty(value = "资讯分类id")
    private Long id;

            @ApiModelProperty(value = "资讯分类名称")
    private String name;

            @ApiModelProperty(value = "分类缩略图")
    private String icon;

            @ApiModelProperty(value = "排序数字")
    private BigDecimal sort;

            @ApiModelProperty(value = "是否启用 1-是 0-否 默认为1")
    private Boolean enabled;

            @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

            @ApiModelProperty(value = "创建者")
    private String createBy;

            @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

            @ApiModelProperty(value = "更新者")
    private String updateBy;

            @ApiModelProperty(value = "删除标志 默认0")
    private Boolean delFlag;


}
