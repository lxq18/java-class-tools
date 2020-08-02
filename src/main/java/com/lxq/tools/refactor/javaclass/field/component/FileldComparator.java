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
    public CompareFieldResult compare(Class from, Class to) {
        return compare(from, to, new CompareFieldRule());
    }


    public CompareFieldResult compare(Class from, Class to, CompareFieldRule rule) {
        FileldReader fileldReader = new FileldReader(rule);

        CompareFieldResult result = new CompareFieldResult();

        //from read
        Fields fromFields = fileldReader.readAllFields(from);
        List<CompareResultItem> fromCompareResult = buildItemString(fromFields);

        //to read
        Fields toFields = fileldReader.readAllFields(to);
        List<CompareResultItem> toCompareResult = buildItemString(toFields);

        //compare result
        Set<CompareResultItem> allCompareResult = Sets.newLinkedHashSet();
        allCompareResult.addAll(fromCompareResult);
        allCompareResult.addAll(toCompareResult);
        allCompareResult.forEach(checkItem -> {
            CompareResultItem fromResult = getByName(fromCompareResult, checkItem.getFieldWithParent());
            CompareResultItem toResult = getByName(toCompareResult, checkItem.getFieldWithParent());

            //字段相同
            if (fromResult != null && toResult != null) {
                result.getEqualsFields().add(checkItem);

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

    private boolean containName(List<CompareResultItem> items, CompareResultItem checkItem) {
        for (CompareResultItem item : items) {
            if (checkItem.equals(item)) {
                return true;
            }
        }
        return false;
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
                    .setFieldAnnotation(item.getAnnotation())
            );
            addTarget(item.getSubFields(), target);
            item.getSubFieldsForGenericType().forEach(generic -> addTarget(generic, target));
        });
    }
}
