package com.chauncy.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.enums.common.VerifyStatusEnum;
import com.chauncy.common.enums.goods.GoodsShipTemplateEnum;
import com.chauncy.common.enums.ship.ShipCalculateWayEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.ListUtil;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.product.PmGoodsPo;
import com.chauncy.data.domain.po.product.PmMoneyShippingPo;
import com.chauncy.data.domain.po.product.PmNumberShippingPo;
import com.chauncy.data.domain.po.product.PmShippingTemplatePo;
import com.chauncy.data.dto.manage.ship.add.AddShipTemplateDto;
import com.chauncy.data.dto.manage.ship.delete.DelListDto;
import com.chauncy.data.dto.manage.ship.select.SearchPlatTempDto;
import com.chauncy.data.dto.manage.ship.update.EnableTemplateDto;
import com.chauncy.data.dto.manage.ship.update.VerifyTemplateDto;
import com.chauncy.data.mapper.product.PmGoodsMapper;
import com.chauncy.data.mapper.product.PmMoneyShippingMapper;
import com.chauncy.data.mapper.product.PmNumberShippingMapper;
import com.chauncy.data.mapper.product.PmShippingTemplateMapper;
import com.chauncy.data.vo.manage.ship.PlatTemplateVo;
import com.chauncy.product.service.IPmMoneyShippingService;
import com.chauncy.product.service.IPmNumberShippingService;
import com.chauncy.product.service.IPmShippingTemplateService;
import com.chauncy.security.util.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 运费模版说明表。平台运费模版+商家运费模版 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
@Service
@Slf4j
public class PmShippingTemplateServiceImpl extends AbstractService<PmShippingTemplateMapper, PmShippingTemplatePo> implements IPmShippingTemplateService {

    @Autowired
    private PmShippingTemplateMapper shippingTemplateMapper;

    @Autowired
    private PmMoneyShippingMapper moneyShippingMapper;

    @Autowired
    private PmNumberShippingMapper numberShippingMapper;

    @Autowired
    private IPmMoneyShippingService moneyShippingService;

    @Autowired
    private IPmNumberShippingService numberShippingService;

    @Autowired
    private IPmShippingTemplateService shippingTemplateService;

    @Autowired
    private PmGoodsMapper goodsMapper;

    @Autowired
    private SecurityUtil securityUtil;

    private static int defaultPageSize = 10;

    private static int defaultPageNo = 1;

    private static String defaultSoft = "sort desc";

