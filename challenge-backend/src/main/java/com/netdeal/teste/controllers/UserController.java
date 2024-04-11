package com.netdeal.teste.controllers;

import com.netdeal.teste.dto.CreateUserDto;
import com.netdeal.teste.models.UserModel;
import com.netdeal.teste.services.PasswordStrengthService;
import com.netdeal.teste.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/users/v1")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    PasswordStrengthService passwordStrengthService;

    @GetMapping("/{id}")
    public ResponseEntity<UserModel> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<UserModel> createUser(@RequestBody CreateUserDto createUserDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(createUserDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserModel> updateUser(@PathVariable Long id, @RequestBody UserModel updatedUser) {
        return ResponseEntity.ok(userService.updateUser(id, updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("checkStrength")
    public ResponseEntity<Integer> checkStrength(@RequestBody String password) {
        return ResponseEntity.ok(passwordStrengthService.countStrength(password));
    }

    @GetMapping("superiors-by-name")
    public ResponseEntity<Set<UserModel>> findByNameContaining(@RequestParam String name){
        return ResponseEntity.ok(userService.findByNameContaining(name));
    }

    @GetMapping("superiors")
    public ResponseEntity<Set<UserModel>> getSuperiors(){
        return ResponseEntity.ok(userService.getSuperiors());
    }

    @GetMapping("subordinates/{id}")
    public ResponseEntity<Set<UserModel>> getSubordinates(@PathVariable Long id){
        return ResponseEntity.ok(userService.getSubordinates(id));
    }
}
