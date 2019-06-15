package com.chauncy.data.dto.manage.good.base;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.validation.constraints.Min;

/**查询分页列表基础属性
 * @Author zhangrt
 * @Date 2019/6/8 22:49
 **/
@Data
public class BaseSearchDto {

    private Boolean enabled;

    private String name;
    @Min(1)
    private Integer pageNo;
    @Min(1)
    private Integer pageSize;

}
