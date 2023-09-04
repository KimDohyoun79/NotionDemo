package com.notionBreadCrumbs.notionDemo.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Page {

    private int id;
    private String title;
    private String content;
    private Integer parentId; // 상위 페이지 ID. 최상위 페이지는 null

    // getters and setters
    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }
    public Integer getParentId() {
        return this.parentId;
    }
}