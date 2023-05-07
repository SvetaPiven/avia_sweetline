package com.avia.controller.rest;

import com.avia.dto.requests.UserDto;
import com.avia.exception.EntityNotFoundException;
import com.avia.model.entity.User;
import com.avia.repository.UserRepository;
import com.avia.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {

        List<User> users = userRepository.findAll();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto) {
        User createdUser = userService.createUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok).orElseThrow(() ->
                new EntityNotFoundException("User with id " + id + " not found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        User updatedUser = userService.updateUser(id, userDto);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {

        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new EntityNotFoundException("User with id " + id + " not found");
        }
    }

    @PutMapping("/{id}/delete")
    public ResponseEntity<Void> softDeleteUser(@PathVariable("id") Long id) {

        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setIsDeleted(true);
            userRepository.save(user);
            return ResponseEntity.noContent().build();
        } else {
            throw new EntityNotFoundException("User status with id " + id + " not found!");
        }
    }

//    @GetMapping("/search-by-email")
//    public ResponseEntity<User> getUserByEmail(@RequestParam(value = "email") String email) {
//
//        Optional<User> user = userRepository.findUserByEmail(email);
//
//        return user.map(ResponseEntity::ok).orElseThrow(() ->
//                new EntityNotFoundException("User with id " + email + " not found"));
//    }
}
