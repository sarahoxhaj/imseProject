package com.example.imse.Review;

import com.example.imse.Book.Book;
import com.example.imse.Book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {


    @Autowired
    private ReviewRepository repo;

    public void save(Review review) {
        repo.save(review);
    }
}
