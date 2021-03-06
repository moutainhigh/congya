package com.chauncy.web.api.app.message.information;

import com.chauncy.common.enums.app.advice.AdviceLocationEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.app.message.information.select.SearchInfoByConditionDto;
import com.chauncy.data.dto.manage.message.information.add.AddInformationCommentDto;
import com.chauncy.data.dto.manage.message.information.select.InformationCommentDto;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.app.message.information.InformationBaseVo;
import com.chauncy.data.vo.app.message.information.InformationPagingVo;
import com.chauncy.data.vo.manage.message.information.category.InformationCategoryVo;
import com.chauncy.data.vo.manage.message.information.comment.InformationMainCommentVo;
import com.chauncy.data.vo.manage.message.information.comment.InformationViceCommentVo;
import com.chauncy.data.vo.manage.message.information.label.InformationLabelVo;
import com.chauncy.message.advice.IMmAdviceService;
import com.chauncy.message.information.category.service.IMmInformationCategoryService;
import com.chauncy.message.information.comment.service.IMmInformationCommentService;
import com.chauncy.message.information.label.service.IMmInformationLabelService;
import com.chauncy.message.information.service.IMmInformationService;
import com.chauncy.security.util.SecurityUtil;
import com.chauncy.store.service.ISmStoreService;
import com.chauncy.web.base.BaseApi;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author yeJH
 * @since 2019/7/1 22:24
 */
@Api(tags = "APP_资讯专区接口")
@RestController
@RequestMapping("/app/information")
@Slf4j
public class AmInformationApi extends BaseApi {

    @Autowired
    private IMmInformationService mmInformationService;
    @Autowired
    private ISmStoreService smStoreService;
    @Autowired
    private IMmInformationCategoryService mmInformationCategoryService;
    @Autowired
    private IMmInformationLabelService mmInformationLabelService;
    @Autowired
    private IMmInformationCommentService mmInformationCommentService;
    @Autowired
    private IMmAdviceService mmAdviceService;
    @Autowired
    private SecurityUtil securityUtil;

    /**
     * 查询所有的资讯分类
     * @return
     */
    /*@ApiOperation(value = "查询所有的资讯分类", notes = "查询所有的资讯分类")
    @GetMapping("/category/selectAll")
    public JsonViewData<InformationCategoryVo> searchCategoryAll() {

        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                mmInformationCategoryService.selectAll());
    }*/

    /**
     * 查询所有的资讯标签
     * @return
     */
   /* @ApiOperation(value = "查询所有的资讯标签", notes = "查询所有的资讯标签")
    @GetMapping("/label/selectAll")
    public JsonViewData<InformationLabelVo> searchLabelAll() {

        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                mmInformationLabelService.selectAll());
    }*/

    /**
     * 资讯专区列表
     * @param searchInfoByConditionDto
     * @return
     */
    @ApiOperation(value = "资讯专区列表",
            notes = "资讯类型（informationType）：    \n1：全部资讯列表    \n2：关注资讯列表   \n" +
                    "3：推荐资讯列表   \n4：分类资讯列表   \n5：搜索资讯列表   \n")
    @PostMapping("/searchPaging")
    public JsonViewData<PageInfo<InformationPagingVo>> searchPaging(@Valid @ApiParam(required = true,
            name = "searchInfoByConditionDto", value = "查询条件") @RequestBody SearchInfoByConditionDto searchInfoByConditionDto) {

        PageInfo<InformationPagingVo> smStoreBaseVoPageInfo = mmInformationService.searchPaging(searchInfoByConditionDto);
        return new JsonViewData(ResultCode.SUCCESS, "查询成功",
                smStoreBaseVoPageInfo);
    }

    /**
     * 获取关注的店铺更新的资讯数目
     *
     * @return
     */
    @GetMapping("/getFocusInfoSum")
    @ApiOperation(value = "获取关注的店铺更新的资讯数目")
    public JsonViewData getFocusInfoSum(){

        return new JsonViewData(ResultCode.SUCCESS,"查找成功",mmInformationService.getFocusInfoSum());
    }

    /**
     * 获取资讯动态下推荐的分类
     *
     * @return
     */
    @GetMapping("/findInfoCategory")
    @ApiOperation(value = "获取资讯动态下推荐的分类")
    public JsonViewData<List<BaseVo>> findStoreCategory(){

        List<BaseVo> infoCategorys = mmAdviceService.findInfoCategory();
        return new JsonViewData(ResultCode.SUCCESS,"查找成功",infoCategorys);
    }

