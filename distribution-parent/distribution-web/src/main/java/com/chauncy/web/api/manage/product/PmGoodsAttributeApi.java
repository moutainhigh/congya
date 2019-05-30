package com.chauncy.web.api.manage.product;


import com.chauncy.common.enums.ResultCode;
import com.chauncy.common.enums.goods.GoodsAttribute;
import com.chauncy.data.domain.po.product.PmGoodsAttributePo;
import com.chauncy.data.domain.po.product.PmGoodsAttributeValuePo;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.product.service.IPmGoodsAttributeService;
import com.chauncy.product.service.IPmGoodsAttributeValueService;
import com.chauncy.security.util.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 商品属性页面控制器
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
@Api(description = "商品属性页面控制器")
@RestController
@RequestMapping("/pm-goods-attribute-po")
@Slf4j
public class PmGoodsAttributeApi {

    @Autowired
    private IPmGoodsAttributeService goodsAttributeService;

    @Autowired
    private IPmGoodsAttributeValueService goodsAttributeValueService;

    @Autowired
    private SecurityUtil securityUtil;

    /**
     * 保存属性
     *
     * @param goodsAttributePo
     */
    @PostMapping("/saveAttribute")
    @ApiOperation(value = "保存商品属性（品牌、规格、服务等）")
    public JsonViewData saveAttribute(@ModelAttribute PmGoodsAttributePo goodsAttributePo){
        LocalDateTime date = LocalDateTime.now();
        //获取当前用户
        String user = securityUtil.getCurrUser().getUsername();
        goodsAttributePo.setCreateBy(user);
        goodsAttributePo.setCreateTime(date);

        //判断必要字段
        if (goodsAttributePo.getType() == null || goodsAttributePo.getType() == ' ' || goodsAttributePo.getName() == null || goodsAttributePo.getName().equals(' ')) {
            return new JsonViewData(ResultCode.FAIL, "缺少必需字段name或type");
        }

        //判断不同类型对应的属性名称是否已经存在
        if (goodsAttributeService.findByTypeAndName(goodsAttributePo.getType(), goodsAttributePo.getName()) != null) {
            return new JsonViewData(ResultCode.FAIL, "该属性名称已存在");
        }

        //先保存基本信息，在保存对应的值
        goodsAttributeService.save(goodsAttributePo);
        System.out.println(goodsAttributePo.getId());

        //处理属性类型为规格类型的
        if (GoodsAttribute.STANDARD.getId() == goodsAttributePo.getType()) {
            if (goodsAttributePo.getValues() !=null ) {
                for (String value : goodsAttributePo.getValues()) {
                    PmGoodsAttributeValuePo po = new PmGoodsAttributeValuePo();
                    po.setProductAttributeId(goodsAttributePo.getId());
                    po.setValue(value);
                    po.setCreateBy(user);
                    po.setCreateTime(date);
                    goodsAttributeValueService.save(po);
                }
            }
        }
        return new JsonViewData(ResultCode.SUCCESS, "保存成功", goodsAttributePo);
    }

    /**
     * 根据id删除属性
     *
     * @param ids
     */
    @ApiOperation(value = "删除属性", notes = "根据id批量删除")
    @PostMapping("/deleteAttributeByIds")
    public void deleteAttributeByIds(@ApiParam(required = true, name = "ids", value = "id集合")
                                     @RequestParam(value = "ids") Integer[] ids) {
        List<Integer> idList = new ArrayList<>();
//        idList = JSONUtils.toList(ids, Integer.class);
        idList = Arrays.asList(ids);
        goodsAttributeService.removeByIds(idList);
    }
}
