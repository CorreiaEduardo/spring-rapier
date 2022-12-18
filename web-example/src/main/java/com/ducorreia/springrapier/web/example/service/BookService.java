package com.ducorreia.springrapier.web.example.service;

import com.ducorreia.springrapier.core.data.DataProvider;
import com.ducorreia.springrapier.web.example.entity.Book;
import com.ducorreia.springrapier.web.example.repository.BookRepository;
import com.ducorreia.springrapier.web.service.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService extends CrudService<Book> {
    private final BookRepository repository;

    @Override
    public Class<Book> getEntityClass() {
        return Book.class;
    }

    @Override
    public DataProvider<Book> getDataProvider() {
        return repository;
    }

}
