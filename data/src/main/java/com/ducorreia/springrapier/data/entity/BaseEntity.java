package com.ducorreia.springrapier.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    protected Integer id;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true)
    protected String hash;

    @Column
    protected Boolean active;

    @PrePersist
    protected void prePersist() {
        generateHash();
        ensureActive();
    }

    protected void ensureActive() {
        this.active = true;
    }

    protected void generateHash() {
        this.hash = UUID.randomUUID().toString();
    }
}
