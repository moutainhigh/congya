package com.chauncy.web.api.manage.message.information;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.order.bill.update.BatchAuditDto;
import com.chauncy.data.vo.JsonViewData;
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
 * @since 2019/6/25 17:57
 */
@Api(tags = "平台_资讯管理接口")
@RestController
@RequestMapping("/manage/information")
@Slf4j
public class MmInformationApi extends BaseApi {

    @Autowired
    private IMmInformationService mmInformationService;

    /**
     * 审核资讯
     *
     * @param batchAuditDto
     */
    @PostMapping("/verifyInfo")
    @ApiOperation(value = "审核资讯")
    public JsonViewData verifyInfo(@Valid @RequestBody @ApiParam(required = true, name = "batchAuditDto", value = "id、修改的状态值")
                                           BatchAuditDto batchAuditDto) {

        mmInformationService.verifyInfo(batchAuditDto);
        return new JsonViewData(ResultCode.SUCCESS, "修改状态成功");
    }


}
