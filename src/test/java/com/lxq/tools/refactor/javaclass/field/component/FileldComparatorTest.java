package com.lxq.tools.refactor.javaclass.field.component;

import com.lxq.tools.refactor.javaclass.field.dto.CompareFieldResult;
import com.lxq.tools.refactor.javaclass.field.dto.CompareFieldRule;
import com.lxq.tools.refactor.javaclass.model.Article1;
import com.lxq.tools.refactor.javaclass.model.Article2;
import org.junit.Test;

public class FileldComparatorTest {
    @Test
    public void compare() throws Exception {
        CompareFieldRule rule = new CompareFieldRule().setOrderFields(false);
        CompareFieldResult result = new FileldComparator()
                .compare(Article2.class, Article1.class, rule);
        CompareResultPrinter.print(result, rule);
    }

}