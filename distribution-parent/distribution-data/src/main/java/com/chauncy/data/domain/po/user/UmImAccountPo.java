package com.chauncy.data.domain.po.user;

    import com.baomidou.mybatisplus.annotation.TableName;
    import com.baomidou.mybatisplus.annotation.IdType;
    import java.time.LocalDateTime;
    import com.baomidou.mybatisplus.annotation.TableId;
    import com.baomidou.mybatisplus.annotation.TableLogic;
    import java.io.Serializable;
    import io.swagger.annotations.ApiModel;
    import io.swagger.annotations.ApiModelProperty;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

/**
* <p>
    * 用户IM账号
    * </p>
*
* @author huangwancheng
* @since 2019-12-18
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("um_im_account")
    @ApiModel(value="UmImAccountPo对象", description="用户IM账号")
    public class UmImAccountPo implements Serializable {

    private static final long serialVersionUID = 1L;

            @ApiModelProperty(value = "ID")
            @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

            @ApiModelProperty(value = "IM账号")
    private String imAccount;

            @ApiModelProperty(value = "店铺id")
    private Long storeId;

            @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

            @ApiModelProperty(value = "删除标志 1-删除 0未删除")
        @TableLogic
    private Boolean delFlag;


}
