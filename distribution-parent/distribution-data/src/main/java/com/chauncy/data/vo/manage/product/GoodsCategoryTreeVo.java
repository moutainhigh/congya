package com.chauncy.data.vo.manage.product;

import com.chauncy.data.domain.MyBaseTree;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author zhangrt
 * @Date 2019/6/27 18:54
 **/

@Data
public class GoodsCategoryTreeVo extends MyBaseTree<Long,GoodsCategoryTreeVo> {

    @ApiModelProperty(value = "名称")
    private String label;
}
