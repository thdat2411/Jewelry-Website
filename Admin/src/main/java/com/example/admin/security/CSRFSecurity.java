package com.example.admin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class CSRFSecurity {
        @Bean
        protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

                http.authorizeRequests()
                                .antMatchers("/categories", "/save-category", "/findById/**", "/update-category",
                                                "/delete-category",
                                                "/enable-category", "/login", "/home", "/", "/do-login", "/orders",
                                                "/accept-order",
                                                "/cancel-order", "/products")
                                .permitAll()
                                .antMatchers("/css/**", "/js/**", "/img/**", "/vendor/**", "/scss/**", "/customCss/**")
                                .permitAll()
                                .antMatchers("/add-product", "/save-product", "/update-product/**", "/enable-product",
                                                "/delete-product")
                                .authenticated()
                                .and()
                                .csrf();

                return http.build();
        }
}