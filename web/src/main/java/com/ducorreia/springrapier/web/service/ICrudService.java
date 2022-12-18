package com.ducorreia.springrapier.web.service;

import com.ducorreia.springrapier.core.data.DataEntity;
import com.ducorreia.springrapier.core.data.DataProvider;
import com.ducorreia.springrapier.core.pagination.Page;
import com.ducorreia.springrapier.core.pagination.PageRequest;
import org.springframework.util.MultiValueMap;

import java.util.Optional;
import java.util.Set;

public interface ICrudService<E extends DataEntity> {
    Class<E> getEntityClass();

    DataProvider<E> getDataProvider();

    Optional<E> findById(Integer id);

    Optional<E> findByHash(String hash);

    Page<E> findAll(MultiValueMap<String, String> parameters, PageRequest pageable);

    Set<E> findAll();

    E insert(E entity);

    E partialUpdate(E entity);

    E update(E entity);

    void deleteByHash(String hash);

    void deleteById(Integer id);
}
