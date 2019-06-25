package com.chauncy.product.service.impl;

import com.chauncy.common.enums.goods.GoodsShipTemplateEnum;
import com.chauncy.common.enums.goods.GoodsVerifyStatusEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.product.PmMoneyShippingPo;
import com.chauncy.data.domain.po.product.PmShippingTemplatePo;
import com.chauncy.data.dto.manage.ship.add.AddAmountTemplateDto;
import com.chauncy.data.dto.manage.ship.select.SearchPlatTempDto;
import com.chauncy.data.mapper.product.PmMoneyShippingMapper;
import com.chauncy.data.mapper.product.PmNumberShippingMapper;
import com.chauncy.data.mapper.product.PmShippingTemplateMapper;
import com.chauncy.data.vo.manage.ship.PlatTemplateVo;
import com.chauncy.data.vo.supplier.PmGoodsVo;
import com.chauncy.product.service.IPmMoneyShippingService;
import com.chauncy.product.service.IPmShippingTemplateService;
import com.chauncy.security.util.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private SecurityUtil securityUtil;

    private static int defaultPageSize = 10;

    private static int defaultPageNo = 1;

    private static String defaultSoft = "sort desc";

    /**
     * 添加按金额计算运费模版
     *
     * @param addAmountTemplateDto
     */
    @Override
    public void addShipTemplate(AddAmountTemplateDto addAmountTemplateDto) {

        //获取当前用户平台用户还是商家用户
        Boolean isPlat = securityUtil.getCurrUser().getStoreId()==null;
        //先保存按金额计算模版
        PmShippingTemplatePo shippingTemplatePo = new PmShippingTemplatePo();
        BeanUtils.copyProperties(addAmountTemplateDto,shippingTemplatePo);
        shippingTemplatePo.setId(null);
        if (isPlat){
            shippingTemplatePo.setType(GoodsShipTemplateEnum.PLATFORM_SHIP.getId());
        }else{
            shippingTemplatePo.setType(GoodsShipTemplateEnum.MERCHANT_SHIP.getId());
            shippingTemplatePo.setStoreId(securityUtil.getCurrUser().getStoreId());
            shippingTemplatePo.setVerifyStatus(GoodsVerifyStatusEnum.UNCHECKED.getId());
        }
        shippingTemplatePo.setCreateBy(securityUtil.getCurrUser().getUsername());
        //去重
        Map<String,Object> map = new HashMap<>();
        map.put("name",shippingTemplatePo.getName());
        map.put("type",shippingTemplatePo.getType());
        List<String> name = shippingTemplateMapper.selectByMap(map).stream().map(a->a.getName()).collect(Collectors.toList());
        if (name.contains(shippingTemplatePo.getName())){
            throw new ServiceException(ResultCode.DUPLICATION, "添加失败,模版名称已存在");
        }
        shippingTemplateMapper.insert(shippingTemplatePo);
        //再保存指定地区按金额计算运费方式
        List<Long> destinationList = addAmountTemplateDto.getAmountDtos().stream().map(a->a.getDestinationId()).collect(Collectors.toList());
        // 通过去重之后的HashSet长度来判断原list是否包含重复元素
        boolean isRepeat = destinationList.size() != new HashSet<Long>(destinationList).size();
        if (isRepeat) {
            throw new ServiceException(ResultCode.DUPLICATION, "添加失败,目的地重复");
        }
        List<PmMoneyShippingPo> moneyShippingPoList = Lists.newArrayList();
        addAmountTemplateDto.getAmountDtos().forEach(a->{
            PmMoneyShippingPo moneyShippingPo = new PmMoneyShippingPo();
            BeanUtils.copyProperties(a,moneyShippingPo);
            moneyShippingPo.setShippingId(shippingTemplatePo.getId());
            moneyShippingPo.setId(null);
            moneyShippingPo.setCreateBy(securityUtil.getCurrUser().getUsername());
            moneyShippingPoList.add(moneyShippingPo);
        });
        moneyShippingService.saveBatch(moneyShippingPoList);
    }

    /**
     * 批量删除按金额计算运费列表
     *
     * @param amountIds
     * @return
     */
    @Override
    public void delAmountByIds(Long[] amountIds) {
        moneyShippingMapper.deleteBatchIds(Arrays.asList(amountIds));

    }

    /**
     * 批量删除运费模版
     *
     * @param templateIds
     * @return
     */
    @Override
    public void delTemplateByIds(Long[] templateIds) {
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
//        if ()
        platTemplateVos = PageHelper.startPage(pageNo, pageSize/*, defaultSoft*/)
                .doSelectPageInfo(() -> shippingTemplateMapper.searchPlatTempByConditions(searchPlatTempDto));

        return platTemplateVos;
    }

}
