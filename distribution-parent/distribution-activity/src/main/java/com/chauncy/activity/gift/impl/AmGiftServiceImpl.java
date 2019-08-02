package com.chauncy.activity.gift.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.activity.gift.IAmGiftRelGiftCouponService;
import com.chauncy.activity.gift.IAmGiftService;
import com.chauncy.common.enums.app.gift.GiftTypeEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.ListUtil;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.activity.gift.AmGiftOrderPo;
import com.chauncy.data.domain.po.activity.gift.AmGiftPo;
import com.chauncy.data.domain.po.activity.gift.AmGiftRelGiftCouponPo;
import com.chauncy.data.domain.po.activity.gift.AmGiftRelGiftUserPo;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.manage.activity.gift.add.SaveGiftDto;
import com.chauncy.data.dto.manage.activity.gift.select.SearchBuyGiftRecordDto;
import com.chauncy.data.dto.manage.activity.gift.select.SearchGiftDto;
import com.chauncy.data.dto.manage.activity.gift.select.SearchReceiveGiftRecordDto;
import com.chauncy.data.mapper.activity.coupon.AmCouponMapper;
import com.chauncy.data.mapper.activity.gift.AmGiftMapper;
import com.chauncy.data.mapper.activity.gift.AmGiftOrderMapper;
import com.chauncy.data.mapper.activity.gift.AmGiftRelGiftCouponMapper;
import com.chauncy.data.mapper.activity.gift.AmGiftRelGiftUserMapper;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.manage.activity.gift.FindGiftVo;
import com.chauncy.data.vo.manage.activity.gift.SearchBuyGiftRecordVo;
import com.chauncy.data.vo.manage.activity.gift.SearchGiftListVo;
import com.chauncy.data.vo.manage.activity.gift.SearchReceiveGiftRecordVo;
import com.chauncy.security.util.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 礼包表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-20
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AmGiftServiceImpl extends AbstractService<AmGiftMapper, AmGiftPo> implements IAmGiftService {

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private IAmGiftRelGiftCouponService relGiftCouponSaveBatchService;

    @Autowired
    private AmGiftMapper mapper;

    @Autowired
    private AmGiftRelGiftCouponMapper relGiftCouponMapper;

    @Autowired
    private AmCouponMapper couponMapper;

    @Autowired
    private AmGiftRelGiftUserMapper relGiftUserMapper;

    @Autowired
    private AmGiftOrderMapper giftOrderMapper;

    /**
     * 保存礼包
     * @param saveGiftDto
     * @return
     */
    @Override
    public void saveGift(SaveGiftDto saveGiftDto) {

        SysUserPo sysUserPo = securityUtil.getCurrUser();
        AmGiftPo giftPo = new AmGiftPo();
        List<AmGiftRelGiftCouponPo> relGiftCouponPos = Lists.newArrayList();
        //添加
        if (saveGiftDto.getId()==0){
            BeanUtils.copyProperties(saveGiftDto,giftPo);
            giftPo.setId(null);
            giftPo.setCreateBy(sysUserPo.getUsername());
            mapper.insert(giftPo);
        }
        //修改
        else{
            giftPo = mapper.selectById(saveGiftDto.getId());
            BeanUtils.copyProperties(saveGiftDto,giftPo);
            giftPo.setUpdateBy(sysUserPo.getUsername());
            mapper.updateById(giftPo);
        }
        if (!ListUtil.isListNullAndEmpty(saveGiftDto.getCouponIdList())){
            AmGiftPo finalGiftPo = giftPo;
            saveGiftDto.getCouponIdList().forEach(a->{
                AmGiftRelGiftCouponPo giftRelGiftCouponPo = new AmGiftRelGiftCouponPo();
                giftRelGiftCouponPo.setGiftId(finalGiftPo.getId());
                giftRelGiftCouponPo.setCouponId(a);
                giftRelGiftCouponPo.setCreateBy(sysUserPo.getUsername());
                relGiftCouponPos.add(giftRelGiftCouponPo);
            });
            //批量插入
           relGiftCouponSaveBatchService.saveBatch(relGiftCouponPos);
        }
    }

    /**
     * 批量删除礼包关联的优惠券
     *
     * @param relIds
     * @return
     */
    @Override
    public void delRelCouponByIds(List<Long> relIds) {

        relIds.forEach(a->{
            if (relGiftCouponMapper.selectById(a)==null){
                throw new ServiceException(ResultCode.FAIL, String.format("不存在该优惠券:[%s],请检查",a));
            }
        });
        relGiftCouponMapper.deleteBatchIds(relIds);
    }

    /**
     * 根据ID查找礼包信息
     *
     * @param id
     * @return
     */
    @Override
    public FindGiftVo findById(Long id) {

        FindGiftVo findGiftVo = mapper.findById(id);
        //获取全部的优惠券
        List<BaseVo> allCoupon = couponMapper.findAllCoupon();
        //获取已关联的优惠券
        List<BaseVo> associationed = mapper.findAssociationed(id);
        List<Long> associationedIds = associationed.stream().map(a->a.getId()).collect(Collectors.toList());
        //筛选未被关联的优惠券
        List<BaseVo> association = allCoupon.stream().filter(a->!associationedIds.contains(a.getId())).collect(Collectors.toList());

        findGiftVo.setAssociationedList(associationed);
        findGiftVo.setAssociationList(association);

        return findGiftVo;
    }

    /**
     * 多条件分页获取礼包信息
     * @param searchGiftDto
     * @return
     */
    @Override
    public PageInfo<SearchGiftListVo> searchGiftList(SearchGiftDto searchGiftDto) {

        Integer pageNo = searchGiftDto.getPageNo()==null ? defaultPageNo : searchGiftDto.getPageNo();
        Integer pageSize = searchGiftDto.getPageSize()==null ? defaultPageSize : searchGiftDto.getPageSize();

        PageInfo<SearchGiftListVo> searchGiftListVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() ->mapper.searchGiftList(searchGiftDto));

        searchGiftListVoPageInfo.getList().forEach(b->{
            //获取全部的优惠券
            List<BaseVo> allCoupon = couponMapper.findAllCoupon();
            //获取已关联的优惠券
            List<BaseVo> associationed = mapper.findAssociationed(b.getId());
            List<Long> associationedIds = associationed.stream().map(a->a.getId()).collect(Collectors.toList());
            //筛选未被关联的优惠券
            List<BaseVo> association = allCoupon.stream().filter(a->!associationedIds.contains(a.getId())).collect(Collectors.toList());
            b.setAssociationedList(associationed);
            b.setAssociationList(association);
            if (!ListUtil.isListNullAndEmpty(associationed)){
                b.setNum(associationed.size());
            }

        });


        return searchGiftListVoPageInfo;
    }

    /**
     * 批量删除礼包
     *
     * @param ids
     * @return
     */
    @Override
    public void delByIds(List<Long> ids) {
        ids.forEach(a->{
            if (mapper.selectById(a)==null){
                throw new ServiceException(ResultCode.FAIL, String.format("不存在该礼包:[%s],请检查",a));
            }
            //礼包被领取或者被购买了则不能删除
            AmGiftRelGiftUserPo giftRelGiftUserPo = relGiftUserMapper.selectOne(new QueryWrapper<AmGiftRelGiftUserPo>().eq("gift_id",a));
            if (giftRelGiftUserPo!=null){
                throw new ServiceException(ResultCode.FAIL,String.format("该礼包[%s]已被领取,不能删除",mapper.selectById(a).getName()));
            }
            List<AmGiftOrderPo> giftOrderPos = giftOrderMapper.selectList(new QueryWrapper<AmGiftOrderPo>().eq("gift_id",a));
            if (!ListUtil.isListNullAndEmpty(giftOrderPos)){
                throw new ServiceException(ResultCode.FAIL,String.format("该礼包[%s]已被购买,不能删除",mapper.selectById(a).getName()));
            }
        });

        mapper.deleteBatchIds(ids);
    }

    /**
     * 新人领取礼包
     * @param giftId
     * @return
     */
    @Override
    public void receiveGift(Long giftId) {

        UmUserPo userPo = securityUtil.getAppCurrUser();
        AmGiftPo giftPo = mapper.selectById(giftId);
        //判断礼包是否存在
        if (giftPo==null){
            throw new ServiceException(ResultCode.NO_EXISTS,String.format("该礼包【%s】不存在，请检查",giftId));
        }else if (giftPo.getType()!= GiftTypeEnum.NEW_COMER.getId()){
            throw new ServiceException(ResultCode.FAIL,String.format("该礼包【%s】不是新人礼包!",giftPo.getName()));
        }
        AmGiftRelGiftUserPo relGiftUserPo = new AmGiftRelGiftUserPo();
        relGiftUserPo.setCreateBy(userPo.getTrueName());
        relGiftUserPo.setId(null);
        relGiftUserPo.setUserId(userPo.getId());
        relGiftUserPo.setGiftId(giftId);
        relGiftUserMapper.insert(relGiftUserPo);
    }

    /**
     * 判断用户是否领取过新人礼包
     * @return
     */
    @Override
    public Boolean isReceive() {
        UmUserPo userPo = securityUtil.getAppCurrUser();
        AmGiftRelGiftUserPo relGiftUserPo = relGiftUserMapper.selectOne(new QueryWrapper<AmGiftRelGiftUserPo>().eq("user_id", userPo.getId()));
        if (relGiftUserPo == null){
            return true;
        }else if (relGiftUserPo != null){
            return false;
        }
        return null;
    }

    /**
     * 多条件查询新人礼包领取记录
     *
     * @param searchReceiveRecordDto
     * @return
     */
    @Override
    public PageInfo<SearchReceiveGiftRecordVo> searchReceiveRecord(SearchReceiveGiftRecordDto searchReceiveRecordDto) {
        Integer pageNo = searchReceiveRecordDto.getPageNo()==null ? defaultPageNo : searchReceiveRecordDto.getPageNo();
        Integer pageSize = searchReceiveRecordDto.getPageSize()==null ? defaultPageSize : searchReceiveRecordDto.getPageSize();

        PageInfo<SearchReceiveGiftRecordVo> searchReceiveGiftRecordVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() ->mapper.searchReceiveRecord(searchReceiveRecordDto));

        searchReceiveGiftRecordVoPageInfo.getList().forEach(b->{
            //获取已关联的优惠券
            List<BaseVo> associationed = mapper.findAssociationed(b.getGiftId());
            b.setCouponList(associationed);
            if (!ListUtil.isListNullAndEmpty(associationed)){
                b.setNum(associationed.size());
            }

        });


        return searchReceiveGiftRecordVoPageInfo;
    }

    /**
     * 多条件查询购买礼包记录
     *
     * @param searchBuyGiftRecordDto
     * @return
     */
    @Override
    public PageInfo<SearchBuyGiftRecordVo> searchBuyGiftRecord(SearchBuyGiftRecordDto searchBuyGiftRecordDto) {

        Integer pageNo = searchBuyGiftRecordDto.getPageNo()==null ? defaultPageNo : searchBuyGiftRecordDto.getPageNo();
        Integer pageSize = searchBuyGiftRecordDto.getPageSize()==null ? defaultPageSize : searchBuyGiftRecordDto.getPageSize();

        PageInfo<SearchBuyGiftRecordVo> searchBuyGiftRecordVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() ->mapper.searchBuyGiftRecord(searchBuyGiftRecordDto));
        searchBuyGiftRecordVoPageInfo.getList().forEach(b->{
            //获取已关联的优惠券
            List<BaseVo> associationed = mapper.findAssociationed(b.getGiftId());
            b.setCouponList(associationed);
            if (!ListUtil.isListNullAndEmpty(associationed)){
                b.setNum(associationed.size());
            }

        });
        return searchBuyGiftRecordVoPageInfo;
    }
}