    /**
     * 添加按金额计算运费模版
     *
     * @param addShipTemplateDto
     */
    @Override
    public void addShipTemplate(AddShipTemplateDto addShipTemplateDto) {

        //获取当前用户平台用户还是商家用户
        Boolean isPlat = securityUtil.getCurrUser().getStoreId() == null;
        //先保存按金额计算模版
        PmShippingTemplatePo shippingTemplatePo = new PmShippingTemplatePo();
        BeanUtils.copyProperties(addShipTemplateDto, shippingTemplatePo);
        if (isPlat) {
            shippingTemplatePo.setType(GoodsShipTemplateEnum.PLATFORM_SHIP.getId());
            shippingTemplatePo.setEnable(true);
        } else {
            shippingTemplatePo.setType(GoodsShipTemplateEnum.MERCHANT_SHIP.getId());
            shippingTemplatePo.setVerifyStatus(VerifyStatusEnum.UNCHECKED.getId());
        }

        Map<String, Object> map = new HashMap<>();
        map.put("name", shippingTemplatePo.getName());
        map.put("type", shippingTemplatePo.getType());
        List<String> names = shippingTemplateMapper.selectByMap(map).stream().map(a -> a.getName()).collect(Collectors.toList());

        //TODO 新增操作
        if (addShipTemplateDto.getId() == 0) {
            if (isPlat) {
                shippingTemplatePo.setVerifyStatus(VerifyStatusEnum.CHECKED.getId());
            } else {
                shippingTemplatePo.setStoreId(securityUtil.getCurrUser().getStoreId());
            }
            //去重
            if (names.contains(shippingTemplatePo.getName())) {
                throw new ServiceException(ResultCode.DUPLICATION, "操作失败,模版名称已存在");
            }
            shippingTemplatePo.setId(null);
            shippingTemplatePo.setCreateBy(securityUtil.getCurrUser().getUsername());
            shippingTemplateMapper.insert(shippingTemplatePo);

            //保存指定地区按金额计算运费方式
            if (addShipTemplateDto.getCalculateWay() == ShipCalculateWayEnum.AMOUNT.getId() && addShipTemplateDto.getAmountDtos() != null && addShipTemplateDto.getAmountDtos().size() != 0) {
                List<Long> destinationList = addShipTemplateDto.getAmountDtos().stream().map(a -> a.getDestinationId()).collect(Collectors.toList());
                // 通过去重之后的HashSet长度来判断原list是否包含重复元素
                boolean isRepeat = destinationList.size() != new HashSet<Long>(destinationList).size();
                if (isRepeat) {
                    throw new ServiceException(ResultCode.DUPLICATION, "添加失败,目的地重复");
                }
                List<PmMoneyShippingPo> moneyShippingPoList = Lists.newArrayList();
                addShipTemplateDto.getAmountDtos().forEach(a -> {
                    PmMoneyShippingPo moneyShippingPo = new PmMoneyShippingPo();
                    BeanUtils.copyProperties(a, moneyShippingPo);
                    moneyShippingPo.setShippingId(shippingTemplatePo.getId());
                    moneyShippingPo.setId(null);
                    moneyShippingPo.setCreateBy(securityUtil.getCurrUser().getUsername());
                    moneyShippingPoList.add(moneyShippingPo);
                });
                moneyShippingService.saveBatch(moneyShippingPoList);
            }
            //保存指定地区按件数计算运费方式
            else if (addShipTemplateDto.getCalculateWay() == ShipCalculateWayEnum.NUMBER.getId() && addShipTemplateDto.getNumDtos() != null && addShipTemplateDto.getNumDtos().size() != 0) {

                List<PmNumberShippingPo> numberShippingPoList = Lists.newArrayList();
                List<Long> destinationList = addShipTemplateDto.getNumDtos().stream().map(a -> a.getDestinationId()).collect(Collectors.toList());
                // 通过去重之后的HashSet长度来判断原list是否包含重复元素
                boolean isRepeat = destinationList.size() != new HashSet<Long>(destinationList).size();
                if (isRepeat) {
                    throw new ServiceException(ResultCode.DUPLICATION, "添加失败,目的地重复");
                }
                addShipTemplateDto.getNumDtos().forEach(b -> {
                    PmNumberShippingPo numberShippingPo = new PmNumberShippingPo();
                    BeanUtils.copyProperties(b, numberShippingPo);
                    numberShippingPo.setShippingId(shippingTemplatePo.getId()).setId(null).setCreateBy(securityUtil.getCurrUser().getUsername());
                    numberShippingPoList.add(numberShippingPo);
                });
                numberShippingService.saveBatch(numberShippingPoList);
            }
        }
        //TODO 进行修改操作
        else {
            if (!isPlat && (addShipTemplateDto.getVerifyStatus()==VerifyStatusEnum.WAIT_CONFIRM.getId() ||
                    addShipTemplateDto.getVerifyStatus()==VerifyStatusEnum.CHECKED.getId() )){
                throw new ServiceException(ResultCode.FAIL,"待审核或者审核通过状态不允许修改");
            }
            /**先处理模版,判断名称不能重复、不能修改计算方式**/
            PmShippingTemplatePo shippingTemplate = shippingTemplateMapper.selectById(addShipTemplateDto.getId());
            if (shippingTemplate == null) {
                throw new ServiceException(ResultCode.NO_EXISTS, "该改运费模版不存在", addShipTemplateDto.getId());
            }
            //模版名称不能重复
            if (names != null && names.size() != 0 && !shippingTemplate.getName().equals(addShipTemplateDto.getName())) {
                throw new ServiceException(ResultCode.DUPLICATION, "修改失败，改模版名称已存在", addShipTemplateDto.getName());
            }
            //不能更换计算方式
            if (addShipTemplateDto.getCalculateWay() != shippingTemplateMapper.selectById(addShipTemplateDto.getId()).getCalculateWay()) {
                throw new ServiceException(ResultCode.FAIL, "修改失败,不能更换计算方式");
            }
            //更新模版信息
            shippingTemplatePo.setUpdateBy(securityUtil.getCurrUser().getUsername());
            shippingTemplateMapper.updateById(shippingTemplatePo);

            /**处理按金额计算运费*/

            if (addShipTemplateDto.getCalculateWay() == ShipCalculateWayEnum.AMOUNT.getId()) {
                List<PmMoneyShippingPo> updateAmounts = Lists.newArrayList();
                List<PmMoneyShippingPo> addAmounts = Lists.newArrayList();
                List<Long> destinationList = addShipTemplateDto.getAmountDtos().stream().map(a -> a.getDestinationId()).collect(Collectors.toList());
                // 通过去重之后的HashSet长度来判断原list是否包含重复元素
                boolean isRepeat = destinationList.size() != new HashSet<Long>(destinationList).size();
                if (isRepeat) {
                    throw new ServiceException(ResultCode.DUPLICATION, "修改失败,填写的目的地重复");
                }
                Map<String, Object> map1 = new HashMap<>();
                map1.put("shipping_id", addShipTemplateDto.getId());
                List<Long> address = moneyShippingMapper.selectByMap(map1).stream().map(w -> w.getDestinationId()).collect(Collectors.toList());
                addShipTemplateDto.getAmountDtos().forEach(q -> {
                    //新增操作
                    if (q.getId() == 0) {
                        //判断是否与数据库的数据重复
                        if (address.contains(q.getDestinationId())) {
                            throw new ServiceException(ResultCode.DUPLICATION, "修改失败,目的地在数据库已存在");
                        }
                        PmMoneyShippingPo moneyShippingPo = new PmMoneyShippingPo();
                        BeanUtils.copyProperties(q, moneyShippingPo);
                        moneyShippingPo.setUpdateBy(securityUtil.getCurrUser().getUsername());
                        moneyShippingPo.setCreateBy(securityUtil.getCurrUser().getUsername());
                        moneyShippingPo.setShippingId(shippingTemplatePo.getId());
                        moneyShippingPo.setId(null);
                        addAmounts.add(moneyShippingPo);
                    } else {
                        //判断是否与数据库的数据重复
                        if (address.contains(q.getDestinationId()) && !q.getDestinationId().equals(moneyShippingMapper.selectById(q.getId()).getDestinationId())) {
                            throw new ServiceException(ResultCode.DUPLICATION, "修改失败,目的地在数据库已存在");
                        }
                        PmMoneyShippingPo moneyShippingPo = new PmMoneyShippingPo();
                        BeanUtils.copyProperties(q, moneyShippingPo);
                        moneyShippingPo.setUpdateBy(securityUtil.getCurrUser().getUsername());
                        updateAmounts.add(moneyShippingPo);
                    }
                });
                //保存和修改按金额计算运费
                if (addAmounts != null && addAmounts.size() != 0) {
                    moneyShippingService.saveBatch(addAmounts);
                }
                if (updateAmounts != null && updateAmounts.size() != 0) {
                    moneyShippingService.updateBatchById(updateAmounts);
                }
            }
            /**处理按件数计算运费*/
            if (addShipTemplateDto.getCalculateWay() == ShipCalculateWayEnum.NUMBER.getId()) {
                List<PmNumberShippingPo> addNumShip = Lists.newArrayList();
                List<PmNumberShippingPo> updateNumShip = Lists.newArrayList();
                Map<String, Object> map1 = new HashMap<>();
                map1.put("shipping_id", addShipTemplateDto.getId());
                List<Long> addressList = numberShippingMapper.selectByMap(map1).stream().map(w -> w.getDestinationId()).collect(Collectors.toList());
                List<Long> destinations = addShipTemplateDto.getNumDtos().stream().map(a -> a.getDestinationId()).collect(Collectors.toList());
                // 通过去重之后的HashSet长度来判断原list是否包含重复元素
                boolean isRepeat = destinations.size() != new HashSet<Long>(destinations).size();
                if (isRepeat) {
                    throw new ServiceException(ResultCode.DUPLICATION, "修改失败,填写的目的地重复");
                }
                addShipTemplateDto.getNumDtos().forEach(y -> {

                    //新增按件数计算运费操作
                    if (y.getId() == 0) {
                        //判断是否与数据库的数据重复
                        if (addressList.contains(y.getDestinationId())) {
                            throw new ServiceException(ResultCode.DUPLICATION, "修改失败,目的地在数据库已存在");
                        }
                        PmNumberShippingPo numberShippingPo = new PmNumberShippingPo();
                        BeanUtils.copyProperties(y, numberShippingPo);
                        numberShippingPo.setUpdateBy(securityUtil.getCurrUser().getUsername());
                        numberShippingPo.setCreateBy(securityUtil.getCurrUser().getUsername());
                        numberShippingPo.setId(null);
                        numberShippingPo.setShippingId(shippingTemplatePo.getId());
                        addNumShip.add(numberShippingPo);
                    } else {
                        //判断是否与数据库的数据重复
                        if (addressList.contains(y.getDestinationId()) && !y.getDestinationId().equals(numberShippingMapper.selectById(y.getId()).getDestinationId())) {
                            throw new ServiceException(ResultCode.DUPLICATION, "修改失败,目的地在数据库已存在");
                        }
                        PmNumberShippingPo numberShippingPo = new PmNumberShippingPo();
                        BeanUtils.copyProperties(y, numberShippingPo);
                        numberShippingPo.setUpdateBy(securityUtil.getCurrUser().getUsername());
                        numberShippingPo.setUpdateTime(LocalDateTime.now());
                        updateNumShip.add(numberShippingPo);
                    }
                });
                //保存和修改按金额计算运费
                if (addNumShip != null && addNumShip.size() != 0) {
                    numberShippingService.saveBatch(addNumShip);
                }
                if (updateNumShip != null && updateNumShip.size() != 0) {
                    numberShippingService.updateBatchById(updateNumShip);
                }
            }
        }
    }

