package com.app_products.app_products.Security.Services;

import com.app_products.app_products.Security.Enums.RolName;
import com.app_products.app_products.Security.Models.Rol;
import com.app_products.app_products.Security.Repositories.RolRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class RolService {
    RolRepository rolRepository;

    public Optional<Rol> getByRolName(RolName rolName){
        return rolRepository.findByRolName(rolName);
    }
}
