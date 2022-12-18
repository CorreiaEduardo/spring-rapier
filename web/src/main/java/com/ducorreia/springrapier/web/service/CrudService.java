package com.ducorreia.springrapier.web.service;

import com.ducorreia.springrapier.core.data.DataEntity;
import com.ducorreia.springrapier.core.pagination.Page;
import com.ducorreia.springrapier.core.pagination.PageRequest;
import com.ducorreia.springrapier.core.query.Filter;
import com.ducorreia.springrapier.core.query.QueryParamParser;
import com.ducorreia.springrapier.web.util.ModelMapperFactory;
import com.google.common.collect.Sets;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public abstract class CrudService<E extends DataEntity> implements ICrudService<E> {

    public abstract Class<E> getEntityClass();

    public Page<E> findAll(MultiValueMap<String, String> parameters, PageRequest pageRequest) {
        final List<Filter> filters = QueryParamParser.parseFilters(parameters, getEntityClass());

        return getDataProvider().findAll(filters, pageRequest);
    }

    @Override
    public Optional<E> findById(Integer id) {
        return getDataProvider().findById(id);
    }

    @Override
    public Optional<E> findByHash(String hash) {
        return getDataProvider().findByHash(hash);
    }

    @Override
    public Set<E> findAll() {
        return Sets.newHashSet(getDataProvider().findAll());
    }

    @Override
    public E insert(E entity) {
        return getDataProvider().save(entity);
    }

    @Override
    public E partialUpdate(E entity) {

        if (entity.getId() == null) {
            throw new RuntimeException();
        }

        final Optional<E> optionalEntity = findById(entity.getId());

        if (optionalEntity.isPresent()) {
            final E savedEntity = optionalEntity.get();
            ModelMapperFactory.skippingNull().map(entity, savedEntity);
            return getDataProvider().save(entity);
        }

        throw new RuntimeException();
    }

    @Override
    public E update(E entity) {
        return getDataProvider().save(entity);
    }

    @Override
    public void deleteByHash(String hash) {
        findByHash(hash).ifPresent(entity -> {
            entity.setActive(false);
            getDataProvider().save(entity);
        });
    }

    @Override
    public void deleteById(Integer id) {
        findById(id).ifPresent(entity -> {
            entity.setActive(false);
            getDataProvider().save(entity);
        });
    }
}
