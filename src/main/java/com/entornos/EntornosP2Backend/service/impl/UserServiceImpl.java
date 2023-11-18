package com.entornos.EntornosP2Backend.service.impl;

import com.entornos.EntornosP2Backend.dto.EditUserRequestDTO;
import com.entornos.EntornosP2Backend.dto.SignUpRequestDTO;
import com.entornos.EntornosP2Backend.dto.UserDataDTO;
import com.entornos.EntornosP2Backend.model.Role;
import com.entornos.EntornosP2Backend.model.User;
import com.entornos.EntornosP2Backend.model.UserRoles;
import com.entornos.EntornosP2Backend.model.UserRolesId;
import com.entornos.EntornosP2Backend.repository.IRoleRepository;
import com.entornos.EntornosP2Backend.repository.IUserRepository;
import com.entornos.EntornosP2Backend.repository.IUserRoleRepository;
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
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private IUserRepository userRepository;

    private IUserRoleRepository userRoleRepository;
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

    public UserDataDTO getUserById(Long id) {
        UserDataDTO userDataDTO = new UserDataDTO();
        userRepository.findById(id).ifPresent(user -> {
            userDataDTO.setFull_name(user.getFullName());
            userDataDTO.setUsername(user.getUsername());
            userDataDTO.setEmail(user.getEmail());
            userDataDTO.setStudent_code(user.getStudentCode());
            userDataDTO.setId(user.getId().longValue());
            userDataDTO.setRoles(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
        });
        return userDataDTO;
    }

    public User create(SignUpRequestDTO request) {
        var user = new User();
        user.setFullName(request.getFull_name());
        user.setStudentCode(request.getStudent_code());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        Role role = roleRepository.findByName("user");
        user = this.save(user);
        var userRoleId = new UserRolesId();
        userRoleId.setRoleId(role.getId());
        userRoleId.setUserId(user.getId());
        var userRole = new UserRoles();
        userRole.setId(userRoleId);
        userRole.setCreatedAt(new Date());
        userRole.setUpdatedAt(new Date());
        this.userRoleRepository.save(userRole);

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
        userDataDTO.setRoles(userDetails.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toList()));
        userRepository.findByUsername(userDetails.getUsername()).ifPresent(user -> {
            userDataDTO.setFull_name(user.getFullName());
            userDataDTO.setId(user.getId());
        });
        return userDataDTO;
    }

    public boolean edit(EditUserRequestDTO editedUser) {
        Optional<User> oldUser = userRepository.findById(editedUser.getId());
        if (oldUser.isPresent()){
            if(editedUser.getPassword().isEmpty()){
                editedUser.setPassword(oldUser.get().getPassword());
            }else{
                editedUser.setPassword(passwordEncoder.encode(editedUser.getPassword()));
            }
            User user = new User();
            user.setId(editedUser.getId());
            user.setFullName(editedUser.getFull_name());
            user.setStudentCode(editedUser.getStudent_code());
            user.setEmail(editedUser.getEmail());
            user.setUsername(editedUser.getUsername());
            user.setPassword(editedUser.getPassword());
            this.save(user);
            this.editUserRoles(editedUser);
            //user.setRoles(this.editUserRoles(editedUser));
            return true;
        }
        return false;
    }

    private void editUserRoles(EditUserRequestDTO newUser) {
        Set<Role> newRoles = new HashSet<>();
        User oldUser = userRepository.findById(newUser.getId()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var roles = oldUser.getRoles();
        List<String> newRolesNames = new ArrayList<>();
        if (roles != null){
            newRolesNames = newUser.getRoles().stream().filter(role -> !roles.contains(role)).toList();
        } else {
            newRolesNames = newUser.getRoles();
        }

        if (newRolesNames.isEmpty()) {
            Role defaultRole = roleRepository.findByName("user");
            saveUserRole(newUser, defaultRole);
        }
        for(String roleName : newRolesNames) {
            Role role = roleRepository.findByName(roleName);
            if(role != null) {
                saveUserRole(newUser, role);
            }
        }
    }

    private void saveUserRole(EditUserRequestDTO newUser, Role defaultRole) {
        var userRoleId = new UserRolesId();
        userRoleId.setRoleId(defaultRole.getId());
        userRoleId.setUserId(newUser.getId());
        var userRole = new UserRoles();
        userRole.setId(userRoleId);
        userRole.setCreatedAt(new Date());
        userRole.setUpdatedAt(new Date());
        this.userRoleRepository.save(userRole);
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

    @Autowired
    public void setUserRoleRepository(IUserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }
}
