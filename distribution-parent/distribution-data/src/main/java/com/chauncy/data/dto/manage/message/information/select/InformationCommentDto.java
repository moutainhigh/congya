package com.chauncy.data.dto.manage.message.information.select;

import com.chauncy.data.dto.base.BaseSearchDto;
import com.chauncy.data.dto.base.BaseSearchPagingDto;
import com.chauncy.data.valid.annotation.NeedExistConstraint;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/7/1 14:31
 */
@Data
@ApiModel("根据资讯id获取评论")
public class InformationCommentDto extends BaseSearchPagingDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "资讯id")
    @NeedExistConstraint(tableName = "mm_information")
    @NotNull(message = "id不能为空")
    private Long id;

    @JsonIgnore
    @ApiModelProperty(value = "用户id")
    private Long userId;

}
