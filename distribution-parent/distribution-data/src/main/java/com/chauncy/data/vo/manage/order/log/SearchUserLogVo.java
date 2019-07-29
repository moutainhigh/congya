package com.chauncy.data.vo.manage.order.log;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author yeJH
 * @since 2019/7/29 15:11
 */
@Data
@ApiModel(value = "SearchUserLogVo对象", description  = "用户账目流水信息")
public class SearchUserLogVo  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账目余额")
    private BigDecimal amount;

    @ApiModelProperty(value = "流水详情列表")
    private PageInfo<UserLogDetailVo> userLogDetailVoPageInfo;

}
