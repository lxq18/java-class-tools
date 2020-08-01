package com.lxq.tools.javaclass.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 字段比较规则
 *
 * @author lixiaoqiang
 * @create 2020/8/1 19:46
 */
@Data
@Accessors(chain = true)
public class CompareFieldRule {
    /**
     * 不进行比较的类型包名
     */
    private String notCompareRootPackage;

    /**
     * 打印相同字段
     */
    private boolean printEqualField;

    /**
     * 字段注释注解
     * 目前只支持：com.fasterxml.jackson.annotation.JsonProperty
     */
    private Class fieldAnnotation;

    /**
     * 忽略字段注解
     */
    private boolean ignoreFieldAnnotation;
    /**
     * 排序所有字段
     */
    private boolean orderFields;

    public CompareFieldRule() {
        this.printEqualField = true;
        this.orderFields = true;
        this.fieldAnnotation = JsonProperty.class;
    }
}
