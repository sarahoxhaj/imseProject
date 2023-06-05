package com.example.imse.Book;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository repo;

    public List<Book> listAll(){
        return (List<Book>) repo.findAll();
    }
}
