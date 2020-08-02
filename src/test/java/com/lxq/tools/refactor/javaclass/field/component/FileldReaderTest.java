package com.lxq.tools.refactor.javaclass.field.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lxq.tools.refactor.javaclass.field.dto.CompareFieldRule;
import com.lxq.tools.refactor.javaclass.field.dto.Fields;
import com.lxq.tools.refactor.javaclass.model.Article2;
import org.junit.Test;


public class FileldReaderTest {
    @Test
    public void readAllFields() throws Exception {
        Fields fields = new FileldReader(new CompareFieldRule().setOrderFields(true))
                .readAllFields(Article2.class);
        System.out.println(JSON.toJSONString(fields, SerializerFeature.PrettyFormat));
    }
}