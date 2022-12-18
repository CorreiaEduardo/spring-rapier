package com.ducorreia.springrapier.core.data;

import com.ducorreia.springrapier.core.pagination.Page;
import com.ducorreia.springrapier.core.pagination.PageRequest;
import com.ducorreia.springrapier.core.query.Filter;

import java.util.List;
import java.util.Optional;

public interface DataProvider<E> {
    <S extends E> S save(S entity);

    <S extends E> Iterable<S> saveAll(Iterable<S> entities);

    boolean existsById(Integer id);

    long count();

    Optional<E> findById(Integer id);

    Optional<E> findByHash(String hash);

    Iterable<E> findAllById(Iterable<Integer> ids);

    Iterable<E> findAll();

    Page<E> findAll(List<Filter> filters, PageRequest pageRequest);

    void deleteById(Integer id);

    void deleteAllById(Iterable<? extends Integer> ids);

    void delete(E entity);

    void deleteAll(Iterable<? extends E> entities);

    void deleteAll();
}
