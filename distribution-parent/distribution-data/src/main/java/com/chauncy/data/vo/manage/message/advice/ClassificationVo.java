package com.chauncy.data.vo.manage.message.advice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cheng
 * @create 2019-08-20 20:25
 *
 * 条件分页获取葱鸭百货分类推荐/资讯分类推荐对应的分类信息
 *
 */
@Data
@ApiModel("条件分页获取葱鸭百货分类推荐/资讯分类推荐对应的分类信息")
@Accessors(chain = true)
public class ClassificationVo {

    @ApiModelProperty("该广告对应分类信息列表的ID,新增一条分类时传0")
    private Long relAssociatedId;

    @ApiModelProperty("分类ID")
    private Long classificationId;

    @ApiModelProperty("分类名称")
    private String classificationName;
}
