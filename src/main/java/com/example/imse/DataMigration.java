package com.example.imse;

import com.example.imse.sql.Book.Book;
import com.example.imse.sql.Book.BookMongo;
import com.example.imse.sql.Book.BookMongoRepository;
import com.example.imse.sql.Book.BookRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


//public class DataMigration {
//    private final BookRepository bookRepository; // MySQL repository
//    private final BookMongoRepository bookMongoRepository; // MongoDB repository

//    @Autowired
//    public DataMigration(BookRepository bookRepository, BookMongoRepository bookMongoRepository) {
//        this.bookRepository = bookRepository;
//        this.bookMongoRepository = bookMongoRepository;
//    }

//    @PostConstruct
//    public void migrateData() {
//        List<Book> books = bookRepository.findAll();
//        for (Book book : books) {
//            BookMongo bookMongo = convertToBookMongo(book);
//            bookMongoRepository.save(bookMongo);
//        }
//    }
//
//    private BookMongo convertToBookMongo(Book book) {
//        BookMongo bookMongo = new BookMongo();
//        bookMongo.setIsbn(book.getIsbn());
//        bookMongo.setTitle(book.getTitle());
//        bookMongo.setDescription(book.getDescription());
//        bookMongo.setGenre(book.getGenre());
//        bookMongo.setAuthors(book.getAuthors());
//        bookMongo.setPublisher(book.getPublisher());
//        return bookMongo;
//    }
//}
