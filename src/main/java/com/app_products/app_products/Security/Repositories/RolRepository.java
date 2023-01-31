package com.app_products.app_products.Security.Repositories;

import com.app_products.app_products.Security.Enums.RolName;
import com.app_products.app_products.Security.Models.Rol;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends CrudRepository<Rol, Integer> {
    public abstract Optional<Rol> findByRolName(RolName rolName);
}
