package com.example.imse.User;

import com.example.imse.Book.Book;
import com.example.imse.Book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repo;

    public List<User> listAll() {
        return (List<User>) repo.findAll();
    }

    public User getUserById(Integer id) {
        Optional<User> optional = repo.findById(id);
        User user = null;
        if (optional.isPresent()) {
            user = optional.get();
        } else {
            throw new RuntimeException("user not found for id :: " + id);
        }
        return user;
    }


}
