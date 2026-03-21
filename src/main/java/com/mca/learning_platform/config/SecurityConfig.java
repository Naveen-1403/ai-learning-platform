package com.mca.learning_platform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // பாஸ்வேர்டை பாதுகாப்பாக Encrypt செய்யும் மெத்தட்
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // பதிவு செய்வதை அனுமதிக்க இதை தற்காலிகமாக நிறுத்துகிறோம்
            .authorizeHttpRequests(auth -> auth
                // லாகின், ரிஜிஸ்டர் பக்கங்களுக்கு யார் வேண்டுமானாலும் செல்லலாம்
            		.requestMatchers("/", "/login.html", "/register.html", "/api/auth/**", "/css/**", "/js/**").permitAll()
                // மற்ற எல்லா பக்கங்களுக்கும் லாகின் கட்டாயம்
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/index.html", true) // லாகின் ஆனதும் Dashboard-க்கு செல்லும்
                .failureUrl("/login.html?error=true")
                .permitAll()
            )
            .logout(logout -> logout.permitAll());

        return http.build();
    }
}