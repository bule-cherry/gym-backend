package com.clz.web.suggest.entity;

import lombok.Data;

@Data
public class SuggestParam {
    private Long currentPage;
    private Long pageSize;
    private String title;
}