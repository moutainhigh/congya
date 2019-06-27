package com.chauncy.web.api.supplier.message;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.manage.message.information.add.InformationDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.message.information.sensitive.service.IMmInformationSensitiveService;
import com.chauncy.message.information.service.IMmInformationService;
import com.chauncy.web.base.BaseApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author yeJH
 * @since 2019/6/26 15:56
 */
@Api(tags = "商家_店铺资讯管理接口")
@RestController
@RequestMapping("/supplier/information")
@Slf4j
public class MsInformationApi extends BaseApi {

    @Autowired
    private IMmInformationService mmInformationService;
    
    /**
     * 保存店铺资讯信息
     * @param informationDto
     * @return
     */
    @PostMapping("/save")
    @ApiOperation(value = "保存店铺资讯信息")
    @Transactional(rollbackFor = Exception.class)
    public JsonViewData save(@Valid @RequestBody @ApiParam(required = true, name = "informationDto", value = "店铺资讯信息")
                                     InformationDto informationDto) {

        mmInformationService.saveInformation(informationDto);
        return new JsonViewData(ResultCode.SUCCESS, "添加成功");
    }

}
