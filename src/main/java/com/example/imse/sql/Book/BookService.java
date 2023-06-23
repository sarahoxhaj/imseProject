package com.example.imse.sql.Book;


import com.example.imse.nosql.Book.BookMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository repo;
    @Autowired
    private BookMongoRepository bookMongoRepository;

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

    public Book addBook(Book book) {
        return repo.save(book);
    }

    public List<Object[]> getBooksWithoutReviews() {
        return repo.getBooksWithoutReviews();
    }


    @Autowired
    public BookService(BookRepository bookRepository, BookMongoRepository bookMongoRepository) {
        this.repo = bookRepository;
        this.bookMongoRepository = bookMongoRepository;
    }
}
