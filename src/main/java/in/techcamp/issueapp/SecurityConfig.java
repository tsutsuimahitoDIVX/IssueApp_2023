package in.techcamp.issueapp;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/css/**","/").permitAll()
                        .anyRequest().authenticated())
                .formLogin(login -> login
                        .loginPage("/loginForm")
                        .defaultSuccessUrl("/")
                        .failureUrl("/login?error")
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/"));
        return http.build();
    }
}