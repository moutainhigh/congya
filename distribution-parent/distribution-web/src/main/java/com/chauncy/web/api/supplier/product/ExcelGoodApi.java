package com.chauncy.web.api.supplier.product;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.ListUtil;
import com.chauncy.common.util.LoggerUtil;
import com.chauncy.common.util.StringUtils;
import com.chauncy.data.domain.po.product.*;
import com.chauncy.data.domain.po.product.stock.PmGoodsVirtualStockPo;
import com.chauncy.data.domain.po.user.PmMemberLevelPo;
import com.chauncy.data.dto.supplier.good.select.SearchExcelDto;
import com.chauncy.data.temp.product.service.IPmGoodsRelAttributeValueGoodService;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.excel.ExcelImportErrorLogVo;
import com.chauncy.data.vo.supplier.good.ExcelGoodVo;
import com.chauncy.poi.util.ReadExcelUtil;
import com.chauncy.product.service.*;
import com.chauncy.product.stock.IPmGoodsVirtualStockService;
import com.chauncy.security.util.SecurityUtil;
import com.chauncy.user.service.IPmMemberLevelService;
import com.chauncy.web.base.BaseApi;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.mockito.internal.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author zhangrt
 * @Date 2019/6/17 17:46
 **/

@RestController
@Api(tags = "商家端_商品管理_商品导入")
@RequestMapping("/supplier/product")
public class ExcelGoodApi extends BaseApi {

    //导入商品基本属性的列数
    private final int BASE_GOOD_ROW_NUMBER = 17;

    //导入商品基本属性的列数
    private final int BASE_SKU_NUMBER = 7;

    //导入财务信息的列数
    private final int BASE_FINANCE_NUMBER = 4;

    //导入运营信息的列数
    private final int BASE_OPERATE_NUMBER = 9;

    @Value("${upload.file.path}")
    private String saveDir;

    @Autowired
    private IPmGoodsService pmGoodsService;

    @Autowired
    private IPmGoodsCategoryService categoryService;

    @Autowired
    private IPmGoodsAttributeService attributePoService;

    @Autowired
    private IPmGoodsAttributeValueService valueService;

    @Autowired
    private IPmAssociationGoodsService associationGoodsService;

    @Autowired
    private IPmGoodsRelAttributeValueGoodService relAttributeValueGoodService;

    @Autowired
    private IPmGoodsRelAttributeGoodService relAttributeGoodService;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private IPmGoodsSkuService skuService;

    @Autowired
    private IPmGoodsVirtualStockService pmGoodsVirtualStockService;

    @Autowired
    private IPmGoodsRelAttributeValueSkuService relAttributeValueSkuService;

    @Autowired
    private IPmMemberLevelService memberLevelService;


    @PostMapping(value = "/importbase", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "导入商品基本信息")
    @Transactional(rollbackFor = Exception.class)
    @SuppressWarnings("unchecked")
    public JsonViewData<List<ExcelImportErrorLogVo>> importBase(@RequestParam(value = "file") MultipartFile file) throws InstantiationException, IllegalAccessException, IOException {

        List<List<String>> excelData = getDataFromExcel(file.getInputStream());

        List<ExcelImportErrorLogVo> excelImportErrorLogVos = saveBaseAndGetErrorLogs(excelData);
        return new JsonViewData(ResultCode.SUCCESS,
                "操作成功，共有" + excelImportErrorLogVos.size() + "条导入失败！",
                excelImportErrorLogVos
        );
    }


    @PostMapping(value = "/finance", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "导入商品财务信息")
    @Transactional(rollbackFor = Exception.class)
    @SuppressWarnings("unchecked")
    public JsonViewData<List<ExcelImportErrorLogVo>> finance(@RequestParam(value = "file") MultipartFile file) throws InstantiationException, IllegalAccessException, IOException {

        List<List<String>> excelData = getDataFromExcel(file.getInputStream());
        List<ExcelImportErrorLogVo> excelImportErrorLogVos = savefinanceAndGetErrorLogs(excelData);
        return new JsonViewData(ResultCode.SUCCESS,
                "操作成功，共有" + excelImportErrorLogVos.size() + "条导入失败！",
                excelImportErrorLogVos
        );
    }

