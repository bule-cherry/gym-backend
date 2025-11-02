package com.clz.web.member_card.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListCard {
    private Integer currentPage;
    private Integer pageSize;
    private String title;
}