    /**
     * app根据ID查找资讯
     * @param id
     * @return
     */
    @ApiOperation(value = "查找资讯", notes = "根据资讯ID查找")
    @GetMapping("/findBaseById/{id}")
    public JsonViewData<InformationBaseVo> findBaseById(@ApiParam(required = true, value = "id")
                                                            @PathVariable Long id) {

        UmUserPo umUserPo = securityUtil.getAppCurrUser();
        Long userId = null == umUserPo ? null : umUserPo.getId();
        return new JsonViewData(ResultCode.SUCCESS, "查询成功",
                mmInformationService.findBaseById(id, userId));
    }

    /**
     * @Author yeJH
     * @Date 2019/11/3 14:22
     * @Description APP分享资讯到微信  微信用户访问资讯（无需登录）
     *
     * @Update yeJH
     *
     * @param  id
     * @return com.chauncy.data.vo.JsonViewData<com.chauncy.data.vo.app.message.information.InformationBaseVo>
     **/
    @ApiOperation(value = "游客查找资讯详情", notes = "非登录用户根据资讯id查找资讯详情")
    @GetMapping("/share/findBaseById/{id}")
    public JsonViewData<InformationBaseVo> findShareInfo(@ApiParam(required = true, value = "id")
                                                            @PathVariable Long id) {

        return new JsonViewData(ResultCode.SUCCESS, "查询成功",
                mmInformationService.findBaseById(id, null));
    }

    /**
     * @Author yeJH
     * @Date 2019/11/3 15:34
     * @Description 游客根据资讯id获取资讯详情
     *
     * @Update yeJH
     *
     * @param  informationCommentDto
     * @return com.chauncy.data.vo.JsonViewData<com.github.pagehelper.PageInfo<com.chauncy.data.vo.manage.message.information.comment.InformationMainCommentVo>>
     **/
    @ApiOperation(value = "游客获取资讯评论", notes = "非登录用户根据资讯id获取资讯评论详情")
    @PostMapping("/share/comment/searchShareComment")
    public JsonViewData<PageInfo<InformationMainCommentVo>> searchShareComment(@Valid @ApiParam(required = true,
            name = "informationCommentDto", value = "查询条件")@RequestBody InformationCommentDto informationCommentDto) {

        return new JsonViewData(ResultCode.SUCCESS, "查询成功",
                mmInformationCommentService.searchInfoCommentById(informationCommentDto));
    }

    /**
     * @Author yeJH
     * @Date 2019/11/3 15:36
     * @Description 根据主评论id查询副评论
     *
     * @Update yeJH
     *
     * @param  mainId
     * @return com.chauncy.data.vo.JsonViewData<com.chauncy.data.vo.manage.message.information.comment.InformationViceCommentVo>
     **/
    @ApiOperation(value = "查找资讯副评论", notes = "根据主评论ID查找所有副评论")
    @PostMapping("/share/comment/searchShareViceComment/{mainId}")
    public JsonViewData<InformationViceCommentVo> searchShareViceComment(@ApiParam(required = true, value = "mainId")
                                                                            @PathVariable Long mainId) {

        return new JsonViewData(ResultCode.SUCCESS, "查询成功",
                mmInformationCommentService.searchViceCommentByMainId(mainId,  null));
    }


    /**
     * 根据资讯id获取关联的商品
     *
     * @param baseSearchDto
     * @return
     */
    /*@ApiOperation(value = "查找资讯关联商品", notes = "根据资讯id获取关联的商品")
    @GetMapping("/searchGoodsById")
    public JsonViewData<PageInfo<GoodsBaseInfoVo>> searchGoodsById(@Validated @RequestBody  @ApiParam(required = true, value = "baseSearchDto")
                                                                           BaseSearchDto baseSearchDto) {

        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                mmInformationService.searchGoodsById(baseSearchDto));

    }*/


