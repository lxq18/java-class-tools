package com.lxq.tools.refactor.javaclass.field.component;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.lxq.tools.refactor.javaclass.field.dto.*;

import java.util.List;
import java.util.Set;

/**
 * @author lixiaoqiang
 * @create 2020/8/1 11:11
 */
public class FileldComparator {

    public CompareFieldResult compare(Class from, Class to, CompareFieldRule rule) {
        Fields fromFields = new FileldReader(rule).readAllFields(from);
        Fields toFields = new FileldReader(rule).readAllFields(to);
        return compare(fromFields, toFields, rule);
    }

    public CompareFieldResult compare(Fields fromFields, Fields toFields, CompareFieldRule rule) {
        CompareFieldResult result = new CompareFieldResult();

        List<CompareResultItem> fromCompareResult = buildItemString(fromFields);
        List<CompareResultItem> toCompareResult = buildItemString(toFields);

        result.setFromFields(fromFields);
        result.setToFields(toFields);

        //compare result
        Set<CompareResultItem> allCompareResult = Sets.newLinkedHashSet();
        fromCompareResult.forEach(item -> {
            if (rule.getIgnoreFromClassList().contains(item.getClazz())) {
                return;
            }
            allCompareResult.add(item);
        });
        toCompareResult.forEach(item -> {
            if (rule.getIgnoreToClassList().contains(item.getClazz())) {
                return;
            }
            allCompareResult.add(item);
        });
        allCompareResult.forEach(checkItem -> {
            CompareResultItem fromResult = getByName(fromCompareResult, checkItem.getFieldWithParent());
            CompareResultItem toResult = getByName(toCompareResult, checkItem.getFieldWithParent());

            //字段相同
            if (fromResult != null && toResult != null) {
                result.getEqualsFields().add(checkItem);

                //字段类型
                if (!checkBasicTypeEquals(fromResult.getType(), toResult.getType())) {
                    result.getNonEqualsType().add(new CompareResultFieldType(checkItem.getFieldWithParent(),
                            fromResult.getType(),
                            toResult.getType()));
                }

                //annotation
                if (fromResult.getFieldAnnotation() == null
                        || toResult.getFieldAnnotation() == null) {
                    return;
                }
                if (fromResult.getFieldAnnotation() != null
                        || toResult.getFieldAnnotation() == null) {
                    result.getNonEqualsAnnotation().add(
                            new CompareResultAnnotation(
                                    checkItem.getFieldWithParent(),
                                    fromResult.getFieldAnnotation().getType(),
                                    fromResult.getFieldAnnotation().getValue()
                            ));
                    return;
                }
                if (fromResult.getFieldAnnotation() == null
                        || toResult.getFieldAnnotation() != null) {
                    result.getNonEqualsAnnotation().add(
                            new CompareResultAnnotation(
                                    checkItem.getFieldWithParent(),
                                    toResult.getFieldAnnotation().getType(),
                                    toResult.getFieldAnnotation().getValue()
                            ));
                    return;
                }
                return;
            }

            //only from
            if (fromResult != null && toResult == null) {
                result.getOnlyFromHasFields().add(checkItem);
                return;
            }

            //only to
            if (fromResult == null && toResult != null) {
                result.getOnlyToHasFields().add(checkItem);
                return;
            }
        });

        return result;
    }


    private CompareResultItem getByName(List<CompareResultItem> items, String name) {
        for (CompareResultItem item : items) {
            if (name.equals(item.getFieldWithParent())) {
                return item;
            }
        }
        return null;
    }

    /**
     * 比较int/Integer等 基本类型
     *
     * @param fromType
     * @param toType
     * @return 自定义类型直接返回true
     */
    private boolean checkBasicTypeEquals(String fromType, String toType) {
        if (fromType.equals(toType)) {
            return true;
        }
        if (fromType.equals("java.util.Map") && toType.equals("com.alibaba.fastjson.JSONObject")) {
            return false;
        }
        if (fromType.equals("com.alibaba.fastjson.JSONObject") && toType.equals("java.util.Map")) {
            return false;
        }

        Boolean x = compareBasicAndPack(fromType, toType);
        if (x != null) return x;

        String javaLang = "java.lang";
        if (fromType.startsWith(javaLang)
                && toType.startsWith(javaLang)) {
            return fromType.equals(toType);
        }

        return true;
    }

    /**
     * 基本类型和包装类型比较
     *
     * @param fromType
     * @param toType
     * @return
     */
    private Boolean compareBasicAndPack(String fromType, String toType) {
        if (!fromType.contains(".") && !toType.contains(".")) {
            return fromType.equals(toType);
        }
        if (!fromType.contains(".") && toType.contains(".")) {
            return false;
        }
        if (fromType.contains(".") && !toType.contains(".")) {
            return false;
        }
        return null;
    }

    private List<CompareResultItem> buildItemString(Fields fields) {
        List<CompareResultItem> target = Lists.newArrayList();
        addTarget(fields.getItems(), target);
        return target;
    }

    private void addTarget(List<FieldItem> fieldItems, List<CompareResultItem> target) {
        fieldItems.forEach(item -> {
            target.add(new CompareResultItem()
                    .setFieldWithParent(
                            item.buildNameWithParent())
                    .setType(item.getType())
                    .setClazz(item.getClazz())
                    .setFieldAnnotation(item.getAnnotation())
            );
            addTarget(item.getSubFields(), target);
            item.getSubFieldsForGenericType().forEach(generic -> addTarget(generic, target));
        });
    }
}
