package com.chauncy.message.information.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chauncy.common.enums.common.VerifyStatusEnum;
import com.chauncy.common.enums.message.InformationTypeEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.RelativeDateFormatUtil;
import com.chauncy.data.domain.po.message.information.MmInformationPo;
import com.chauncy.data.domain.po.message.information.MmUserInformationTimePo;
import com.chauncy.data.domain.po.message.information.rel.MmInformationForwardPo;
import com.chauncy.data.domain.po.message.information.rel.MmInformationLikedPo;
import com.chauncy.data.domain.po.message.information.rel.MmRelInformationGoodsPo;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.app.message.information.select.FindInfoParamDto;
import com.chauncy.data.dto.app.message.information.select.SearchInfoByConditionDto;
import com.chauncy.data.dto.base.BaseSearchPagingDto;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.message.information.add.InformationDto;
import com.chauncy.data.dto.base.BaseSearchByTimeDto;
import com.chauncy.data.dto.manage.order.bill.update.BatchAuditDto;
import com.chauncy.data.mapper.message.information.MmInformationMapper;
import com.chauncy.data.mapper.message.information.MmUserInformationTimeMapper;
import com.chauncy.data.mapper.message.information.rel.MmInformationForwardMapper;
import com.chauncy.data.mapper.message.information.rel.MmInformationLikedMapper;
import com.chauncy.data.mapper.message.information.rel.MmRelInformationGoodsMapper;
import com.chauncy.data.mapper.product.PmGoodsCategoryMapper;
import com.chauncy.data.mapper.product.PmGoodsMapper;
import com.chauncy.data.mapper.user.UmUserFavoritesMapper;
import com.chauncy.data.mapper.user.UmUserMapper;
import com.chauncy.data.vo.app.component.ScreenInfoParamVo;
import com.chauncy.data.vo.app.component.ScreenParamVo;
import com.chauncy.data.vo.app.goods.GoodsBaseInfoVo;
import com.chauncy.data.vo.app.message.information.InformationBaseVo;
import com.chauncy.data.vo.app.message.information.InformationPagingVo;
import com.chauncy.data.vo.manage.message.information.InformationContentVo;
import com.chauncy.data.vo.manage.message.information.InformationPageInfoVo;
import com.chauncy.data.vo.manage.message.information.InformationVo;
import com.chauncy.data.vo.supplier.good.InformationRelGoodsVo;
import com.chauncy.message.information.rel.service.IMmRelInformationGoodsService;
import com.chauncy.message.information.service.IMmInformationService;
import com.chauncy.data.core.AbstractService;
import com.chauncy.security.util.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Splitter;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

