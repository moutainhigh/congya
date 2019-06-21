package com.chauncy.web.api.supplier.product;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.util.ListUtil;
import com.chauncy.common.util.LoggerUtil;
import com.chauncy.common.util.StringUtils;
import com.chauncy.data.domain.po.product.*;
import com.chauncy.data.temp.product.service.IPmGoodsRelAttributeValueGoodService;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.excel.ExcelImportErrorLogVo;
import com.chauncy.poi.util.ReadExcelUtil;
import com.chauncy.product.service.*;
import com.chauncy.web.base.BaseApi;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author zhangrt
 * @Date 2019/6/17 17:46
 **/

@RestController
@Api(description = "商家端商品管理")
@RequestMapping("/supplier/product")
public class ExcelGoodApi extends BaseApi {

    //导入商品基本属性的列数
    private final int BASE_GOOD_ROW_NUMBER = 18;

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


    @PostMapping(value = "/importbase", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "导入商品基本信息、调用这个接口前先调用上传文件到千牛云的接口获取路径")
    @Transactional(rollbackFor = Exception.class)
    @SuppressWarnings("unchecked")
    public JsonViewData<List<ExcelImportErrorLogVo>> importBase(@RequestParam(value = "file") MultipartFile file) throws InstantiationException, IllegalAccessException, IOException {

        List<List<String>> excelData;
        try {
            excelData = ReadExcelUtil.readExcelInfo(file.getInputStream());
            if (ListUtil.isListNullAndEmpty(excelData)) {
                return setJsonViewData(ResultCode.FAIL, "获取excel数据失败！");
            }
        } catch (Exception e) {
            LoggerUtil.error(e);
            return setJsonViewData(ResultCode.FAIL, "获取excel数据失败！");
        }
        List<ExcelImportErrorLogVo> excelImportErrorLogVos= saveAndGetErrorLogs(excelData);
        return new JsonViewData(ResultCode.SUCCESS,
                "操作成功，共有"+excelImportErrorLogVos.size()+"条导入失败！",
                excelImportErrorLogVos
                );
    }



