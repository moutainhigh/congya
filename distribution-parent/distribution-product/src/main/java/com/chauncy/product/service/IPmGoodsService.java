package com.chauncy.product.service;

import com.chauncy.data.domain.po.product.PmGoodsPo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chauncy.data.dto.product.GoodBaseDto;
import com.chauncy.data.vo.JsonViewData;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * <p>
 * 商品信息 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
public interface IPmGoodsService extends IService<PmGoodsPo> {

    JsonViewData addBase(@ModelAttribute GoodBaseDto goodBaseDto);
}
