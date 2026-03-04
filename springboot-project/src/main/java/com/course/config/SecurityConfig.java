package com.course.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.beans.factory.annotation.Autowired;
import com.course.service.CustomUserDetailsService;

@Configuration
public class SecurityConfig {

	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http)
	        throws Exception {

		http.userDetailsService(userDetailsService);
		
	    http
	    .authorizeHttpRequests(auth -> auth

	        // 公開頁面
	        .requestMatchers(
	                "/",
	                "/index",
	                "/home",
	                "/login",
	                "/doLogin",
	                "/register",
	                "/registerPage",
	                "/css/**",
	                "/js/**",
	                "/images/**"
	        ).permitAll()

	        // 其他需要登入
	        .anyRequest().authenticated()
	    )

	    .formLogin(form -> form
	            .loginPage("/login")
	            .loginProcessingUrl("/doLogin")
	            .defaultSuccessUrl("/loginSuccess")
	            .failureUrl("/login?error")
	            .permitAll()
	    )

	    .logout(logout -> logout
	            .logoutUrl("/logout")
	            .logoutSuccessUrl("/login?logout")
	    )

	    .csrf(csrf -> csrf.disable());

	    return http.build();
	}
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}