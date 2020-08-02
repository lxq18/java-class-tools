package com.lxq.tools.refactor.javaclass.field.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 字段注解
 *
 * @author lixiaoqiang
 * @create 2020/8/1 21:00
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class CompareResultAnnotation {
    private String field;
    private String type;
    private String value;

    public CompareResultAnnotation(String field, String type, String value) {
        this.field = field;
        this.type = type;
        this.value = value;
    }

    public CompareResultAnnotation(String type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompareResultAnnotation that = (CompareResultAnnotation) o;

        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
