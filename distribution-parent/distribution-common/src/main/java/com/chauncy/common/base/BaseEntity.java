package com.chauncy.common.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author: HUANGWANCHENG
 * @Date: 2019/04/26 17:50
 * @Version 1.0
 */
@Data
public class BaseEntity {
        /**
         * 创建者
         */
        private String createBy;

        /**
         * 创建时间
         */
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date createTime;

        /**
         * 更新者
         */
        private String updateBy;

        /**
         * 更新时间
         */
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date updateTime;

        /**
         * 备注
         */
        private String remark;
        /**
         * 删除标志
         */
        private Boolean delFlag;
}
