package com.chauncy.data.vo.app.component;

import com.chauncy.data.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author yeJH
 * @Date 2019/9/19 18:42
 * @Description 资讯筛选参数Vo
 *
 * @Update yeJH
 *
 * @Param
 * @return
 **/
@Data
@ApiModel(value = "ScreenInfoParamVo", description =  "资讯筛选参数")
public class ScreenInfoParamVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id是为了满足一对多的查询将品牌，标签，类目分组")
    private Integer id;

    @ApiModelProperty(value = "资讯标签")
    private List<BaseVo> labelList;

    @ApiModelProperty(value = "内容分类")
    private List<BaseVo> categoryList;

}