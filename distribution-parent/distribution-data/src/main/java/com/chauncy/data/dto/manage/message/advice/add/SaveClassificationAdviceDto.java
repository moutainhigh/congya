package com.chauncy.data.dto.manage.message.advice.add;

import com.alibaba.fastjson.annotation.JSONField;
import com.chauncy.common.enums.app.advice.AdviceLocationEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-08-20 16:09
 *
 * 添加推荐的分类:葱鸭百货分类推荐/资讯分类推荐
 *
 */
@Data
@ApiModel(description = "添加推荐的分类:葱鸭百货分类推荐/资讯分类推荐")
@Accessors(chain = true)
public class SaveClassificationAdviceDto {

    @ApiModelProperty("广告ID,新增时传0")
    @JSONField(ordinal = 0)
    private Long adviceId;

    @ApiModelProperty("广告位置(保存充值入口/拼团鸭广告)")
    @EnumConstraint(target = AdviceLocationEnum.class)
    @JSONField(ordinal = 1)
    private String location;

    @ApiModelProperty("广告名称")
    @NotNull(message = "广告名称不能为空")
    @JSONField(ordinal = 2)
    private String name;

    @ApiModelProperty("图片")
    @NotNull(message = "图片不能为空")
    @JSONField(ordinal = 3)
    private String picture;

    @ApiModelProperty("分类列表信息")
    @NotNull(message = "分类列表信息不能为空")
    @Valid
    @JSONField(ordinal = 4)
    private List<ClassificationDto> classificationsDtoList;
}
