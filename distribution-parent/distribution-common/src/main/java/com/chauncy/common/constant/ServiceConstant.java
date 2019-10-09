package com.chauncy.common.constant;

/**
 * 业务常量
 * @author yeJH
 * @since 2019/8/16 0:01
 */
public interface ServiceConstant {

    /**
     * 店铺关系  团队合作关系层级最多为5层
     */
    Integer TEAM_WORK_LEVEL = 5;

    /**
     * 评论回复默认展示前两条
     */
    Integer VICE_COMMENT_NUM = 2;

    /**
     * 拼团活动商品展示拼团团长头像  最多5个
     */
    Integer SPELL_HEAD_PORTRAIT = 5;

    /**
     * 秒杀活动销售百分比超过70%展示即将售罄
     */
    Integer SALE_PERCENTAGE = 70;

    /**
     * APP店铺列表中店铺展示商品列表默认展示排序前三个
     */
    Integer DEFAULT_SHOW_GOODS_NUM = 3;
}
