package com.app_products.app_products.DTO;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;


@Data
public class ProductDTO {

    @NotEmpty(message = "Name cannot be empty.")
    private String name;

    @Min(value = 0, message = "Minimum price is 0.")
    @NotNull(message = "Price cannot be null")
    private float price;

    public ProductDTO(){
    }
    public ProductDTO(String name, float price){
        this.name = name;
        this.price = price;
    }

    public String validator(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<ProductDTO>> violations = validator.validate(new ProductDTO(this.name, this.price));
        String msg = "";
        for (ConstraintViolation<ProductDTO> violation : violations) {
            msg += violation.getMessage();
        }
        return msg;
    }
}
