package mijan.letsplay.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mijan.letsplay.services.JWTService;
import mijan.letsplay.services.UserInfoDetailsService;

// The JWTFilter class extends the OncePerRequestFilter class
// and overrides the doFilterInternal method to implement the filter logic.
@Component
public class JWTFilter extends OncePerRequestFilter {

    // The JWTService bean is used to generate and validate the JWT
    @Autowired
    private JWTService jwtService;

    // The UserDetailsService bean is used to retrieve user details from the database
    @Autowired
    private UserInfoDetailsService userInfoDetailsService;

    // The InvalidTokenException class is used to throw an exception when the token is invalid
    public static class InvalidTokenException extends RuntimeException {
        public InvalidTokenException(String message) {
            super(message);
        }
    }

    // The doFilterInternal method is called by Spring Security for every request
    // and is used to implement the filter logic.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // Handle the request
            handleRequest(request, response, filterChain);
        } catch (Exception e) {
            // Handle exceptions
            handleException(response, e);
        }
    }

    // The handleRequest method is used to handle the request
    private void handleRequest(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        // Get the Authorization header from the request
        String authHeader = request.getHeader("Authorization");

        // Check if the Authorization header is valid
        if (isBearerToken(authHeader)) {

            // Extract the JWT from the Authorization header
            String jwt = authHeader.substring(7); // Remove "Bearer "
            handleBearerToken(jwt, request);// Handle the JWT
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }

    // The isBearerToken method is used to check if the Authorization header is valid
    private boolean isBearerToken(String authHeader) {
        return authHeader != null && authHeader.startsWith("Bearer ");
    }

    // The handleBearerToken method is used to handle the JWT
    private void handleBearerToken(String jwt, HttpServletRequest request) {
        // Extract the username from the JWT
        String username = jwtService.extractUsername(jwt);
        // Check if the username is valid
        if (username == null || SecurityContextHolder.getContext().getAuthentication() != null) {
            return;
        }

        // Get the user details from the database
        UserDetails userDetails = getUserDetailsOrThrow(username);
        // Check if the JWT is valid
        if (!jwtService.validateToken(jwt, userDetails)) {
            throw new InvalidTokenException("Invalid token");
        }

        // Set the user in the SecurityContext
        populateSecurityContext(userDetails, request);
    }

    // The getUserDetailsOrThrow method is used to get the user details from the database
    private UserDetails getUserDetailsOrThrow(String username) {
        // Get the user details from the database
        try {
            return userInfoDetailsService.loadUserByUsername(username);
            // If the user does not exist, throw an exception
        } catch (UsernameNotFoundException e) {
            throw new IllegalStateException("User not found", e);
        }
    }

    // The populateSecurityContext method is used to set the user in the SecurityContext
    private void populateSecurityContext(UserDetails userDetails, HttpServletRequest request) {
        // Create an authentication object using the user details
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

                // Set the authentication details
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // Set the authentication object in the SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    // The handleException method is used to handle exceptions
    private void handleException(HttpServletResponse response, Exception e) throws IOException {
        // Check if the exception is an instance of IllegalStateException or InvalidTokenException
        if (e instanceof IllegalStateException || e instanceof InvalidTokenException) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
        } else {
            response.sendError(HttpStatus.BAD_REQUEST.value(), "An error occurred");
        }
    }
}
