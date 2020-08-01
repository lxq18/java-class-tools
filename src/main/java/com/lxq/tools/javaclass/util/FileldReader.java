package com.lxq.tools.javaclass.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.lxq.tools.javaclass.dto.CompareFieldRule;
import com.lxq.tools.javaclass.dto.FieldAnnotation;
import com.lxq.tools.javaclass.dto.FieldItem;
import com.lxq.tools.javaclass.dto.Fields;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

/**
 * @author lixiaoqiang
 * @create 2020/8/1 17:18
 */
@Slf4j
public class FileldReader {
    private CompareFieldRule rule;
    private Set<Class> hasReadClassSet = Sets.newHashSet();

    public FileldReader() {
        this.rule = new CompareFieldRule();
    }

    public FileldReader(CompareFieldRule rule) {
        this.rule = rule;
    }

    public Fields readAllFields(Class<?> obj) {
        Fields result = new Fields();

        //之前如果解析过，则之后就不解析了，避免类循环引用发生死循环
        if (hasReadClassSet.contains(obj)) {
            return result;
        }

        if (basicType(obj)) {
            return result;
        }

        hasReadClassSet.add(obj);
        Field[] javaFields = obj.getDeclaredFields();
        for (Field f : javaFields) {
            log.debug("process " + f.getName());
            FieldItem aField = readField(f);
            result.getItems()
                    .add(aField);
        }

        //解析类过程中会设置多次，以最后一次为准
        result.setTotalCustomClassNum(this.hasReadClassSet.size());
        if (rule.isOrderFields()) {
            result.resetOder();
        }
        return result;
    }

    private FieldItem readField(Field javaField) {
        Type javaFieldType = javaField.getGenericType();
        FieldItem item = new FieldItem();
        item.setName(javaField.getName());
        item.setType(javaFieldType);

        //注解
        setAnnotation(javaField, item);

        //array
        if (javaField.getType().isArray()) {
            item.setSubFields(
                    readAllFields(((Class<?>) javaFieldType).getComponentType()).getItems());
            return item;
        }

        //泛型
        if (javaFieldType instanceof ParameterizedType) {
            initForGenericType(javaField, item);
            return item;
        }

        //int、float、Integer、fastjson等
        if (basicType(javaFieldType)
                || javaFieldType instanceof JSONObject
                || javaFieldType instanceof JSONArray
                ) {
            return item;
        }

        //自定义类
        item.setSubFields(
                readAllFields((Class<?>) javaFieldType)
                        .getItems());
        return item;
    }

    private void setAnnotation(Field javaField, FieldItem item) {
        if (!rule.isIgnoreFieldAnnotation()
                && JsonProperty.class.getName().equals(rule.getFieldAnnotation().getName())) {
            JsonProperty jsonProperty = javaField.getAnnotation(JsonProperty.class);
            if (jsonProperty != null) {
                item.setAnnotation(new FieldAnnotation(
                        jsonProperty.annotationType().getName(),
                        jsonProperty.value()
                ));
            }
        }
    }

    private void initForGenericType(Field javaField, FieldItem item) {
        Type genericType = javaField.getGenericType();
        List<Class<?>> target = Lists.newArrayList();
        addType(genericType, target);
        target.forEach(t -> {
            item.getSubFieldsForGenericType().add(
                    readAllFields(t)
                            .getItems());
        });
    }

    private void addType(Type type, List<Class<?>> target) {
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] types = parameterizedType.getActualTypeArguments();
            for (int i = 0; i < types.length; i++) {
                addType(types[i], target);
            }
            return;
        }

        if (basicType(type)) {
            return;
        }

        target.add((Class<?>) type);
    }

    private boolean basicType(Type javaFieldType) {
        return
                //基础类型
                ((Class) javaFieldType).isPrimitive()
                        //String、Boolean等
                        || javaFieldType.getTypeName().startsWith("java.lang")
                        //Date等
                        || javaFieldType.getTypeName().equals("java.util.Date");
    }
}
