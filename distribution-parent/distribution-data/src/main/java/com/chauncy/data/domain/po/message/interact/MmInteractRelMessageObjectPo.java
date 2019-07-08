package com.chauncy.data.domain.po.message.interact;

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
    * 推送信息与推送对象关联表
    * </p>
*
* @author huangwancheng
* @since 2019-07-08
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("mm_interact_rel_message_object")
    @ApiModel(value="MmInteractRelMessageObjectPo对象", description="推送信息与推送对象关联表")
    public class MmInteractRelMessageObjectPo implements Serializable {

    private static final long serialVersionUID = 1L;

            @ApiModelProperty(value = "推送信息与推送对象关联ID")
            @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

            @ApiModelProperty(value = "创建者")
    private String createBy;

            @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

            @ApiModelProperty(value = "推送信息ID")
    private Long pushId;

            @ApiModelProperty(value = "推送对象id")
    private Long objectId;


}
