package com.chauncy.data.dto.manage.store.add;

import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.chauncy.data.valid.group.IUpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/6/25 18:29
 */
@Data
@ApiModel(value = "InformationLabelDto对象", description = "店铺资讯标签信息")
public class InformationLabelDto   implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "id,当新增时为空")
    @NotNull(groups = IUpdateGroup.class)
    private Long id;

    @ApiModelProperty(value = "资讯标题")
    @TableField(condition = SqlCondition.LIKE)
    private String title;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "是否启用 1-是 0-否 默认为0")
    private Boolean enabled;

    @ApiModelProperty(value = "资讯标签id（sm_information_label主键）")
    private Long infoLabelId;

    @ApiModelProperty(value = "资讯分类id（sm_information_category主键）")
    private Long infoCategoryId;

    @ApiModelProperty(value = "所属店铺Id")
    private Long storeId;

    @ApiModelProperty(value = "排序数字")
    private Integer sort;

    @ApiModelProperty(value = "封面图片")
    private String coverImage;

    @ApiModelProperty(value = "资讯正文")
    private String text;

    @ApiModelProperty(value = "1->待审核；2->审核通过；3->不通过/驳回")
    private Integer verifyStatus;

}
