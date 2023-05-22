package com.avia.controller.rest;

import com.avia.model.entity.AuthenticationInfo;
import com.avia.model.entity.User;
import com.avia.repository.UserRepository;
import com.avia.security.config.JwtConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/admin/users")
@RequiredArgsConstructor
public class UserAdminDataController {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    private final JwtConfiguration configuration;

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
