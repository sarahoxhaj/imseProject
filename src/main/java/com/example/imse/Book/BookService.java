package com.example.imse.Book;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository repo;

    public List<Book> listAll(){
        return (List<Book>) repo.findAll();
    }

    public Book getBookByISBN(Integer id) {
        Optional<Book> optional = repo.findById(id);
        Book book = null;
        if (optional.isPresent()) {
            book = optional.get();
        } else {
            throw new RuntimeException("Book not found for id :: " + id);
        }
        return book;
    }



}
