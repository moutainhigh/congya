package com.chauncy.data.vo.supplier;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Author cheng
 * @create 2019-06-20 11:07
 *
 * 显示给前端属性信息
 */
@ApiModel(description = "属性信息")
@Data
public class AttributeInfos {

    private List<Map<String,String>> attributeInfos;
}
