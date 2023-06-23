package com.example.imse.sql.Review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository repo;

    public List<Review> listAll() {
        return (List<Review>) repo.findAll();
    }

    public void save(Review review) {
        repo.save(review);
    }
    public List<Object[]> getTopBooksByReviewCount() {
        return repo.findTopBooksByReviewCount();
    }
}
