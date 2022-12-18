package com.ducorreia.springrapier.core.query;

import com.ducorreia.springrapier.core.util.ReflectionUtils;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QueryParamParser {

    /**
     * @param src name=LK:XXX&category=IN:1,2,3&createdAt=GT:12-02-2022
     */
    public static List<Filter> parseFilters(MultiValueMap<String, String> src, Class target) {
        final List<Filter> filters = new ArrayList<>();
        src.forEach((field, values) -> {
            if (ReflectionUtils.getFields(target).stream().anyMatch(it -> it.getName().equals(field))) {
                values.forEach(param -> {
                    final Filter filter = new Filter();
                    final QueryOperator operator = QueryOperator.of(param.split(":")[0]);
                    final String value = param.split(":")[1];

                    filter.setField(field);
                    filter.setOperator(operator);
                    if (operator == QueryOperator.IN) {
                        final List<String> inValues = Arrays.asList(value.split(","));
                        filter.setValues(inValues);
                    } else {
                        filter.setValue(value);
                    }

                    filters.add(filter);
                });
            }
        });

        return Collections.unmodifiableList(filters);
    }
}
