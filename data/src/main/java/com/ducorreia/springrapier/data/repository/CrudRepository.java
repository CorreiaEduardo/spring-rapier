package com.ducorreia.springrapier.data.repository;

import com.ducorreia.springrapier.core.data.DataProvider;
import com.ducorreia.springrapier.core.pagination.Page;
import com.ducorreia.springrapier.core.pagination.PageRequest;
import com.ducorreia.springrapier.core.query.Filter;
import com.ducorreia.springrapier.data.pagination.PageImpl;
import com.ducorreia.springrapier.data.query.SpecificationFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public abstract class CrudRepository<E> implements DataProvider<E> {

    private SimpleJpaRepository<E, Integer> repository;

    @PersistenceContext
    private EntityManager entityManager;

    @PostConstruct
    private void setup() {
        this.repository = new SimpleJpaRepository(getEntityClass(), entityManager);
    }

    public abstract Class<E> getEntityClass();

    @Transactional
    @Override
    public <S extends E> S save(S entity) {
        return this.repository.save(entity);
    }

    @Transactional
    @Override
    public <S extends E> Iterable<S> saveAll(Iterable<S> entities) {
        return this.repository.saveAll(entities);
    }

    @Override
    public boolean existsById(Integer id) {
        return this.repository.existsById(id);
    }

    @Override
    public long count() {
        return this.repository.count();
    }

    @Override
    public Optional<E> findById(Integer id) {
        return this.repository.findById(id);
    }

    @Override
    public Optional<E> findByHash(String hash) {
        return repository.findOne(SpecificationFactory.byHash(hash));
    }

    @Override
    public Iterable<E> findAllById(Iterable<Integer> ids) {
        return this.repository.findAllById(ids);
    }

    @Override
    public Iterable<E> findAll() {
        return this.repository.findAll();
    }

    @Override
    public Page<E> findAll(List<Filter> filters, PageRequest pageRequest) {
        final Specification<E> specification = SpecificationFactory.of(filters);
        final Pageable pageable = org.springframework.data.domain.PageRequest.of(pageRequest.getPage(), pageRequest.getSize());

        final org.springframework.data.domain.Page<E> all = this.repository.findAll(specification, pageable);
        return new PageImpl(all.getContent(), pageable.getPageNumber(), pageable.getPageSize(), all.getTotalElements(), all.getTotalPages());
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        this.repository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteAllById(Iterable<? extends Integer> ids) {
        this.repository.deleteAllById(ids);
    }

    @Transactional
    @Override
    public void delete(E entity) {
        this.repository.delete(entity);
    }

    @Transactional
    @Override
    public void deleteAll(Iterable<? extends E> entities) {
        this.repository.deleteAll(entities);
    }

    @Transactional
    @Override
    public void deleteAll() {
        this.repository.deleteAll();
    }
}
