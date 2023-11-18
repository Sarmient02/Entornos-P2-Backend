package com.entornos.EntornosP2Backend.service.interfaces;

import com.entornos.EntornosP2Backend.dto.EditUserRequestDTO;
import com.entornos.EntornosP2Backend.dto.SignUpRequestDTO;
import com.entornos.EntornosP2Backend.dto.UserDataDTO;
import com.entornos.EntornosP2Backend.model.Role;

import com.entornos.EntornosP2Backend.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUserService {

    public User create(SignUpRequestDTO request);

    public UserDetailsService userDetailsService();

    public UserDataDTO getUserById(Long id);

    public List<User> findAll();

    public User save(User newUser);

    public void delete(Long id);

    public boolean edit(EditUserRequestDTO editedUser);

    Role saveRole(Role role);

    public UserDataDTO getUserData();

}