    /**
     * 插入数据库并获取出错日志
     *
     * @param excelDataList
     * @return
     */
    private List<ExcelImportErrorLogVo> saveAndGetErrorLogs(List<List<String>> excelDataList) throws IllegalAccessException,InstantiationException {
        //excel导入出错记录
        List<ExcelImportErrorLogVo> excelImportErrorLogVos = Lists.newArrayList();

        for (int i = 2; i < excelDataList.size(); i++) {
            ExcelImportErrorLogVo excelImportErrorLogVo = new ExcelImportErrorLogVo();
            //获得整行数据
            List<String> rowDataList = excelDataList.get(i);
            if (rowDataList.size() < BASE_GOOD_ROW_NUMBER) {
                excelImportErrorLogVo.setRowNumber(i+1);
                excelImportErrorLogVo.setErrorMessage("excel列数与模板不符合");
                excelImportErrorLogVos.add(excelImportErrorLogVo);
                continue;
            }
            //判断不为空的
            if (StringUtils.isHasBlank(rowDataList.get(0),rowDataList.get(1),rowDataList.get(2),rowDataList.get(5),
                    rowDataList.get(6),rowDataList.get(7),rowDataList.get(8),rowDataList.get(15),rowDataList.get(16))){
                excelImportErrorLogVo.setRowNumber(i+1);
                excelImportErrorLogVo.setErrorMessage("请填写完整excel中红色的列");
                excelImportErrorLogVos.add(excelImportErrorLogVo);
                continue;
            }
            //商品分类
            PmGoodsCategoryPo categoryCondition=new PmGoodsCategoryPo(rowDataList.get(0),3);
            Wrapper categoryPoWrapper=new QueryWrapper<>(categoryCondition,"id");
            PmGoodsCategoryPo categoryPo = categoryService.getOne(categoryPoWrapper);
            if (categoryPo==null){
                excelImportErrorLogVo.setRowNumber(i+1);
                excelImportErrorLogVo.setErrorMessage("系统中商品三级分类不存在该名称");
                excelImportErrorLogVos.add(excelImportErrorLogVo);
                continue;
            }
            //商品品牌
            PmGoodsAttributePo brandCondition=new PmGoodsAttributePo(rowDataList.get(5),8);
            Wrapper brandWrapper=new QueryWrapper<>(brandCondition,"id");
            PmGoodsAttributePo brand = attributePoService.getOne(brandWrapper);
            if (brand==null){
                excelImportErrorLogVo.setRowNumber(i+1);
                excelImportErrorLogVo.setErrorMessage("系统中商品品牌不存在该名称");
                excelImportErrorLogVos.add(excelImportErrorLogVo);
                continue;
            }
            //商品标签
            List<String>  labelNames=Splitter.on(";").splitToList(rowDataList.get(6));
            List<Long> labelIds = pmGoodsService.findIdByNamesInAndTableName(labelNames, "pm_goods_attribute", "and type=5");
            if (ListUtil.isListNullAndEmpty(labelIds)){
                excelImportErrorLogVo.setRowNumber(i+1);
                excelImportErrorLogVo.setErrorMessage("系统中商品标签不存在");
                excelImportErrorLogVos.add(excelImportErrorLogVo);
                continue;
            }

            Boolean recommandStatus="是".equals(rowDataList.get(7));
            Boolean starStatus="是".equals(rowDataList.get(8));

            //平台服务说明与商家服务说明
            if (StringUtils.isAllBlank(rowDataList.get(9),rowDataList.get(10)))
            {
                excelImportErrorLogVo.setRowNumber(i+1);
                excelImportErrorLogVo.setErrorMessage("平台服务说明与商家服务说明不能同时为空");
                excelImportErrorLogVos.add(excelImportErrorLogVo);
                continue;
            }
            List<Long> serviceIds=Lists.newArrayList();
            List<String> serviceNames;
            if (!StringUtils.isAllBlank(rowDataList.get(9))){
                serviceNames=Splitter.on(";").omitEmptyStrings().splitToList(rowDataList.get(9));
                serviceIds=categoryService.findAttributeIdsByNamesAndCategoryId(serviceNames,1,categoryPo.getId());
            }
            else {
                serviceNames=Splitter.on(";").omitEmptyStrings().splitToList(rowDataList.get(10));
                serviceIds=categoryService.findAttributeIdsByNamesAndCategoryId(serviceNames,2,categoryPo.getId());
            }
            if (ListUtil.isListNullAndEmpty(serviceIds)){
                excelImportErrorLogVo.setRowNumber(i+1);
                excelImportErrorLogVo.setErrorMessage("服务说明不存在！");
                excelImportErrorLogVos.add(excelImportErrorLogVo);
                continue;
            }

            //运费模板
            if (StringUtils.isAllBlank(rowDataList.get(11),rowDataList.get(12)))
            {
                excelImportErrorLogVo.setRowNumber(i+1);
                excelImportErrorLogVo.setErrorMessage("平台运费模板与店铺运费模板不能同时为空");
                excelImportErrorLogVos.add(excelImportErrorLogVo);
                continue;
            }
            String shipName;
            Long shipId;
            if (!StringUtils.isAllBlank(rowDataList.get(11))){
                shipName=rowDataList.get(11);
                shipId=categoryService.findIdByNamesInAndTableName(Lists.newArrayList(shipName),"pm_shipping_template","and type=1").get(0);
            }
            else {
                shipName=rowDataList.get(12);
                shipId=categoryService.findIdByNamesInAndTableName(Lists.newArrayList(shipName),"pm_shipping_template","and type=2").get(0);
            }
            if (shipId==null){
                excelImportErrorLogVo.setRowNumber(i+1);
                excelImportErrorLogVo.setErrorMessage("运费模板不存在");
                excelImportErrorLogVos.add(excelImportErrorLogVo);
                continue;
            }

            //商品参数与参数值
            List<String> attributeNames;
            List<String> attributeValues=null;
            List<Long> attributeIds = null;
            if (StringUtils.isNotBlank(rowDataList.get(13))){
                attributeValues=Splitter.on(";").omitEmptyStrings().splitToList(rowDataList.get(14));
                attributeNames=Splitter.on(";").omitEmptyStrings().splitToList(rowDataList.get(13));
                attributeIds = categoryService.findAttributeIdsByNamesAndCategoryId(attributeNames, 4, categoryPo.getId());
            }
            if (ListUtil.isListNullAndEmpty(attributeIds)){
                excelImportErrorLogVo.setRowNumber(i+1);
                excelImportErrorLogVo.setErrorMessage("商品参数不能为空，或者系统中不存在改商品参数");
                excelImportErrorLogVos.add(excelImportErrorLogVo);
                continue;
            }

            List<Long>  goodIds=Splitter.on(";").trimResults().omitEmptyStrings().splitToList(rowDataList.get(17))
                    .stream().map(x->Long.parseLong(x)).collect(Collectors.toList());

            //保存商品表
            PmGoodsPo saveGoodPo=new PmGoodsPo();
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

            pmGoodsService.save(saveGoodPo);

            List<PmGoodsRelAttributeGoodPo>  relAttributeGoodPos=Lists.newArrayList();
            //品牌
            relAttributeGoodPos.add(new PmGoodsRelAttributeGoodPo(brand.getId(),saveGoodPo.getId(),getUser().getUsername()));
            //标签
            for (Long labelId:labelIds){
                relAttributeGoodPos.add(new PmGoodsRelAttributeGoodPo(labelId,saveGoodPo.getId(),getUser().getUsername()));
            }
            //服务说明
            for (Long serviceId:serviceIds){
                relAttributeGoodPos.add(new PmGoodsRelAttributeGoodPo(serviceId,saveGoodPo.getId(),getUser().getUsername()));
            }
            //参数属性与属性值
            List<PmGoodsRelAttributeValueGoodPo> saveRelValueGoods=Lists.newArrayList();
            for (int j=0;j<attributeIds.size();j++){
                Long attributeId=attributeIds.get(j);
                //查出该属性下的属性值是否存在
                PmGoodsAttributeValuePo valueCondition=new PmGoodsAttributeValuePo();
                valueCondition.setProductAttributeId(attributeId);
                valueCondition.setValue(attributeValues.get(j));
                Wrapper<PmGoodsAttributeValuePo> goodsAttributeValuePoWrapper=new QueryWrapper<>(valueCondition,"id");
                PmGoodsAttributeValuePo selectValuePo = valueService.getOne(goodsAttributeValuePoWrapper);
                //不存在则新增为自定义属性值
                if (selectValuePo==null){
                    PmGoodsAttributeValuePo saveValuePo=new PmGoodsAttributeValuePo();
                    saveValuePo.setValue(attributeValues.get(j));
                    saveValuePo.setProductAttributeId(attributeIds.get(j));
                    saveValuePo.setIsCustom(true);
                    saveValuePo.setCreateBy(getUser().getUsername());
                    valueService.save(saveValuePo);
                    saveRelValueGoods.add(new PmGoodsRelAttributeValueGoodPo(saveValuePo.getId(),saveGoodPo.getId(),getUser().getUsername()));
                }
                else {
                    saveRelValueGoods.add(new PmGoodsRelAttributeValueGoodPo(selectValuePo.getId(),saveGoodPo.getId(),getUser().getUsername()));
                }
            }
            relAttributeValueGoodService.saveBatch(saveRelValueGoods);


            if (!ListUtil.isListNullAndEmpty(goodIds)){
                List<PmAssociationGoodsPo> associationGoodsPos=Lists.newArrayList();
                for (Long goodId:goodIds){
                    PmAssociationGoodsPo associationGoodsPo=new PmAssociationGoodsPo(saveGoodPo.getId(),789l,goodId,2,getUser().getUsername());
                    associationGoodsPos.add(associationGoodsPo);
                }
                associationGoodsService.saveBatch(associationGoodsPos);

            }
            relAttributeGoodService.saveBatch(relAttributeGoodPos);
        }
        return excelImportErrorLogVos;
    }

}


