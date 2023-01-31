package com.app_products.app_products.Security.Repositories;

import com.app_products.app_products.Security.Models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    public abstract Optional<User> findByUsername(String username);
    public abstract boolean existsByUsername(String username);
    public abstract boolean existsByEmail(String email);
}
