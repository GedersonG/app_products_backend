package com.app_products.app_products.Services;

import com.app_products.app_products.Models.ProductModel;
import com.app_products.app_products.Repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public List<ProductModel> getProducts(){
        return (List<ProductModel>)productRepository.findAll();
    }

    public Optional<ProductModel> getProductById(int id){
        return productRepository.findById(id);
    }

    public Optional<ProductModel> getProductByName(String name){
        return productRepository.findByName(name);
    }

    public ProductModel saveProduct(ProductModel product){
        return productRepository.save(product);
    }

    public boolean removeProductById(int id){
        try{
            productRepository.deleteById(id);
            return true;
        } catch(Exception e){
            System.err.println("Ha ocurrido un error: " + e.getMessage());
            return false;
        }
    }

    public boolean existsById(int id){
        return productRepository.existsById(id);
    }

    public boolean existsByName(String name){
        return productRepository.existsByName(name);
    }

    public void update(int id, ProductModel product){
        ProductModel productModel = productRepository.findById(id).orElse(null);
        if(productModel != null){
            productModel.setName(product.getName());
            productModel.setPrice(product.getPrice());
            productRepository.save(productModel);
        }
    }
}
