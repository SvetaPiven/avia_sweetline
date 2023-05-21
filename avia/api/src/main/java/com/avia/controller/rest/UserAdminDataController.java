package com.avia.controller.rest;

import com.avia.exception.EntityNotFoundException;
import com.avia.model.entity.AuthenticationInfo;
import com.avia.model.entity.User;
import com.avia.repository.UserRepository;
//import com.noirix.domain.hibernate.HibernateUser;
//import com.noirix.repository.springdata.UserDataRepository;
//import com.noirix.security.util.PrincipalUtils;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.avia.security.PrincipalUtils;
import com.avia.security.config.JwtConfiguration;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/admin/users")
@RequiredArgsConstructor
public class UserAdminDataController {

    private final UserRepository userRepository;

    private final PrincipalUtils principalUtils;

    private final PasswordEncoder encoder;

    private final JwtConfiguration configuration;

    @Operation(
            summary = "Spring Data User Find All Search",
            description = "Find All Users without limitations",
            responses = {
                    @ApiResponse(
                            responseCode = "OK",
                            description = "Successfully loaded Users",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))
                    )
            }
    )
    @GetMapping
    public ResponseEntity<Object> getAllUsers(@Parameter(hidden = true) Principal principal) {

        String username = principalUtils.getUsername(principal);

        List<User> users = userRepository.findAll();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/passwords")
    public ResponseEntity<Object> updateUsersPasswords() {

        List<User> all = userRepository.findAll();

        for (User user : all) {
            AuthenticationInfo authenticationInfo = user.getAuthenticationInfo();

            String password = authenticationInfo.getUserPassword() + configuration.getPasswordSalt();
            String encodedPassword = encoder.encode(password);

            authenticationInfo.setUserPassword(encodedPassword);
            user.setAuthenticationInfo(authenticationInfo);
            userRepository.save(user);
        }

        return new ResponseEntity<>(all.size(), HttpStatus.OK);
    }

}
