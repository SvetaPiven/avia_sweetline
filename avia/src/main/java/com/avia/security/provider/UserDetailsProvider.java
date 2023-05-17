package com.avia.security.provider;

import com.avia.model.entity.Role;
import com.avia.model.entity.User;
import com.avia.repository.UserRepository;
import com.avia.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDetailsProvider implements UserDetailsService {

    private final UserService userService;
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> searchResult = userRepository.findUserByEmail(email);

        if (searchResult.isPresent()) {
            User user = searchResult.get();
//            return org.springframework.security.core.userdetails.User.builder()
//                    .username(user.getAuthenticationInfo().getEmail())
//                    .password(user.getAuthenticationInfo().getUserPassword())
//                    .roles(user.getRoles()
//                            .stream()
//                            .map(Role::getRoleName)
//                            .toArray(String[]::new))
//
//                    .build();
            return new org.springframework.security.core.userdetails.User(
                    user.getAuthenticationInfo().getEmail(),
                    user.getAuthenticationInfo().getUserPassword(),
                    getA(user)
            );
        } else {
            throw new UsernameNotFoundException("No user found with email: " + email);
        }
    }
    private Collection<? extends GrantedAuthority> getA(User user) {
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRoleName())));
        return authorities;
    }
}