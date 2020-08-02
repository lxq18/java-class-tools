package com.lxq.tools.refactor.javaclass.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author lixiaoqiang
 * @create 2020/8/1 10:50
 */
@Getter
@Setter
public class Article2 {
    private int id;
    @JsonProperty("description")
    private String desc;
    private Integer numberOfWords;
    private CommentInfo2 commentInfo;
    private Date date;
    private int[] intArray;
    private Integer[] integerArray;
    private Img[] imgArray;
    private List<Img> imgList;
    private Set<Img> imgSet;
    private Map<String, Img> imgMap;
    private Map<String, List<Img>> genericTypeMap;
    private EmptyClass emptyClass;
    private Article2 article2;

    public boolean descNotEmpty() {
        return StringUtils.isNotEmpty(desc);
    }
}