    /**
     * 批量删除计算运费列表
     *
     * @param delListDto
     * @return
     */
    @Override
    public void delByIds( DelListDto delListDto) {

        if (delListDto.getCalculateType()==ShipCalculateWayEnum.AMOUNT.getId()){
            Arrays.asList(delListDto.getIds()).forEach(a->{
                if (moneyShippingMapper.selectById(a)==null){
                    throw new ServiceException(ResultCode.FAIL,"操作失败,"+a+"不存在");
                }
            });
            moneyShippingMapper.deleteBatchIds(Arrays.asList(delListDto.getIds()));
        }

        else if (delListDto.getCalculateType()==ShipCalculateWayEnum.NUMBER.getId()) {
            Arrays.asList(delListDto.getIds()).forEach(a->{
                if (numberShippingMapper.selectById(a)==null){
                    throw new ServiceException(ResultCode.FAIL,"操作失败,"+a+"不存在");
                }
            });
            numberShippingMapper.deleteBatchIds(Arrays.asList(delListDto.getIds()));
        }

    }

    /**
     * 批量删除运费模版
     *
     * @param templateIds
     * @return
     */
    @Override
    public void delTemplateByIds(Long[] templateIds) {
        //先删除关联的运费列表
        Arrays.asList(templateIds).forEach(a->{
            //判断该模版是否已被应用
            List<PmGoodsPo> goodsPos = goodsMapper.selectList(new QueryWrapper<PmGoodsPo>().eq("shipping_template_id",a));
            if (!ListUtil.isListNullAndEmpty(goodsPos)){
                throw new ServiceException(ResultCode.FAIL,String.format("该模版:[%s]已被商品引用,不能删除!",shippingTemplateMapper.selectById(a).getName()));
            }
            if (shippingTemplateMapper.selectById(a)==null){
                throw new ServiceException(ResultCode.FAIL,"操作失败,"+a+"不存在");
            }
            Map<String, Object> map = new HashMap<>();
            map.put("shipping_id", a);
            if (shippingTemplateMapper.selectById(a).getCalculateWay()==ShipCalculateWayEnum.NUMBER.getId()) {
                List<Long> ids = numberShippingMapper.selectByMap(map).stream().map(b->b.getId()).collect(Collectors.toList());
                if (ids !=null && ids.size()!=0){
                    numberShippingMapper.deleteBatchIds(ids);
                }
            }
            else if (shippingTemplateMapper.selectById(a).getCalculateWay()==ShipCalculateWayEnum.AMOUNT.getId()) {
                List<Long> ids =moneyShippingMapper.selectByMap(map).stream().map(b->b.getId()).collect(Collectors.toList());
                if (ids !=null && ids.size()!=0){
                    moneyShippingMapper.deleteBatchIds(ids);
                }
            }
            });
        shippingTemplateMapper.deleteBatchIds(Arrays.asList(templateIds));
    }

