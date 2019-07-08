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
    * app用户反馈列表
    * </p>
*
* @author huangwancheng
* @since 2019-07-06
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("mm_feed_back")
    @ApiModel(value="MmFeedBackPo对象", description="app用户反馈列表")
    public class MmFeedBackPo implements Serializable {

    private static final long serialVersionUID = 1L;

            @ApiModelProperty(value = "意见反馈ID")
            @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

            @ApiModelProperty(value = "创建者")
    private String createBy;

            @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

            @ApiModelProperty(value = "删除标志 1-删除 0未删除")
    private Boolean delFlag;

            @ApiModelProperty(value = "app用户ID")
    private Long userId;

            @ApiModelProperty(value = "反馈内容")
    private String content;


}
