package com.lxq.tools.refactor.javaclass.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Map;

/**
 * @author lixiaoqiang
 * @create 2020/8/1 10:50
 */
@Getter
@Setter
public class Article1 {
    private String title;
    @JsonProperty("description")
    private String desc;
    private Integer numberOfWords;
    private CommentInfo1 commentInfo;
    private Date date1;
    private Map<String, Img> imgMap;

    public boolean titleNotEmpty() {
        return StringUtils.isNotEmpty(title);
    }
}
