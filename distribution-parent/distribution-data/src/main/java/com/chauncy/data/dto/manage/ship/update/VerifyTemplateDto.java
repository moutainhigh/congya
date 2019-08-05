package com.chauncy.data.dto.manage.ship.update;

import com.chauncy.common.enums.common.VerifyStatusEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cheng
 * @create 2019-06-25 18:37
 *
 * 批量修改模版的审核状态
 */
@Data
@ApiModel("批量修改模版的审核状态")
@Accessors(chain = true)
public class VerifyTemplateDto {

    @ApiModelProperty("模版ID集合")
    @NeedExistConstraint(tableName = "pm_shipping_template")
    private Long[] ids;

    @ApiModelProperty("检验状态 1-待提交 2-待审核 3-审核通过 4-不通过/驳回")
    @EnumConstraint(target = VerifyStatusEnum.class)
    private Integer verifyStatus;

    @ApiModelProperty("不通过/反馈原因")
    private String content;
}
