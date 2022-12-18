package com.ducorreia.springrapier.core.query;

import java.util.Arrays;

public enum QueryOperator {
    EQUALS("EQ"),
    NOT_EQUALS("NEQ"),
    GREATER_THAN("GT"),
    LESS_THAN("LT"),
    LIKE("LK"),
    IN("IN");

    private final String alias;

    QueryOperator(String alias) {
        this.alias = alias;
    }

    public static QueryOperator of(String alias) {
        return Arrays.stream(QueryOperator.values())
                .filter(it -> it.alias.equals(alias.toUpperCase()))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}
