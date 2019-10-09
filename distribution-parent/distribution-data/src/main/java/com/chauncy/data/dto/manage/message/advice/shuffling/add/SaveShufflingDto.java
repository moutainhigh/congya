package com.chauncy.data.dto.manage.message.advice.shuffling.add;

import com.chauncy.common.enums.app.advice.AdviceLocationEnum;
import com.chauncy.data.dto.manage.message.advice.tab.association.add.StoreTabsDto;
import com.chauncy.data.valid.annotation.EnumConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-08-20 13:52
 * <p>
 * 保存无关联轮播图广告
 * 首页左上角/首页底部/首页中部1/首页中部2/首页中部3/首页跳转内容-有品/首页跳转内容-有店/特卖内部/优选内部/个人中心展示样式
 */
@Data
@ApiModel(description = "保存无关联轮播图广告")
@Accessors(chain = true)
public class SaveShufflingDto {

    @ApiModelProperty("广告ID，新增时传0")
    private Long adviceId;

    @ApiModelProperty("广告位置(首页左上角/首页底部/首页中部1/首页中部2/首页中部3/首页跳转内容-有品/首页跳转内容-有店/特卖内部/优选内部" +
            "/个人中心展示样式/满减内部轮播图/积分内部轮播图/拼团内部轮播图)")
    @EnumConstraint(target = AdviceLocationEnum.class)
    private String location;

    @ApiModelProperty("广告名称")
    @NotNull(message = "广告名称不能为空")
    private String name;

    @ApiModelProperty("广告图片")
    private String picture;

    @ApiModelProperty("轮播图信息")
    @NotNull(message = "轮播图信息不能为空")
    @Valid
    private List<ShufflingDto> shufflingDtos;
}
