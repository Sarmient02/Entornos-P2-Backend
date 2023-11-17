package com.entornos.EntornosP2Backend.repository;

import com.entornos.EntornosP2Backend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

}
