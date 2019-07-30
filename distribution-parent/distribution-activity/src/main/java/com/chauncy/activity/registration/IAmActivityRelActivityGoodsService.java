package com.chauncy.activity.registration;

import com.chauncy.data.domain.po.activity.registration.AmActivityRelActivityGoodsPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.supplier.activity.add.SaveRegistrationDto;
import com.chauncy.data.dto.supplier.activity.delete.CancelRegistrationDto;
import com.chauncy.data.dto.supplier.activity.select.FindActivitySkuDto;
import com.chauncy.data.dto.supplier.activity.select.SearchAssociatedGoodsDto;
import com.chauncy.data.dto.supplier.activity.select.SearchSupplierActivityDto;
import com.chauncy.data.dto.supplier.activity.select.UpdateVerifyStatusDto;
import com.chauncy.data.vo.supplier.activity.GetActivitySkuInfoVo;
import com.chauncy.data.vo.supplier.activity.SearchAssociatedGoodsVo;
import com.chauncy.data.vo.supplier.activity.SearchSupplierActivityVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 * 平台活动与商品关联表 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-26
 */
public interface IAmActivityRelActivityGoodsService extends Service<AmActivityRelActivityGoodsPo> {

    /**
     * 条件查询需要被选参与活动的商品
     *
     * @param searchAssociatedGoodsDto
     * @return
     */
    PageInfo<SearchAssociatedGoodsVo> searchAssociatedGoods(SearchAssociatedGoodsDto searchAssociatedGoodsDto);

    /**
     * 查找对应的sku信息
     * @param findActivitySkuDto
     * @return
     */
    GetActivitySkuInfoVo findActivitySku(FindActivitySkuDto findActivitySkuDto);

    /**
     * 判断商品是否符合不能参加在同一时间段内的活动、不符合活动要求
     * @param isConformDto
     * @return
     */
    Boolean isComform(FindActivitySkuDto isConformDto);

    /**
     * 保存商家端的报名活动信息
     *
     * @param saveRegistrationDto
     * @return
     */
    void saveRegistration(SaveRegistrationDto saveRegistrationDto);

    /**
     * 商家端查找参与的活动
     *
     * @param searchSupplierActivityDto
     * @return
     */
    PageInfo<SearchSupplierActivityVo> searchSupplierActivity(SearchSupplierActivityDto searchSupplierActivityDto);

    /**
     * 商家端取消活动报名
     * @param cancelRegistrationDtos
     * @return
     */
    void cancelRegistration(List<CancelRegistrationDto> cancelRegistrationDtos);

    /**
     * 修改报名的活动的状态
     *
     * @param updateVerifyStatusDto
     * @return
     */
    void updateVerifyStatus(UpdateVerifyStatusDto updateVerifyStatusDto);
}
