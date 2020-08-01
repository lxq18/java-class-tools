package com.lxq.tools.javaclass.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lxq.tools.javaclass.dto.CompareFieldResult;
import com.lxq.tools.javaclass.model.Article1;
import com.lxq.tools.javaclass.model.Article2;
import org.junit.Test;

public class FileldComparatorTest {
    @Test
    public void compare() throws Exception {
        CompareFieldResult result = new FileldComparator().compare(Article2.class, Article1.class);
        String json = JSON.toJSONString(result, SerializerFeature.PrettyFormat);
        System.out.println(json);
    }

}