package com.chauncy.message.advice.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.enums.app.sort.SortFileEnum;
import com.chauncy.common.enums.app.sort.SortWayEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.ListUtil;
import com.chauncy.data.domain.po.message.advice.MmAdvicePo;
import com.chauncy.data.domain.po.message.advice.MmAdviceRelSpellGoodsPo;
import com.chauncy.data.domain.po.product.PmGoodsCategoryPo;
import com.chauncy.data.domain.po.product.PmGoodsPo;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.dto.manage.message.advice.add.SaveSpellAdviceDto;
import com.chauncy.data.dto.manage.message.advice.select.SearchSpellAdviceBindGoodsDto;
import com.chauncy.data.dto.manage.message.advice.select.SearchSpellAdviceGoodsDto;
import com.chauncy.data.mapper.message.advice.MmAdviceMapper;
import com.chauncy.data.mapper.message.advice.MmAdviceRelSpellGoodsMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.mapper.product.PmGoodsCategoryMapper;
import com.chauncy.data.mapper.product.PmGoodsMapper;
import com.chauncy.data.vo.manage.message.advice.tab.tab.SearchAdviceGoodsVo;
import com.chauncy.message.advice.IMmAdviceRelSpellGoodsService;
import com.chauncy.security.util.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * <p>
 * 今日必拼广告绑定参加拼团活动商品关联表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-10-09
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MmAdviceRelSpellGoodsServiceImpl extends AbstractService<MmAdviceRelSpellGoodsMapper, MmAdviceRelSpellGoodsPo> implements IMmAdviceRelSpellGoodsService {

    @Autowired
    private MmAdviceRelSpellGoodsMapper mapper;

    @Autowired
    private PmGoodsMapper goodsMapper;

    @Autowired
    private PmGoodsCategoryMapper goodsCategoryMapper;

    @Autowired
    private MmAdviceMapper adviceMapper;

    @Autowired
    private SecurityUtil securityUtil;

    /**
     * @param searchSpellAdviceGoodsDto
     * @return com.github.pagehelper.PageInfo<com.chauncy.data.vo.manage.message.advice.tab.tab.SearchAdviceGoodsVo>
     * @Author chauncy
     * @Date 2019-10-09 20:21
     * @Description //条件分页查询今日必拼广告需要绑定的参加拼团的商品信息
     * @Update chauncy
     **/
    @Override
    public PageInfo<SearchAdviceGoodsVo> searchSpellAdviceGoods(SearchSpellAdviceGoodsDto searchSpellAdviceGoodsDto) {

        Integer pageNo = searchSpellAdviceGoodsDto.getPageNo() == null ? defaultPageNo : searchSpellAdviceGoodsDto.getPageNo();
        Integer pageSize = searchSpellAdviceGoodsDto.getPageSize() == null ? defaultPageSize : searchSpellAdviceGoodsDto.getPageSize();

        if (null == searchSpellAdviceGoodsDto.getSortFile()) {
            //默认综合排序
            searchSpellAdviceGoodsDto.setSortFile(SortFileEnum.COMPREHENSIVE_SORT);
        }
        if (null == searchSpellAdviceGoodsDto.getSortWay()) {
            //默认降序
            searchSpellAdviceGoodsDto.setSortWay(SortWayEnum.DESC);
        }

        PageInfo<SearchAdviceGoodsVo> goodsList = PageHelper.startPage(pageNo, pageSize/*, defaultSoft*/)
                .doSelectPageInfo(() -> goodsMapper.searchSpellAdviceGoods(searchSpellAdviceGoodsDto));

        //获取三级类目名称
        AtomicReference<String> level3 = new AtomicReference<>("");
        AtomicReference<String> level2 = new AtomicReference<>("");
        AtomicReference<String> level1 = new AtomicReference<>("");
        goodsList.getList().forEach(a -> {
            PmGoodsCategoryPo goodsCategoryPo3 = goodsCategoryMapper.selectById(a.getGoodsCategoryId());
            if (goodsCategoryPo3 != null) {
                level3.set(goodsCategoryPo3.getName());
                PmGoodsCategoryPo goodsCategoryPo2 = goodsCategoryMapper.selectById(goodsCategoryPo3.getParentId());
                if (goodsCategoryPo2 != null) {
                    level2.set(goodsCategoryPo2.getName());
                    PmGoodsCategoryPo goodsCategoryPo1 = goodsCategoryMapper.selectById(goodsCategoryPo2.getParentId());
                    if (goodsCategoryPo1 != null) {
                        level1.set(goodsCategoryPo1.getName());
                    }
                    String categoryName = level1 + "/" + level2 + "/" + level3;
                    a.setCategory(categoryName);
                }
            }
        });

        return goodsList;
    }

    /**
     * @param searchSpellAdviceGoodsDto
     * @return com.github.pagehelper.PageInfo<com.chauncy.data.vo.manage.message.advice.tab.tab.SearchAdviceGoodsVo>
     * @Author chauncy
     * @Date 2019-10-09 21:45
     * @Description //条件分页查询今日必拼广告已经绑定的参加拼团的商品信息
     * @Update chauncy
     **/
    @Override
    public PageInfo<SearchAdviceGoodsVo> searchSpellAdviceBindGoods(SearchSpellAdviceBindGoodsDto searchSpellAdviceGoodsDto) {

        Integer pageNo = searchSpellAdviceGoodsDto.getPageNo() == null ? defaultPageNo : searchSpellAdviceGoodsDto.getPageNo();
        Integer pageSize = searchSpellAdviceGoodsDto.getPageSize() == null ? defaultPageSize : searchSpellAdviceGoodsDto.getPageSize();

        PageInfo<SearchAdviceGoodsVo> goodsList = PageHelper.startPage(pageNo, pageSize/*, defaultSoft*/)
                .doSelectPageInfo(() -> goodsMapper.searchSpellAdviceBindGoods(searchSpellAdviceGoodsDto));

        //获取三级类目名称
        AtomicReference<String> level3 = new AtomicReference<>("");
        AtomicReference<String> level2 = new AtomicReference<>("");
        AtomicReference<String> level1 = new AtomicReference<>("");
        goodsList.getList().forEach(a -> {
            PmGoodsCategoryPo goodsCategoryPo3 = goodsCategoryMapper.selectById(a.getGoodsCategoryId());
            if (goodsCategoryPo3 != null) {
                level3.set(goodsCategoryPo3.getName());
                PmGoodsCategoryPo goodsCategoryPo2 = goodsCategoryMapper.selectById(goodsCategoryPo3.getParentId());
                if (goodsCategoryPo2 != null) {
                    level2.set(goodsCategoryPo2.getName());
                    PmGoodsCategoryPo goodsCategoryPo1 = goodsCategoryMapper.selectById(goodsCategoryPo2.getParentId());
                    if (goodsCategoryPo1 != null) {
                        level1.set(goodsCategoryPo1.getName());
                    }
                    String categoryName = level1 + "/" + level2 + "/" + level3;
                    a.setCategory(categoryName);
                }
            }
        });


        return goodsList;
    }

    /**
     * @param saveSpellAdviceDto
     * @return void
     * @Author chauncy
     * @Date 2019-10-09 22:26
     * @Description //保存今日必拼广告信息
     * @Update chauncy
     **/
    @Override
    public void saveSpellAdvice(SaveSpellAdviceDto saveSpellAdviceDto) {

        SysUserPo user = securityUtil.getCurrUser();
        if (user == null) {
            throw new ServiceException(ResultCode.FAIL, "您不是系统用户!");
        }
        MmAdvicePo advicePo = new MmAdvicePo();

        //判断二:选择的商品是否符合条件(正在参加拼团的并且是上架的商品)
        saveSpellAdviceDto.getGoodsIds().forEach(b -> {
            PmGoodsPo goodsPo1 = goodsMapper.selectById(b);
            if (goodsPo1 == null ){
                throw new ServiceException(ResultCode.NO_EXISTS,String.format("数据库不存在id为【%s】的商品，请检查",b));
            }
            PmGoodsPo goodsPo = mapper.findValidGoods(b);
            if (goodsPo == null) {
                throw new ServiceException(ResultCode.FAIL, String.format("该商品:【%s】不处于正在参加拼团的或者是上架状态", goodsPo1.getName()));
            }
        });

        //判断三:绑定的商品不能重复
        boolean goodsIsRepeat = saveSpellAdviceDto.getGoodsIds().size() != new HashSet<Long>(saveSpellAdviceDto.getGoodsIds()).size();
        if (goodsIsRepeat) {
            List<String> repeatNames = Lists.newArrayList();
            //记录每个商品出现的个数
            Map<Long, Integer> repeatMap = new HashMap<>();

            saveSpellAdviceDto.getGoodsIds().forEach(b -> {
                Integer i = 1;
                if (repeatMap.get(b) != null) {
                    i = repeatMap.get(b) + 1;
                }
                repeatMap.put(b, i);

            });
            for (Long s : repeatMap.keySet()) {
                if (repeatMap.get(s) > 1) {
                    repeatNames.add(goodsMapper.selectById(s).getName());
                }
            }
            throw new ServiceException(ResultCode.DUPLICATION, String.format("存在重复商品：%s,请检查!", repeatNames.toString()));

        }

        //新增广告
        if (saveSpellAdviceDto.getAdviceId() == 0) {
            //判断一: 广告名称不能相同
            List<String> nameList = adviceMapper.selectList(null).stream().map(a -> a.getName()).collect(Collectors.toList());
            if (nameList.contains(saveSpellAdviceDto.getName())) {
                throw new ServiceException(ResultCode.FAIL, String.format("广告名称【%s】已经存在,请检查！", saveSpellAdviceDto.getName()));
            }

            BeanUtils.copyProperties(saveSpellAdviceDto, advicePo);
            advicePo.setId(null).setCreateBy(user.getId());
            adviceMapper.insert(advicePo);

            //保存广告绑定的商品到mm_advice_rel_spell_goods
            MmAdvicePo finalAdvicePo1 = advicePo;
            saveSpellAdviceDto.getGoodsIds().forEach(a -> {
                MmAdviceRelSpellGoodsPo relSpellGoodsPo = new MmAdviceRelSpellGoodsPo();
                //获取商品的类目信息
                relSpellGoodsPo = goodsCategoryMapper.getCategoryByGoodsId(a);
                relSpellGoodsPo.setAdviceId(finalAdvicePo1.getId()).setCreateBy(user.getId()).setGoodsId(a)
                .setId(null);

                mapper.insert(relSpellGoodsPo);
            });

        }
        //编辑广告
        else {

            //广告不能重复
            advicePo = adviceMapper.selectById(saveSpellAdviceDto.getAdviceId());
            if (advicePo == null){
                throw new ServiceException(ResultCode.NO_EXISTS,String.format("不存在ID为【%s】的广告，请检查",saveSpellAdviceDto.getAdviceId()));
            }
            else {
                //获取今日必拼广告位置的除了该广告的所有广告名称
                MmAdvicePo finalAdvicePo = advicePo;
                List<String> nameList = adviceMapper.selectList(null).stream().filter(name -> !name.getName().equals(finalAdvicePo.getName())).map(a -> a.getName()).collect(Collectors.toList());
                if (!ListUtil.isListNullAndEmpty(nameList) && nameList.contains(saveSpellAdviceDto.getName())) {
                    throw new ServiceException(ResultCode.FAIL, String.format("广告名称【%s】已经存在,请检查！", saveSpellAdviceDto.getName()));
                }

                //更新广告信息
                MmAdvicePo mmAdvicePo = adviceMapper.selectById(saveSpellAdviceDto.getAdviceId());
                BeanUtils.copyProperties(saveSpellAdviceDto, mmAdvicePo);
                mmAdvicePo.setUpdateBy(user.getId());
                adviceMapper.updateById(mmAdvicePo);

                //对绑定的商品进行处理（增删）
                //获取该广告绑定的商品
                List<Long> allGoodsIds = mapper.selectList(new QueryWrapper<MmAdviceRelSpellGoodsPo>().lambda().and(obj->obj
                        .eq(MmAdviceRelSpellGoodsPo::getAdviceId,saveSpellAdviceDto.getAdviceId()))).stream().map(a->a.getGoodsId()).collect(Collectors.toList());
                //获取前端传的值
                List<Long> remainGoodsIds = saveSpellAdviceDto.getGoodsIds();

                //该广告绑定的商品不为空
                if (!ListUtil.isListNullAndEmpty(allGoodsIds)){
                  List<Long>  delIds = Lists.newArrayList();
                  //全部删除
                  if (ListUtil.isListNullAndEmpty(remainGoodsIds)){
                      delIds = allGoodsIds;
                      allGoodsIds.forEach(a->{
                          mapper.delete(new QueryWrapper<MmAdviceRelSpellGoodsPo>().lambda().and(obj->obj
                                  .eq(MmAdviceRelSpellGoodsPo::getAdviceId,saveSpellAdviceDto.getAdviceId())
                                  .eq(MmAdviceRelSpellGoodsPo::getGoodsId,a)));
                      });
                  }
                  //部分删除，部分新增
                  else{
                      delIds = allGoodsIds.stream().filter(del->!remainGoodsIds.contains(del)).collect(Collectors.toList());
                      if (!ListUtil.isListNullAndEmpty(delIds)){
                          delIds.forEach(a->{
                              mapper.delete(new QueryWrapper<MmAdviceRelSpellGoodsPo>().lambda().and(obj->obj
                                      .eq(MmAdviceRelSpellGoodsPo::getAdviceId,saveSpellAdviceDto.getAdviceId())
                                      .eq(MmAdviceRelSpellGoodsPo::getGoodsId,a)));
                          });
                      }
                      //获取新增的绑定商品并保存到数据库中
                      List<Long> saveIds = remainGoodsIds.stream().filter(save->!allGoodsIds.contains(save)).collect(Collectors.toList());
                      if (!ListUtil.isListNullAndEmpty(saveIds)){
                          saveIds.forEach(a->{
                              MmAdviceRelSpellGoodsPo relSpellGoodsPo = new MmAdviceRelSpellGoodsPo();
                              //获取商品的类目信息
                              relSpellGoodsPo = goodsCategoryMapper.getCategoryByGoodsId(a);
                              relSpellGoodsPo.setAdviceId(saveSpellAdviceDto.getAdviceId()).setCreateBy(user.getId()).setGoodsId(a)
                                      .setId(null);

                              mapper.insert(relSpellGoodsPo);
                          });
                      }
                  }
                }

                //该广告绑定的商品为空
                else {
                    if (!ListUtil.isListNullAndEmpty(remainGoodsIds)){
                        remainGoodsIds.forEach(a->{
                            MmAdviceRelSpellGoodsPo relSpellGoodsPo = new MmAdviceRelSpellGoodsPo();
                            //获取商品的类目信息
                            relSpellGoodsPo = goodsCategoryMapper.getCategoryByGoodsId(a);
                            relSpellGoodsPo.setAdviceId(saveSpellAdviceDto.getAdviceId()).setCreateBy(user.getId()).setGoodsId(a)
                                    .setId(null);

                            mapper.insert(relSpellGoodsPo);

                        });
                    }
                }
            }

        }
    }

}
