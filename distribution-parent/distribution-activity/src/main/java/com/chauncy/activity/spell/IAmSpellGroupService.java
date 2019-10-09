package com.chauncy.activity.spell;

import com.chauncy.data.domain.po.activity.spell.AmSpellGroupPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.app.activity.SearchMySpellGroupDto;
import com.chauncy.data.dto.app.activity.SearchSpellGroupInfoDto;
import com.chauncy.data.dto.app.product.SearchSpellGroupGoodsDto;
import com.chauncy.data.dto.manage.activity.SearchActivityListDto;
import com.chauncy.data.dto.manage.activity.spell.SaveSpellDto;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.app.activity.spell.MySpellGroupVo;
import com.chauncy.data.vo.app.activity.spell.SpellGroupDetailVo;
import com.chauncy.data.vo.app.activity.spell.SpellGroupInfoVo;
import com.chauncy.data.vo.app.goods.SpellGroupGoodsVo;
import com.chauncy.data.vo.manage.activity.SearchActivityListVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 * 秒杀活动管理 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-23
 */
public interface IAmSpellGroupService extends Service<AmSpellGroupPo> {


    /**
     * @Author yeJH
     * @Date 2019/10/7 21:33
     * @Description 获取拼团详情
     *
     * @Update yeJH
     *
     * @param relId 用户拼团id
     * @return com.chauncy.data.vo.app.activity.spell.SpellGroupDetailVo
     **/
    SpellGroupDetailVo getSpellGroupDetail(Long relId);

    /**
     * @Author yeJH
     * @Date 2019/10/6 23:47
     * @Description 查询我的拼团
     *
     * @Update yeJH
     *
     * @param  searchMySpellGroupDto
     * @return com.github.pagehelper.PageInfo<com.chauncy.data.vo.app.activity.spell.MySpellGroupVo>
     **/
    PageInfo<MySpellGroupVo> searchMySpellGroup(SearchMySpellGroupDto searchMySpellGroupDto);

    /**
     * @Author yeJH
     * @Date 2019/10/6 18:22
     * @Description 根据商品id获取拼团信息
     *
     * @Update yeJH
     *
     * @param  searchSpellGroupInfoDto
     * @return com.github.pagehelper.PageInfo<SpellGroupInfoVo>
     **/
     PageInfo<SpellGroupInfoVo> searchSpellGroupInfo(SearchSpellGroupInfoDto searchSpellGroupInfoDto);

    /**
     * @Author yeJH
     * @Date 2019/10/3 18:37
     * @Description 获取拼团动商品列表
     *
     * @Update yeJH
     *
     * @param  searchSpellGroupGoodsDto
     * @return com.github.pagehelper.PageInfo<com.chauncy.data.vo.app.goods.SpellGroupGoodsVo>
     **/
    PageInfo<SpellGroupGoodsVo> searchActivityGoodsList(SearchSpellGroupGoodsDto searchSpellGroupGoodsDto);

    /**
     * @Author yeJH
     * @Date 2019/10/6 17:25
     * @Description 获取拼团活动商品一级分类
     *
     * @Update yeJH
     *
     * @param
     * @return java.util.List<com.chauncy.data.vo.BaseVo>
     **/
    List<BaseVo> findGoodsCategory();

    /**
     * 保存秒杀活动信息
     * @param saveSpellDto
     * @return
     */
    void saveSpell(SaveSpellDto saveSpellDto);

    /**
     * 条件查询拼团活动信息
     *
     * @param searchActivityListDto
     * @return
     */
    PageInfo<SearchActivityListVo> searchSpellList(SearchActivityListDto searchActivityListDto);

    /**
     * 批量删除活动
     *
     * @param ids
     * @return
     */
    void delByIds(List<Long> ids);
}
