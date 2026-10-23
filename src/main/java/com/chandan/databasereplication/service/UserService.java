package com.chandan.databasereplication.service;

import com.chandan.databasereplication.model.User;
import com.chandan.databasereplication.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {
    UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User save(String name, String email) {
        return userRepository.save(name, email);
    }

}
