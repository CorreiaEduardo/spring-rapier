package com.ducorreia.springrapier.core.query;

import lombok.Data;

import java.util.List;

@Data
public class Filter {
    private String field;
    private QueryOperator operator;
    private String value;
    private List<String> values; //Used in case of IN operator
}
