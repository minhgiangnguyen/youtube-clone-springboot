package com.petproject.youtubeclone.config;

import com.petproject.youtubeclone.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
//@Order(1)
public class WebConfigSecurity {

    @Bean
    UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http.authenticationProvider(authenticationProvider());

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->auth
                        .requestMatchers("/"
                                        ,"/403"
                                        ,"/home"
                                        ,"/register"
                                        ,"/webjars/**"
                                        ,"/assets_home/**"
                                        ,"/user-videos/**"
                                        ,"/user-photos/**"
                                        ,"/videos/**"
                                        ,"/channels/**")
                        .permitAll()
                        .requestMatchers("/studio/**","/studio").hasRole("USER")
                        .requestMatchers("/admin/**","/admin*").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(login ->
                        login.usernameParameter("email")
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/success", true)
                                .failureUrl("/login?error")
                                .permitAll()
                )
                .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login?logout").permitAll()
                )
				.exceptionHandling((ex) -> ex.accessDeniedPage("/403"));

        return http.build();
    }


}
