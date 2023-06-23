package com.example.imse;

import com.example.imse.nosql.Publisher.PublisherMongo;
import com.example.imse.nosql.Publisher.PublisherMongoRepository;
import com.example.imse.sql.Author.Author;
import com.example.imse.nosql.Author.AuthorMongo;
import com.example.imse.nosql.Author.AuthorMongoRepository;
import com.example.imse.sql.Author.AuthorRepository;
import com.example.imse.sql.Book.Book;
import com.example.imse.nosql.Book.BookMongo;
import com.example.imse.nosql.Book.BookMongoRepository;
import com.example.imse.sql.Book.BookRepository;
import com.example.imse.sql.Follows.Follows;
import com.example.imse.nosql.Follows.FollowsMongo;
import com.example.imse.nosql.Follows.FollowsMongoRepository;
import com.example.imse.sql.Follows.FollowsRepository;
import com.example.imse.nosql.Publisher.Publisher.Publisher;
import com.example.imse.nosql.Publisher.Publisher.PublisherRepository;
import com.example.imse.sql.Review.Review;
import com.example.imse.nosql.Review.ReviewMongo;
import com.example.imse.nosql.Review.ReviewMongoRepository;
import com.example.imse.sql.Review.ReviewRepository;
import com.example.imse.sql.User.User;
import com.example.imse.nosql.User.UserMongo;
import com.example.imse.nosql.User.UserMongoRepository;
import com.example.imse.sql.User.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DataMigration {
    private static boolean sql=true;

    public static boolean isSql() {
        return sql;
    }

    private final BookRepository bookRepository; // MySQL repository
    private final BookMongoRepository bookMongoRepository; // MongoDB repository

    private final AuthorRepository authorRepository;
    private final AuthorMongoRepository authorMongoRepository;

    private final FollowsRepository followsRepository;
    private final FollowsMongoRepository followsMongoRepository;

    private final PublisherRepository publisherRepository;
    private final PublisherMongoRepository publisherMongoRepository;

    private final ReviewRepository reviewRepository;
    private final ReviewMongoRepository reviewMongoRepository;

    private final UserRepository userRepository;
    private final UserMongoRepository userMongoRepository;

    // loading users, publishers and authors first, before the books, follows, and reviews
    public void migrateData() {
        for(var user: userRepository.findAll()){
            convertToUserMongo(user);
        }
        for (var publisher:publisherRepository.findAll()){
            convertToPublisherMongo(publisher);
        }
        for(var author: authorRepository.findAll()){
            convertToAuthorMongo(author);
        }
        for(var follows: followsRepository.findAll()){
            convertToFollowsMongo(follows);
        }
        for(var book: bookRepository.findAll()){
            convertToBookMongo(book);
        }
        for(var review: reviewRepository.findAll()){
            convertToReviewMongo(review);
        }
        sql=false;
    }

    private UserMongo convertToUserMongo(User user){
        UserMongo userMongo=new UserMongo();
        userMongo.setEmail(user.getEmail());
        userMongo.setPassword(user.getPassword());
        userMongo.setId(user.getId().toString());
        userMongo.setUsername(user.getUsername());
        userMongoRepository.save(userMongo);
        return userMongo;
    }
    private PublisherMongo convertToPublisherMongo(Publisher publisher){
        PublisherMongo publisherMongo=new PublisherMongo();
        publisherMongo.setEmail(publisher.getEmail());
        publisherMongo.setId(publisher.getId().toString());
        publisherMongo.setName(publisher.getName());
        publisherMongoRepository.save(publisherMongo);
        return publisherMongo;
    }
    private AuthorMongo convertToAuthorMongo(Author author){
        AuthorMongo authorMongo= new AuthorMongo();
        authorMongo.setName(author.getName());
        authorMongo.setId(author.getId().toString());
        authorMongo.setDescription(author.getDescription());
        authorMongo.setBirthday(author.getBirthday());
        authorMongoRepository.save(authorMongo);
        return authorMongo;
    }

    private FollowsMongo convertToFollowsMongo(Follows follows){
        FollowsMongo followsMongo= new FollowsMongo();
        followsMongo.setId(follows.getId().toString());
        var followedMongo= userMongoRepository.findById(follows.getUser().getId().toString()).orElse(null);
        var followerMongo= userMongoRepository.findById(follows.getFollower().getId().toString()).orElse(null);
        followsMongo.setUser(followedMongo);
        followsMongo.setFollower(followerMongo);

        followedMongo.getFollowers().add(followsMongo);
        followerMongo.getFollowing().add(followsMongo);
        userMongoRepository.save(followedMongo);
        userMongoRepository.save(followerMongo);
        followsMongoRepository.save(followsMongo);
        return followsMongo;
    }

    private BookMongo convertToBookMongo(Book book) {
        BookMongo bookMongo = new BookMongo();
        bookMongo.setIsbn(book.getIsbn().toString());
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

        bookMongoRepository.save(bookMongo);
        return bookMongo;
    }

    private ReviewMongo convertToReviewMongo(Review review){
        ReviewMongo reviewMongo= new ReviewMongo();
        reviewMongo.setId(review.getId().toString());
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

}
