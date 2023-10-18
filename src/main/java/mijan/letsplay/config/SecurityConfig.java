package mijan.letsplay.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;
import mijan.letsplay.filter.JWTFilter;
import mijan.letsplay.services.UserInfoDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

        // First we need to inject out JWTFilter into the SecurityFilterChain
        @Autowired
        private JWTFilter jwtFilter;

        // Then we need to configure the UserDetailsService
        @Bean
        public UserDetailsService userDetailService() {
                return new UserInfoDetailsService();
        }

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http.csrf(csrf -> csrf.disable())
                                .exceptionHandling(
                                                exceptionHandling -> exceptionHandling
                                                                .authenticationEntryPoint((request, response,
                                                                                authException) -> response
                                                                                                .sendError(HttpServletResponse.SC_UNAUTHORIZED)))
                                .authorizeHttpRequests(
                                                authorize -> authorize.requestMatchers("/api/products").permitAll()
                                                                .requestMatchers("/api/products/{id}").permitAll()
                                                                .requestMatchers("/api/auth").permitAll()
                                                                .anyRequest().authenticated())
                                .authenticationProvider(authenticationProvider())
                                .sessionManagement(
                                                sessionManagement -> sessionManagement.sessionCreationPolicy(
                                                                SessionCreationPolicy.STATELESS))
                                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationProvider authenticationProvider() {
                DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
                authProvider.setUserDetailsService(userDetailService());
                authProvider.setPasswordEncoder(passwordEncoder());
                return authProvider;
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
                return config.getAuthenticationManager();
        }

}
