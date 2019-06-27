package com.chauncy.data.dto.manage.message.content.add;

import com.chauncy.common.enums.message.ArticleLocationEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import com.chauncy.data.valid.annotation.NeedExistConstraint;
import com.chauncy.data.valid.group.IUpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @Author cheng
 * @create 2019-06-26 13:18
 *
 *
 * 添加文章Dto
 */
@Data
@ApiModel(description = "添加文章Dto")
@Accessors(chain = true)
public class AddArticleDto {

    @ApiModelProperty("文章id")
    @NotNull(message = "文章id不能为空",groups = IUpdateGroup.class)
    @NeedExistConstraint(tableName = "mm_article",groups = IUpdateGroup.class)
    private Long id;

    @ApiModelProperty("文章名称")
    @NotNull(message = "文章名称不能为空")
    private String name;

    @ApiModelProperty("文章位置")
    @NotNull(message = "文章位置不能为空")
    @EnumConstraint(target = ArticleLocationEnum.class)
    private String articleLocation;

    @ApiModelProperty("文章图文详情")
    @NotNull(message = "文章图文详情不能为空")
    private String detailHtml;




}
