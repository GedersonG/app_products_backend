package com.app_products.app_products.Security.Controllers;

import com.app_products.app_products.DTO.Message;
import com.app_products.app_products.Security.DTO.JwtDTO;
import com.app_products.app_products.Security.DTO.LoginUser;
import com.app_products.app_products.Security.DTO.NewUser;
import com.app_products.app_products.Security.Enums.RolName;
import com.app_products.app_products.Security.JWT.JwtProvider;
import com.app_products.app_products.Security.Models.Rol;
import com.app_products.app_products.Security.Models.User;
import com.app_products.app_products.Security.Services.RolService;
import com.app_products.app_products.Security.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    RolService rolService;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/new")
    public ResponseEntity<?> newUser(@Valid @RequestBody NewUser newUser, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Message("The fields are not correct"), HttpStatus.BAD_REQUEST);
        if (userService.existByUsername(newUser.getUsername()))
            return new ResponseEntity(new Message("The username " + newUser.getUsername() + " already exists."), HttpStatus.BAD_REQUEST);
        if (userService.existByEmail(newUser.getEmail()))
            return new ResponseEntity(new Message("The email " + newUser.getEmail() + " it is already in use."), HttpStatus.BAD_REQUEST);
        User user = new User(newUser.getName(), newUser.getUsername(), newUser.getEmail(), passwordEncoder.encode(newUser.getPassword()));
        Set<Rol> role = new HashSet<>();
        role.add(rolService.getByRolName(RolName.ROLE_USER).get());
        if(newUser.getRole().contains("admin"))
            role.add(rolService.getByRolName(RolName.ROLE_ADMIN).get());
        user.setRole(role);
        userService.save(user);
        return new ResponseEntity(new Message("You have successfully registered"), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDTO> login(@Valid @RequestBody LoginUser loginUser, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Message("The name or password is not correct"), HttpStatus.BAD_REQUEST);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        JwtDTO jwtDTO = new JwtDTO(jwt, userDetails.getUsername(), userDetails.getAuthorities());
        return new ResponseEntity(jwtDTO, HttpStatus.OK);
    }
}
