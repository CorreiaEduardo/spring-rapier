package com.ducorreia.springrapier.web.util;

import lombok.experimental.UtilityClass;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;

@UtilityClass
public class ModelMapperFactory {
    public static ModelMapper getDefault() {
        return new ModelMapper();
    }

    public static ModelMapper skippingNull() {
        final ModelMapper mapper = getDefault();
        mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

        return mapper;
    }
}
