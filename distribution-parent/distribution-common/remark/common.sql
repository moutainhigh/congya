
-- 公用字段
CREATE TABLE `aa` (
                    `id` bigint(20) NOT NULL COMMENT '运费模版ID',
                    `create_by` varchar(255) NOT NULL COMMENT '创建者',
                    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                    `update_by`  varchar(255) DEFAULT NULL COMMENT '修改者',
                    `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                    `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标志 1-删除 0未删除',
                    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='运费模版说明表。平台运费模版+商家运费模版';

