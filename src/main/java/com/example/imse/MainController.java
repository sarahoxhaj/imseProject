package com.example.imse;



import com.example.imse.nosql.Author.AuthorMongo;
import com.example.imse.nosql.Book.BookMongo;
import com.example.imse.nosql.Book.BookMongoRepository;
import com.example.imse.sql.Author.Author;
import com.example.imse.sql.Book.Book;
import com.example.imse.sql.Book.BookService;
import com.example.imse.sql.Review.ReviewService;
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
    private BookService bookService;
    @Autowired
    private BookMongoRepository bookMongoRepository;

    @GetMapping("")
    public String showHomePage() {
        return "index";
    }

    @GetMapping("/review/{id}")
    public String showReviewForm(@PathVariable(value = "id") String id, Model model) {
        List<String> authorNames;
        if(DataMigration.isSql()){
            // ids in sql database are numbers, whereas in mongo they are UUIDs
            var id2= Integer.parseInt(id);
            Book book = bookService.getBookByISBN(id2);
            List<Author> authors = book.getAuthors();
            authorNames = authors.stream()
                    .map(Author::getName)
                    .collect(Collectors.toList());
            model.addAttribute("book",book);
        }
        else
        {
            BookMongo bookMongo= bookMongoRepository.findById(id).orElse(null);
            List<AuthorMongo> authors = bookMongo.getAuthors();
            authorNames = authors.stream()
                    .map(AuthorMongo::getName)
                    .collect(Collectors.toList());
            model.addAttribute("book",bookMongo);
        }
        model.addAttribute("authorNames", authorNames);

        return "review";
    }




}
