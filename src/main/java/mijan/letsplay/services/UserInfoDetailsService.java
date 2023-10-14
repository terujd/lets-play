package mijan.letsplay.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import mijan.letsplay.models.User;
import mijan.letsplay.repositories.UserRepository; // You should replace this with your UserRepository or data access logic

@Service
public class UserInfoDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Replace the logic below with how you retrieve user information from your database or data source
        User user = UserRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // You should have a User class that implements UserDetails or a custom UserDetails implementation
        // Return the UserDetails object based on your User entity
        return (UserDetails) user;
    }
}
