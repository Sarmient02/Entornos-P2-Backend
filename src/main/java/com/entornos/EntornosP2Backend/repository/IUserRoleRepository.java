package com.entornos.EntornosP2Backend.repository;

import com.entornos.EntornosP2Backend.model.UserRoles;
import com.entornos.EntornosP2Backend.model.UserRolesId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRoleRepository extends JpaRepository<UserRoles, UserRolesId> {
}
