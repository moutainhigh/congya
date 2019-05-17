package com.chauncy.test.domain.DO;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @Author: HUANGWANCHENG
 * @Date: 2019/04/24 17:58
 * @Version 1.0
 */
@Data
@Document(collection = "sw_ebay_global")
public class GlobalDO {

    /**
     * 站点ID
     */
    @Field("site_id")
    private String siteId;

    /**
     * 站点名称
     */
    @Field("site_name")
    private String siteName;

    /**
     * 全球ID
     */
    @Field("global_id")
    private String globalId;

}

