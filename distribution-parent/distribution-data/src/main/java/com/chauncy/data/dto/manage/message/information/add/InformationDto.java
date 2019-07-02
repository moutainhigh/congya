package com.chauncy.data.dto.manage.message.information.add;

import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.chauncy.common.enums.common.VerifyStatusEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import com.chauncy.data.valid.annotation.NeedExistConstraint;
import com.chauncy.data.valid.group.IUpdateGroup;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author yeJH
 * @since 2019/6/25 19:54
 */
@Data
@ApiModel(value = "InformationDto对象", description = "店铺资讯信息")
public class InformationDto  implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "id,当新增时为空")
    @NotNull(groups = IUpdateGroup.class)
    @NeedExistConstraint(tableName = "mm_information")
    private Long id;

    @ApiModelProperty(value = "资讯标题")
    @NotBlank(message = "资讯分类名称不能为空")
    private String title;

    @ApiModelProperty(value = "作者")
    @NotBlank(message = "作者不能为空")
    private String author;

    @ApiModelProperty(value = "资讯标签id（mm_information_label主键）")
    @NeedExistConstraint(tableName = "mm_information_label")
    private Long infoLabelId;

    @ApiModelProperty(value = "资讯分类id（mm_information_category主键）")
    @NeedExistConstraint(tableName = "mm_information_category")
    private Long infoCategoryId;

    @ApiModelProperty(value = "关联商品id")
    @NeedExistConstraint(tableName = "pm_goods")
    private List<Long> goodsIds;

    @ApiModelProperty(value = "排序数字")
    @NotNull(message = "排序数字不能为空")
    private Integer sort;

    @ApiModelProperty(value = "封面图片")
    @NotBlank(message = "封面图片不能为空")
    private String coverImage;

    @ApiModelProperty(value = "资讯正文带格式文本")
    @NotBlank(message = "资讯正文不能为空")
    private String detailHtml;

    @ApiModelProperty(value = "资讯正文纯文本")
    @NotBlank(message = "资讯正文不能为空")
    private String  pureText ;

}
