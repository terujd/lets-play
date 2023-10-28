package mijan.letsplay.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import mijan.letsplay.models.User;
import mijan.letsplay.repositories.UserRepository;

// UserDetailsService is an interface provided by Spring Security
// It has a method called loadUserByUsername which returns a UserDetails object
// We need to implement this method and provide our own logic for loading user information
@Component
public class UserInfoDetailsService implements UserDetailsService {

    // This method is called by Spring Security to get the user details from the
    // database and return a UserDetails object
    @Autowired
    private UserRepository userRepository;

    // This method is called by Spring Security to get the user details from the
    // database and return a UserDetails object
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Get user information from database
        Optional<User> userOptional = userRepository.findByName(username);
        // Check if user exists
        if (userOptional.isEmpty()) {
            // If user not found. Throw this exception.
            throw new UsernameNotFoundException("User not found: " + username);
        }
        // Get user object
        User user = userOptional.get();

        // Initialize authorities
        List<GrantedAuthority> authorities = new ArrayList<>();
        // Add our user's authorities
        authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));

        // Return Spring Security User object
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(),
                authorities);
    }
}
