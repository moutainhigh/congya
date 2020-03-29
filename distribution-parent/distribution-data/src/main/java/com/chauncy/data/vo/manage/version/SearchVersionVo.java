package com.chauncy.data.vo.manage.version;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author cheng
 * @create 2020-03-27 21:39
 */
@ApiModel(description = "条件分页查询版本信息")
@Data
public class SearchVersionVo {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改者")
    private String updateBy;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "app类型 1-android 2-ios")
    private Integer type;

    @ApiModelProperty(value = "版本号")
    private Integer version;

    @ApiModelProperty(value = "版本名称")
    private String versionName;

    @ApiModelProperty(value = "app下载链接")
    private String appLink;

    @ApiModelProperty(value = "是否为当前版本 1-是 0-否")
    private Boolean currentFlag;
}
