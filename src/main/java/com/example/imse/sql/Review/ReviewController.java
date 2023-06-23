package com.example.imse.sql.Review;

import com.example.imse.DataMigration;
import com.example.imse.nosql.Book.BookMongo;
import com.example.imse.nosql.Book.BookMongoRepository;
import com.example.imse.nosql.Review.ReviewMongo;
import com.example.imse.nosql.Review.ReviewMongoRepository;
import com.example.imse.nosql.User.UserMongoRepository;
import com.example.imse.sql.Book.Book;
import com.example.imse.sql.Book.BookService;
import com.example.imse.sql.User.User;
import com.example.imse.sql.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ReviewController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ReviewMongoRepository reviewMongoRepository;
    @Autowired
    private UserMongoRepository userMongoRepository;
    @Autowired
    private BookMongoRepository bookMongoRepository;

    private String getID(){
        var existingReviews= reviewMongoRepository.findAll();
        var max=existingReviews.get(0).getId();
        for(var areview:existingReviews){
            max= String.valueOf(Math.max(Integer.parseInt(max),Integer.parseInt(areview.getId())));
        }
        return String.valueOf(Integer.parseInt(max)+1);
    }
    private ReviewMongo convertToReviewMongo(Review review){
        ReviewMongo reviewMongo= new ReviewMongo();
        reviewMongo.setId(getID());
        reviewMongo.setRating(review.getRating());
        reviewMongo.setComment(review.getComment());
        var userMongo= userMongoRepository.findById(review.getUser().getId().toString()).orElse(null);
        var bookMongo= bookMongoRepository.findById(review.getBook().getIsbn().toString()).orElse(null);
        reviewMongo.setUser(userMongo);
        reviewMongo.setBook(bookMongo);

        userMongo.getReviews().add(reviewMongo);
        bookMongo.getReviews().add(reviewMongo);

        userMongoRepository.save(userMongo);
        bookMongoRepository.save(bookMongo);
        reviewMongoRepository.save(reviewMongo);
        return reviewMongo;
    }

    @PostMapping("/review")
    public String saveReview(@ModelAttribute("review") Review review, @RequestParam("rate") int rating,
                             @RequestParam("bookISBN") Integer bookISBN, @RequestParam("userId") int userId) {

        if(DataMigration.isSql()){
            review.setRating(rating);
            User user = userService.getUserById(userId); // Replace userService.findById with your actual method to fetch a user by ID
            review.setUser(user);
            Book book = bookService.getBookByISBN(bookISBN); // Replace bookService.findByISBN with your actual method to fetch a book by ISBN
            review.setBook(book);
            reviewService.save(review);
        }
        else {
            ReviewMongo reviewMongo= new ReviewMongo();
            reviewMongo.setId(getID());
            reviewMongo.setRating(review.getRating());
            reviewMongo.setComment(review.getComment());
            var userMongo= userMongoRepository.findById(review.getUser().getId().toString()).orElse(null);
            var bookMongo= bookMongoRepository.findById(review.getBook().getIsbn().toString()).orElse(null);
            reviewMongo.setUser(userMongo);
            reviewMongo.setBook(bookMongo);

            userMongo.getReviews().add(reviewMongo);
            bookMongo.getReviews().add(reviewMongo);

            userMongoRepository.save(userMongo);
            bookMongoRepository.save(bookMongo);
            reviewMongoRepository.save(reviewMongo);
        }

        return "redirect:/data";
    }

    @GetMapping("/myreview")
    public String showReviewList(Model model) {
        if(DataMigration.isSql()){
            List<Review> listReviews = reviewService.listAll();
            model.addAttribute("listReviews", listReviews);
        }
        else {
            List<ReviewMongo> listReviews = reviewMongoRepository.findAll();
            System.out.println(listReviews);
            model.addAttribute("listReviews", listReviews);
        }
        return "myreview";
    }

    @GetMapping("/report1")
    public String getTopBooks(Model model) {
        if(DataMigration.isSql()){
            List<Object[]> topBooks = reviewService.getTopBooksByReviewCount();
            model.addAttribute("topBooks", topBooks);
        }
        else {
            List<ReviewMongo> listReviews = reviewMongoRepository.findAll();

            Map<String, Integer> bookReviewCountMap = new HashMap<>();
            for (ReviewMongo review : listReviews) {
                BookMongo book = review.getBook();
                bookReviewCountMap.put(book.getIsbn(), bookReviewCountMap.getOrDefault(book.getIsbn(), 0) + 1);
            }

            List<Map.Entry<String, Integer>> sortedBooks = new ArrayList<>(bookReviewCountMap.entrySet());
            sortedBooks.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

            List<Object[]> topBooks = new ArrayList<>();
            for(int i=0;i<5;i++){
                String isbn= sortedBooks.get(i).getKey();
                String publisherName=bookMongoRepository.findById(sortedBooks.get(i).getKey()).orElse(null).getPublisher().getName();
                topBooks.add(new Object[]{isbn,publisherName});
            }

            model.addAttribute("topBooks", topBooks);
        }

        return "report1";
    }

}
