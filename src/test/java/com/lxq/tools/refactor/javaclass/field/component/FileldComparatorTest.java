package com.lxq.tools.refactor.javaclass.field.component;

import com.lxq.tools.refactor.javaclass.field.dto.CompareFieldResult;
import com.lxq.tools.refactor.javaclass.field.dto.CompareFieldRule;
import com.lxq.tools.refactor.javaclass.model.Article1;
import com.lxq.tools.refactor.javaclass.model.Article2;
import org.junit.Test;

import java.util.Arrays;

public class FileldComparatorTest {
    @Test
    public void compare() throws Exception {
        CompareResultPrinter.print(Article2.class, Article1.class);
    }

    @Test
    public void compareWithIgnore() throws Exception {
        //ignore后比较
        CompareFieldRule rule = (new CompareFieldRule())
                .setOrderFields(true);
        rule.getIgnoreFromClassList().addAll(Arrays.asList(
                "com.lxq.tools.refactor.javaclass.model.CommentInfo2",
                "com.lxq.tools.refactor.javaclass.model.User2"
        ));
        rule.getIgnoreToClassList().addAll(Arrays.asList(
                "com.lxq.tools.refactor.javaclass.model.CommentInfo1"
        ));

        CompareFieldResult result = (new FileldComparator()).compare(Article2.class, Article1.class, rule);
        CompareResultPrinter.print(result, rule);
    }
}