package com.chauncy.data.dto.manage.message.information.select;

import com.chauncy.data.dto.base.BaseSearchDto;
import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/7/3 20:43
 */
@Data
@ApiModel("根据主评论id获取副评论")
public class InformationViceCommentDto  extends BaseSearchDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "评论id")
    @NeedExistConstraint(tableName = "mm_information")
    @NotNull(message = "id不能为空")
    private Long id;

}