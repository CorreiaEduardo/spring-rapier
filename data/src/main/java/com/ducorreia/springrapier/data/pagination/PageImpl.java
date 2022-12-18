package com.ducorreia.springrapier.data.pagination;


import com.ducorreia.springrapier.core.pagination.Page;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class PageImpl<T> implements Page<T> {
    private final List<T> content;
    private final Integer page;
    private final Integer size;
    private final Long totalElements;
    private final Integer totalPages;
}
