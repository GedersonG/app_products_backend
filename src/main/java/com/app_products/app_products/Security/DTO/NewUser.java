package com.app_products.app_products.Security.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class NewUser {
    @NotBlank
    private String name;

    @NotBlank
    private String username;

    @Email
    private String email;

    @NotBlank
    private String password;
    private Set<String> role = new HashSet<>();
}
