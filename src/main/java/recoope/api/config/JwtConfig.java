//package recoope.api.config;
//
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import jakarta.security.auth.message.callback.SecretKeyCallback;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import recoope.api.config.filter.JwtAuthFilter;
//import recoope.api.services.CustomUserDetailService;
//
//import javax.crypto.SecretKey;
//
//@Configuration
//@EnableWebSecurity
//public class JwtConfig {
//    private final CustomUserDetailService customUserDetailService;
//
//    public JwtConfig (CustomUserDetailService customUserDetailService) {
//        this.customUserDetailService = customUserDetailService;
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.
//                authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/api/auth/login").permitAll()
//                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
//                        .requestMatchers("/api/user/**").hasRole("USER")
//                        .anyRequest().authenticated()
//                        )
//                .formLogin(AbstractHttpConfigurer::disable) // Disable from-based authentication
//                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for simplicity
//                .addFilterBefore(new JwtAuthFilter(customUserDetailService, secretKey()),
//                        UsernamePasswordAuthenticationFilter.class)
//                .userDetailsService(customUserDetailService);
//
//
//        return http.build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SecretKey secretKey() {
//        return Keys.secretKeyFor(SignatureAlgorithm.HS512);
//    }
//
//
//}
