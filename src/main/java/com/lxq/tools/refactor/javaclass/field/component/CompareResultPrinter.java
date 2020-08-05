package com.lxq.tools.refactor.javaclass.field.component;

import com.lxq.tools.refactor.javaclass.field.dto.*;

import java.util.List;
import java.util.Set;

/**
 * @author lixiaoqiang
 * @create 2020/8/2 10:33
 */
public class CompareResultPrinter {
    public static void print(Fields fromFields, Fields toFields) {
        CompareFieldRule rule = new CompareFieldRule().setOrderFields(true);
        CompareFieldResult result = new FileldComparator()
                .compare(fromFields, toFields, rule);
        CompareResultPrinter.print(result, rule);
    }

    public static void print(Class from, Class to) {
        CompareFieldRule rule = new CompareFieldRule().setOrderFields(true);
        CompareFieldResult result = new FileldComparator()
                .compare(from, to, rule);
        CompareResultPrinter.print(result, rule);
    }

    public static void print(CompareFieldResult compareFieldResult, CompareFieldRule rule) {
        if (rule.isOrderFields()) {
            compareFieldResult.resetOrder();
        }

        printCustomClass(compareFieldResult.getFromFields().getHasReadClassSet(), "from custom class");
        printCustomClass(compareFieldResult.getToFields().getHasReadClassSet(), "to custom class");
        print(compareFieldResult.getEqualsFields(), "equals");
        print(compareFieldResult.getOnlyFromHasFields(), "only from");
        print(compareFieldResult.getOnlyToHasFields(), "only to");
        printAnnotation(compareFieldResult.getNonEqualsAnnotation(), "diff annotation");
        printDiffType(compareFieldResult.getNonEqualsType(), "diff type");

        System.out.println("\n------------------- 数字统计" + " -------------------");
        System.out.println(String.format("equals = %s", compareFieldResult.getEqualsFields().size()));
        System.out.println(String.format("only from = %s", compareFieldResult.getOnlyFromHasFields().size()));
        System.out.println(String.format("only to = %s", compareFieldResult.getOnlyToHasFields().size()));
        System.out.println(String.format("diff annotation = %s", compareFieldResult.getNonEqualsAnnotation().size()));
    }

    private static void printAnnotation(List<CompareResultAnnotation> nonEqualsAnnotation,
                                        String title) {
        System.out.println("\n------------------- " + title + "(total: " + nonEqualsAnnotation.size() + ")" + " -------------------");
        nonEqualsAnnotation.forEach(item -> System.out.println(item.getField()
                + "\t" + item.getType()
                + "\t" + item.getValue()));
    }

    private static void print(List<CompareResultItem> items, String title) {
        System.out.println("\n------------------- " + title + "(total: " + items.size() + ")" + " -------------------");
        items.forEach(item -> System.out.println(item.getFieldWithParent()));
    }

    private static void printDiffType(List<CompareResultFieldType> items, String title) {
        System.out.println("\n------------------- " + title + "(total: " + items.size() + ")" + " -------------------");
        items.forEach(item -> System.out.println(item.getField() + "\t" + item.getFromType() + "\t" + item.getToType()));
    }

    private static void printCustomClass(Set<Class> classSet, String title) {
        System.out.println("\n------------------- " + title + "(total: " + classSet.size() + ")" + " -------------------");
        classSet.forEach(item -> System.out.println("\"" + item.getName() + "\","));
    }
}
