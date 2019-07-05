package com.chauncy.data.domain.po.store.rel;

    import com.baomidou.mybatisplus.annotation.TableName;
    import com.baomidou.mybatisplus.annotation.IdType;
    import java.time.LocalDateTime;
    import com.baomidou.mybatisplus.annotation.TableId;
    import java.io.Serializable;
    import io.swagger.annotations.ApiModel;
    import io.swagger.annotations.ApiModelProperty;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

/**
* <p>
    * 用户关注店铺关联信息表
    * </p>
*
* @author huangwancheng
* @since 2019-07-02
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("sm_rel_user_foucs_store")
    @ApiModel(value="SmRelUserFoucsStorePo对象", description="用户关注店铺关联信息表")
    public class SmRelUserFoucsStorePo implements Serializable {

    private static final long serialVersionUID = 1L;

            @ApiModelProperty(value = "主键id")
            @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

            @ApiModelProperty(value = "店铺id")
    private Long storeId;

            @ApiModelProperty(value = "用户 id")
    private Long userId;

            @ApiModelProperty(value = "创建者")
    private String createBy;

            @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

            @ApiModelProperty(value = "修改者")
    private String updateBy;

            @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

            @ApiModelProperty(value = "删除标志 1-删除 0未删除")
    private Boolean delFlag;


}
