package com.chauncy.data.vo.manage.store.label;

import com.chauncy.common.util.serializer.LongJsonDeserializer;
import com.chauncy.common.util.serializer.LongJsonSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author yeJH
 * @since 2019/6/15 20:27
 */
@Data
public class SmStoreLabelVo implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "id")
    @JsonSerialize(using = LongJsonSerializer.class)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    private Long id;


    @ApiModelProperty(value = "标签名称")
    private String name;

    @ApiModelProperty(value = "备注")
    private String remark;

    @CreatedDate
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
}
