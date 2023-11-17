package com.entornos.EntornosP2Backend.service.impl;

import com.entornos.EntornosP2Backend.dto.EditUserRequestDTO;
import com.entornos.EntornosP2Backend.dto.SignUpRequestDTO;
import com.entornos.EntornosP2Backend.dto.UserDataDTO;
import com.entornos.EntornosP2Backend.model.Role;
import com.entornos.EntornosP2Backend.model.User;
import com.entornos.EntornosP2Backend.repository.IRoleRepository;
import com.entornos.EntornosP2Backend.repository.IUserRepository;
import com.entornos.EntornosP2Backend.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private IUserRepository userRepository;

    private IRoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public User loadUserByUsername(String username) {
                return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

    public List<User> findAll() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public User create(SignUpRequestDTO request) {
        var user = new User();
        user.setFullName(request.getFullName());
        user.setStudentCode(request.getStudentCode());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        Role role = roleRepository.findByName("user");
        user.setRoles(Set.of(role));

        user = this.save(user);
        return user;
    }

    public User save(User newUser) {
        if (newUser.getId() == null){
            newUser.setCreatedAt(LocalDateTime.now());
        }else{
            newUser.setUpdatedAt(LocalDateTime.now());
            userRepository.editUser(newUser.getUsername(), newUser.getFullName(), newUser.getStudentCode(), newUser.getEmail(), newUser.getUpdatedAt(), newUser.getPassword(), newUser.getId());;
        }
        return userRepository.save(newUser);
    }


    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public UserDataDTO getUserData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserDataDTO userDataDTO = new UserDataDTO();
        userDataDTO.setUsername(userDetails.getUsername());
        userDataDTO.setRole(userDetails.getAuthorities().toArray()[0].toString());
        userRepository.findByUsername(userDetails.getUsername()).ifPresent(user -> {
            userDataDTO.setFullName(user.getFullName());
            userDataDTO.setId(user.getId());
        });
        return userDataDTO;
    }

    public boolean edit(EditUserRequestDTO editedUser) {
        Optional<User> oldUser = userRepository.findById(editedUser.getId());
        if (oldUser.isPresent()){
            if(editedUser.getPassword().isEmpty() || editedUser.getPassword() == null){
                editedUser.setPassword(oldUser.get().getPassword());
            }else{
                editedUser.setPassword(passwordEncoder.encode(editedUser.getPassword()));
            }
            User user = new User();
            user.setId(editedUser.getId());
            user.setFullName(editedUser.getFullName());
            user.setStudentCode(editedUser.getStudentCode());
            user.setEmail(editedUser.getEmail());
            user.setUsername(editedUser.getUsername());
            user.setPassword(editedUser.getPassword());
            user.setRoles(this.editUserRoles(editedUser));
            this.save(user);
            return true;
        }
        return false;
    }

    public Set<Role> editUserRoles(EditUserRequestDTO newUser) {
        Set<Role> newRoles = new HashSet<>();

        for(String roleName : newUser.getRoles()) {
            Role role = roleRepository.findByName(roleName);
            if(role != null) {
                newRoles.add(role);
            }
        }
        return newRoles;
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Autowired
    public void setUserRepository(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(IRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

}
