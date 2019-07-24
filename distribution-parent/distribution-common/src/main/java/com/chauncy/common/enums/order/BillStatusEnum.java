package com.chauncy.common.enums.order;

import com.chauncy.common.enums.BaseEnum;

import java.util.Objects;

/**
 * @author yeJH
 * @since 2019/7/23 0:11
 */
public enum BillStatusEnum implements BaseEnum {

    /**
     * 账单状态
     * 1.待提现   生成的货款、利润账单的默认状态
     * 2.待审核   用户确定提现
     * 3.处理中   平台审核通过显示为处理中  财务线下打款
     * 4.结算完成   打款完成由财务确认结算完成
     * 5.审核失败   平台审核不通过显示为审核失败
     */
    TO_BE_WITHDRAWN (1, "待提现"),
    TO_BE_AUDITED(2, "待审核"),
    PROCESSING (3, "处理中"),
    SETTLEMENT_SUCCESS(4, "结算完成"),
    AUDIT_FAIL(5, "审核失败"),
    ;


    private Integer id;
    private String name;
    BillStatusEnum(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString(){
        return this.id + "_" + this.name;
    }

    public static String value(String name){
        return name;
    }

    //通过Id获取结果
    public static BillTypeEnum getById(Integer id) {
        for (BillTypeEnum type : BillTypeEnum.values()) {
            if (type.getId() == id)
                return type;
        }
        return null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean isExist(Object field) {

        return Objects.nonNull(getById(Integer.parseInt(field.toString())));
    }


}
