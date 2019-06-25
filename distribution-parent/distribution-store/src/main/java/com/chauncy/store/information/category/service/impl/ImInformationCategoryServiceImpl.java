package com.chauncy.store.information.category.service.impl;

import com.chauncy.data.domain.po.store.information.category.ImInformationCategoryPo;
import com.chauncy.data.mapper.store.information.category.ImInformationCategoryMapper;
import com.chauncy.store.information.category.service.IImInformationCategoryService;
import com.chauncy.data.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-25
 */
@Service
public class ImInformationCategoryServiceImpl extends AbstractService<ImInformationCategoryMapper,ImInformationCategoryPo> implements IImInformationCategoryService {

 @Autowired
 private ImInformationCategoryMapper mapper;

}
