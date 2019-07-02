package com.chauncy.message.information.service;

import com.chauncy.data.domain.po.message.information.MmInformationPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.manage.message.information.add.InformationDto;
import com.chauncy.data.dto.manage.message.information.select.InformationSearchDto;
import com.chauncy.data.vo.manage.message.information.InformationPageInfoVo;
import com.chauncy.data.vo.manage.message.information.InformationVo;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-25
 */
public interface IMmInformationService extends Service<MmInformationPo> {

    /**
     * 保存资讯
     * @param informationDto
     */
    void saveInformation(InformationDto informationDto);
    /**
     * 编辑资讯
     * @param informationDto
     */
    void editInformation(InformationDto informationDto);

    /**
     * 批量删除资讯
     * @param ids
     */
    void delInformationByIds(Long[] ids);

    /**
     * 根据ID查找店铺资讯
     * @param id 资讯id
     * @return
     */
    InformationVo findById(Long id);

    /**
     * 根据关联ID删除资讯跟店铺的绑定关系
     * @param id 资讯商品关联id
     * @return
     */
    void delRelById(Long id);

    /**
     * 分页条件查询
     * @param informationSearchDto
     * @return
     */
    PageInfo<InformationPageInfoVo> searchPaging(InformationSearchDto informationSearchDto);
}
