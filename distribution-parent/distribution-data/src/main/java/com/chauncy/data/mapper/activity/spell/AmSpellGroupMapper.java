package com.chauncy.data.mapper.activity.spell;

import com.chauncy.data.domain.po.activity.spell.AmSpellGroupPo;
import com.chauncy.data.dto.app.activity.SearchMySpellGroupDto;
import com.chauncy.data.dto.app.product.SearchSpellGroupGoodsDto;
import com.chauncy.data.dto.manage.activity.SearchActivityListDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.app.activity.spell.MySpellGroupVo;
import com.chauncy.data.vo.app.activity.spell.SpellGroupDetailVo;
import com.chauncy.data.vo.app.activity.spell.SpellGroupInfoVo;
import com.chauncy.data.vo.app.goods.SpellGroupGoodsVo;
import com.chauncy.data.vo.manage.activity.SearchActivityListVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 秒杀活动管理 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-23
 */
public interface AmSpellGroupMapper extends IBaseMapper<AmSpellGroupPo> {


    /**
     * @Author yeJH
     * @Date 2019/10/7 22:04
     * @Description 拼团详情
     *
     * @Update yeJH
     *
     * @param  relId  用户拼团id
     * @param  mainId  团id
     * @return com.chauncy.data.vo.app.activity.spell.SpellGroupDetailVo
     **/
    SpellGroupDetailVo getSpellGroupDetail(@Param("relId") Long relId, @Param("mainId") Long mainId);

    /**
     * @Author yeJH
     * @Date 2019/10/6 23:51
     * @Description 查询我的拼团
     *
     * @Update yeJH
     *
     * @param  searchMySpellGroupDto
     * @return java.util.List<MySpellGroupVo>
     **/
    List<MySpellGroupVo> searchMySpellGroup(SearchMySpellGroupDto searchMySpellGroupDto);

    /**
     * @Author yeJH
     * @Date 2020/2/17 17:11
     * @Description 根据商品id获取拼团信息
     *
     * @Update yeJH
     *
     * @param  relId  活动跟商品关联的id
     * @param  userId  用户本人拼团的团不展示
     * @param  headPortrait  拼团用户默认头像
     * @return java.util.List<com.chauncy.data.vo.app.activity.spell.SpellGroupInfoVo>
     **/
    List<SpellGroupInfoVo> searchSpellGroupInfo(@Param("relId") Long relId,
                                                @Param("userId") Long userId,
                                                @Param("headPortrait") String headPortrait );

    /**
     * @Author yeJH
     * @Date 2019/10/7 1:43
     * @Description 获取拼团团员头像
     *
     * @Update yeJH
     *
     * @param  mainId
     * @return java.util.List<java.lang.String>
     **/
    List<String> getMemberHeadPortrait(@Param("mainId") Long mainId, @Param("spellHeadPortrait") Integer spellHeadPortrait);

    /**
     * @Author yeJH
     * @Date 2019/10/5 17:32
     * @Description 获取拼团团长头像
     *
     * @Update yeJH
     *
     * @param  mainIds
     * @return java.lang.String
     **/
    List<String> getMainHeadPortrait(String mainIds);

    /**
     * @Author yeJH
     * @Date 2019/10/6 17:29
     * @Description 获取拼团活动商品一级分类
     *
     * @Update yeJH
     *
     * @param  
     * @return java.util.List<com.chauncy.data.vo.BaseVo>
     **/
    List<BaseVo> findGoodsCategory();
    
    /**
     * @Author yeJH
     * @Date 2019/10/3 18:48
     * @Description 获取拼团动商品列表
     *
     * @Update yeJH
     *
     * @param  searchSpellGroupGoodsDto
     * @return java.util.List<com.chauncy.data.vo.app.goods.SpellGroupGoodsVo>
     **/
    List<SpellGroupGoodsVo> searchActivityGoodsList(SearchSpellGroupGoodsDto searchSpellGroupGoodsDto);

    /**
     * 条件查询拼团活动信息
     *
     * @param searchActivityListDto
     * @return
     */
    List<SearchActivityListVo> searchSpellList(SearchActivityListDto searchActivityListDto);

    /**
     * 查询拼团详情
     * @param id
     * @return
     */
    SearchActivityListVo findSpellGroupById(Long id);

    /**
     * @Author chauncy
     * @Date 2019-10-10 15:32
     * @Description //获取拼团主页面中的今日必拼的三个商品
     *
     * @Update chauncy
     *
     * @param  adviceId
     * @return java.util.List<com.chauncy.data.vo.app.goods.SpellGroupGoodsVo>
     **/
    List<SpellGroupGoodsVo> findTodaySpell(Long adviceId);

    /**
     * @Author chauncy
     * @Date 2019-10-10 15:52
     * @Description //获取今日必拼商品相关的类目
     *
     * @Update chauncy
     *
     * @param
     * @return java.util.List<com.chauncy.data.vo.BaseVo>
     **/
    @Select("select a.id,a.name " +
            "from pm_goods_category a,mm_advice_rel_spell_goods b,mm_advice c " +
            "where b.del_flag = 0 and a.del_flag = 0 and a.enabled = 1 and c.del_flag = 0 and c.enabled = 1 " +
            "and b.first_category_id = a.id and c.id = b.advice_id")
    List<BaseVo> findTodaySpellCategory();

    /**
     * @Author chauncy
     * @Date 2019-10-10 16:02
     * @Description //查询今日必拼商品列表参数
     *
     * @Update chauncy
     *
     * @param  searchTodaySpellGoods
     * @return void
     **/
    List<SpellGroupGoodsVo> searchTodaySpellGoods(SearchSpellGroupGoodsDto searchTodaySpellGoods);
}
