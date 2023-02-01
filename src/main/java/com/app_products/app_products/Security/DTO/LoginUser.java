package com.app_products.app_products.Security.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginUser {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
