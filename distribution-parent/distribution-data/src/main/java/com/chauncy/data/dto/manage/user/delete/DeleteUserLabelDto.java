package com.chauncy.data.dto.manage.user.delete;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @Author zhangrt
 * @Date 2019/6/12 12:39
 **/
@Data
@ApiModel( description = "标签删除dto")
public class DeleteUserLabelDto {

    @NotEmpty(message = "标签")
    @NeedExistConstraint(tableName = "um_rel_user_label",isNeedExists = false,field = "user_label_id"
    ,message = "删除失败：用户标签已被引用！")
    @ApiModelProperty(value = "标签删除id集合")
    List<Long> ids;
}
