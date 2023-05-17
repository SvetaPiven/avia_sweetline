package com.avia.security.provider;

import com.avia.model.entity.AuthenticationInfo;
import com.avia.model.entity.Role;
import com.avia.model.entity.User;
import com.avia.repository.RoleRepository;
import com.avia.repository.UserRepository;
import com.avia.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserDetailsProvider implements UserDetailsService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> searchResult = userRepository.findByAuthenticationInfoEmail(email);

        if (searchResult.isPresent()) {
            User user = searchResult.get();
            AuthenticationInfo authenticationInfo = user.getAuthenticationInfo();
            Integer roleId = user.getIdRole().getIdRole();
            Role role = roleRepository.findById(roleId).orElseThrow(() -> new UsernameNotFoundException("Role not found"));

            return new org.springframework.security.core.userdetails.User(
                    authenticationInfo.getEmail(),
                    authenticationInfo.getUserPassword(),
//                        ["ROLE_USER", "ROLE_ADMIN"]
                    AuthorityUtils.createAuthorityList(role.getRoleName())
            );
//            return new org.springframework.security.core.userdetails.User(
//                    user.getAuthenticationInfo().getEmail(),
//                    user.getAuthenticationInfo().getUserPassword(),
//                    getA(user)
//            );
        } else {
            throw new UsernameNotFoundException("No user found with email: " + email);
        }
    }

    public Collection<? extends GrantedAuthority> getA(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<Role> roles = userRepository.findRolesByIdUser(user.getIdUser());
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRoleName())));
        return authorities;
    }
//    private Collection<? extends GrantedAuthority> getA(User user) {
//        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
//        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRoleName())));
//        return authorities;
//    }
}