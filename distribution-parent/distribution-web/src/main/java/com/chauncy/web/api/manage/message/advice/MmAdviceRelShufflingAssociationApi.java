package com.chauncy.web.api.manage.message.advice;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import com.chauncy.message.advice.IMmAdviceRelShufflingAssociationService;

import org.springframework.web.bind.annotation.RestController;
import com.chauncy.web.base.BaseApi;

/**
 * <p>
 * 广告与(品牌、一级分类)关联轮播图关联表(不同品牌、不同一级分类有不同广告轮播图) 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-14
 */
@RestController
@RequestMapping("mm-advice-rel-shuffling-association-po")
@Api(tags = "广告与(品牌、一级分类)关联轮播图关联表(不同品牌、不同一级分类有不同广告轮播图)")
public class MmAdviceRelShufflingAssociationApi extends BaseApi {

    @Autowired
    private IMmAdviceRelShufflingAssociationService service;


}
