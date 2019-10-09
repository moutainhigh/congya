package com.chauncy.message.advice.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.enums.app.advice.AdviceTypeEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.ListUtil;
import com.chauncy.data.domain.po.message.advice.MmAdvicePo;
import com.chauncy.data.domain.po.message.advice.MmAdviceRelShufflingPo;
import com.chauncy.data.domain.po.product.PmGoodsCategoryPo;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.dto.manage.message.advice.shuffling.add.SaveShufflingDto;
import com.chauncy.data.dto.manage.message.advice.shuffling.select.SearchShufflingAssociatedDetailDto;
import com.chauncy.data.mapper.message.advice.MmAdviceMapper;
import com.chauncy.data.mapper.message.advice.MmAdviceRelShufflingMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.mapper.message.information.MmInformationMapper;
import com.chauncy.data.mapper.product.PmGoodsCategoryMapper;
import com.chauncy.data.mapper.product.PmGoodsMapper;
import com.chauncy.data.mapper.store.SmStoreMapper;
import com.chauncy.data.vo.manage.message.advice.shuffling.SearchShufflingAssociatedDetailVo;
import com.chauncy.message.advice.IMmAdviceRelShufflingService;
import com.chauncy.security.util.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 广告与无关联轮播图关联表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-14
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MmAdviceRelShufflingServiceImpl extends AbstractService<MmAdviceRelShufflingMapper, MmAdviceRelShufflingPo> implements IMmAdviceRelShufflingService {

    @Autowired
    private MmAdviceRelShufflingMapper relShufflingMapper;

    @Autowired
    private PmGoodsMapper goodsMapper;

    @Autowired
    private PmGoodsCategoryMapper goodsCategoryMapper;

    @Autowired
    private MmInformationMapper informationMapper;

    @Autowired
    private SmStoreMapper storeMapper;

    @Autowired
    private MmAdviceMapper adviceMapper;

    @Autowired
    private SecurityUtil securityUtil;

    /**
     * 条件分页查询轮播图广告需要绑定的资讯、商品、店铺
     *
     * @param searchShufflingAssociatedDetailDto
     * @return
     */
    @Override
    public PageInfo<SearchShufflingAssociatedDetailVo> searchShufflingAssociatedDetail(SearchShufflingAssociatedDetailDto searchShufflingAssociatedDetailDto) {

        Integer pageNo = searchShufflingAssociatedDetailDto.getPageNo() == null ? defaultPageNo : searchShufflingAssociatedDetailDto.getPageNo();
        Integer pageSize = searchShufflingAssociatedDetailDto.getPageSize() == null ? defaultPageSize : searchShufflingAssociatedDetailDto.getPageSize();

        PageInfo<SearchShufflingAssociatedDetailVo> detailVoPageInfo = new PageInfo<>();
        AdviceTypeEnum adviceTypeEnum = AdviceTypeEnum.getAdviceTypeEnum(searchShufflingAssociatedDetailDto.getAdviceType());

        switch (adviceTypeEnum) {
            case HTML_DETAIL:
                break;
            case INFORMATION:
                detailVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                        .doSelectPageInfo(() -> relShufflingMapper.searchInformationDetail(searchShufflingAssociatedDetailDto));
                break;
            case STROE:
                detailVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                        .doSelectPageInfo(() -> relShufflingMapper.searchStoreDetail(searchShufflingAssociatedDetailDto));
                break;
            case GOODS:
                detailVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                        .doSelectPageInfo(() -> relShufflingMapper.searchGoodsDetail(searchShufflingAssociatedDetailDto));
                //获取所属分类
                detailVoPageInfo.getList().forEach(a->{
                    PmGoodsCategoryPo goodsCategoryPo = goodsCategoryMapper.selectById(a.getCategoryId());
                    String level3 = goodsCategoryPo.getName();
                    PmGoodsCategoryPo goodsCategoryPo2 = goodsCategoryMapper.selectById(goodsCategoryPo.getParentId());
                    String level2 = goodsCategoryPo2.getName();
                    String level1 = goodsCategoryMapper.selectById(goodsCategoryPo2.getParentId()).getName();

                    String categoryName = level1 + "/" + level2 + "/" + level3;
                    a.setCategoryName(categoryName);
                });

                break;
        }

        return detailVoPageInfo;
    }

    /**
     *
     * 广告位置：首页左上角/首页底部/首页中部1/首页中部2/首页中部3/首页跳转内容-有品/首页跳转内容-有店/特卖内部/优选内部/个人中心展示样式
     * 保存无关联广告轮播图
     *
     * @param saveShufflingDto
     * @return
     */
    @Override
    public void saveShuffling(SaveShufflingDto saveShufflingDto) {

        SysUserPo user = securityUtil.getCurrUser();

        //新增操作
        if (saveShufflingDto.getAdviceId() == 0 ){
            //判断一: 广告名称不能相同
            List<String> nameList = adviceMapper.selectList(null).stream().map(a -> a.getName()).collect(Collectors.toList());
            if (nameList.contains(saveShufflingDto.getName())) {
                throw new ServiceException(ResultCode.FAIL, String.format("广告名称【%s】已经存在,请检查！", saveShufflingDto.getName()));
            }
            //判断二: 同一个广告不能有相同商品/资讯/店铺
            List<Long> brandRelIds = saveShufflingDto.getShufflingDtos().stream().map(rel -> rel.getDetailId()).collect(Collectors.toList());
            if (!ListUtil.isListNullAndEmpty(brandRelIds)) {
                boolean relIdIsrepeat = brandRelIds.size() != new HashSet<Long>(brandRelIds).size();
                if (relIdIsrepeat) {
                    throw new ServiceException(ResultCode.DUPLICATION, String.format("存在重复数据,请检查!"));
                }
            }
            /******************** 1、保存广告 ********************/
            MmAdvicePo advicePo = new MmAdvicePo();
            advicePo.setId(null).setCreateBy(user.getUsername()).setLocation(saveShufflingDto.getLocation())
                    .setName(saveShufflingDto.getName()).setPicture(saveShufflingDto.getPicture());
            adviceMapper.insert(advicePo);
            /******************** 2、保存广告对饮的轮播图信息 ********************/
            saveShufflingDto.getShufflingDtos().forEach(a->{
                //开始时间和结束时间处理
                if (a.getEndTime().isBefore(a.getStartTime()) || a.getEndTime().equals(a.getStartTime())) {
                    throw new ServiceException(ResultCode.FAIL, String.format("开始时间不能大于等于结束时间"));
                }
                if (a.getStartTime().isBefore(LocalDateTime.now())) {
                    throw new ServiceException(ResultCode.FAIL, String.format("开始时间需要在当前时间之后"));
                }
                if (a.getEndTime().isBefore(LocalDateTime.now())) {
                    throw new ServiceException(ResultCode.FAIL, String.format("结束时间需要在当前时间之后"));
                }
                MmAdviceRelShufflingPo relShufflingPo = new MmAdviceRelShufflingPo();
                //获取广告类型
                AdviceTypeEnum adviceTypeEnum = AdviceTypeEnum.getAdviceTypeEnum(a.getAdviceType());
                if (adviceTypeEnum == null){
                    throw new ServiceException(ResultCode.NO_EXISTS,"所传枚举值adviceType不存在");
                }

                switch (adviceTypeEnum) {
                    case HTML_DETAIL:
                        relShufflingPo.setId(null).setCreateBy(user.getUsername()).setAdviceId(advicePo.getId())
                                .setAdviceType(adviceTypeEnum).setHtmlDetail(a.getHtmlDetail()).setRelTabBrandId(null)
                                .setBrandId(null).setStartTime(a.getStartTime()).setEndTime(a.getEndTime())
                                .setCoverPhoto(a.getCoverPhoto()).setDetailId(null).setRelCategoryId(null).setFirstCategoryId(null);
                        relShufflingMapper.insert(relShufflingPo);
                        break;
                    case INFORMATION:
                    case STROE:
                    case GOODS:
                        if (adviceTypeEnum.equals(AdviceTypeEnum.GOODS)) {
                            if (goodsMapper.selectById(a.getDetailId()) == null) {
                                throw new ServiceException(ResultCode.NO_EXISTS, String.format("数据库不存在ID为[%s]的商品不存在，请检查", a.getDetailId()));
                            }
                        }else if (adviceTypeEnum.equals(AdviceTypeEnum.INFORMATION)) {
                            if (informationMapper.selectById(a.getDetailId()) == null) {
                                throw new ServiceException(ResultCode.NO_EXISTS, String.format("数据库不存在ID为[%s]的资讯不存在，请检查", a.getDetailId()));
                            }
                        }else if (adviceTypeEnum.equals(AdviceTypeEnum.STROE)) {
                            if (storeMapper.selectById(a.getDetailId()) == null) {
                                throw new ServiceException(ResultCode.NO_EXISTS, String.format("数据库不存在ID为[%s]的店铺不存在，请检查", a.getDetailId()));
                            }
                        }
                        relShufflingPo.setId(null).setCreateBy(user.getUsername()).setAdviceId(advicePo.getId())
                                .setAdviceType(adviceTypeEnum).setDetailId(a.getDetailId()).setRelTabBrandId(null)
                                .setBrandId(null).setStartTime(a.getStartTime()).setEndTime(a.getEndTime())
                                .setCoverPhoto(a.getCoverPhoto()).setHtmlDetail(null).setRelCategoryId(null).setFirstCategoryId(null);
                        relShufflingMapper.insert(relShufflingPo);
                        break;
                }
            });
        }
        //编辑操作
        else{

            //判断一: 广告名称不能相同
            //获取该广告名称
            String adviceName = adviceMapper.selectById(saveShufflingDto.getAdviceId()).getName();
            //获取除该广告名称之外的所有广告名称
            List<String> nameList = adviceMapper.selectList(null).stream().filter(name -> !name.getName().equals(adviceName)).map(a -> a.getName()).collect(Collectors.toList());
            if (!ListUtil.isListNullAndEmpty(nameList) && nameList.contains(saveShufflingDto.getName())) {
                throw new ServiceException(ResultCode.FAIL, String.format("广告名称【%s】已经存在,请检查！", saveShufflingDto.getName()));
            }

            //1、更改广告信息到广告表——mm_advice
            MmAdvicePo mmAdvicePo = adviceMapper.selectById(saveShufflingDto.getAdviceId());
            BeanUtils.copyProperties(saveShufflingDto, mmAdvicePo);
            mmAdvicePo.setUpdateBy(user.getUsername());
            adviceMapper.updateById(mmAdvicePo);

            /******************************************* 删除轮播图 start *******************************************/
            //获取原来的所有的轮播图
            List<Long> allIds = relShufflingMapper.selectList(new QueryWrapper<MmAdviceRelShufflingPo>().lambda()
                    .eq(MmAdviceRelShufflingPo::getAdviceId,saveShufflingDto.getAdviceId())).stream()
                    .map(a->a.getId()).collect(Collectors.toList());
            //获取前端传来的除了shufflingId为0的数据
            List<Long> remainIds = saveShufflingDto.getShufflingDtos().stream().filter(a->a.getShufflingId()!=0).map(b->b.getShufflingId()).collect(Collectors.toList());
            if (!ListUtil.isListNullAndEmpty(allIds)) {
                List<Long> delIds = new ArrayList<>();
                if (ListUtil.isListNullAndEmpty(remainIds)){
                    delIds = allIds;
                    relShufflingMapper.deleteBatchIds(delIds);
                }else {
                    delIds = allIds.stream().filter(y->!remainIds.contains(y)).collect(Collectors.toList());
                    if (!ListUtil.isListNullAndEmpty(delIds)){
                        relShufflingMapper.deleteBatchIds(delIds);
                    }
                }
            }
            /*if (!ListUtil.isListNullAndEmpty(allIds)) {
                //获取需要删除的数据
                List<Long> delIds = Lists.newArrayList(allIds);
                delIds.removeAll(remainIds);
                if (!ListUtil.isListNullAndEmpty(delIds)){
                    relShufflingMapper.deleteBatchIds(delIds);
                }
            }*/
            /******************************************* 删除轮播图 end *********************************************/

            saveShufflingDto.getShufflingDtos().forEach(a->{
                //新增轮播图
                if (a.getShufflingId() == 0){


                    //开始时间和结束时间处理
                    if (a.getEndTime().isBefore(a.getStartTime()) || a.getEndTime().equals(a.getStartTime())) {
                        throw new ServiceException(ResultCode.FAIL, String.format("开始时间不能大于等于结束时间"));
                    }
                    if (a.getStartTime().isBefore(LocalDateTime.now())) {
                        throw new ServiceException(ResultCode.FAIL, String.format("开始时间需要在当前时间之后"));
                    }
                    if (a.getEndTime().isBefore(LocalDateTime.now())) {
                        throw new ServiceException(ResultCode.FAIL, String.format("结束时间需要在当前时间之后"));
                    }
                    MmAdviceRelShufflingPo relShufflingPo = new MmAdviceRelShufflingPo();
                    //获取广告类型
                    AdviceTypeEnum adviceTypeEnum = AdviceTypeEnum.getAdviceTypeEnum(a.getAdviceType());
                    switch (adviceTypeEnum) {
                        case HTML_DETAIL:
                            relShufflingPo.setId(null).setCreateBy(user.getUsername()).setAdviceId(saveShufflingDto.getAdviceId())
                                    .setAdviceType(adviceTypeEnum).setHtmlDetail(a.getHtmlDetail()).setRelTabBrandId(null)
                                    .setBrandId(null).setStartTime(a.getStartTime()).setEndTime(a.getEndTime())
                                    .setCoverPhoto(a.getCoverPhoto()).setDetailId(null).setRelCategoryId(null).setFirstCategoryId(null);
                            relShufflingMapper.insert(relShufflingPo);
                            break;
                        case INFORMATION:
                        case STROE:
                        case GOODS:
                            if (adviceTypeEnum.equals(AdviceTypeEnum.GOODS)) {
                                if (goodsMapper.selectById(a.getDetailId()) == null) {
                                    throw new ServiceException(ResultCode.NO_EXISTS, String.format("数据库不存在ID为[%s]的商品不存在，请检查", a.getDetailId()));
                                }
                            }else if (adviceTypeEnum.equals(AdviceTypeEnum.INFORMATION)) {
                                if (informationMapper.selectById(a.getDetailId()) == null) {
                                    throw new ServiceException(ResultCode.NO_EXISTS, String.format("数据库不存在ID为[%s]的资讯不存在，请检查", a.getDetailId()));
                                }
                            }else if (adviceTypeEnum.equals(AdviceTypeEnum.STROE)) {
                                if (storeMapper.selectById(a.getDetailId()) == null) {
                                    throw new ServiceException(ResultCode.NO_EXISTS, String.format("数据库不存在ID为[%s]的店铺不存在，请检查", a.getDetailId()));
                                }
                            }
                            relShufflingPo.setId(null).setCreateBy(user.getUsername()).setAdviceId(saveShufflingDto.getAdviceId())
                                    .setAdviceType(adviceTypeEnum).setDetailId(a.getDetailId()).setRelTabBrandId(null)
                                    .setBrandId(null).setStartTime(a.getStartTime()).setEndTime(a.getEndTime())
                                    .setCoverPhoto(a.getCoverPhoto()).setHtmlDetail(null).setRelCategoryId(null).setFirstCategoryId(null);
                            relShufflingMapper.insert(relShufflingPo);
                            break;
                    }
                    /******************************************* 新增轮播图 end *******************************************/
                }
                //修改轮播图
                else {
                    /******************************************* 修改轮播图 start *******************************************/
                    //判断开始时间和结束时间都不能小于当前时间，开始时间不能大于结束时间
                    if (a.getEndTime().isBefore(a.getStartTime()) || a.getEndTime().equals(a.getStartTime())) {
                        throw new ServiceException(ResultCode.FAIL, String.format("开始时间不能大于等于结束时间"));
                    }
                    //判断是否修改开始时间和结束时间
                    LocalDateTime startTime = relShufflingMapper.selectById(a.getShufflingId()).getStartTime();
                    LocalDateTime endTime = relShufflingMapper.selectById(a.getShufflingId()).getEndTime();
                    if (!startTime.equals(a.getStartTime())){
                        //已经开始但还没结束，不能修改开始时间
                        if (startTime.isBefore(LocalDateTime.now())&&endTime.isAfter(LocalDateTime.now())){
                            throw new ServiceException(ResultCode.FAIL, String.format("存在轮播图广告已经开始,不能修改开始时间,请检查"));
                        }
                        if (a.getStartTime().isBefore(LocalDateTime.now())) {
                            throw new ServiceException(ResultCode.FAIL, String.format("开始时间需要在当前时间之后"));
                        }
                    }
                    if (!endTime.equals(a.getEndTime())) {
                        if (a.getEndTime().isBefore(LocalDateTime.now())) {
                            throw new ServiceException(ResultCode.FAIL, String.format("结束时间需要在当前时间之后"));
                        }
                    }

                    MmAdviceRelShufflingPo relShufflingPo = relShufflingMapper.selectById(a.getShufflingId());
                    //获取广告类型
                    AdviceTypeEnum adviceTypeEnum = AdviceTypeEnum.getAdviceTypeEnum(a.getAdviceType());
                    switch (adviceTypeEnum) {
                        case HTML_DETAIL:
                            relShufflingPo.setUpdateBy(user.getUsername()).setAdviceId(saveShufflingDto.getAdviceId())
                                    .setAdviceType(adviceTypeEnum).setHtmlDetail(a.getHtmlDetail()).setRelTabBrandId(null)
                                    .setBrandId(null).setStartTime(a.getStartTime()).setEndTime(a.getEndTime())
                                    .setCoverPhoto(a.getCoverPhoto()).setDetailId(null).setRelCategoryId(null).setFirstCategoryId(null);
                            relShufflingMapper.updateById(relShufflingPo);
                            break;
                        case INFORMATION:
                        case STROE:
                        case GOODS:
                            if (adviceTypeEnum.equals(AdviceTypeEnum.GOODS)) {
                                if (goodsMapper.selectById(a.getDetailId()) == null) {
                                    throw new ServiceException(ResultCode.NO_EXISTS, String.format("数据库不存在ID为[%s]的商品不存在，请检查", a.getDetailId()));
                                }
                            }else if (adviceTypeEnum.equals(AdviceTypeEnum.INFORMATION)) {
                                if (informationMapper.selectById(a.getDetailId()) == null) {
                                    throw new ServiceException(ResultCode.NO_EXISTS, String.format("数据库不存在ID为[%s]的资讯不存在，请检查", a.getDetailId()));
                                }
                            }else if (adviceTypeEnum.equals(AdviceTypeEnum.STROE)) {
                                if (storeMapper.selectById(a.getDetailId()) == null) {
                                    throw new ServiceException(ResultCode.NO_EXISTS, String.format("数据库不存在ID为[%s]的店铺不存在，请检查", a.getDetailId()));
                                }
                            }
                            relShufflingPo.setUpdateBy(user.getUsername()).setAdviceId(saveShufflingDto.getAdviceId())
                                    .setAdviceType(adviceTypeEnum).setDetailId(a.getDetailId()).setRelTabBrandId(null)
                                    .setBrandId(null).setStartTime(a.getStartTime()).setEndTime(a.getEndTime())
                                    .setCoverPhoto(a.getCoverPhoto()).setHtmlDetail(null).setRelCategoryId(null).setFirstCategoryId(null);
                            relShufflingMapper.updateById(relShufflingPo);
                            break;
                    }
                    /******************************************* 修改轮播图 end *******************************************/
                }
            });

        }
    }
}
