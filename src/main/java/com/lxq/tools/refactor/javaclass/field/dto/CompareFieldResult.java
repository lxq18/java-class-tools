package com.lxq.tools.refactor.javaclass.field.dto;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * @author lixiaoqiang
 * @create 2020/8/1 11:16
 */
@Data
public class CompareFieldResult {
    private List<CompareResultItem> equalsFields = Lists.newArrayList();
    private List<CompareResultItem> onlyFromHasFields = Lists.newArrayList();
    private List<CompareResultItem> onlyToHasFields = Lists.newArrayList();
    private List<CompareResultAnnotation> nonEqualsAnnotation = Lists.newArrayList();

    public void resetOrder() {
        Collections.sort(equalsFields);
        Collections.sort(onlyFromHasFields);
        Collections.sort(onlyToHasFields);
    }
}
