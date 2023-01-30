package com.app_products.app_products.Repositories;

import com.app_products.app_products.Models.ProductModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository <ProductModel, Integer> {
    public abstract Optional<ProductModel> findByName(String name);
    public abstract boolean existsByName(String name);
}
