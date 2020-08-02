package com.lxq.tools.javaclass.field.dto;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Collections;
import java.util.List;

/**
 * @author lixiaoqiang
 * @create 2020/8/1 11:32
 */
@Getter
@Setter
@Accessors(chain = true)
public class Fields {
    /**
     * 总自定义类的数量
     */
    private int totalCustomClassNum;
    private List<FieldItem> items = Lists.newArrayList();

    public boolean containsField(FieldItem fieldItem) {
        return false;
    }

    public void resetOder() {
        resetOder(this.items);
    }

    public void resetOder(List<FieldItem> items) {
        Collections.sort(items);
        items.forEach(sub -> {
            if (sub.getSubFields() != null) {
                resetOder(sub.getSubFields());
            }
            sub.getSubFieldsForGenericType().forEach(subGeneric -> {
                resetOder(subGeneric);
            });
        });
    }

    @Override
    public String toString() {
        return "Fields{" +
                "totalCustomClassNum=" + totalCustomClassNum +
                ", itemsSize=" + items.size() +
                '}';
    }
}
