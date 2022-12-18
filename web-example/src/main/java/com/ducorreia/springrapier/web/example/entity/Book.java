package com.ducorreia.springrapier.web.example.entity;

import com.ducorreia.springrapier.data.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table
public class Book extends BaseEntity {
    private String name;
    private Integer pageCount;
    private String publicationYear;
}
