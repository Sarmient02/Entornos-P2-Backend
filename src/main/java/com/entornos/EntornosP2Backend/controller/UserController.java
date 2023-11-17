package com.entornos.EntornosP2Backend.controller;

import com.entornos.EntornosP2Backend.dto.EditUserRequestDTO;
import com.entornos.EntornosP2Backend.dto.SignUpRequestDTO;
import com.entornos.EntornosP2Backend.dto.UserDataDTO;
import com.entornos.EntornosP2Backend.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private IUserService userService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping("/new")
    @PreAuthorize("hasAuthority('admin')")
    public void create(@RequestBody SignUpRequestDTO request) {
        userService.create(request);
    }

    @PutMapping()
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> edit(@RequestBody EditUserRequestDTO request) {
        if(userService.edit(request)){
            return ResponseEntity.ok("User edited successfully");
        } else {
            return ResponseEntity.badRequest().body("User not found");
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping("/data")
    @PreAuthorize("hasAuthority('admin') || hasAuthority('user')")
    public ResponseEntity<UserDataDTO> getUserData() {
        return ResponseEntity.ok(userService.getUserData());
    }

    @Autowired
    public void setUserService(@Qualifier("userServiceImpl") IUserService userService){
        this.userService = userService;
    }

}