import static java.util.stream.Collectors.toList;

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
    private MmInformationLikedMapper mmInformationLikedMapper;
    @Autowired
    private MmInformationForwardMapper mmInformationForwardMapper;
    @Autowired
    private PmGoodsCategoryMapper pmGoodsCategoryMapper;
    @Autowired
    private MmUserInformationTimeMapper mmUserInformationTimeMapper;
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
     * @Author yeJH
     * @Date 2019/10/21 23:44
     * @Description 根据资讯id查询资讯信息
     *
     * @Update yeJH
     *
     * @param  infoId 资讯id
     * @return com.chauncy.data.vo.manage.message.information.InformationContentVo
     **/
    @Override
    public InformationContentVo getBaseInformation(Long infoId) {

        MmInformationPo mmInformationPo = mmInformationMapper.selectById(infoId);
        if(null == mmInformationPo) {
            throw new ServiceException(ResultCode.NO_EXISTS);
        }

        InformationContentVo informationContentVo = mmInformationMapper.getBaseInformation(infoId);
        if (null == informationContentVo) {
            return new InformationContentVo();
        } else {
            //资讯图片
            List<String> coverImages = Splitter.on(",").omitEmptyStrings()
                    .splitToList(informationContentVo.getCoverImage());
            informationContentVo.setCoverImages(coverImages);
            return informationContentVo;
        }


    }

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

        //查询新更改的品牌中缺少的已有品牌是否有关联的商品  如果有则编辑失败
        List<Long> oldRelGoodsIds = mmInformationMapper.selectRelGoodsIdsById(informationDto.getId());
        List<Long> newRelGoodsIds = informationDto.getGoodsIds();
        //oldRelGoodsIds跟newRelGoodsIds的差集  需要删除的关联
        List<Long> reduceList = oldRelGoodsIds.stream().filter(item -> !newRelGoodsIds.contains(item)).collect(toList());
        //删除关联
        if(null != reduceList && reduceList.size() > 0) {
            QueryWrapper<MmRelInformationGoodsPo> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda()
                    .eq(MmRelInformationGoodsPo::getInformationId, informationDto.getId())
                    .in(MmRelInformationGoodsPo::getGoodsId, reduceList);
            mmRelInformationGoodsMapper.delete(queryWrapper);
        }
        //批量插入商品资讯关联记录  此时需要插入的关联应该为 newRelGoodsIds  与 oldRelGoodsIds 的差集
        List<Long> needInsertList = newRelGoodsIds.stream().filter(item -> !oldRelGoodsIds.contains(item)).collect(toList());
        if(null != needInsertList && needInsertList.size() > 0) {
            informationDto.setGoodsIds(needInsertList);
            saveBatchRelInformationGoods(informationDto, sysUserPo.getUsername());
        }
    }

    /**
     * 批量删除资讯
     * @param ids
     */
    @Override
    public void delInformationByIds(Long[] ids) {

        List<Long> idList = Arrays.asList(ids);
        //删除的资讯enabled置为false
        UpdateWrapper<MmInformationPo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().in(MmInformationPo::getId, idList)
                .set(MmInformationPo::getEnabled, false);
        this.update(updateWrapper);

        //批量删除资讯
        mmInformationMapper.deleteBatchIds(idList);

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
            List<String> categoryList = pmGoodsCategoryMapper.loadParentName(informationRelGoodsVo.getGoodsCategoryId());
            StringBuilder stringBuilder = new StringBuilder();
            for(String category : categoryList) {
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
        if(null == mmInformationPo) {
            throw new ServiceException(ResultCode.NO_EXISTS, "资讯不存在");
        }
        //资讯添加浏览记录
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("id", id);
        updateWrapper.set("browsing_num", mmInformationPo.getBrowsingNum() + 1);
        this.update(updateWrapper);
        //资讯基本信息
        InformationBaseVo informationBaseVo = mmInformationMapper.findBaseById(id, userId);
        if (null != informationBaseVo.getCoverImage()){
            informationBaseVo.setCoverImageList(Splitter.on(",")
                    .omitEmptyStrings().splitToList(informationBaseVo.getCoverImage()));
            //资讯主图数量
            informationBaseVo.setPictureNum( informationBaseVo.getCoverImageList().size());
        }
        //查询资讯关联的商品列表
        List<GoodsBaseInfoVo> goodsBaseInfoVoList = new ArrayList<>();
        goodsBaseInfoVoList = pmGoodsMapper.searchGoodsByInfoId(mmInformationPo.getId());
        informationBaseVo.setGoodsBaseInfoVoList(goodsBaseInfoVoList);
        //商品数量
        informationBaseVo.setGoodsNum(null != goodsBaseInfoVoList ? goodsBaseInfoVoList.size() : 0);
        return informationBaseVo;
    }

    /**
     * 根据关联ID删除资讯跟商品的绑定关系
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

        SysUserPo sysUserPo = securityUtil.getCurrUser();
        if(null != sysUserPo.getStoreId()) {
            //店铺用户
            baseSearchByTimeDto.setStoreId(sysUserPo.getStoreId());
        }

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

        //获取当前app用户信息
        UmUserPo umUserPo = securityUtil.getAppCurrUser();
        if(null == umUserPo) {
            throw new ServiceException(ResultCode.NO_LOGIN,"未登陆或登陆已超时");
        } else {
            searchInfoByConditionDto.setUserId(umUserPo.getId());
        }

        if(searchInfoByConditionDto.getInformationType().equals(InformationTypeEnum.CATEGORY_LIST.getId())) {
            //根据资讯分类获取资讯列表
            if(null == searchInfoByConditionDto.getInfoCategoryId()) {
                throw new ServiceException(ResultCode.PARAM_ERROR, "infoCategoryId参数不能为空");
            }
        }
        if(searchInfoByConditionDto.getInformationType().equals(InformationTypeEnum.SEARCH_LIST.getId())) {
            //根据资讯名称搜索
            if(null == searchInfoByConditionDto.getKeyword()) {
                throw new ServiceException(ResultCode.PARAM_ERROR, "keyword参数不能为空");
            }
        }

        Integer pageNo = searchInfoByConditionDto.getPageNo()==null ? defaultPageNo : searchInfoByConditionDto.getPageNo();
        Integer pageSize = searchInfoByConditionDto.getPageSize()==null ? defaultPageSize : searchInfoByConditionDto.getPageSize();

        PageInfo<InformationPagingVo> informationPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> mmInformationMapper.searchInfoBasePaging(searchInfoByConditionDto));

        informationPageInfo.getList().forEach(informationPagingVo -> {
            if (null != informationPagingVo.getCoverImage()){
                informationPagingVo.setCoverImageList(Splitter.on(",")
                        .omitEmptyStrings().splitToList(informationPagingVo.getCoverImage()));
            }
            if (null != informationPagingVo.getInformationStoreInfo().getStoreLabels()){
                informationPagingVo.getInformationStoreInfo().setStoreLabelList(Splitter.on(",")
                        .omitEmptyStrings().splitToList(informationPagingVo.getInformationStoreInfo().getStoreLabels()));
            }
            informationPagingVo.setReleaseTime(RelativeDateFormatUtil.format(informationPagingVo.getUpdateTime()));
        });

        //关注资讯列表  更新用户最近一次访问资讯时间
        if(searchInfoByConditionDto.getInformationType().equals(InformationTypeEnum.FOCUS_LIST.getId())) {
            MmUserInformationTimePo mmUserInformationTimePo = mmUserInformationTimeMapper.selectById(umUserPo.getId());
            if(null != mmUserInformationTimePo) {
                //记录已存在 更新访问时间
                mmUserInformationTimePo.setReadTime(LocalDateTime.now());
                mmUserInformationTimeMapper.updateById(mmUserInformationTimePo);
            } else {
                mmUserInformationTimePo = new MmUserInformationTimePo();
                mmUserInformationTimePo.setId(umUserPo.getId());
                mmUserInformationTimePo.setReadTime(LocalDateTime.now());
                mmUserInformationTimeMapper.insert(mmUserInformationTimePo);
            }
        }
        return informationPageInfo;
    }


    /**
     * 用户资讯点赞、取消点赞
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
        if(null == mmInformationPo){
            throw new ServiceException(ResultCode.NO_EXISTS,"资讯不存在");
        }
        //评论点赞数
        Integer infoLikedNum = mmInformationPo.getLikedNum();

        //查询是否点赞过
        MmInformationLikedPo mmInformationLikedPo = mmInformationLikedMapper.selectForUpdate(infoId, userId);
        if(null == mmInformationLikedPo) {
            //未点赞过
            mmInformationLikedPo = new MmInformationLikedPo();
            mmInformationLikedPo.setInfoId(infoId);
            mmInformationLikedPo.setCreateBy(userId.toString());
            mmInformationLikedPo.setUserId(userId);
            //新增点赞记录
            mmInformationLikedMapper.insert(mmInformationLikedPo);
            infoLikedNum += 1;
        } else if(!mmInformationLikedPo.getDelFlag()) {
            //取消点赞
            mmInformationLikedPo.setDelFlag(true);
            mmInformationLikedMapper.updateById(mmInformationLikedPo);
            infoLikedNum -= 1;
        } else {
            //点赞
            mmInformationLikedPo.setDelFlag(false);
            mmInformationLikedMapper.updateById(mmInformationLikedPo);
            infoLikedNum += 1;
        }
        UpdateWrapper<MmInformationPo> updateWrapper = new UpdateWrapper();
        updateWrapper.lambda().eq(MmInformationPo::getId, infoId)
                .set(MmInformationPo::getLikedNum, infoLikedNum);
        this.update(updateWrapper);
    }

    /**
     * 用户转发资讯成功
     *
     * @param infoId
     * @param userId
     */
    @Override
    public void forwardInfo(Long infoId, Long userId) {

        MmInformationPo mmInformationPo = mmInformationMapper.selectById(infoId);
        if(null == mmInformationPo){
            throw new ServiceException(ResultCode.NO_EXISTS,"资讯不存在");
        }

        //查询是否转发过
        MmInformationForwardPo mmInformationForwardPo = mmInformationForwardMapper.selectForUpdate(infoId, userId);
        if(null == mmInformationForwardPo) {
            //未转发过
            mmInformationForwardPo = new MmInformationForwardPo();
            mmInformationForwardPo.setInfoId(infoId);
            mmInformationForwardPo.setCreateBy(userId.toString());
            mmInformationForwardPo.setUserId(userId);
            //新增转发记录
            mmInformationForwardMapper.insert(mmInformationForwardPo);
            UpdateWrapper<MmInformationPo> updateWrapper = new UpdateWrapper();
            updateWrapper.lambda().eq(MmInformationPo::getId, infoId)
                    .set(MmInformationPo::getForwardNum, mmInformationPo.getForwardNum() + 1);
            this.update(updateWrapper);
        }
    }

    /**
     * 店铺详情-首页-动态
     * @param storeId
     * @return
     */
    @Override
    public PageInfo<InformationPagingVo> searchInformationList(Long storeId, BaseSearchPagingDto baseSearchPagingDto) {

        //获取当前app用户信息
        UmUserPo umUserPo = securityUtil.getAppCurrUser();
        if(null == umUserPo) {
            throw new ServiceException(ResultCode.NO_LOGIN,"未登陆或登陆已超时");
        }

        Integer pageNo = baseSearchPagingDto.getPageNo()==null ? defaultPageNo : baseSearchPagingDto.getPageNo();
        Integer pageSize = baseSearchPagingDto.getPageSize()==null ? defaultPageSize : baseSearchPagingDto.getPageSize();

        PageInfo<InformationPagingVo> informationPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> mmInformationMapper.searchInformationList(storeId, umUserPo.getId()));


        informationPageInfo.getList().forEach(informationPagingVo -> {
            if (null != informationPagingVo.getCoverImage()){
                informationPagingVo.setCoverImageList(Splitter.on(",")
                        .omitEmptyStrings().splitToList(informationPagingVo.getCoverImage()));
            }
            informationPagingVo.setReleaseTime(RelativeDateFormatUtil.format(informationPagingVo.getUpdateTime()));
        });

        return informationPageInfo;
    }

    /**
     * @Author yeJH
     * @Date 2019/9/19 23:49
     * @Description 根据筛选资讯的条件获取资讯对应的资讯标签，内容分类等参数
     *
     * @Update yeJH
     *
     * @Param [searchInformationDto]
     * @return com.chauncy.data.vo.app.component.ScreenParamVo
     **/
    @Override
    public ScreenParamVo findScreenInfoParam(FindInfoParamDto findInfoParamDto) {
        //获取当前app用户信息
        UmUserPo umUserPo = securityUtil.getAppCurrUser();
        if(null == umUserPo) {
            throw new ServiceException(ResultCode.NO_LOGIN,"未登陆或登陆已超时");
        } else {
            findInfoParamDto.setUserId(umUserPo.getId());
        }


        if(findInfoParamDto.getInformationType().equals(InformationTypeEnum.CATEGORY_LIST.getId())
                && null == findInfoParamDto.getInfoCategoryId()) {
            throw new ServiceException(ResultCode.PARAM_ERROR, "infoCategoryId参数不能为空");
        }
        if(findInfoParamDto.getInformationType().equals(InformationTypeEnum.SEARCH_LIST.getId())
                && null == findInfoParamDto.getKeyword()) {
            throw new ServiceException(ResultCode.PARAM_ERROR, "keyword参数不能为空");
        }

        ScreenParamVo screenParamVo = new ScreenParamVo();
        ScreenInfoParamVo screenInfoParamVo = mmInformationMapper.findScreenInfoParam(findInfoParamDto);
        screenParamVo.setScreenInfoParamVo(screenInfoParamVo);
        return screenParamVo;
    }

    /**
     * 获取关注的店铺更新的资讯数目
     * @return
     */
    @Override
    public Integer getFocusInfoSum() {

        UmUserPo umUserPo = securityUtil.getAppCurrUser();
        Integer focusInfoSum;
        //关注资讯列表  更新用户最近一次访问资讯时间
        MmUserInformationTimePo mmUserInformationTimePo = mmUserInformationTimeMapper.selectById(umUserPo.getId());
        if(null == mmUserInformationTimePo) {
            //记录不存在 添加记录，记录用户访问关注店铺的时间
            mmUserInformationTimePo = new MmUserInformationTimePo();
            mmUserInformationTimePo.setId(umUserPo.getId());
            mmUserInformationTimeMapper.insert(mmUserInformationTimePo);
        }
        //获取数目
        focusInfoSum = mmInformationMapper.getFocusInfoSum(umUserPo.getId(), mmUserInformationTimePo.getReadTime());
        return focusInfoSum;
}

    /**
     * 审核资讯
     *
     * @param batchAuditDto
     */
    @Override
    public void verifyInfo(BatchAuditDto batchAuditDto) {
        MmInformationPo mmInformationPo = mmInformationMapper.selectById(batchAuditDto.getIds()[0]);
        if(null == mmInformationPo) {
            throw new ServiceException(ResultCode.NO_EXISTS,"资讯不存在");
        } else {
            if(!mmInformationPo.getVerifyStatus().equals(VerifyStatusEnum.WAIT_CONFIRM.getId()))  {
                throw new ServiceException(ResultCode.PARAM_ERROR,"资讯不是待审核状态");
            } else {
                UpdateWrapper updateWrapper = new UpdateWrapper();
                updateWrapper.eq("id", mmInformationPo.getId());
                updateWrapper.set("verify_status", batchAuditDto.getEnabled() ? VerifyStatusEnum.CHECKED.getId() : VerifyStatusEnum.NOT_APPROVED.getId());
                updateWrapper.set("verify_time", LocalDateTime.now());
                updateWrapper.set("verify_by", securityUtil.getCurrUser().getUsername());
                if(Strings.isNotBlank(batchAuditDto.getRejectReason())) {
                    updateWrapper.set("remark", batchAuditDto.getRejectReason());
                }
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
    /*@Override
    public PageInfo<GoodsBaseInfoVo> searchGoodsById(BaseSearchDto baseSearchDto) {
        Integer pageNo = baseSearchDto.getPageNo()==null ? defaultPageNo : baseSearchDto.getPageNo();
        Integer pageSize = baseSearchDto.getPageSize()==null ? defaultPageSize : baseSearchDto.getPageSize();


        PageInfo<GoodsBaseInfoVo> goodsBaseInfoVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> pmGoodsMapper.searchGoodsByInfoId(baseSearchDto));
        return goodsBaseInfoVoPageInfo;
    }*/

    /**
     * 批量修改资讯状态
     *
     * @param baseUpdateStatusDto
     */
    @Override
    public void editInformationStatus(BaseUpdateStatusDto baseUpdateStatusDto) {
        Arrays.asList(baseUpdateStatusDto.getId()).forEach(informationId -> {
            //审核通过的资讯才能启用禁用
            MmInformationPo mmInformationPo = mmInformationMapper.selectById(informationId);
            if (!VerifyStatusEnum.CHECKED.getId().equals(mmInformationPo.getVerifyStatus())) {
                throw new ServiceException(ResultCode.FAIL, "操作失败,审核通过的资讯才能启用禁用");
            }
        });
        UpdateWrapper<MmInformationPo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .in(MmInformationPo::getId, baseUpdateStatusDto.getId())
                .set(MmInformationPo::getEnabled, baseUpdateStatusDto.getEnabled());
        this.update(updateWrapper);
    }
}
