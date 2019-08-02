package com.chauncy.web.api.manage.user.idcard;


import com.chauncy.data.dto.manage.user.select.SearchUserIdCardDto;
import com.chauncy.data.valid.annotation.NeedExistConstraint;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.user.idCard.SearchIdCardVo;
import com.chauncy.user.service.IUmUserService;
import com.chauncy.web.base.BaseApi;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 用户标签 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-26
 */
@Api(tags = "平台_用户实名认证")
@RestController
@RequestMapping("/manage/user/id_card")
public class UmUserIdCardApi extends BaseApi {

    @Autowired
    private IUmUserService service;

    @PostMapping("/search")
    @ApiOperation("用户认证列表")
    public JsonViewData<List<SearchIdCardVo>> search(@Validated @RequestBody SearchUserIdCardDto searchUserIdCardDto){
        PageInfo<SearchIdCardVo> searchIdCardVoPageInfo = service.searchIdCardVos(searchUserIdCardDto);
        return setJsonViewData(searchIdCardVoPageInfo);

    }





}
