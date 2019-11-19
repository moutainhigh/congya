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

    /**
     * 流水图标路径
     */
    String ICON_PATH = "http://bo2h.com/common/png/{0}.png";

    /**
     * 分享标题
     */
    String SHARE_TITLE = "我发现一个很好用的商城～一起来愉快地购物吧！";
    /**
     * 分享描述
     */
    String SHARE_DESCRIBE = "我发现了一个不错的商品，赶快来看看吧";
    /**
     * 分享商品链接
     */
    String SHARE_URL_GOODS = "{0}/goodsdetail?goodsId={1}&inviteCode={2}";
    /**
     * 分享资讯链接
     */
    String SHARE_URL_INFO = "{0}/info?id={1}&inviteCode={2}";
    /**
     * 分享用户注册链接
     */
    String SHARE_URL_REGISTER = "{0}/invite?inviteCode={1}";
}
