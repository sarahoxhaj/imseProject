package com.example.imse;



import com.example.imse.sql.Author.Author;
import com.example.imse.sql.Book.Book;
import com.example.imse.sql.Book.BookService;
import com.example.imse.sql.Review.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MainController {

    @Autowired
    private BookService service;

    @Autowired
    private ReviewService reviewService;

    @GetMapping("")
    public String showHomePage() {
        return "index";
    }

    @GetMapping("/review/{id}")
    public String showReviewForm(@PathVariable(value = "id") Integer id, Model model) {
        Book book = service.getBookByISBN(id);
        List<Author> authors = book.getAuthors();
        List<String> authorNames = authors.stream()
                .map(Author::getName)
                .collect(Collectors.toList());

        model.addAttribute("book",book);
        model.addAttribute("authorNames", authorNames);
        return "review";
    }




}
