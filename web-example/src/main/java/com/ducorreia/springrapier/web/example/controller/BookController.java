package com.ducorreia.springrapier.web.example.controller;

import com.ducorreia.springrapier.web.controller.CrudController;
import com.ducorreia.springrapier.web.example.entity.Book;
import com.ducorreia.springrapier.web.example.service.BookService;
import com.ducorreia.springrapier.web.service.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor
public class BookController extends CrudController<Book> {

    private final BookService bookService;

    @Override
    protected CrudService<Book> getService() {
        return bookService;
    }
}
