package com.chauncy.data.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 构建树结构模块
 *
 * @author zhangrt
 * @date 2019-06-15
 * @time 10:57
 **/
@Setter
@Getter
@ToString
public abstract class BaseTree<ID extends Comparable<ID>, E extends BaseTree> implements Serializable {

    /**
     * ID标识
     */
    @JSONField(ordinal = 1)
    protected ID id;

    /**
     * 父级ID标识
     */
    @JSONField(ordinal = 2)
    protected ID parentId;

    /**
     * 子节点
     */
    @JSONField(ordinal = 5)
    protected List<E> children;

    /**
     * 构建树
     *
     * @param baseTrees 基础数据，无子节点
     * @param <T>       泛型
     * @return 返回树层次结构
     */
    public static <T extends BaseTree> List<T> build(List<T> baseTrees) {
        if (baseTrees == null || baseTrees.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        //操作副本，避免干预原数据
        List<T> copyList = Lists.newArrayList(baseTrees);
        //取出根节点
        List<T> superParent = copyList.parallelStream().filter(tree -> tree.getParentId() == null).collect(Collectors.toList());
        //所有的子节点
        List<T> children = copyList.parallelStream().filter(tree -> tree.getParentId() != null).collect(Collectors.toList());
        for (T parent : superParent) {
            execute(parent, children);
        }
        return superParent;
    }

    /**
     * 递归层次查找子节点
     *
     * @param parent    父节点
     * @param baseTrees 子节点集
     * @param <T>       泛型
     */
    private static <T extends BaseTree> void execute(BaseTree parent, List<T> baseTrees) {
        List<T> children = baseTrees.parallelStream().filter(b -> b.getParentId().compareTo(parent.id) == 0).collect(Collectors.toList());
        if (children.isEmpty()) {
            return;
        }
        if (Objects.isNull(parent.children)) {
            parent.children = Lists.newArrayListWithCapacity(children.size());
        }
        parent.children.addAll(children);
        for (T tree : children) {
            execute(tree, baseTrees);
        }
    }

    /**
     * 递归深层次排序
     *
     * @param trees      需要排序的集合
     * @param comparator 比较器
     * @param <T>        泛型
     */
    public static <T extends BaseTree> void recursionSort(List<T> trees, Comparator comparator) {
        if (Objects.isNull(trees) || trees.isEmpty()) {
            return;
        }
        trees.sort(comparator);
        for (BaseTree baseTree : trees) {
            recursionSort(baseTree.getChildren(), comparator);
        }
    }

    @Override
    public int hashCode() {
        if (getId() != null) {
            return getId().hashCode();
        }
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BaseTree) {
            BaseTree baseTree = (BaseTree) obj;
            if (baseTree.getId() != null) {
                return baseTree.getId().equals(getId());
            }
        }
        return super.equals(obj);
    }
}
