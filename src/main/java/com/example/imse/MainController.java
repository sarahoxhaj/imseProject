package com.example.imse;

import com.example.imse.Author.Author;
import com.example.imse.Book.Book;
import com.example.imse.Book.BookService;
import com.example.imse.Review.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MainController {

    @Autowired
    private BookService service;

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
