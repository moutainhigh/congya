package com.chauncy.data.vo.manage.message.content;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author cheng
 * @create 2019-06-26 19:09
 *
 * 查找关键字
 *
 */
@ApiModel(description = "查找关键字")
@Data
public class KeyWordVo {

    @ApiModelProperty(value = "热搜关键字ID")
    private Long id;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "类别： 商品 店铺 资讯")
    private String type;

    @ApiModelProperty(value = "关键字")
    private String name;

    @ApiModelProperty(value = "排序")
    private BigDecimal sort;
}
