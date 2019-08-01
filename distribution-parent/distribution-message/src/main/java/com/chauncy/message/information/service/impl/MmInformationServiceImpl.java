package com.chauncy.message.information.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chauncy.common.enums.app.sort.SortFileEnum;
import com.chauncy.common.enums.app.sort.SortWayEnum;
import com.chauncy.common.enums.common.VerifyStatusEnum;
import com.chauncy.common.enums.goods.StoreGoodsListTypeEnum;
import com.chauncy.common.enums.message.InformationTypeEnum;
import com.chauncy.common.enums.message.KeyWordTypeEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.data.domain.po.message.information.MmInformationPo;
import com.chauncy.data.domain.po.message.information.rel.MmRelInformationGoodsPo;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.domain.po.user.UmUserFavoritesPo;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.app.message.information.select.SearchInfoByConditionDto;
import com.chauncy.data.dto.base.BaseSearchDto;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.message.information.add.InformationDto;
import com.chauncy.data.dto.base.BaseSearchByTimeDto;
import com.chauncy.data.mapper.message.information.MmInformationMapper;
import com.chauncy.data.mapper.message.information.rel.MmRelInformationGoodsMapper;
import com.chauncy.data.mapper.product.PmGoodsCategoryMapper;
import com.chauncy.data.mapper.product.PmGoodsMapper;
import com.chauncy.data.mapper.user.UmUserFavoritesMapper;
import com.chauncy.data.mapper.user.UmUserMapper;
import com.chauncy.data.vo.app.goods.GoodsBaseInfoVo;
import com.chauncy.data.vo.app.message.information.InformationBaseVo;
import com.chauncy.data.vo.app.message.information.InformationPagingVo;
import com.chauncy.data.vo.manage.message.information.InformationPageInfoVo;
import com.chauncy.data.vo.manage.message.information.InformationVo;
import com.chauncy.data.vo.supplier.good.InformationRelGoodsVo;
import com.chauncy.message.information.rel.service.IMmRelInformationGoodsService;
import com.chauncy.message.information.service.IMmInformationService;
import com.chauncy.data.core.AbstractService;
import com.chauncy.security.util.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-25
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MmInformationServiceImpl extends AbstractService<MmInformationMapper, MmInformationPo> implements IMmInformationService {

    @Autowired
    private MmInformationMapper mmInformationMapper;
    @Autowired
    private MmRelInformationGoodsMapper mmRelInformationGoodsMapper;
    @Autowired
    private PmGoodsCategoryMapper pmGoodsCategoryMapper;
    @Autowired
    private PmGoodsMapper pmGoodsMapper;
    @Autowired
    private UmUserMapper umUserMapper;
    @Autowired
    private UmUserFavoritesMapper umUserFavoritesMapper;
    @Autowired
    private IMmRelInformationGoodsService mmRelInformationGoodsService;

    @Autowired
    private SecurityUtil securityUtil;

    /**
     * 保存资讯
     *
     * @param informationDto
     */
    @Override
    public void saveInformation(InformationDto informationDto) {

        MmInformationPo mmInformationPo = new MmInformationPo();
        BeanUtils.copyProperties(informationDto, mmInformationPo);
        //获取当前用户
        SysUserPo sysUserPo = securityUtil.getCurrUser();
        if(null == sysUserPo.getStoreId()) {
            throw  new ServiceException(ResultCode.FAIL, "当前登录用户不是商家用户");
        }
        mmInformationPo.setCreateBy(sysUserPo.getUsername());
        mmInformationPo.setId(null);
        //新增资讯默认为待审核状态
        mmInformationPo.setVerifyStatus(VerifyStatusEnum.WAIT_CONFIRM.getId());
        mmInformationPo.setStoreId(sysUserPo.getStoreId());
        mmInformationPo.setUpdateTime(LocalDateTime.now());
        mmInformationMapper.insert(mmInformationPo);

        //批量插入商品资讯关联记录
        informationDto.setId(mmInformationPo.getId());
        saveBatchRelInformationGoods(informationDto, sysUserPo.getUsername());
    }

    /**
     * 批量插入店铺品牌关联记录
     * @param informationDto
     * @param userName
     */
    private void saveBatchRelInformationGoods(InformationDto informationDto, String userName) {
        List<MmRelInformationGoodsPo> mmRelInformationGoodsPoList = new ArrayList<>();
        for(Long goodsId : informationDto.getGoodsIds()) {
            MmRelInformationGoodsPo mmRelInformationGoodsPo = new MmRelInformationGoodsPo();
            mmRelInformationGoodsPo.setInformationId(informationDto.getId());
            mmRelInformationGoodsPo.setCreateBy(userName);
            mmRelInformationGoodsPo.setGoodsId(goodsId);
            mmRelInformationGoodsPoList.add(mmRelInformationGoodsPo);
        }
        mmRelInformationGoodsService.saveBatch(mmRelInformationGoodsPoList);
    }

    /**
     * 编辑资讯
     *
     * @param informationDto
     */
    @Override
    public void editInformation(InformationDto informationDto) {

        MmInformationPo mmInformationPo = mmInformationMapper.selectById(informationDto.getId());
        SysUserPo sysUserPo = securityUtil.getCurrUser();
        if(null == sysUserPo.getStoreId()) {
            //平台用户
            if (mmInformationPo.getVerifyStatus().equals(VerifyStatusEnum.WAIT_CONFIRM.getId()) ||
                    mmInformationPo.getVerifyStatus().equals(VerifyStatusEnum.CHECKED.getId())) {
                //只有资讯的状态为待审核或者审核通过  平台用户才能编辑资讯  并且不更改资讯审核状态
                BeanUtils.copyProperties(informationDto, mmInformationPo);
                mmInformationPo.setUpdateBy(sysUserPo.getUsername());
                mmInformationMapper.updateById(mmInformationPo);
            } else {
                throw new ServiceException(ResultCode.NO_AUTH,"当前不可编辑已被审核驳回的资讯");
            }
        } else {
            //商家用户
            if (mmInformationPo.getVerifyStatus().equals(VerifyStatusEnum.NOT_APPROVED.getId())) {
                //只有资讯的状态为待审核或者审核通过  平台用户才能编辑资讯
                BeanUtils.copyProperties(informationDto, mmInformationPo);
                mmInformationPo.setUpdateBy(sysUserPo.getUsername());
                //编辑之后的资讯变回待审核状态
                mmInformationPo.setVerifyStatus(VerifyStatusEnum.WAIT_CONFIRM.getId());
                mmInformationMapper.updateById(mmInformationPo);
            } else {
                throw new ServiceException(ResultCode.NO_AUTH,"当前不可编辑待审核或已审核通过的资讯");
            }
        }

        //将资讯与商品关联表的记录删除
        Map<String, Object> map = new HashMap<>();
        map.put("information_id", informationDto.getId());
        mmRelInformationGoodsMapper.deleteByMap(map);

        //批量插入商品资讯关联记录
        saveBatchRelInformationGoods(informationDto, sysUserPo.getUsername());
    }

    /**
     * 批量删除资讯
     * @param ids
     */
    @Override
    public void delInformationByIds(Long[] ids) {

        //批量删除资讯
        mmInformationMapper.deleteBatchIds(Arrays.asList(ids));

        //删除资讯商品关联表的记录
        QueryWrapper<MmRelInformationGoodsPo> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("information_id", Arrays.asList(ids));
        mmRelInformationGoodsMapper.delete(queryWrapper);
    }

    /**
     * 后台根据ID查找资讯
     *
     * @param id 资讯id
     * @return
     */
    @Override
    public InformationVo findById(Long id) {
        //资讯基本信息
        InformationVo informationVo = mmInformationMapper.findById(id);
        //跟资讯绑定关系的商品信息
        List<InformationRelGoodsVo> goodsList = pmGoodsMapper.searchRelGoodsByInfoId(id);

        for(InformationRelGoodsVo informationRelGoodsVo : goodsList) {
            List<String> categorys = pmGoodsCategoryMapper.loadParentName(informationRelGoodsVo.getGoodsCategoryId());
            StringBuilder stringBuilder = new StringBuilder();
            for(String category : categorys) {
                stringBuilder.append(category).append("/");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            informationRelGoodsVo.setCategoryName(stringBuilder.toString());
        }
        informationVo.setGoodsList(goodsList);

        return informationVo;
    }

    /**
     * app根据ID查找资讯
     *
     * @param id 资讯id
     * @return
     */
    @Override
    public InformationBaseVo findBaseById(Long id) {
        UmUserPo umUserPo = securityUtil.getAppCurrUser();
        Long userId = null == umUserPo ? null : umUserPo.getId();
        MmInformationPo mmInformationPo = mmInformationMapper.selectById(id);
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("id", id);
        updateWrapper.set("browsing_num", mmInformationPo.getBrowsingNum() + 1);
        this.update(updateWrapper);
        //资讯基本信息
        InformationBaseVo informationBaseVo = mmInformationMapper.findBaseById(id);
        QueryWrapper<UmUserFavoritesPo> queryWrapper = new QueryWrapper();
        queryWrapper.lambda()
                .eq(UmUserFavoritesPo::getType, KeyWordTypeEnum.MERCHANT.getName())
                .eq(UmUserFavoritesPo::getUserId, userId)
                .eq(UmUserFavoritesPo::getFavoritesId, informationBaseVo.getId());
        if(null != umUserFavoritesMapper.selectOne(queryWrapper)) {
            //表示用户已关注
            informationBaseVo.setFocusStatus(true);
        } else {
            informationBaseVo.setFocusStatus(false);
        }
        //查询资讯关联的商品列表按最晚添加时间的第一个商品信息
        BaseSearchDto baseSearchDto = new BaseSearchDto();
        baseSearchDto.setId(mmInformationPo.getId());
        PageInfo<GoodsBaseInfoVo> goodsBaseInfoVoPageInfo = PageHelper.startPage(1, 1)
                .doSelectPageInfo(() -> pmGoodsMapper.searchGoodsByInfoId(baseSearchDto));
        if(goodsBaseInfoVoPageInfo.getList().size() > 0) {
            informationBaseVo.setGoodsBaseInfoVo(goodsBaseInfoVoPageInfo.getList().get(0));
        }
        return informationBaseVo;
    }

    /**
     * 根据关联ID删除资讯跟店铺的绑定关系
     *
     * @param id 资讯商品关联id
     * @return
     */
    @Override
    public void delRelById(Long id) {
        MmRelInformationGoodsPo mmRelInformationGoodsPo = mmRelInformationGoodsMapper.selectById(id);
        if(null != mmRelInformationGoodsPo) {
            //直接删除
            mmRelInformationGoodsMapper.deleteById(id);
        } else {
            throw new ServiceException(ResultCode.NO_EXISTS,"删除的信息不存在");
        }
    }


    /**
     * 后台分页条件查询
     *
     * @param baseSearchByTimeDto
     * @return
     */
    @Override
    public PageInfo<InformationPageInfoVo> searchPaging(BaseSearchByTimeDto baseSearchByTimeDto) {

        Integer pageNo = baseSearchByTimeDto.getPageNo()==null ? defaultPageNo : baseSearchByTimeDto.getPageNo();
        Integer pageSize = baseSearchByTimeDto.getPageSize()==null ? defaultPageSize : baseSearchByTimeDto.getPageSize();

        PageInfo<InformationPageInfoVo> informationPageInfoVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> mmInformationMapper.searchInfoPaging(baseSearchByTimeDto));
        return informationPageInfoVoPageInfo;
    }


    /**
     * app分页条件查询
     *
     * @param searchInfoByConditionDto
     * @return
     */
    @Override
    public PageInfo<InformationPagingVo> searchPaging(SearchInfoByConditionDto searchInfoByConditionDto) {

        if(null != searchInfoByConditionDto.getInformationTypeEnum() &&
                searchInfoByConditionDto.getInformationTypeEnum().equals(InformationTypeEnum.FOCUSLIST)) {
            //获取当前app用户信息
            UmUserPo umUserPo = securityUtil.getAppCurrUser();
            if(null == umUserPo) {
                throw new ServiceException(ResultCode.NO_LOGIN,"未登陆或登陆已超时");
            } else {
                searchInfoByConditionDto.setUserId(umUserPo.getId());
            }
        }

        Integer pageNo = searchInfoByConditionDto.getPageNo()==null ? defaultPageNo : searchInfoByConditionDto.getPageNo();
        Integer pageSize = searchInfoByConditionDto.getPageSize()==null ? defaultPageSize : searchInfoByConditionDto.getPageSize();

        PageInfo<InformationPagingVo> informationBaseVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> mmInformationMapper.searchInfoBasePaging(searchInfoByConditionDto));
        return informationBaseVoPageInfo;
    }


    /**
     * 用户点赞资讯
     *
     * @param infoId 资讯id
     * @param userId 用户id
     * @return
     */
    @Override
    public void likeInfo(Long infoId, Long userId) {
        UmUserPo umUserPo = umUserMapper.selectById(userId);
        if(null == umUserPo) {
            throw new ServiceException(ResultCode.NO_EXISTS,"用户不存在");
        }
        MmInformationPo mmInformationPo = mmInformationMapper.selectById(infoId);
        if(null != mmInformationPo) {
            UpdateWrapper updateWrapper = new UpdateWrapper();
            updateWrapper.eq("id", infoId);
            updateWrapper.set("liked_num", mmInformationPo.getLikedNum() + 1);
            this.update(updateWrapper);
        } else {
            throw new ServiceException(ResultCode.NO_EXISTS,"资讯不存在");
        }
    }

    /**
     * 审核资讯
     *
     * @param baseUpdateStatusDto
     */
    @Override
    public void verifyInfo(BaseUpdateStatusDto baseUpdateStatusDto) {
        MmInformationPo mmInformationPo = mmInformationMapper.selectById(baseUpdateStatusDto.getId()[0]);
        if(null == mmInformationPo) {
            throw new ServiceException(ResultCode.NO_EXISTS,"资讯不存在");
        } else {
            if(!mmInformationPo.getVerifyStatus().equals(VerifyStatusEnum.WAIT_CONFIRM.getId()))  {
                throw new ServiceException(ResultCode.PARAM_ERROR,"资讯不是待审核状态");
            } else {
                UpdateWrapper updateWrapper = new UpdateWrapper();
                updateWrapper.eq("id", mmInformationPo.getId());
                updateWrapper.set("verify_status", baseUpdateStatusDto.getEnabled());
                updateWrapper.set("verify_time", LocalDateTime.now());
                updateWrapper.set("verify_by", securityUtil.getCurrUser().getUsername());
                this.update(updateWrapper);
            }
        }
    }

    /**
     * 根据资讯id获取关联的商品
     *
     * @param baseSearchDto
     * @return
     */
    @Override
    public PageInfo<GoodsBaseInfoVo> searchGoodsById(BaseSearchDto baseSearchDto) {
        Integer pageNo = baseSearchDto.getPageNo()==null ? defaultPageNo : baseSearchDto.getPageNo();
        Integer pageSize = baseSearchDto.getPageSize()==null ? defaultPageSize : baseSearchDto.getPageSize();


        PageInfo<GoodsBaseInfoVo> goodsBaseInfoVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> pmGoodsMapper.searchGoodsByInfoId(baseSearchDto));
        return goodsBaseInfoVoPageInfo;
    }
}
