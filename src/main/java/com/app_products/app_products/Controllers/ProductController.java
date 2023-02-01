package com.app_products.app_products.Controllers;

import com.app_products.app_products.DTO.Message;
import com.app_products.app_products.DTO.ProductDTO;
import com.app_products.app_products.Models.ProductModel;
import com.app_products.app_products.Services.ProductService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "http://localhost:4200/")
public class ProductController {
    @Autowired
    ProductService productService;

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    @GetMapping
    public ResponseEntity<List<ProductModel>> getAll(){
        List<ProductModel> products = productService.getProducts();
        return new ResponseEntity<List<ProductModel>>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductModel> getById(@PathVariable("id") int id){
        if(!productService.existsById(id))
            return new ResponseEntity(new Message("El id " + id + " No existe."), HttpStatus.NOT_FOUND);
        ProductModel product = productService.getProductById(id).get();
        return new ResponseEntity(product, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ProductModel> getById(@PathVariable("name") String name){
        if(!productService.existsByName(name))
            return new ResponseEntity(new Message("El producto " + name + " No existe."), HttpStatus.NOT_FOUND);
        ProductModel product = productService.getProductByName(name).get();
        return new ResponseEntity(product, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<?> save(@RequestBody ProductModel product){
        ProductDTO productDTO = new ProductDTO(product.getName(), product.getPrice());
        if(!productDTO.validator().equals(""))
            return new ResponseEntity(new Message(productDTO.validator()), HttpStatus.BAD_REQUEST);
        if(productService.existsByName(product.getName()))
            return new ResponseEntity(new Message("El producto " + product.getName() + " ya existe."), HttpStatus.BAD_REQUEST);
        ProductModel saved = productService.saveProduct(product);
        return new ResponseEntity(new Message("Producto creado con éxito."), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody ProductModel product){
        if(!productService.existsById(id))
            return new ResponseEntity(new Message("El producto con el id " + id + " no existe."), HttpStatus.BAD_REQUEST);
        if(productService.existsByName(product.getName()) && productService.getProductByName(product.getName()).get().getId() != id)
            return new ResponseEntity(new Message("El producto " + product.getName() + " ya existe."), HttpStatus.BAD_REQUEST);
        ProductDTO productDTO = new ProductDTO(product.getName(), product.getPrice());
        if(!productDTO.validator().equals(""))
            return new ResponseEntity(new Message(productDTO.validator()), HttpStatus.BAD_REQUEST);
        productService.update(id, product);
        return new ResponseEntity(new Message("Producto actualizado con éxito."), HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id){
        if(!productService.existsById(id))
            return new ResponseEntity(new Message("El producto con el id " + id + " no existe."), HttpStatus.BAD_REQUEST);
        if(productService.removeProductById(id)) {
            return new ResponseEntity(new Message("Producto " + id + " Eliminado con éxito."), HttpStatus.OK);
        }
        return new ResponseEntity(new Message("Ha ocurrido un error en el servidor."), HttpStatus.BAD_REQUEST);
    }
}