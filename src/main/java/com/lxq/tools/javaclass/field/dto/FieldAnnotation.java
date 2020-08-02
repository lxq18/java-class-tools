package com.lxq.tools.javaclass.field.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 字段注解
 *
 * @author lixiaoqiang
 * @create 2020/8/1 21:00
 */
@Getter
@Setter
@NoArgsConstructor
public class FieldAnnotation {
    private String type;
    private String value;

    public FieldAnnotation(String type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FieldAnnotation that = (FieldAnnotation) o;

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
