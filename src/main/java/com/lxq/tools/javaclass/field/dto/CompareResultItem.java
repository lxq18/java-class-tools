package com.lxq.tools.javaclass.field.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 比较结果
 *
 * @author lixiaoqiang
 * @create 2020/8/2 10:32
 */
@Getter
@Setter
@Accessors(chain = true)
public class CompareResultItem implements Comparable{
    private FieldAnnotation fieldAnnotation;
    private String fieldWithParent;
    private String type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompareResultItem that = (CompareResultItem) o;

        return fieldWithParent != null ? fieldWithParent.equals(that.fieldWithParent) : that.fieldWithParent == null;
    }

    @Override
    public int hashCode() {
        return fieldWithParent != null ? fieldWithParent.hashCode() : 0;
    }

    @Override
    public int compareTo(Object o) {
        CompareResultItem target = (CompareResultItem) o;
        return this.getFieldWithParent().compareTo(target.getFieldWithParent());
    }
}
