package com.chauncy.data.domain.base;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 树形结构
 *
 * @Author zhangrt
 * @Date 2019/6/17 14:14
 **/
@Data
public class BaseTreeObj<E, ID extends Serializable> implements Serializable {
    private static final long serialVersionUID = 1L;
    private ID id;
    private ID parentId;
    private List<E> childList;
}