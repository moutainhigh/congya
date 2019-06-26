package com.chauncy.message.content.service;

import com.chauncy.data.domain.po.message.content.MmBootPagePo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.manage.message.content.add.AddBootPageDto;
import com.chauncy.data.dto.manage.message.content.select.search.SearchContentDto;
import com.chauncy.data.vo.manage.message.content.BootPageVo;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * 启动页管理 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-26
 */
public interface IMmBootPageService extends Service<MmBootPagePo> {

    /**
     * 添加启动页
     *
     * @param addBootPageDto
     * @return
     */
    void addBootPage(AddBootPageDto addBootPageDto);

    /**
     * 修改启动页
     *
     * @param updateBootPage
     * @return
     */
    void updateBootPage(AddBootPageDto updateBootPage);

    /**
     * 批量删除启动页
     * @param ids
     * @return
     */
    void delBootPageByIds(Long[] ids);

    /**
     * 查找启动页
     *
     * @param searchContentDto
     * @return
     */
    PageInfo<BootPageVo> searchPages(SearchContentDto searchContentDto);
}