    @PostMapping(value = "/operate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "导入商品运营信息")
    @Transactional(rollbackFor = Exception.class)
    @SuppressWarnings("unchecked")
    public JsonViewData<List<ExcelImportErrorLogVo>> operate(@RequestParam(value = "file") MultipartFile file) throws InstantiationException, IllegalAccessException, IOException {

        List<List<String>> excelData = getDataFromExcel(file.getInputStream());
        List<ExcelImportErrorLogVo> excelImportErrorLogVos = saveOperateAndGetErrorLogs(excelData);
        return new JsonViewData(ResultCode.SUCCESS,
                "操作成功，共有" + excelImportErrorLogVos.size() + "条导入失败！",
                excelImportErrorLogVos
        );
    }


    @PostMapping(value = "/importsku", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "导入sku")
    @Transactional(rollbackFor = Exception.class)
    @SuppressWarnings("unchecked")
    public JsonViewData<List<ExcelImportErrorLogVo>> importsku(@RequestParam(value = "file") MultipartFile file) throws InstantiationException, IllegalAccessException, IOException {

        List<List<String>> excelData = getDataFromExcel(file.getInputStream());
        List<ExcelImportErrorLogVo> excelImportErrorLogVos = saveSkuAndGetErrorLogs(excelData);
        return new JsonViewData(ResultCode.SUCCESS,
                "操作成功，共有" + excelImportErrorLogVos.size() + "条导入失败！",
                excelImportErrorLogVos
        );
    }

    @PostMapping(value = "/searchExcelGoods")
    @ApiOperation(value = "查询导入商品列表")
    @Transactional(rollbackFor = Exception.class)
    @SuppressWarnings("unchecked")
    public JsonViewData<List<ExcelGoodVo>> searchExcelGoods(@Validated @RequestBody SearchExcelDto searchExcelDto) {
        return setJsonViewData(pmGoodsService.searchExcelGoods(searchExcelDto));

    }


    /**
     * 获取excel中的数据
     *
     * @param inputStream
     * @return
     */
    private List<List<String>> getDataFromExcel(InputStream inputStream) {
        List<List<String>> excelData;
        try {
            excelData = ReadExcelUtil.readExcelInfo(inputStream);
            if (ListUtil.isListNullAndEmpty(excelData)) {
                throw new ServiceException(ResultCode.FAIL, "获取excel数据失败！");
            }
        } catch (Exception e) {
            LoggerUtil.error(e);
            throw new ServiceException(ResultCode.FAIL, "获取excel数据失败！");
        }
        return excelData;
    }


    /**
     * 插入商品基本信息数据库并获取出错日志
     *
     * @param excelDataList
     * @return
     */
    private List<ExcelImportErrorLogVo> saveBaseAndGetErrorLogs(List<List<String>> excelDataList) throws IllegalAccessException, InstantiationException {
        //excel导入出错记录
        List<ExcelImportErrorLogVo> excelImportErrorLogVos = Lists.newArrayList();

        for (int i = 2; i < excelDataList.size(); i++) {
            ExcelImportErrorLogVo excelImportErrorLogVo = new ExcelImportErrorLogVo();
            //获得整行数据
            List<String> rowDataList = excelDataList.get(i);
            if (rowDataList.size() < BASE_GOOD_ROW_NUMBER) {
                excelImportErrorLogVo.setRowNumber(i + 1);
                excelImportErrorLogVo.setErrorMessage("excel列数与模板不符合");
                excelImportErrorLogVos.add(excelImportErrorLogVo);
                continue;
            }
            //判断不为空的
            if (StringUtils.isHasBlank(rowDataList.get(0), rowDataList.get(1), rowDataList.get(2), rowDataList.get(5),
                    rowDataList.get(6), rowDataList.get(7), rowDataList.get(8), rowDataList.get(15), rowDataList.get(16))) {
                excelImportErrorLogVo.setRowNumber(i + 1);
                excelImportErrorLogVo.setErrorMessage("请填写完整excel中红色的列");
                excelImportErrorLogVos.add(excelImportErrorLogVo);
                continue;
            }
            //商品分类
            //PmGoodsCategoryPo categoryCondition = new PmGoodsCategoryPo(rowDataList.get(0), 3,true);
            QueryWrapper<PmGoodsCategoryPo> categoryPoWrapper = new QueryWrapper<>();
            categoryPoWrapper.lambda().eq(PmGoodsCategoryPo::getName,rowDataList.get(0)).
                    eq(PmGoodsCategoryPo::getLevel,3).eq(PmGoodsCategoryPo::getEnabled,true);

            PmGoodsCategoryPo categoryPo = categoryService.getOne(categoryPoWrapper);
            if (categoryPo == null) {
                excelImportErrorLogVo.setRowNumber(i + 1);
                excelImportErrorLogVo.setErrorMessage(String.format("系统中商品三级分类【%s】不存在该名称", rowDataList.get(0)));
                excelImportErrorLogVos.add(excelImportErrorLogVo);
                continue;
            }
            //商品品牌
            PmGoodsAttributePo brandCondition = new PmGoodsAttributePo(rowDataList.get(5), 8,true);
            Wrapper brandWrapper = new QueryWrapper<>(brandCondition, "id");
            PmGoodsAttributePo brand = attributePoService.getOne(brandWrapper);
            if (brand == null) {
                excelImportErrorLogVo.setRowNumber(i + 1);
                excelImportErrorLogVo.setErrorMessage(String.format("系统商品品牌【%s】不存在该名称", rowDataList.get(5)));
                excelImportErrorLogVos.add(excelImportErrorLogVo);
                continue;
            }
            //商品标签
            List<String> labelNames = Splitter.on(";").splitToList(rowDataList.get(6));
            List<Long> labelIds = pmGoodsService.findIdByNamesInAndTableName(labelNames, "pm_goods_attribute", "and type=5");
            if (ListUtil.isListNullAndEmpty(labelIds)) {
                excelImportErrorLogVo.setRowNumber(i + 1);
                excelImportErrorLogVo.setErrorMessage(String.format("系统中商品标签【%s】不存在", rowDataList.get(6)));
                excelImportErrorLogVos.add(excelImportErrorLogVo);
                continue;
            }

            Boolean recommandStatus = "是".equals(rowDataList.get(7));
            Boolean starStatus = "是".equals(rowDataList.get(8));

            //平台服务说明与商家服务说明
            if (StringUtils.isAllBlank(rowDataList.get(9), rowDataList.get(10))) {
                excelImportErrorLogVo.setRowNumber(i + 1);
                excelImportErrorLogVo.setErrorMessage("平台服务说明与商家服务说明不能同时为空");
                excelImportErrorLogVos.add(excelImportErrorLogVo);
                continue;
            }
            List<Long> serviceIds = Lists.newArrayList();
            List<String> serviceNames;
            if (!StringUtils.isAllBlank(rowDataList.get(9))) {
                serviceNames = Splitter.on(";").omitEmptyStrings().splitToList(rowDataList.get(9));
                serviceIds = categoryService.findAttributeIdsByNamesAndCategoryId(serviceNames, 1, categoryPo.getId());
            } else {
                serviceNames = Splitter.on(";").omitEmptyStrings().splitToList(rowDataList.get(10));
                QueryWrapper<PmGoodsAttributePo> attributePoQueryWrapper=new QueryWrapper<>();
                attributePoQueryWrapper.lambda().in(PmGoodsAttributePo::getName,serviceNames).
                        eq(PmGoodsAttributePo::getEnabled,true);
                List<PmGoodsAttributePo> queryGoodsAttributes = attributePoService.list(attributePoQueryWrapper);
                serviceIds = queryGoodsAttributes.stream().map(PmGoodsAttributePo::getId).collect(Collectors.toList());

            }
            if (ListUtil.isListNullAndEmpty(serviceIds)) {
                excelImportErrorLogVo.setRowNumber(i + 1);
                excelImportErrorLogVo.setErrorMessage("服务说明不存在！");
                excelImportErrorLogVos.add(excelImportErrorLogVo);
                continue;
            }

            //运费模板
            if (StringUtils.isAllBlank(rowDataList.get(11), rowDataList.get(12))) {
                excelImportErrorLogVo.setRowNumber(i + 1);
                excelImportErrorLogVo.setErrorMessage("平台运费模板与店铺运费模板不能同时为空");
                excelImportErrorLogVos.add(excelImportErrorLogVo);
                continue;
            }
            String shipName;
            List<Long> shipIds;
            if (!StringUtils.isAllBlank(rowDataList.get(11))) {
                shipName = rowDataList.get(11);
                shipIds = categoryService.findIdByNamesInAndTableName(Lists.newArrayList(shipName), "pm_shipping_template", "and type=1");
            } else {
                shipName = rowDataList.get(12);
                shipIds = categoryService.findIdByNamesInAndTableName(Lists.newArrayList(shipName), "pm_shipping_template", "and type=2");
            }
            if (shipIds == null) {
                excelImportErrorLogVo.setRowNumber(i + 1);
                excelImportErrorLogVo.setErrorMessage("运费模板不存在");
                excelImportErrorLogVos.add(excelImportErrorLogVo);
                continue;
            }
            Long shipId=shipIds.get(0);

            //商品参数与参数值
            List<String> attributeNames;
            List<String> attributeValues = null;
            List<Long> attributeIds = null;
            if (StringUtils.isNotBlank(rowDataList.get(13))) {
                attributeValues = Splitter.on(";").omitEmptyStrings().splitToList(rowDataList.get(14));
                attributeNames = Splitter.on(";").omitEmptyStrings().splitToList(rowDataList.get(13));
                attributeIds = categoryService.findAttributeIdsByNamesAndCategoryId(attributeNames, 4, categoryPo.getId());
            }
            if (ListUtil.isListNullAndEmpty(attributeIds)) {
                excelImportErrorLogVo.setRowNumber(i + 1);
                excelImportErrorLogVo.setErrorMessage(String.format("【%s】中存在系统中没有的商品参数", rowDataList.get(13)));
                excelImportErrorLogVos.add(excelImportErrorLogVo);
                continue;
            }

            List<Long> goodIds =new ArrayList<>();
            if (rowDataList.size()>=18){
                goodIds=Splitter.on(";").trimResults().omitEmptyStrings().splitToList(rowDataList.get(17))
                        .stream().map(x -> Long.parseLong(x)).collect(Collectors.toList());
            }

            //保存商品表
            PmGoodsPo saveGoodPo = new PmGoodsPo();
            saveGoodPo.setGoodsCategoryId(categoryPo.getId());
            saveGoodPo.setGoodsType(rowDataList.get(1));
            saveGoodPo.setName(rowDataList.get(2));
            saveGoodPo.setSubtitle(rowDataList.get(3));
            saveGoodPo.setSpu(rowDataList.get(4));
            saveGoodPo.setRecommandStatus(recommandStatus);
            saveGoodPo.setStarStatus(starStatus);
            saveGoodPo.setShippingTemplateId(shipId);
            saveGoodPo.setLocation(rowDataList.get(15));
            saveGoodPo.setPurchaseLimit(Integer.parseInt(rowDataList.get(16)));
            saveGoodPo.setCreateBy(getUser().getUsername());
            saveGoodPo.setIsExcel(true).setStoreId(securityUtil.getCurrUser().getStoreId());

            pmGoodsService.save(saveGoodPo);

            List<PmGoodsRelAttributeGoodPo> relAttributeGoodPos = Lists.newArrayList();
            //品牌
            relAttributeGoodPos.add(new PmGoodsRelAttributeGoodPo(brand.getId(), saveGoodPo.getId(), getUser().getUsername()));
            //标签
            for (Long labelId : labelIds) {
                relAttributeGoodPos.add(new PmGoodsRelAttributeGoodPo(labelId, saveGoodPo.getId(), getUser().getUsername()));
            }
            //服务说明
            for (Long serviceId : serviceIds) {
                relAttributeGoodPos.add(new PmGoodsRelAttributeGoodPo(serviceId, saveGoodPo.getId(), getUser().getUsername()));
            }
            //商品参数与参数值
            List<PmGoodsRelAttributeValueGoodPo> saveRelValueGoods = Lists.newArrayList();
            for (int j = 0; j < attributeIds.size(); j++) {
                Long attributeId = attributeIds.get(j);
                /*//查出该属性下的属性值是否存在
                PmGoodsAttributeValuePo valueCondition=new PmGoodsAttributeValuePo();
                valueCondition.setProductAttributeId(attributeId);
                valueCondition.setValue(attributeValues.get(j));
                Wrapper<PmGoodsAttributeValuePo> goodsAttributeValuePoWrapper=new QueryWrapper<>(valueCondition,"id");
                PmGoodsAttributeValuePo selectValuePo = valueService.getOne(goodsAttributeValuePoWrapper);*/
                //不存在则新增为自定义属性值
//                if (selectValuePo==null){
                PmGoodsAttributeValuePo saveValuePo = new PmGoodsAttributeValuePo();
                saveValuePo.setValue(attributeValues.get(j));
                saveValuePo.setProductAttributeId(attributeIds.get(j));
                saveValuePo.setIsCustom(true);
                saveValuePo.setCreateBy(getUser().getUsername());
                valueService.save(saveValuePo);
                saveRelValueGoods.add(new PmGoodsRelAttributeValueGoodPo(saveValuePo.getId(), saveGoodPo.getId(), getUser().getUsername()));
                //}
               /* else {
                    saveRelValueGoods.add(new PmGoodsRelAttributeValueGoodPo(selectValuePo.getId(),saveGoodPo.getId(),getUser().getUsername()));
                }*/
            }
            relAttributeValueGoodService.saveBatch(saveRelValueGoods);


            if (!ListUtil.isListNullAndEmpty(goodIds)) {
                List<PmAssociationGoodsPo> associationGoodsPos = Lists.newArrayList();
                for (Long goodId : goodIds) {
                    PmAssociationGoodsPo associationGoodsPo = new PmAssociationGoodsPo(saveGoodPo.getId(), securityUtil.getCurrUser().getStoreId(), goodId, 2, getUser().getUsername());
                    associationGoodsPos.add(associationGoodsPo);
                }
                associationGoodsService.saveBatch(associationGoodsPos);

            }
            relAttributeGoodService.saveBatch(relAttributeGoodPos);
        }
        return excelImportErrorLogVos;
    }

    /**
     * 插入sku数据库并获取出错日志
     *
     * @param excelDataList
     * @return
     */
    private List<ExcelImportErrorLogVo> saveSkuAndGetErrorLogs(List<List<String>> excelDataList) throws IllegalAccessException, InstantiationException {
        //excel导入出错记录
        List<ExcelImportErrorLogVo> excelImportErrorLogVos = Lists.newArrayList();

        for (int i = 2; i < excelDataList.size(); i++) {
            ExcelImportErrorLogVo excelImportErrorLogVo = new ExcelImportErrorLogVo();
            //获得整行数据
            List<String> rowDataList = excelDataList.get(i);
            if (rowDataList.size() < BASE_SKU_NUMBER) {
                excelImportErrorLogVo.setRowNumber(i + 1);
                excelImportErrorLogVo.setErrorMessage("excel列数与模板不符合");
                excelImportErrorLogVos.add(excelImportErrorLogVo);
                continue;
            }
            //判断不为空的
            if (StringUtils.isHasBlank(rowDataList.get(1), rowDataList.get(2), rowDataList.get(3),
                    rowDataList.get(4))) {
                excelImportErrorLogVo.setRowNumber(i + 1);
                excelImportErrorLogVo.setErrorMessage("请填写完整excel中红色的列");
                excelImportErrorLogVos.add(excelImportErrorLogVo);
                continue;
            }
            //商品id
            PmGoodsPo queryGood = pmGoodsService.getById(rowDataList.get(0));
            if (queryGood == null) {
                excelImportErrorLogVo.setRowNumber(i + 1);
                excelImportErrorLogVo.setErrorMessage(String.format("商品id【%s】不存在!", rowDataList.get(0)));
                excelImportErrorLogVos.add(excelImportErrorLogVo);
                continue;
            } else {
                if (queryGood.getVerifyStatus().equals(2) || queryGood.getVerifyStatus().equals(3) ||
                        (queryGood.getPublishStatus() != null && queryGood.getPublishStatus())) {
                    excelImportErrorLogVo.setRowNumber(i + 1);
                    excelImportErrorLogVo.setErrorMessage(String.format("上架、待审核、审核通过的商品不能导入！"));
                    excelImportErrorLogVos.add(excelImportErrorLogVo);
                    continue;
                }
            }
            //规格、规格值
            List<String> attributeNames = Lists.newArrayList();
            List<String> attributeValues = Lists.newArrayList();
            try {
                List<String> standards = Splitter.on(";").omitEmptyStrings().splitToList(rowDataList.get(1));
                List<String> finalAttributeNames = attributeNames;
                List<String> finalAttributeValues = attributeValues;
                standards.forEach(s -> {
                    List<String> strings = Splitter.on(":").trimResults().omitEmptyStrings().splitToList(s);
                    finalAttributeNames.add(strings.get(0));
                    finalAttributeValues.add(strings.get(1));
                });
            } catch (Exception e) {
                excelImportErrorLogVo.setRowNumber(i + 1);
                excelImportErrorLogVo.setErrorMessage(String.format("规格、规格值输入格式不符合模板要求"));
                excelImportErrorLogVos.add(excelImportErrorLogVo);
                continue;
            }
            List<Long> standardIds = categoryService.findAttributeIdsByNamesAndCategoryId(attributeNames, 7, queryGood.getGoodsCategoryId());
            if (ListUtil.isListNullAndEmpty(standardIds) || standardIds.size() != attributeNames.size()) {
                excelImportErrorLogVo.setRowNumber(i + 1);
                excelImportErrorLogVo.setErrorMessage(String.format("【%s】中某些规格在当前分类不存在！", rowDataList.get(1)));
                excelImportErrorLogVos.add(excelImportErrorLogVo);
                continue;
            }

            PmGoodsSkuPo skuPo = new PmGoodsSkuPo();
            skuPo.setArticleNumber(rowDataList.get(5)).setCreateBy(getUser().getUsername()).
                    setGoodsId(Long.parseLong(rowDataList.get(0))).setBarCode(rowDataList.get(6))
                    .setSellPrice(new BigDecimal(rowDataList.get(2))).setLinePrice(new BigDecimal(rowDataList.get(2)))
                    .setStock((int) Double.parseDouble(rowDataList.get(4)));

            //保存sku主表
            skuService.save(skuPo);
            //插入商品虚拟库存
            PmGoodsVirtualStockPo pmGoodsVirtualStockPo = new PmGoodsVirtualStockPo();
            pmGoodsVirtualStockPo.setStoreId(queryGood.getStoreId())
                    .setGoodsId(queryGood.getId())
                    .setGoodsSkuId(skuPo.getId())
                    .setStockNum(skuPo.getStock())
                    .setCreateBy(getUser().getUsername());
            pmGoodsVirtualStockService.save(pmGoodsVirtualStockPo);
            //修改库存
            pmGoodsService.updateStock(skuPo.getGoodsId(), skuPo.getStock());


            List<PmGoodsRelAttributeValueSkuPo> saveRelAttributeValueSkuGoods = Lists.newArrayList();
            for (int j = 0; j < standardIds.size(); j++) {

                Long attributeId = categoryService.findAttributeIdsByNameAndCategoryId(attributeNames.get(j), 7, queryGood.getGoodsCategoryId());
                //查出该属性下的规格值是否存在
                PmGoodsAttributeValuePo valueCondition = new PmGoodsAttributeValuePo();
                valueCondition.setProductAttributeId(attributeId);
                valueCondition.setValue(attributeValues.get(j));
                Wrapper<PmGoodsAttributeValuePo> goodsAttributeValuePoWrapper = new QueryWrapper<>(valueCondition, "id");
                PmGoodsAttributeValuePo selectValuePo = valueService.getOne(goodsAttributeValuePoWrapper);
                //不存在则新增为自定义规格值
                if (selectValuePo == null) {
                    PmGoodsAttributeValuePo saveValuePo = new PmGoodsAttributeValuePo();
                    saveValuePo.setValue(attributeValues.get(j));
                    saveValuePo.setProductAttributeId(attributeId);
                    saveValuePo.setIsCustom(true);
                    saveValuePo.setCreateBy(getUser().getUsername());
                    valueService.save(saveValuePo);
                    saveRelAttributeValueSkuGoods.add(new PmGoodsRelAttributeValueSkuPo(saveValuePo.getId(), skuPo.getId(), getUser().getUsername()));
                } else {
                    saveRelAttributeValueSkuGoods.add(new PmGoodsRelAttributeValueSkuPo(selectValuePo.getId(), skuPo.getId(), getUser().getUsername()));
                }
            }
            relAttributeValueSkuService.saveBatch(saveRelAttributeValueSkuGoods);
        }
        return excelImportErrorLogVos;
    }


    /**
     * 插入财务信息并获取出错日志
     *
     * @param excelDataList
     * @return
     */
    private List<ExcelImportErrorLogVo> savefinanceAndGetErrorLogs(List<List<String>> excelDataList) throws IllegalAccessException, InstantiationException {
        //excel导入出错记录
        List<ExcelImportErrorLogVo> excelImportErrorLogVos = Lists.newArrayList();

        for (int i = 2; i < excelDataList.size(); i++) {
            ExcelImportErrorLogVo excelImportErrorLogVo = new ExcelImportErrorLogVo();
            //获得整行数据
            List<String> rowDataList = excelDataList.get(i);
            if (rowDataList.size() < BASE_FINANCE_NUMBER) {
                excelImportErrorLogVo.setRowNumber(i + 1);
                excelImportErrorLogVo.setErrorMessage("excel列数与模板不符合");
                excelImportErrorLogVos.add(excelImportErrorLogVo);
                continue;
            }
            //判断不为空的
            if (StringUtils.isHasBlank(rowDataList.get(0), rowDataList.get(1), rowDataList.get(2),
                    rowDataList.get(3))) {
                excelImportErrorLogVo.setRowNumber(i + 1);
                excelImportErrorLogVo.setErrorMessage("请填写完整excel中红色的列");
                excelImportErrorLogVos.add(excelImportErrorLogVo);
                continue;
            }
            //skuId

            PmGoodsSkuPo querySku = skuService.getById(rowDataList.get(0));
            if (querySku == null) {
                excelImportErrorLogVo.setRowNumber(i + 1);
                excelImportErrorLogVo.setErrorMessage(String.format("sku id【%s】不存在!", rowDataList.get(0)));
                excelImportErrorLogVos.add(excelImportErrorLogVo);
                continue;
            } else {
                PmGoodsPo queryGood = pmGoodsService.getById(querySku.getGoodsId());
                if (queryGood.getVerifyStatus().equals(2) || queryGood.getVerifyStatus().equals(3) ||
                        (queryGood.getPublishStatus() != null && queryGood.getPublishStatus())) {
                    excelImportErrorLogVo.setErrorMessage(String.format("上架、待审核、审核通过的商品不能导入！"));
                    excelImportErrorLogVo.setRowNumber(i + 1);
                    excelImportErrorLogVos.add(excelImportErrorLogVo);
                    continue;
                }
            }
            //供货价
            BigDecimal supplierPrice = new BigDecimal(rowDataList.get(1));

            //利润比例
            BigDecimal profitRate = new BigDecimal(rowDataList.get(2));

            //运营成本
            BigDecimal operationCost = new BigDecimal(rowDataList.get(3));

            PmGoodsSkuPo updateSku = new PmGoodsSkuPo();
            updateSku.setId(querySku.getId()).setSupplierPrice(supplierPrice).setProfitRate(profitRate).setOperationCost(operationCost)
                    .setUpdateBy(getUser().getUsername());

            skuService.updateById(updateSku);

        }
        return excelImportErrorLogVos;
    }


    /**
     * 插入运营信息并获取出错日志
     *
     * @param excelDataList
     * @return
     */
    private List<ExcelImportErrorLogVo> saveOperateAndGetErrorLogs(List<List<String>> excelDataList) throws IllegalAccessException, InstantiationException {
        //excel导入出错记录
        List<ExcelImportErrorLogVo> excelImportErrorLogVos = Lists.newArrayList();

        for (int i = 2; i < excelDataList.size(); i++) {
            ExcelImportErrorLogVo excelImportErrorLogVo = new ExcelImportErrorLogVo();
            //获得整行数据
            List<String> rowDataList = excelDataList.get(i);
            if (rowDataList.size() < BASE_OPERATE_NUMBER) {
                excelImportErrorLogVo.setErrorMessage("excel列数与模板不符合");
                excelImportErrorLogVo.setRowNumber(i + 1);
                excelImportErrorLogVos.add(excelImportErrorLogVo);
                continue;
            }
            //判断不为空的
            if (StringUtils.isHasBlank(rowDataList.get(0), rowDataList.get(1), rowDataList.get(2),
                    rowDataList.get(3), rowDataList.get(4), rowDataList.get(5), rowDataList.get(8))) {
                excelImportErrorLogVo.setRowNumber(i + 1);
                excelImportErrorLogVo.setErrorMessage("请填写完整excel中红色的列");
                excelImportErrorLogVos.add(excelImportErrorLogVo);
                continue;
            }
            //商品id
            PmGoodsPo queryGood = pmGoodsService.getById(rowDataList.get(0));
            if (queryGood == null) {
                excelImportErrorLogVo.setErrorMessage(String.format("商品id【%s】不存在!", rowDataList.get(0)));
                excelImportErrorLogVo.setRowNumber(i + 1);
                excelImportErrorLogVos.add(excelImportErrorLogVo);
                continue;
            } else {
                if (queryGood.getVerifyStatus().equals(2) || queryGood.getVerifyStatus().equals(3) ||
                        (queryGood.getPublishStatus() != null && queryGood.getPublishStatus())) {
                    excelImportErrorLogVo.setRowNumber(i + 1);
                    excelImportErrorLogVos.add(excelImportErrorLogVo);
                    excelImportErrorLogVo.setErrorMessage(String.format("上架、待审核、审核通过的商品不能导入！"));
                    continue;
                }
            }

            //活动成本
            BigDecimal activityCostRate = new BigDecimal(rowDataList.get(1));

            //让利成本
            BigDecimal profitsRate = new BigDecimal(rowDataList.get(2));

            //推广成本
            BigDecimal generalizeCostRate = new BigDecimal(rowDataList.get(3));

            //会员等级购买权限
            PmMemberLevelPo memberLevelPo = new PmMemberLevelPo();
            memberLevelPo.setLevelName(rowDataList.get(4));
            PmMemberLevelPo queryMemberLevel = memberLevelService.getOne(new QueryWrapper<>(memberLevelPo));
            if (queryMemberLevel == null) {
                excelImportErrorLogVo.setErrorMessage(String.format("会员等级名称【%s】不存在!", rowDataList.get(4)));
                excelImportErrorLogVo.setRowNumber(i + 1);
                excelImportErrorLogVos.add(excelImportErrorLogVo);
                continue;
            }

            //排序数字
            BigDecimal sort = new BigDecimal(rowDataList.get(5));

            //税率选择
            Integer taxRateType = 3;
            BigDecimal customTaxRate = null;
            if ("保税仓".equals(queryGood.getGoodsType()) || "海外直邮".equals(queryGood.getGoodsType())) {
                if (StringUtils.isBlank(rowDataList.get(6))) {
                    excelImportErrorLogVo.setErrorMessage(String.format("保税仓与海外直邮必须选择税率类型"));
                    excelImportErrorLogVo.setRowNumber(i + 1);
                    excelImportErrorLogVos.add(excelImportErrorLogVo);
                    continue;
                } else {
                    if (rowDataList.get(6).equals("自定义税率")) {
                        if (StringUtils.isBlank(rowDataList.get(7))) {
                            excelImportErrorLogVo.setErrorMessage(String.format("选择了自定义税率就必须填写具体的税率"));
                            excelImportErrorLogVo.setRowNumber(i + 1);
                            excelImportErrorLogVos.add(excelImportErrorLogVo);
                            continue;
                        } else {
                            taxRateType = 2;
                            customTaxRate = new BigDecimal(rowDataList.get(7));
                        }
                    } else {
                        //获得该商品所属分类的税率
                        PmGoodsCategoryPo queryCategory = categoryService.getById(queryGood.getGoodsCategoryId());
                        taxRateType = 1;
                        customTaxRate = queryCategory.getTaxRate();
                    }
                }
            }

            Boolean isFreePostage = "是".equals(rowDataList.get(8));

            PmGoodsPo updateGood = new PmGoodsPo();
            updateGood.setUpdateBy(getUser().getUsername()).setId(queryGood.getId()).setActivityCostRate(activityCostRate)
                    .setProfitsRate(profitsRate).setGeneralizeCostRate(generalizeCostRate).setSort(sort).setTaxRateType(taxRateType)
                    .setCustomTaxRate(customTaxRate).setIsFreePostage(isFreePostage).setMemberLevelId(queryMemberLevel.getId());

            pmGoodsService.updateById(updateGood);

        }

        return excelImportErrorLogVos;
    }


}


