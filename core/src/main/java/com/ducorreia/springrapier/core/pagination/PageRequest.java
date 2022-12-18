package com.ducorreia.springrapier.core.pagination;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PageRequest {
    private Integer page;
    private Integer size;

    public static PageRequest of(Integer page, Integer size) {
        return new PageRequest(page, size);
    }
}
