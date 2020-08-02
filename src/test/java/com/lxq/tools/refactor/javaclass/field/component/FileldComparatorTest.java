package com.lxq.tools.refactor.javaclass.field.component;

import com.lxq.tools.refactor.javaclass.model.Article1;
import com.lxq.tools.refactor.javaclass.model.Article2;
import org.junit.Test;

public class FileldComparatorTest {
    @Test
    public void compare() throws Exception {
        CompareResultPrinter.print(Article2.class, Article1.class);
    }
}