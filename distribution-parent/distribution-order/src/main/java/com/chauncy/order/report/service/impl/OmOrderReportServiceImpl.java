package com.chauncy.order.report.service.impl;

import com.chauncy.common.enums.order.ReportTypeEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.data.domain.po.order.report.OmOrderReportPo;
import com.chauncy.data.dto.manage.order.report.select.SearchReportDto;
import com.chauncy.data.mapper.order.report.OmOrderReportMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.vo.manage.order.report.ReportBaseInfoVo;
import com.chauncy.order.report.service.IOmOrderReportService;
import com.chauncy.security.util.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 商品销售报表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-09
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OmOrderReportServiceImpl extends AbstractService<OmOrderReportMapper, OmOrderReportPo> implements IOmOrderReportService {

    @Autowired
    private OmOrderReportMapper omOrderReportMapper;

    @Autowired
    private SecurityUtil securityUtil;


    /**
     * 查询商品销售报表
     *
     * @param searchReportDto
     * @return
     */
    @Override
    public PageInfo<ReportBaseInfoVo> searchReportPaging(SearchReportDto searchReportDto) {

        if(!ReportTypeEnum.PLATFORM_REPORT.name().equals(searchReportDto.getReportType())) {
            //店铺用户查找对应的商品销售报表
            //获取当前店铺用户
            Long storeId = securityUtil.getCurrUser().getStoreId();
            if(null != storeId) {
                searchReportDto.setStoreId(storeId);
            } else {
                throw  new ServiceException(ResultCode.FAIL, "当前登录用户跟操作不匹配");
            }
        }
        Integer pageNo = searchReportDto.getPageNo() == null ? defaultPageNo : searchReportDto.getPageNo();
        Integer pageSize  = searchReportDto.getPageSize() == null ? defaultPageSize : searchReportDto.getPageSize();

        PageInfo<ReportBaseInfoVo> reportBaseInfoVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> omOrderReportMapper.searchReportPaging(searchReportDto));

        return reportBaseInfoVoPageInfo;

    }


    /**
     * 根据ID查找商品销售报表信息
     *
     * @param id
     * @return
     */
    @Override
    public ReportBaseInfoVo findReportById(Long id) {
        return null;
    }
}
