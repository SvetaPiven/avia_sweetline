package com.avia.controller.rest;

import com.avia.exception.EntityNotFoundException;
import com.avia.exception.ValidationException;
import com.avia.model.entity.User;
import com.avia.model.request.UserRequest;
import com.avia.repository.UserRepository;
import com.avia.service.UserService;
import com.google.maps.errors.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/rest/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    private final UserRepository userRepository;

    @Value("${user.page-capacity}")
    private Integer userPageCapacity;

    @Operation(
            summary = "Spring Data User Find All Search",
            description = "Find All Users without limitations",
            responses = {
                    @ApiResponse(
                            responseCode = "OK",
                            description = "Successfully loaded Users",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = User.class)))

                    ),
                    @ApiResponse(responseCode = "INTERVAL_SERVER_ERROR", description = "Internal Server Error")
            }
    )
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {

        List<User> users = userRepository.findAll();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Operation(
            summary = "Spring Data User Search with Pageable Params",
            description = "Load page by number with sort and offset params",
            responses = {
                    @ApiResponse(
                            responseCode = "OK",
                            description = "Successfully loaded Users",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = User.class)))
                    ),
                    @ApiResponse(
                            responseCode = "NOT_FOUND",
                            description = "Users not found"
                    )
            }
    )
    @GetMapping("/page/{page}")
    public ResponseEntity<Map<String, Page<User>>> getAllUsersWithPageAndSort(@Parameter(name = "page", example = "0", required = true) @PathVariable int page) {

        Pageable pageable = PageRequest.of(page, userPageCapacity, Sort.by("idUser").ascending());

        Page<User> users = userRepository.findAll(pageable);

        if (users.hasContent()) {
            Map<String, Page<User>> resultMap = Collections.singletonMap("result", users);
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Spring Data Create User",
            description = "Creates a new user",
            responses = {
                    @ApiResponse(
                            responseCode = "CREATED",
                            description = "User created",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = User.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "BAD_REQUEST",
                            description = "Validation error"
                    )
            }
    )
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody @Parameter(description = "User information", required = true) UserRequest userRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new ValidationException(errorMessage);
        }

        User createdUser = userService.createUser(userRequest);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }


    @Operation(
            summary = "Get User by ID",
            description = "Get user based on specified id",
            responses = {
                    @ApiResponse(
                            responseCode = "OK",
                            description = "User found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = User.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "BAD_REQUEST",
                            description = "User with ID not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@Parameter(description = "User ID", example = "5", required = true) @PathVariable("id") Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok).orElseThrow(() ->
                new EntityNotFoundException("User with id " + id + " not found"));
    }

    @Operation(
            summary = "Update User",
            description = "Updating an existing user",
            responses = {
                    @ApiResponse(
                            responseCode = "OK",
                            description = "User updated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = User.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "BAD_REQUEST",
                            description = "Validation error"
                    )
            }
    )
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@Parameter(description = "User ID") @PathVariable Long id,
                                           @Valid @RequestBody UserRequest userRequest,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new ValidationException(errorMessage);
        }
        User updatedUser = userService.updateUser(id, userRequest);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @Operation(
            summary = "Search users by email",
            description = "Search users by email",
            responses = {
                    @ApiResponse(
                            responseCode = "OK",
                            description = "Successfully loaded Users by Email",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = User.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "NOT_FOUND",
                            description = "Users not found"
                    )
            }
    )
    @GetMapping("/search/{email}")
    public ResponseEntity<List<User>> searchUsersByEmail(
            @Parameter(description = "User email",
                    example = "svetapiven93@gmail.com", required = true)
            @PathVariable("email") String email
    ) {
        List<User> users = userRepository.findUsersByEmail(email);
        if (!users.isEmpty()) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        } else {
            throw new EntityNotFoundException("Users with email " + email + " not found");
        }
    }

    @Operation(
            summary = "Delete User (for admins only)",
            description = "Delete User by ID",
            responses = {
                    @ApiResponse(
                            responseCode = "OK",
                            description = "User deleted successfully",
                            content = @Content(mediaType = "text/plain")
                    ),
                    @ApiResponse(
                            responseCode = "NOT_FOUND",
                            description = "User not found"
                    )
            }
    )
    @DeleteMapping("/{id}")
    public String deleteUser(@Parameter(description = "User ID") @PathVariable("id") Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            userRepository.deleteById(id);
            return "User with ID " + id + " deleted successfully.";
        } else {
            throw new EntityNotFoundException("User with ID " + id + " not found");
        }
    }

    @Operation(
            summary = "Change User Status (for admins only)",
            description = "Changes the status (isDeleted) of a user by ID",
            responses = {
                    @ApiResponse(
                            responseCode = "OK",
                            description = "Status changed successfully"
                    ),
                    @ApiResponse(
                            responseCode = "NOT_FOUND",
                            description = "User not found"
                    )
            }
    )
    @Secured("ROLE_ADMIN")
    @PutMapping("/{id}/status")
    public String changeStatus(
            @Parameter(description = "User ID") @PathVariable("id") Long id,
            @Parameter(description = "Status value") @RequestParam("isDeleted") boolean isDeleted) {

        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setDeleted(isDeleted);
            userRepository.save(user);
            return "Status changed successfully";
        } else {
            throw new EntityNotFoundException("User with id " + id + " not found!");
        }
    }
}
