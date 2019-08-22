package com.chauncy.data.dto.manage.message.advice.add;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @Author cheng
 * @create 2019-08-20 20:06
 *
 * 保存葱鸭百货分类推荐/资讯分类推荐广告对应的分类信息
 *
 */
@Data
@ApiModel(description = "保存葱鸭百货分类推荐/资讯分类推荐广告对应的分类信息")
@Accessors(chain = true)
public class ClassificationDto {

    @ApiModelProperty("该广告对应分类信息列表的ID,新增一条分类时传0")
    @NotNull(message = "该广告对应分类信息列表的Id")
    @JSONField(ordinal = 5)
    private Long RelAssociatedId;

    @ApiModelProperty("分类ID")
    @NotNull(message = "分类ID不能为空")
    @JSONField(ordinal = 6)
    private Long classificationId;

}
