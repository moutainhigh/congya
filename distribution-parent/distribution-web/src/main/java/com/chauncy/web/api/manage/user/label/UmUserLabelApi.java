package com.chauncy.web.api.manage.user.label;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.domain.po.user.UmUserLabelPo;
import com.chauncy.data.dto.base.BaseSearchDto;
import com.chauncy.data.dto.manage.good.delete.GoodCategoryDeleteDto;
import com.chauncy.data.dto.manage.user.add.AddUserLabelDto;
import com.chauncy.data.dto.manage.user.delete.DeleteUserLabelDto;
import com.chauncy.data.valid.group.IUpdateGroup;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.supplier.good.ExcelGoodVo;
import com.chauncy.user.service.IUmUserLabelService;
import com.chauncy.web.base.BaseApi;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户标签 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-26
 */
@Api(tags = "平台_用户管理_用户标签")
@RestController
@RequestMapping("/manage/user/label")
public class UmUserLabelApi extends BaseApi {

    @Autowired
    private IUmUserLabelService service;

    @PostMapping("/save")
    @ApiOperation(value = "新增用户标签")
    @Transactional(rollbackFor = Exception.class)
    public JsonViewData save(@Validated @RequestBody AddUserLabelDto addUserLabelDto) {
        UmUserLabelPo saveLabel=new UmUserLabelPo();
        BeanUtils.copyProperties(addUserLabelDto,saveLabel);
        saveLabel.setId(null).setCreateBy(getUser().getUsername());
        service.save(saveLabel);
        return setJsonViewData(ResultCode.SUCCESS);
    }


    @PostMapping("/search")
    @ApiOperation(value = "查询用户标签列表")
    public JsonViewData<PageInfo<UmUserLabelPo>> search(@Validated @RequestBody BaseSearchDto baseSearchDto) {
        Integer pageNo = baseSearchDto.getPageNo() == null ? defaultPageNo : baseSearchDto.getPageNo();
        Integer pageSize = baseSearchDto.getPageSize() == null ? defaultPageSize : baseSearchDto.getPageSize();
        QueryWrapper<UmUserLabelPo> queryWrapper = new QueryWrapper<>();
        if (baseSearchDto.getEnabled()!=null){
            queryWrapper.eq("enabled",baseSearchDto.getEnabled());
        }
        if (baseSearchDto.getId()!=null){
            queryWrapper.eq("id",baseSearchDto.getId());
        }
        if (!Strings.isNullOrEmpty(baseSearchDto.getName())){
            queryWrapper.like("name",baseSearchDto.getName());
        }
        PageInfo<UmUserLabelPo> umUserLabelPoPageInfo = PageHelper.startPage(pageNo, pageSize, defaultSoft)
                .doSelectPageInfo(() -> service.list(queryWrapper));
        return setJsonViewData(umUserLabelPoPageInfo);
    }


    @PostMapping("/delete")
    @ApiOperation(value = "删除商品标签")
    @Transactional(rollbackFor = Exception.class)
    public JsonViewData delete(@Validated @RequestBody DeleteUserLabelDto deleteUserLabelDto){
        boolean isSuccess = service.removeByIds(deleteUserLabelDto.getIds());
        return isSuccess?setJsonViewData(ResultCode.SUCCESS):setJsonViewData(ResultCode.FAIL);
    }


    @PostMapping("/update")
    @ApiOperation(value = "编辑用户标签")
    @Transactional(rollbackFor = Exception.class)
    public JsonViewData update(@Validated(IUpdateGroup.class) @RequestBody AddUserLabelDto addUserLabelDto) {
        UmUserLabelPo updateLabel=new UmUserLabelPo();
        BeanUtils.copyProperties(addUserLabelDto,updateLabel);
        updateLabel.setUpdateBy(getUser().getUsername());
        service.updateById(updateLabel);
        return setJsonViewData(ResultCode.SUCCESS);
    }









}