    /**
     * 条件查询平台运费模版字段
     *
     * @param searchPlatTempDto
     * @return
     */
    @Override
    public PageInfo<PlatTemplateVo> searchPlatTempByConditions(SearchPlatTempDto searchPlatTempDto) {

        Integer pageNo = searchPlatTempDto.getPageNo() == null ? defaultPageNo : searchPlatTempDto.getPageNo();
        Integer pageSize = searchPlatTempDto.getPageSize() == null ? defaultPageSize : searchPlatTempDto.getPageSize();
        PageInfo<PlatTemplateVo> platTemplateVos = new PageInfo<>();
        platTemplateVos = PageHelper.startPage(pageNo, pageSize/*, defaultSoft*/)
                .doSelectPageInfo(() -> shippingTemplateMapper.searchPlatTempByConditions(searchPlatTempDto));

        return platTemplateVos;
    }

    /**
     * 批量修改模版的审核状态
     *
     * @param verifyTemplateDto
     * @return
     */
    @Override
    public void verifyTemplate(VerifyTemplateDto verifyTemplateDto) {
        //处理提交审核
        if (verifyTemplateDto.getVerifyStatus() == VerifyStatusEnum.WAIT_CONFIRM.getId()) {
            List<PmShippingTemplatePo> shippingTemplatePos = Lists.newArrayList();
            Arrays.asList(verifyTemplateDto.getIds()).forEach(a -> {
                if (shippingTemplateMapper.selectById(a).getVerifyStatus() != VerifyStatusEnum.UNCHECKED.getId()) {
                    throw new ServiceException(ResultCode.FAIL, "操作失败，模版状态不是未审核状态",a);
                }
                PmShippingTemplatePo shippingTemplatePo = new PmShippingTemplatePo();
                shippingTemplatePo.setId(a);
                shippingTemplatePo.setVerifyStatus(verifyTemplateDto.getVerifyStatus());
                shippingTemplatePos.add(shippingTemplatePo);
            });
            shippingTemplateService.updateBatchById(shippingTemplatePos);
        }

        //处理审核通过
        else if (verifyTemplateDto.getVerifyStatus() == VerifyStatusEnum.CHECKED.getId()) {
            List<PmShippingTemplatePo> shippingTemplatePos = Lists.newArrayList();
            Arrays.asList(verifyTemplateDto.getIds()).forEach(a -> {
                if (shippingTemplateMapper.selectById(a).getVerifyStatus() != VerifyStatusEnum.WAIT_CONFIRM.getId()) {
                    throw new ServiceException(ResultCode.FAIL, "操作失败，模版状态不是待审核状态",a);
                }
                PmShippingTemplatePo shippingTemplatePo = new PmShippingTemplatePo();
                shippingTemplatePo.setId(a);
                shippingTemplatePo.setVerifyStatus(verifyTemplateDto.getVerifyStatus());
                shippingTemplatePos.add(shippingTemplatePo);
            });
            shippingTemplateService.updateBatchById(shippingTemplatePos);
        }
         //处理审核不通过
         else if (verifyTemplateDto.getVerifyStatus() == VerifyStatusEnum.NOT_APPROVED.getId()) {
                List<PmShippingTemplatePo> shippingTemplatePos = Lists.newArrayList();
                Arrays.asList(verifyTemplateDto.getIds()).forEach(a -> {
                    if (shippingTemplateMapper.selectById(a).getVerifyStatus() != VerifyStatusEnum.WAIT_CONFIRM.getId()) {
                        throw new ServiceException(ResultCode.FAIL, "审核失败，模版状态不是待审核状态",a);
                    }
                    PmShippingTemplatePo shippingTemplatePo = new PmShippingTemplatePo();
                    shippingTemplatePo.setId(a);
                    shippingTemplatePo.setVerifyStatus(verifyTemplateDto.getVerifyStatus());
                    shippingTemplatePo.setContent(verifyTemplateDto.getContent());
                    shippingTemplatePos.add(shippingTemplatePo);
                });
                shippingTemplateService.updateBatchById(shippingTemplatePos);
            }
    }

    /**
     * 批量启用或禁用模版
     *
     * @param enableTemplateDto
     * @return
     */
    @Override
    public void enableTemplate(EnableTemplateDto enableTemplateDto) {

        List<PmShippingTemplatePo> shippingTemplatePos = Lists.newArrayList();
        Arrays.asList(enableTemplateDto.getIds()).forEach(a->{
            //模版状态在审核通过状态下才能进行启用或禁用
            if (shippingTemplateMapper.selectById(a).getVerifyStatus()!=VerifyStatusEnum.CHECKED.getId()){
                throw new ServiceException(ResultCode.FAIL,"操作失败,含有非审核通过的模版,请重新选择");
            }
            PmShippingTemplatePo shippingTemplatePo = new PmShippingTemplatePo();
            shippingTemplatePo.setEnable(enableTemplateDto.getEnable());
            shippingTemplatePo.setId(a);
            shippingTemplatePos.add(shippingTemplatePo);
        });
        shippingTemplateService.updateBatchById(shippingTemplatePos);
    }

}
