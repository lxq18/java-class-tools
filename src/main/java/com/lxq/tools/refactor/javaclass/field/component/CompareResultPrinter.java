package com.lxq.tools.refactor.javaclass.field.component;

import com.lxq.tools.refactor.javaclass.field.dto.CompareFieldResult;
import com.lxq.tools.refactor.javaclass.field.dto.CompareFieldRule;
import com.lxq.tools.refactor.javaclass.field.dto.CompareResultAnnotation;
import com.lxq.tools.refactor.javaclass.field.dto.CompareResultItem;

import java.util.List;

/**
 * @author lixiaoqiang
 * @create 2020/8/2 10:33
 */
public class CompareResultPrinter {
    public static void print(CompareFieldResult compareFieldResult, CompareFieldRule rule) {
        if (rule.isOrderFields()) {
            compareFieldResult.resetOrder();
        }

        print(compareFieldResult.getEqualsFields(), "equals");
        print(compareFieldResult.getOnlyFromHasFields(), "only from");
        print(compareFieldResult.getOnlyToHasFields(), "only to");
        printAnnotation(compareFieldResult.getNonEqualsAnnotation(), "diff annotation");
    }

    private static void printAnnotation(List<CompareResultAnnotation> nonEqualsAnnotation,
                                        String title) {
        System.out.println("------------------- " + title + "(total: " + nonEqualsAnnotation.size() + ")" + " -------------------");
        nonEqualsAnnotation.forEach(item -> System.out.println(item.getField()
                + "\t" + item.getType()
                + "\t" + item.getValue()));
    }

    private static void print(List<CompareResultItem> items, String title) {
        System.out.println("------------------- " + title + "(total: " + items.size() + ")" + " -------------------");
        items.forEach(item -> {
            System.out.println(item.getFieldWithParent());
        });
    }
}
