package com.lxq.tools.javaclass.field.component;

import com.lxq.tools.javaclass.field.dto.CompareFieldResult;
import com.lxq.tools.javaclass.field.dto.CompareResultAnnotation;
import com.lxq.tools.javaclass.field.dto.CompareResultItem;

import java.util.List;

/**
 * @author lixiaoqiang
 * @create 2020/8/2 10:33
 */
public class CompareResultPrinter {
    public static void print(CompareFieldResult compareFieldResult) {
        compareFieldResult.resetOrder();

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
