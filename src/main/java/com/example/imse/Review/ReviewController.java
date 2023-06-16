package com.example.imse.Review;

import com.example.imse.Book.Book;
import com.example.imse.Book.BookService;
import com.example.imse.User.User;
import com.example.imse.User.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ReviewController {
    @Autowired
    private ReviewService service;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/review/save")
    public String saveReview(@ModelAttribute("review") Review review, @RequestParam("rate") int rating,
                             @RequestParam("bookISBN") Integer bookISBN, @RequestParam("userId") int userId) {
        review.setRating(rating);
        User user = userService.getUserById(userId); // Replace userService.findById with your actual method to fetch a user by ID
        review.setUser(user);

        Book book = bookService.getBookByISBN(bookISBN); // Replace bookService.findByISBN with your actual method to fetch a book by ISBN
        review.setBook(book);

        service.save(review);
        return "redirect:/data";
    }

    @GetMapping("/myreview")
    public String showReviewList(Model model) {
        List<Review> listReviews = service.listAll();
        model.addAttribute("listReviews", listReviews);
        return "myreview";
    }

    @GetMapping("/report1")
    public String getTopBooks(Model model) {
        List<Object[]> topBooks = reviewService.getTopBooksByReviewCount();
        model.addAttribute("topBooks", topBooks);
        return "report1";
    }


}
