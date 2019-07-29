package com.chauncy.data.dto.app.order.log;

import com.chauncy.common.enums.log.AccountLogMatterEnum;
import com.chauncy.common.enums.log.AccountTypeEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/7/29 16:59
 */
@Data
@ApiModel(value = "SearchUserLogDto对象", description = "查找用户流水参数")
public class SearchUserLogDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账目类型")
    @EnumConstraint(target = AccountTypeEnum.class)
    private AccountTypeEnum accountTypeEnum;

    @JsonIgnore
    @ApiModelProperty(value = "用户id")
    private Long userId;

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;

}
