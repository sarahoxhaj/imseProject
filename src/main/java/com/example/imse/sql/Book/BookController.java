package com.example.imse.sql.Book;

import com.example.imse.DataMigration;
import com.example.imse.nosql.Author.AuthorMongo;
import com.example.imse.nosql.Author.AuthorMongoRepository;
import com.example.imse.nosql.Book.BookMongo;
import com.example.imse.nosql.Book.BookMongoRepository;
import com.example.imse.nosql.Publisher.PublisherMongo;
import com.example.imse.nosql.Publisher.PublisherMongoRepository;
import com.example.imse.sql.Author.Author;
import com.example.imse.sql.Author.AuthorRepository;
import com.example.imse.nosql.Publisher.Publisher.Publisher;
import com.example.imse.nosql.Publisher.Publisher.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class BookController {
    @Autowired
    private BookService service;
    @Autowired
    private BookMongoRepository bookMongoRepository;
    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    PublisherMongoRepository publisherMongoRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    AuthorMongoRepository authorMongoRepository;

    @GetMapping("/data")
    public String showBookList(Model model) {

        if(DataMigration.isSql()){
            List<Book> listBooks = service.listAll();
            model.addAttribute("listBooks", listBooks);

        }
        else {
            var listBooks= bookMongoRepository.findAll();
            model.addAttribute("listBooks", listBooks);
        }
        return "data";
    }

    @GetMapping("/newBook")
    public String newBookPage(Model model) {
        if(DataMigration.isSql()){
            List<Publisher> publishers = publisherRepository.findAll();
            model.addAttribute("publishers", publishers);
            model.addAttribute("book", new Book()); // Add an empty book object to bind the form data

            List<Author> authors = authorRepository.findAll();
            model.addAttribute("authors", authors);
            model.addAttribute("book", new Book());
        }
        else {
            List<PublisherMongo> publishers = publisherMongoRepository.findAll();
            model.addAttribute("publishers", publishers);
            model.addAttribute("book", new BookMongo()); // Add an empty book object to bind the form data

            List<AuthorMongo> authors = authorMongoRepository.findAll();
            model.addAttribute("authors", authors);
            model.addAttribute("book", new BookMongo());
        }

        return "addBook";
    }
    private List<Integer> getAuthorsIDs(String[] authorIds){
        var result=new ArrayList<Integer>();
        for(var id:authorIds){
            result.add(Integer.parseInt(id));
        }
        return result;
    }
    private String getISBN(){
        var existingBooks= bookMongoRepository.findAll();
        var max=existingBooks.get(0).getIsbn();
        for(var abook:existingBooks){
            max= String.valueOf(Math.max(Integer.parseInt(max),Integer.parseInt(abook.getIsbn())));
        }
        return String.valueOf(Integer.parseInt(max)+1);
    }
    private BookMongo convertToBookMongo(Book book) {
        BookMongo bookMongo = new BookMongo();
        bookMongo.setIsbn(getISBN());
        bookMongo.setTitle(book.getTitle());
        bookMongo.setDescription(book.getDescription());
        bookMongo.setGenre(book.getGenre());
        // setting authors
        var authorsMongo= new ArrayList<AuthorMongo>();
        for(var author:book.getAuthors()){
            var authorMongo=authorMongoRepository.findById(author.getId().toString()).orElse(null);
            authorsMongo.add(authorMongo);
            authorMongo.getBooks().add(bookMongo);
            authorMongoRepository.save(authorMongo);
        }
        bookMongo.setAuthors(authorsMongo);
        //setting publisher
        var publisherMongo= publisherMongoRepository.findById(book.getPublisher().getId().toString()).orElse(null);
        publisherMongo.getBooks().add(bookMongo);
        bookMongo.setPublisher(publisherMongo);
        publisherMongoRepository.save(publisherMongo);

        // review will be added in review conversion
        bookMongoRepository.save(bookMongo);
        return bookMongo;
    }
    @PostMapping("/addBook")
    public String addBook(@ModelAttribute("book") @Validated Book book, @RequestParam("authors") String[] authorIds) {
        System.out.println("Book Title -> " + book.getTitle());
        List<Author> authors = authorRepository.findAllById(getAuthorsIDs(authorIds));
        book.setAuthors(authors);
        if(DataMigration.isSql()){
            this.service.addBook(book);
        }
        else {
            convertToBookMongo(book);
        }
        return "redirect:/data";
    }

    @GetMapping("/noReviews")
    public String showReviewList(Model model) {
        if(DataMigration.isSql()){
            List<Book> listBooks = service.listAll();
            model.addAttribute("listBooks", listBooks);
        }
        else {
            List<BookMongo> listBooks = bookMongoRepository.findAll();
            model.addAttribute("listBooks", listBooks);
        }

        return "booksWithoutReviews";
    }

    @GetMapping("/report2")
    public String getBooks(Model model) {
        if(DataMigration.isSql()){
            List<Object[]> booksWithoutReviews = service.getBooksWithoutReviews();
            model.addAttribute("booksWithoutReviews", booksWithoutReviews);
        }
        else {
            List<BookMongo> books = bookMongoRepository.getBooksWithoutReviews();
            List<Object[]> booksWithoutReviews = new ArrayList<>();
            for(var book: books){
                String publisherName=book.getPublisher().getName();
                String title= book.getTitle();
//                List<AuthorMongo> authors = book.getAuthors();
                booksWithoutReviews.add(new Object[]{publisherName,title});
//                booksWithoutReviews.add(new Object[]{publisherName,title, authors});
            }
            model.addAttribute("booksWithoutReviews", booksWithoutReviews);
        }

        return "report2";
    }

}
