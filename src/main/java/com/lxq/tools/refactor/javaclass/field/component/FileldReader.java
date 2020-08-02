package com.lxq.tools.refactor.javaclass.field.component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.lxq.tools.refactor.javaclass.field.dto.CompareFieldRule;
import com.lxq.tools.refactor.javaclass.field.dto.FieldAnnotation;
import com.lxq.tools.refactor.javaclass.field.dto.FieldItem;
import com.lxq.tools.refactor.javaclass.field.dto.Fields;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

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
        return readAllFields(obj, null);
    }

    public Fields readAllFields(Class<?> obj, FieldItem superFieldItem) {
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
        List<Field> javaFieldList = Arrays.asList(javaFields);
        //强制顺序，循环引入落入的那个字段尽量统一
        Collections.sort(javaFieldList, Comparator.comparing(Field::getName));
        for (Field f : javaFieldList) {
            log.debug("process " + f.getName());
            FieldItem aField = readField(f);
            if (aField == null) {
                continue;
            }
            aField.setSuperFieldItem(superFieldItem);
            result.getItems()
                    .add(aField);
        }

        //解析类过程中会设置多次，以最后一次为准
        result.setTotalCustomClassNum(this.hasReadClassSet.size());
        result.setHasReadClassSet(this.hasReadClassSet);
        if (rule.isOrderFields()) {
            result.resetOder();
        }
        return result;
    }

    private FieldItem readField(Field javaField) {
        Type javaFieldType = javaField.getGenericType();
        FieldItem aFieldItem = new FieldItem();
        aFieldItem.setName(javaField.getName());
        aFieldItem.setClazz(javaField.getDeclaringClass().getName());
        aFieldItem.setType(javaFieldType.getTypeName());

        //注解
        setAnnotation(javaField, aFieldItem);

        //array
        if (javaField.getType().isArray()) {
            aFieldItem.setSubFields(readAllFields(((Class<?>) javaFieldType)
                    .getComponentType(), aFieldItem)
                    .getItems());
            return aFieldItem;
        }

        //泛型
        if (javaFieldType instanceof ParameterizedType) {
            initForGenericType(javaField, aFieldItem);
            return aFieldItem;
        }

        //int、float、Integer、fastjson等
        if (basicType(javaFieldType)
                || javaFieldType.getTypeName().equals(JSONObject.class.getName())
                || javaFieldType.getTypeName().equals(JSONArray.class.getName())
                ) {
            return aFieldItem;
        }

        //Logger直接忽略
        if (javaFieldType.getTypeName().equals(Logger.class.getName())) {
            return null;
        }

        //自定义类
        aFieldItem.setSubFields(readAllFields((Class<?>) javaFieldType, aFieldItem)
                .getItems());
        return aFieldItem;
    }

    private void setAnnotation(Field javaField, FieldItem fieldItem) {
        if (!rule.isIgnoreFieldAnnotation()
                && JsonProperty.class.getName().equals(rule.getFieldAnnotation().getName())) {
            JsonProperty jsonProperty = javaField.getAnnotation(JsonProperty.class);
            if (jsonProperty != null) {
                fieldItem.setAnnotation(new FieldAnnotation(
                        jsonProperty.annotationType().getName(),
                        jsonProperty.value()
                ));
            }
        }
    }

    private void initForGenericType(Field javaField, FieldItem fieldItem) {
        Type genericType = javaField.getGenericType();
        List<Class<?>> target = Lists.newArrayList();
        addType(genericType, target);
        target.forEach(t -> fieldItem.getSubFieldsForGenericType()
                .add(readAllFields(t, fieldItem).getItems())
        );
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