    /**
     * app根据资讯ID查找资讯评论
     * @param informationCommentDto
     * @return
     */
    @ApiOperation(value = "查找资讯评论", notes = "根据资讯ID查找")
    @PostMapping("/comment/searchMainCommentById")
    public JsonViewData<PageInfo<InformationMainCommentVo>> searchInfoCommentById(@Valid @ApiParam(required = true,
            name = "informationCommentDto", value = "查询条件")@RequestBody InformationCommentDto informationCommentDto) {

        informationCommentDto.setUserId(securityUtil.getAppCurrUser().getId());
        return new JsonViewData(ResultCode.SUCCESS, "查询成功",
                mmInformationCommentService.searchInfoCommentById(informationCommentDto));
    }


    /**
     * 根据主评论id查询副评论
     *
     * @param mainId
     * @return
     */
    @ApiOperation(value = "查找资讯副评论", notes = "根据主评论ID查找所有副评论")
    @PostMapping("/comment/searchViceCommentByMainId/{mainId}")
    public JsonViewData<InformationViceCommentVo> searchViceCommentByMainId(@ApiParam(required = true, value = "mainId")
                                                                                @PathVariable Long mainId) {

        return new JsonViewData(ResultCode.SUCCESS, "查询成功",
                mmInformationCommentService.searchViceCommentByMainId(mainId,  securityUtil.getAppCurrUser().getId()));
    }

    /**
     * 保存app用户资讯评论
     *
     * @param addInformationCommentDto
     * @return
     */
    @ApiOperation(value = "保存app用户资讯评论", notes = "保存app用户资讯评论")
    @PostMapping("/comment/save")
    public JsonViewData<InformationMainCommentVo> saveUserInfoComment(@Valid @ApiParam(required = true, name = "addInformationCommentDto",
            value = "查询条件")@RequestBody AddInformationCommentDto addInformationCommentDto) {

        return new JsonViewData(ResultCode.SUCCESS, "保存成功",
                mmInformationCommentService.saveInfoComment(addInformationCommentDto, securityUtil.getAppCurrUser().getId()));
    }

    /**
     * 用户点赞资讯
     * @param infoId  资讯id
     * @return
     */
    @ApiOperation(value = "用户点赞资讯", notes = "用户点赞资讯")
    @ApiImplicitParam(name = "infoId", value = "资讯id", required = true, dataType = "Long", paramType = "path")
    @GetMapping("/likeInfo/{infoId}")
    public JsonViewData likeInfo(@PathVariable(value = "infoId")Long infoId) {

        mmInformationService.likeInfo(infoId, securityUtil.getAppCurrUser().getId());
        return new JsonViewData(ResultCode.SUCCESS, "操作成功");

    }

    /**
     * 用户转发资讯成功
     * @param infoId  资讯id
     * @return
     */
    @ApiOperation(value = "用户成功转发资讯", notes = "转发成功记录操作")
    @ApiImplicitParam(name = "infoId", value = "资讯id", required = true, dataType = "Long", paramType = "path")
    @GetMapping("/forwardInfo/{infoId}")
    public JsonViewData forwardInfo(@PathVariable(value = "infoId")Long infoId) {

        mmInformationService.forwardInfo(infoId, securityUtil.getAppCurrUser().getId());
        return new JsonViewData(ResultCode.SUCCESS, "操作成功");

    }

    /**
     * 用户评论点赞/取消点赞
     * @param commentId  评论id
     * @return
     */
    @ApiOperation(value = "用户评论点赞/取消点赞", notes = "用户评论点赞/取消点赞")
    @ApiImplicitParam(name = "commentId", value = "评论id", required = true, dataType = "Long", paramType = "path")
    @GetMapping("/comment/likeComment/{commentId}")
    public JsonViewData likeComment(@PathVariable(value = "commentId") Long commentId) {

        mmInformationCommentService.likeComment(commentId, securityUtil.getAppCurrUser().getId());
        return new JsonViewData(ResultCode.SUCCESS, "操作成功");

    }

    /**
     * 用户关注店铺资讯   改为用户收藏店铺
     * @param storeId  店铺id
     * @return
     */
   /* @ApiOperation(value = "用户关注店铺", notes = "用户关注店铺")
    @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, dataType = "Long", paramType = "path")
    @GetMapping("/userFocusStore/{storeId}")
    public JsonViewData userFocusStore(@PathVariable(value = "storeId")Long storeId) {

        smStoreService.userFocusStore(storeId, securityUtil.getAppCurrUser().getId());
        return new JsonViewData(ResultCode.SUCCESS, "关注成功");

    }*/


}
