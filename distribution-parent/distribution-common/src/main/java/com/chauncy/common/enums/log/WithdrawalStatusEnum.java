package com.chauncy.common.enums.log;

import com.chauncy.common.enums.BaseEnum;

import java.util.Objects;

/**
 * @author yeJH
 * @since 2019/7/29 21:42
 */
public enum WithdrawalStatusEnum  implements BaseEnum {

    /**
     * 提现状态
     * 1.待审核   用户确定提现
     * 2.处理中   平台审核通过显示为处理中  财务线下打款
     * 3.提现成功   打款完成由财务确认提现成功
     * 4.驳回   平台审核不通过显示为驳回
     */
    TO_BE_AUDITED(1, "待审核"),
    PROCESSING (2, "处理中"),
    WITHDRAWAL_SUCCESS(3, "提现成功"),
    AUDIT_FAIL(4, "驳回"),
    ;


    private Integer id;
    private String name;
    WithdrawalStatusEnum(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString(){
        return this.getName();
    }

    public static String value(String name){
        return name;
    }

    //通过Id获取结果
    public static WithdrawalStatusEnum getById(Integer id) {
        for (WithdrawalStatusEnum type : WithdrawalStatusEnum.values()) {
            if (type.getId() == id)
                return type;
        }
        return null;
    }
  public static WithdrawalStatusEnum fromName(String name) {
        for (WithdrawalStatusEnum type : WithdrawalStatusEnum.values()) {
            if (type.name().equals(name))
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
