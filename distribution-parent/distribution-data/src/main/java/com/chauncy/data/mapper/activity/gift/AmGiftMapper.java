package com.chauncy.data.mapper.activity.gift;

import com.chauncy.data.domain.po.activity.gift.AmGiftPo;
import com.chauncy.data.dto.manage.activity.gift.select.SearchBuyGiftRecordDto;
import com.chauncy.data.dto.manage.activity.gift.select.SearchGiftDto;
import com.chauncy.data.dto.manage.activity.gift.select.SearchReceiveGiftRecordDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.manage.activity.gift.FindGiftVo;
import com.chauncy.data.vo.manage.activity.gift.SearchBuyGiftRecordVo;
import com.chauncy.data.vo.manage.activity.gift.SearchGiftListVo;
import com.chauncy.data.vo.manage.activity.gift.SearchReceiveGiftRecordVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 礼包表 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-20
 */
public interface AmGiftMapper extends IBaseMapper<AmGiftPo> {

    /**
     * 根据ID查找礼包信息
     *
     * @param id
     * @return
     */
    @Select("list * from am_gift where id = #{id} and del_flag =0")
    FindGiftVo findById(Long id);

    /**
     * 获取已关联的优惠券
     * @param id
     * @return
     */
    List<BaseVo> findAssociationed(Long id);

    /**
     * 多条件分页获取礼包信息
     * @param searchGiftDto
     * @return
     */
    List<SearchGiftListVo> searchGiftList(SearchGiftDto searchGiftDto);

    /** 多条件查询新人礼包领取记录
     *
     * @param searchReceiveRecordDto
     * @return
     */
    List<SearchReceiveGiftRecordVo> searchReceiveRecord(SearchReceiveGiftRecordDto searchReceiveRecordDto);

    /**
     * 多条件查询购买礼包记录
     *
     * @param searchBuyGiftRecordDto
     * @return
     */
    List<SearchBuyGiftRecordVo> searchBuyGiftRecord(SearchBuyGiftRecordDto searchBuyGiftRecordDto);
}
