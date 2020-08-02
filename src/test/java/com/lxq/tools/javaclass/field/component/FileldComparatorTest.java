package com.lxq.tools.javaclass.field.component;

import com.lxq.tools.javaclass.field.dto.CompareFieldResult;
import com.lxq.tools.javaclass.model.Article1;
import com.lxq.tools.javaclass.model.Article2;
import org.junit.Test;

public class FileldComparatorTest {
    @Test
    public void compare() throws Exception {
        CompareFieldResult result = new FileldComparator().compare(Article2.class, Article1.class);
        CompareResultPrinter.print(result);
    }

}