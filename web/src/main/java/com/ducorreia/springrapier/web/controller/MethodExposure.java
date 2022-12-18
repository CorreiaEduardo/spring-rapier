package com.ducorreia.springrapier.web.controller;

import com.ducorreia.springrapier.web.util.CrudMethod;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Getter
public class MethodExposure {
    private final Map<CrudMethod, Boolean> map = new HashMap<>();

    public MethodExposure() {
        this.map.put(CrudMethod.GET, true);
        this.map.put(CrudMethod.GET_ONE, true);
        this.map.put(CrudMethod.POST, true);
        this.map.put(CrudMethod.PUT, true);
        this.map.put(CrudMethod.DELETE, true);
    }

    public MethodExposure(CrudMethod[] excludeList) {
        this();
        Arrays.stream(excludeList).forEach(exception -> this.map.put(exception, false));
    }

    public boolean exposes(CrudMethod method) {
        return this.map.get(method);
    }
}
