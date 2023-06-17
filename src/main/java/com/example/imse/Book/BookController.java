package com.example.imse.Book;

import com.example.imse.Review.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BookController {
    @Autowired
    private BookService service;

    @GetMapping("/data")
    public String showBookList(Model model) {
        List<Book> listBooks = service.listAll();
        model.addAttribute("listBooks", listBooks);
        return "data";
    }

    @GetMapping("/newBook")
    public String newBookPage(Model model) {
        return "addBook";
    }

    @PostMapping("/addBook")
    public String addBook(Book book) {
        System.out.println("Book Title -> " + book.getTitle());
        this.service.addBook(book);
        return "redirect:/data";
    }

    @GetMapping("/noReviews")
    public String showReviewList(Model model) {
        List<Book> listBooks = service.listAll();
        model.addAttribute("listBooks", listBooks);
        return "booksWithoutReviews";
    }

    @GetMapping("/report2")
    public String getBooks(Model model) {
        List<Object[]> booksWithoutReviews = service.getBooksWithoutReviews();
        model.addAttribute("booksWithoutReviews", booksWithoutReviews);
        return "report2";
    }

}
