package com.ducorreia.springrapier.data.query;

import com.ducorreia.springrapier.core.query.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.LinkedList;
import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;

@RequiredArgsConstructor
public class SpecificationFactory {

    public static <T> Specification<T> of(List<Filter> allFilters) {
        if (allFilters.isEmpty()) return null;

        final List<Filter> filters = new LinkedList<>(allFilters);

        Specification<T> specification =
                where(createSpecification(filters.remove(0)));

        for (Filter filter : filters) {
            specification = specification.and(createSpecification(filter));
        }

        return specification;
    }

    public static <T> Specification<T> byHash(String hash) {
        return (Specification<T>) (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("hash"), hash);
    }

    private static <T> Specification<T> createSpecification(Filter filter) {
        switch (filter.getOperator()) {
            case EQUALS:
                return (Specification<T>) (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(filter.getField()),
                        castToRequiredType(root.get(filter.getField()).getJavaType(),
                                filter.getValue()));
            case NOT_EQUALS:
                return (Specification<T>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.notEqual(root.get(filter.getField()),
                                castToRequiredType(root.get(filter.getField()).getJavaType(),
                                        filter.getValue()));
            case GREATER_THAN:
                return (Specification<T>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.gt(root.get(filter.getField()),
                                (Number) castToRequiredType(
                                        root.get(filter.getField()).getJavaType(),
                                        filter.getValue()));
            case LESS_THAN:
                return (Specification<T>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.lt(root.get(filter.getField()),
                                (Number) castToRequiredType(
                                        root.get(filter.getField()).getJavaType(),
                                        filter.getValue()));
            case LIKE:
                return (Specification<T>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.like(root.get(filter.getField()),
                                "%" + filter.getValue() + "%");
            case IN:
                return (Specification<T>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.in(root.get(filter.getField()))
                                .value(castToRequiredType(
                                        root.get(filter.getField()).getJavaType(),
                                        filter.getValues()));
            default:
                throw new RuntimeException("Operation not supported yet");
        }
    }

    private static Object castToRequiredType(Class fieldType, String value) {
        if (fieldType.isAssignableFrom(Double.class)) {
            return Double.valueOf(value);
        } else if (fieldType.isAssignableFrom(Integer.class)) {
            return Integer.valueOf(value);
        } else if (fieldType.isAssignableFrom(Boolean.class)) {
            return Boolean.valueOf(value);
        } else if (fieldType.isAssignableFrom(String.class)) {
            return value;
        } else if (Enum.class.isAssignableFrom(fieldType)) {
            return Enum.valueOf(fieldType, value);
        }
        return null;
    }

    private static Object castToRequiredType(Class fieldType, List<String> values) {
        return values.stream().map(it -> castToRequiredType(fieldType, it));
    }
}
