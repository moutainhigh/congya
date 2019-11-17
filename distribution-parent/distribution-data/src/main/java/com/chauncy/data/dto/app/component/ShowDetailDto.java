package com.chauncy.data.dto.app.component;

import com.chauncy.common.enums.app.component.ShareTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yeJH
 * @Description 获取分享数据接口参数
 * @since 2019/11/17 21:31
 */
@Data
@ApiModel(value = "ShowDetailDto", description =  "获取分享数据接口参数")
public class ShowDetailDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "分享类型")
    private ShareTypeEnum shareType;

    @ApiModelProperty(value = "需要被分享的商品ID/资讯ID")
    private Long shareId;
}
