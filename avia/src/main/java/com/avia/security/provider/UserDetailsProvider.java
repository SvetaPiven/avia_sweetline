package com.avia.security.provider;

import com.avia.model.entity.Role;
import com.avia.model.entity.User;
import com.avia.repository.UserRepository;
import com.avia.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserDetailsProvider implements UserDetailsService {

    private final UserService userService;
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            Optional<User> searchResult = userRepository.findUserByEmail(email);

            if (searchResult.isPresent()) {
                User user = searchResult.get();
                return new org.springframework.security.core.userdetails.User(
                        user.getAuthenticationInfo().getEmail(),
                        user.getAuthenticationInfo().getUserPassword(),
//                        ["ROLE_USER", "ROLE_ADMIN"]
               //         getA(user)
                        AuthorityUtils.commaSeparatedStringToAuthorityList(
                                userService.getUserAuthorities(user.getIdUser())
                                        .stream()
                                        .map(Role::getRoleName)
                                        .collect(Collectors.joining(","))
                        )
                );
            } else {
                throw new UsernameNotFoundException(String.format("No user found with email '%s'.", email));
            }
        } catch (Exception e) {
            throw new UsernameNotFoundException("User with this login not found");
        }
    }
//    private Collection<? extends GrantedAuthority> getA(User user) {
//        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
//        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRoleName())));
//        return authorities;
//    }
}
