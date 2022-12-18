package com.ducorreia.springrapier.web.example.repository;

import com.ducorreia.springrapier.data.repository.CrudRepository;
import com.ducorreia.springrapier.web.example.entity.Book;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepository extends CrudRepository<Book> {

    @Override
    public Class<Book> getEntityClass() {
        return Book.class;
    }
}
