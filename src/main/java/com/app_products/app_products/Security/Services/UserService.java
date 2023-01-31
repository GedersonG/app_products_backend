package com.app_products.app_products.Security.Services;

import com.app_products.app_products.Security.Models.User;
import com.app_products.app_products.Security.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class UserService {
    @Autowired
    UserRepository userRepository;

    public Optional<User> getUserByUsername (String username){
        return userRepository.findByUsername(username);
    }

    public boolean existByUsername (String username){
        return userRepository.existsByUsername(username);
    }

    public boolean existByEmail (String email){
        return userRepository.existsByEmail(email);
    }

    public void save (User user){
        userRepository.save(user);
    }
}
