package com.example.imse;


import com.example.imse.sql.Author.Author;
import com.example.imse.sql.Author.AuthorRepository;
import com.example.imse.sql.Book.Book;
import com.example.imse.sql.Book.BookRepository;
import com.example.imse.sql.Book.BookService;
import com.example.imse.sql.Follows.Follows;
import com.example.imse.sql.Follows.FollowsRepository;
import com.example.imse.sql.Publisher.Publisher;
import com.example.imse.sql.Publisher.PublisherRepository;
import com.example.imse.sql.Review.Review;
import com.example.imse.sql.Review.ReviewRepository;
import com.example.imse.sql.User.User;
import com.example.imse.sql.User.UserRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.*;
import java.util.Date;

@Controller
public class FakerController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private FollowsRepository followsRepository;
    @Autowired
    private DataMigration dataMigration;

    Faker faker = Faker.instance();

    List<Publisher> publishers = new ArrayList<>();

    public FakerController() {
    }

    String url = "jdbc:mysql://localhost:3306/library2?sessionVariables=sql_mode='NO_ENGINE_SUBSTITUTION'&jdbcCompliantTruncation=false";
    String username = "root";
    String password = "password";


    @Autowired
    public FakerController(DataMigration dataMigration) {
        this.dataMigration = dataMigration;
    }

    @PostMapping("/migrateData")
    @ResponseBody
    public String migrate(Model model) {
        dataMigration.migrateData();
        return "index";
    }

    @PostMapping("/generateData")
    @ResponseBody
    public String generateData(Model model) {
        //user data
        if (!userRepository.findAll().isEmpty()) {
            reviewRepository.deleteAll();
            userRepository.deleteAll();
        }

        for (int i = 0; i < 10; i++) {
            String username = faker.name().username();
            String email = faker.internet().emailAddress();
            String password = faker.internet().password();
            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password.toCharArray());
            userRepository.save(user);
        }
        method1();
        return "Fake data for user";
    }


    public String method1() {
        //publisher data
        if (!publisherRepository.findAll().isEmpty()) {
            publisherRepository.deleteAll();
        }

        for (int i = 0; i < 10; i++) {
            String email = faker.internet().emailAddress();
            String name = faker.company().name();
            Publisher publisher = new Publisher();
            publisher.setEmail(email);
            publisher.setName(name);
            publishers.add(publisher);
            publisherRepository.save(publisher);
        }
        method2();
        return "Fake data for publisher";
    }

    public String method2() {
        //book data
        //delete query
        authorRepository.deleteAll();
        String deleteQuery = "DELETE FROM book";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {
            //run query
            int rowsAffected = statement.executeUpdate(deleteQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }



        Set<String> uniqueTitles = new HashSet<>();

        for (int i = 0; i < 10; i++) {
            String title;
            do {
                title = faker.book().title();
            } while (uniqueTitles.contains(title));

            String description = faker.lorem().paragraph();
            String genre = faker.book().genre();

            Random random = new Random();
            Publisher randomPublisher = publishers.get(random.nextInt(publishers.size()));
            Book book = new Book();
            book.setTitle(title);
            book.setDescription(description);
            book.setGenre(genre);
            book.setPublisher(randomPublisher);
            System.out.println(book);
            bookRepository.save(book);

            uniqueTitles.add(title);
        }
        method3();
        return "Fake data for book";
    }


    public String method3() {
        //author data
        String deleteQuery = "DELETE FROM author";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {
            //run query
            int rowsAffected = statement.executeUpdate(deleteQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //bookRepository.deleteAll();
        List<Book> books = bookRepository.findAll();
        for (int i = 0; i < 10; i++) {
            String name = faker.name().fullName();
            String description = faker.lorem().sentence();
            Date birthday = faker.date().birthday();
            Book randomBook = getRandomElement(books);

            Author author = new Author();
            author.setName(name);
            author.setDescription(description);
            author.setBirthday(birthday);

            List<Book> authorBooks = new ArrayList<>();
            authorBooks.add(randomBook);
            author.setBooks(authorBooks);

            authorRepository.save(author);
        }

        method4();
        return "Fake data for author";
    }


    public String method4() {
        //review data
        String deleteQuery = "DELETE FROM review";
        String constraint = "SET FOREIGN_KEY_CHECKS = 0";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {
            //run query
            int rowsAffected1 = statement.executeUpdate(constraint);
            int rowsAffected = statement.executeUpdate(deleteQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Book> books = bookRepository.findAll();
        List<User> users = userRepository.findAll();
        for (int i = 0; i < 10; i++) {
            String comment = faker.lorem().sentence();
            Integer rating = faker.number().numberBetween(1, 5);
            User randomUser = getRandomElement(users);
            Book randomBook = getRandomElement(books);
            Review review = new Review();
            review.setRating(rating);
            review.setComment(comment);
            review.setUser(randomUser);
            review.setBook(randomBook);
            reviewRepository.save(review);
        }
        method5();
        return "Fake data for review";
    }

    public String method5() {
        String deleteQuery = "DELETE FROM follows";
        String constraint = "SET FOREIGN_KEY_CHECKS = 0";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {
            // Run query
            int rowsAffected1 = statement.executeUpdate(constraint);
            int rowsAffected = statement.executeUpdate(deleteQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<User> users = userRepository.findAll();
        int userCount = users.size();

        if (userCount < 2) {
            return "Insufficient users to generate follows data";
        }

        for (int i = 0; i < 10; i++) {
            int userIndex = faker.number().numberBetween(0, userCount);
            User user = users.get(userIndex);

            int followerIndex;
            do {
                followerIndex = faker.number().numberBetween(0, userCount);
            } while (followerIndex == userIndex);

            User follower = users.get(followerIndex);

            Follows follows = new Follows();
            follows.setUser(user);
            follows.setFollower(follower);
            followsRepository.save(follows);
        }

        return "Fake data for follows";
    }


    private <T> T getRandomElement(List<T> list) {
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }

}

