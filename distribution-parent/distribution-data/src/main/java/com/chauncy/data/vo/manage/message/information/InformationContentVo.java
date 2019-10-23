package com.chauncy.data.vo.manage.message.information;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFilter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author yeJH
 * @description: 资讯基本信息
 * @since 2019/10/21 21:47
 */
@Data
@ApiModel(value = "平台查看资讯基本信息")
public class InformationContentVo  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "资讯id")
    private Long infoId;

    @ApiModelProperty(value = "发表资讯店铺id")
    private Long storeId;

    @ApiModelProperty(value = "发表资讯店铺logo")
    private String logoImage;

    @ApiModelProperty(value = "发表资讯店铺名称")
    private String storeName;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "资讯标题")
    private String title;

    @ApiModelProperty(value = "资讯正文")
    private String detailHtml;

    @JSONField(serialize = false)
    @ApiModelProperty(value = "图片")
    private String coverImage;

    @ApiModelProperty(value = "图片")
    private List<String> coverImages;

}
