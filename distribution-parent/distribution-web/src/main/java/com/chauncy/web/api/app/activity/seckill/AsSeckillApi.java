package com.chauncy.web.api.app.activity.seckill;

import com.chauncy.activity.seckill.IAmSeckillService;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.app.product.SearchSeckillGoodsDto;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.app.activity.seckill.SeckillTimeQuantumVo;
import com.chauncy.data.vo.app.goods.SeckillGoodsVo;
import com.chauncy.web.base.BaseApi;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author yeJH
 * @since 2019/10/8 10:26
 */
@Api(tags = "APP_活动_秒杀")
@RestController
@RequestMapping("/app/activity/seckill")
@Slf4j
public class AsSeckillApi extends BaseApi {

    @Autowired
    private IAmSeckillService amSeckillService;

    /**
     * @Author yeJH
     * @Date 2019/10/8 11:06
     * @Description 获取秒杀时间段
     *
     * @Update yeJH
     *
     * @param
     * @return com.chauncy.data.vo.JsonViewData<java.util.List<com.chauncy.data.vo.app.activity.seckill.SeckillTimeQuantumVo>>
     **/
    @PostMapping("/getSeckillTimeQuantum")
    @ApiOperation(value = "获取秒杀时间段")
    public JsonViewData<List<SeckillTimeQuantumVo>> getSeckillTimeQuantum(){

        return new JsonViewData(ResultCode.SUCCESS,"查找成功",
                amSeckillService.getSeckillTimeQuantum());
    }

    /**
     * @Author yeJH
     * @Date 2019/10/8 20:56
     * @Description 获取秒杀活动商品列表
     *
     * @Update yeJH
     *
     * @param  searchSeckillGoodsDto
     * @return com.chauncy.data.vo.JsonViewData<com.github.pagehelper.PageInfo<com.chauncy.data.vo.app.goods.SeckillGoodsVo>>
     **/
    @PostMapping("/searchActivityGoodsList")
    @ApiOperation(value = "获取秒杀活动商品列表")
    public JsonViewData<PageInfo<SeckillGoodsVo>> searchActivityGoodsList(
            @ApiParam(required = true, name = "searchSpellGroupGoodsDto", value = "查询秒杀活动商品列表参数")
            @Valid @RequestBody SearchSeckillGoodsDto searchSeckillGoodsDto){

        return new JsonViewData(ResultCode.SUCCESS,"查找成功",
                amSeckillService.searchActivityGoodsList(searchSeckillGoodsDto));
    }


    /**
     * @Author yeJH
     * @Date 2019/10/9 10:36
     * @Description 获取秒杀活动商品一级分类
     *
     * @Update yeJH
     *
     * @param  searchSeckillGoodsDto
     * @return com.chauncy.data.vo.JsonViewData<java.util.List<com.chauncy.data.vo.BaseVo>>
     **/
    @PostMapping("/findGoodsCategory")
    @ApiOperation(value = "获取秒杀活动商品一级分类")
    public JsonViewData<List<BaseVo>> findGoodsCategory(
            @ApiParam(required = true, name = "searchSpellGroupGoodsDto", value = "查询秒杀活动商品列表参数")
            @Valid @RequestBody SearchSeckillGoodsDto searchSeckillGoodsDto) {

        return new JsonViewData(ResultCode.SUCCESS,"查找成功",
                amSeckillService.findGoodsCategory(searchSeckillGoodsDto));
    }


}
