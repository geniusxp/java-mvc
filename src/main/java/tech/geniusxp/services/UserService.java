package tech.geniusxp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tech.geniusxp.models.Role;
import tech.geniusxp.models.User;
import tech.geniusxp.repositories.RoleRepository;
import tech.geniusxp.repositories.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    public void saveUser(String username, String password, List<String> roles) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        HashSet<Role> userRoles = new HashSet<>();
        for (String roleName : roles) {
            Role role = roleRepository.findByName(roleName);
            if (role != null) {
                userRoles.add(role);
            }
        }

        user.setRoles(userRoles);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        Set<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities);
    }
}