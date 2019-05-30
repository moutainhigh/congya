package com.chauncy.web.api.manage.sys;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chauncy.data.domain.po.sys.SysDictDataPo;
import com.chauncy.data.domain.po.sys.SysDictPo;
import com.chauncy.data.vo.Result;
import com.chauncy.data.util.ResultUtil;
import com.chauncy.system.service.ISysDictDataService;
import com.chauncy.system.service.ISysDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 字典数据 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */
@RestController
@RequestMapping("/sys-dict-data-po")
@Slf4j
@Api(description = "字典数据管理接口")
@CacheConfig(cacheNames = "dictData")
@Transactional
public class SysDictDataApi {

 @Autowired
 private ISysDictService dictService;

 @Autowired
 private ISysDictDataService dictDataService;

 @Autowired
 private StringRedisTemplate redisTemplate;

 /*@RequestMapping(value = "/getByCondition",method = RequestMethod.GET)
 @ApiOperation(value = "多条件分页获取用户列表")
 public Result<Page<SysDictDataPo>> getByCondition(@ModelAttribute SysDictDataPo dictData,
                                                   @ModelAttribute PageVo pageVo){

  Page<SysDictDataPo> page = dictDataService.findByCondition(dictData, PageUtil.initPage(pageVo));
  return new ResultUtil<Page<SysDictDataPo>>().setData(page);
 }*/

 @RequestMapping(value = "/getByType/{type}",method = RequestMethod.GET)
 @ApiOperation(value = "通过类型获取")
 @Cacheable(key = "#type")
 public Result<Object> getByType(@PathVariable String type){

  SysDictPo dict = dictService.findByType(type);
  if (dict == null) {
   return new ResultUtil<Object>().setErrorMsg("字典类型Type不存在");
  }
  List<SysDictDataPo> list = dictDataService.findByDictId(dict.getId());
  return new ResultUtil<Object>().setData(list);
 }

 @RequestMapping(value = "/add",method = RequestMethod.POST)
 @ApiOperation(value = "添加")
 public Result<Object> add(@ModelAttribute SysDictDataPo dictData){

  SysDictPo dict = dictService.getById(dictData.getDictId());
  if (dict == null) {
   return new ResultUtil<Object>().setErrorMsg("字典类型id不存在");
  }
  dictDataService.save(dictData);
  // 删除缓存
  redisTemplate.delete("dictData::"+dict.getType());
  return new ResultUtil<Object>().setSuccessMsg("添加成功");
 }

 @RequestMapping(value = "/edit",method = RequestMethod.POST)
 @ApiOperation(value = "编辑")
 public Result<Object> edit(@ModelAttribute SysDictDataPo dictData){

  UpdateWrapper<SysDictDataPo> dictDataWrapper = new UpdateWrapper<>(dictData);
  dictDataService.update(dictDataWrapper);
  // 删除缓存
  SysDictPo dict = dictService.getById(dictData.getDictId());
  redisTemplate.delete("dictData::"+dict.getType());
  return new ResultUtil<Object>().setSuccessMsg("编辑成功");
 }

 @RequestMapping(value = "/delByIds/{ids}",method = RequestMethod.DELETE)
 @ApiOperation(value = "批量通过id删除")
 public Result<Object> delByIds(@PathVariable String[] ids){

  for(String id : ids){
   SysDictDataPo dictData = dictDataService.getById(id);
   SysDictPo dict = dictService.getById(dictData.getDictId());
   dictDataService.removeById(id);
   // 删除缓存
   redisTemplate.delete("dictData::"+dict.getType());
  }
  return new ResultUtil<Object>().setSuccessMsg("批量通过id删除数据成功");
 }

}
