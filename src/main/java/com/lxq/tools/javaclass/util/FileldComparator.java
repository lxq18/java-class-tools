package com.lxq.tools.javaclass.util;

import com.lxq.tools.javaclass.dto.CompareFieldResult;
import com.lxq.tools.javaclass.dto.FieldItem;
import com.lxq.tools.javaclass.dto.Fields;

/**
 * @author lixiaoqiang
 * @create 2020/8/1 11:11
 */
public class FileldComparator {

    public  CompareFieldResult compare(Class from, Class to) {
        FileldReader fileldReader = new FileldReader();

        CompareFieldResult result = new CompareFieldResult();
        Fields fromFields = fileldReader.readAllFields(from);
        Fields toFields = fileldReader.readAllFields(to);
        Fields allFields = aggregate(fromFields, toFields);

        for (FieldItem f : allFields.getItems()) {
            if (fromFields.containsField(f)
                    && toFields.containsField(f)) {
                result.getEqualsFields().add(f);
                continue;
            }

            if (fromFields.containsField(f)
                    && !toFields.containsField(f)) {
                result.getOnlyFromHasFields().add(f);
                continue;
            }

            if (!fromFields.containsField(f)
                    && toFields.containsField(f)) {
                result.getOnlyToHasFields().add(f);
                continue;
            }

            throw new IllegalArgumentException();
        }

        return result;
    }

    private static Fields aggregate(Fields fromFields, Fields toFields) {
        return null;
    }


}
