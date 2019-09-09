package com.chauncy.data.vo.app.advice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/9/5 14:20
 */
@Data
@ApiModel(description = "广告选项卡信息")
@Accessors(chain = true)
public class AdviceTabVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "选项卡id")
    private Long tabId;

    @ApiModelProperty(value = "选项卡名称")
    private String tabName;

}
