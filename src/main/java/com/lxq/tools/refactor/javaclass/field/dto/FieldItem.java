package com.lxq.tools.refactor.javaclass.field.dto;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author lixiaoqiang
 * @create 2020/8/1 11:13
 */
@Getter
@Setter
@Accessors(chain = true)
public class FieldItem implements Comparable<FieldItem> {
    private String name;
    private String type;
    private FieldAnnotation annotation;
    /**
     * 一般业务类型
     */
    private List<FieldItem> subFields = Lists.newArrayList();
    /**
     * 类型信息
     */
    private List<List<FieldItem>> subFieldsForGenericType = Lists.newArrayList();
    /**
     * 父级
     */
    private FieldItem superFieldItem;

    public String buildNameWithParent() {
        return superFieldItem == null ? getName()
                : superFieldItem.buildNameWithParent() + "\t\t." + getName();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        FieldItem fieldItem = (FieldItem) o;

        if (name != null ? !name.equals(fieldItem.name) : fieldItem.name != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(FieldItem o) {
        if (this.name == null
                || o.getName() == null) {
            return 0;
        }
        return this.name.compareTo(o.getName());
    }
}
