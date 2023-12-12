package com.entornos.EntornosP2Backend.controller;

import com.entornos.EntornosP2Backend.dto.EditUserRequestDTO;
import com.entornos.EntornosP2Backend.dto.SignUpRequestDTO;
import com.entornos.EntornosP2Backend.dto.UserDataDTO;
import com.entornos.EntornosP2Backend.service.impl.JwtServiceImpl;
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
    private JwtServiceImpl jwt;

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
    public void edit(@RequestBody EditUserRequestDTO request) {
        if(userService.edit(request)){
        } else {
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<UserDataDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/data")
    @PreAuthorize("hasAuthority('admin') || hasAuthority('user')")
    public ResponseEntity<UserDataDTO> getUserData() {
        return ResponseEntity.ok(userService.getUserData());
    }

    @PostMapping("/follow")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<?> followUser(@RequestParam Long idUser, @RequestParam Long idFollowed, @RequestHeader("Authorization") String token) {
        var id = Long.valueOf(jwt.extractUserId(token.substring(7)));
        if (!idUser.equals(id)) return ResponseEntity.badRequest().body(null);
        return ResponseEntity.ok(userService.followUser(idUser, idFollowed));
    }

    @PostMapping("/unfollow")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<?> unfollowUser(@RequestParam Long idUser, @RequestParam Long idFollowed, @RequestHeader("Authorization") String token) {
        var id = Long.valueOf(jwt.extractUserId(token.substring(7)));
        if (!idUser.equals(id)) return ResponseEntity.badRequest().body(null);
        return ResponseEntity.ok(userService.unfollowUser(idUser, idFollowed));
    }

    @GetMapping("/checkFollow")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<Boolean> checkFollow(@RequestParam Long idUser, @RequestParam Long idFollowed, @RequestHeader("Authorization") String token) {
        var id = Long.valueOf(jwt.extractUserId(token.substring(7)));
        if (!idUser.equals(id)) return ResponseEntity.badRequest().body(null);
        return ResponseEntity.ok(userService.isFollowing(idUser, idFollowed));
    }


    @Autowired
    public void setUserService(@Qualifier("userServiceImpl") IUserService userService){
        this.userService = userService;
    }

    @Autowired
    public void setJwtService(@Qualifier("jwtServiceImpl") JwtServiceImpl jwt){
        this.jwt = jwt;
    }

}
