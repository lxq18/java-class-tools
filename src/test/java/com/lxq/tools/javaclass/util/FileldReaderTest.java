package com.lxq.tools.javaclass.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lxq.tools.javaclass.component.FileldReader;
import com.lxq.tools.javaclass.dto.CompareFieldRule;
import com.lxq.tools.javaclass.dto.Fields;
import com.lxq.tools.javaclass.model.Article2;
import org.junit.Test;


public class FileldReaderTest {
    @Test
    public void readAllFields() throws Exception {
        Fields fields = new FileldReader(new CompareFieldRule().setOrderFields(true))
                .readAllFields(Article2.class);
        System.out.println(JSON.toJSONString(fields, SerializerFeature.PrettyFormat));
    }
}