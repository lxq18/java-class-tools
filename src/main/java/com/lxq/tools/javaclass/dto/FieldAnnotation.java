package com.lxq.tools.javaclass.dto;

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
}
