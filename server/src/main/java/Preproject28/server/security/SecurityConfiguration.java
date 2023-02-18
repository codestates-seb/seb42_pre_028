package Preproject28.server.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity(debug = true) //스프링 시큐리티 필터가 스프링 필터체인에 등록되게 해줌.
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http

                .csrf().disable()
                .formLogin()
                .and()
                .logout()
                .and()
                .authorizeRequests()
                .antMatchers("/members/**").hasRole("USER")
                .antMatchers("/questions/**").permitAll()
                .antMatchers("/answers/**").authenticated();

        return http.build();
    }
    @Bean
    public UserDetailsManager userDetailsService(){
        UserDetails userDetails = User.withDefaultPasswordEncoder()
                .username("godalsgh@gmail.com")
                .password("1111")
                .roles("ADMIN")
                .build();

        return  new InMemoryUserDetailsManager(userDetails);

    }
}
