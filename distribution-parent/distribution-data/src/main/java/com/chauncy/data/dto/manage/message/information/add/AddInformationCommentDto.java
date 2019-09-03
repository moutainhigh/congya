package com.chauncy.data.dto.manage.message.information.add;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/7/4 11:17
 */
@Data
@ApiModel(value = "InformationCommentDto对象", description = "资讯评论信息")
public class AddInformationCommentDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "评论的资讯ID")
    @NeedExistConstraint(tableName = "mm_information")
    @NotNull(message= "infoId参数不能为空")
    private Long infoId;

    @ApiModelProperty(value = "被回复的评论ID")
    @NeedExistConstraint(tableName = "mm_information_comment")
    private Long parentId;

    @ApiModelProperty(value = "评论内容")
    private String content;
}
