// package com.example.config;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.AuthenticationProvider;
// import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// import com.example.application.auth.JwtUtil;
// import com.example.application.interfaces.UserRepository;
// import com.example.domain.model.User;

// @Configuration
// public class SecurityConfig {

//     @Autowired
//     private UserRepository userRepository;

//     @Autowired
//     private JwtUtil jwtUtil;

//     @Autowired
//     private JwtRequestFilter jwtRequestFilter;
//     @Autowired
//     private AuthEntryPointJwt unauthorizedHandler;
//     @Bean
//     public UserDetailsService userDetailsService() {
//         return email -> {
//             User user = userRepository.findByEmail(email)
//                     .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//             return org.springframework.security.core.userdetails.User.builder().username(user.getEmail()).password(user.getPassword()).roles("USER").build();

//         };
//     }
//     @Bean
//     public JwtRequestFilter authenticationJwtTokenFilter() {
//         return new JwtRequestFilter();
//     }
//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }

//     @Bean
//     public AuthenticationProvider authenticationProvider() {
//         DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//         authProvider.setUserDetailsService(userDetailsService());
//         authProvider.setPasswordEncoder(passwordEncoder());
//         return authProvider;
//     }

//     @Bean
//     public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
//         return configuration.getAuthenticationManager();
//     }

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         // Updated configuration for Spring Security 6.x
//         http
//                 .csrf(csrf -> csrf.disable()) // Disable CSRF
//                 .cors(cors -> cors.disable()) // Disable CORS (or configure if needed)
//                 .exceptionHandling(exceptionHandling ->
//                         exceptionHandling.authenticationEntryPoint(unauthorizedHandler)
//                 )
//                 .sessionManagement(sessionManagement ->
//                         sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                 )
//                 .authorizeHttpRequests(authorizeRequests ->
//                         authorizeRequests
//                                 .requestMatchers("/api/auth/**", "/api/test/all").permitAll() // Use 'requestMatchers' instead of 'antMatchers'
//                                 .anyRequest().authenticated()
//                 );
//         // Add the JWT Token filter before the UsernamePasswordAuthenticationFilter
//         http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//         return http.build();
//     }
// }
