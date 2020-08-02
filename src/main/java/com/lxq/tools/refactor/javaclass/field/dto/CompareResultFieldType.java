package com.lxq.tools.refactor.javaclass.field.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 字段类型
 *
 * @author lixiaoqiang
 * @create 2020/8/1 21:00
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class CompareResultFieldType {
    private String field;
    private String fromType;
    private String toType;

    public CompareResultFieldType(String field, String fromType, String toType) {
        this.field = field;
        this.fromType = fromType;
        this.toType = toType;
    }
}
