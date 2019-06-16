package com.chauncy.data.dto.manage.store.select;

import com.chauncy.data.dto.base.BaseSearchDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author yeJH
 * @since 2019/6/15 20:30
 */
@Data
public class StoreLabelSearchDto  extends BaseSearchDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "标签ID")
    private Long id;

    @ApiModelProperty(value = "标签名称")
    private String name;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat( pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startTime;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endTime;

}
