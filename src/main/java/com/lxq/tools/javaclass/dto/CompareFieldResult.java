package com.lxq.tools.javaclass.dto;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * @author lixiaoqiang
 * @create 2020/8/1 11:16
 */
@Data
public class CompareFieldResult {
    List<FieldItem> equalsFields = Lists.newArrayList();
    List<FieldItem> onlyFromHasFields = Lists.newArrayList();
    List<FieldItem> onlyToHasFields = Lists.newArrayList();
}
