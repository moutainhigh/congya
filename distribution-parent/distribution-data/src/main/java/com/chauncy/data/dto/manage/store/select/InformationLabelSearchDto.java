package com.chauncy.data.dto.manage.store.select;

import com.chauncy.data.dto.base.BaseSearchDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/6/25 21:52
 */
@Data
public class InformationLabelSearchDto   extends BaseSearchDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "标签ID")
    private Long id;

    @ApiModelProperty(value = "标签名称")
    private String name;

}
