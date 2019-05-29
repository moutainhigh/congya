package com.chauncy.web.api.sys;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chauncy.data.domain.po.sys.SysDictPo;
import com.chauncy.data.vo.Result;
import com.chauncy.data.util.ResultUtil;
import com.chauncy.system.service.ISysDictDataService;
import com.chauncy.system.service.ISysDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */
@RestController
@RequestMapping("/sys-dict-po")
@Slf4j
@Api(description = "字典管理接口")
@Transactional
public class SysDictApi {

 @Autowired
 private ISysDictService dictService;


 @Autowired
 private ISysDictDataService dictDataService;

 @Autowired
 private StringRedisTemplate redisTemplate;

 @RequestMapping(value = "/getAll",method = RequestMethod.GET)
 @ApiOperation(value = "获取全部数据")
 public Result<List<SysDictPo>> getAll(){

  List<SysDictPo> list = dictService.findAllOrderBySortOrder();
  return new ResultUtil<List<SysDictPo>>().setData(list);
 }

 @RequestMapping(value = "/add",method = RequestMethod.POST)
 @ApiOperation(value = "添加")
 public Result<Object> add(@ModelAttribute SysDictPo dict){

  if(dictService.findByType(dict.getType())!=null){
   return new ResultUtil<Object>().setErrorMsg("字典类型Type已存在");
  }
  dictService.save(dict);
  return new ResultUtil<Object>().setSuccessMsg("添加成功");
 }

 @RequestMapping(value = "/edit",method = RequestMethod.POST)
 @ApiOperation(value = "编辑")
 public Result<Object> edit(@ModelAttribute SysDictPo dict){

  SysDictPo old = dictService.getById(dict.getId());
  // 若type修改判断唯一
  if(!old.getType().equals(dict.getType())&&dictService.findByType(dict.getType())!=null){
   return new ResultUtil<Object>().setErrorMsg("字典类型Type已存在");
  }
  UpdateWrapper<SysDictPo> dictWrapper = new UpdateWrapper<>(dict);
  dictService.update(dictWrapper);
  return new ResultUtil<Object>().setSuccessMsg("编辑成功");
 }

 @RequestMapping(value = "/delByIds/{id}",method = RequestMethod.DELETE)
 @ApiOperation(value = "通过id删除")
 public Result<Object> delAllByIds(@PathVariable String id){


  SysDictPo dict = dictService.getById(id);
  dictService.removeById(id);
  dictDataService.deleteByDictId(id);
  // 删除缓存
  redisTemplate.delete("dictData::"+dict.getType());
  return new ResultUtil<Object>().setSuccessMsg("删除成功");
 }

 @RequestMapping(value = "/search",method = RequestMethod.GET)
 @ApiOperation(value = "搜索字典")
 public Result<List<SysDictPo>> searchPermissionList(@RequestParam String key){

  List<SysDictPo> list = dictService.findByTitleOrTypeLike(key);
  return new ResultUtil<List<SysDictPo>>().setData(list);
 }
}

