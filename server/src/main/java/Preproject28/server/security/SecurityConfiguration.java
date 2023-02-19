package Preproject28.server.security;

import Preproject28.server.security.auths.JwtTokenizer;
import Preproject28.server.security.auths.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity //스프링 시큐리티 필터가 스프링 필터체인에 등록되게 해줌.
public class SecurityConfiguration {

    private final JwtTokenizer jwtTokenizer; // JWT 설정 추가

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().sameOrigin()
                .and()
//                .csrf().disable()
//                .formLogin()
//                .loginPage("로그인화면 url")
//                .loginProcessingUrl("/process_login") //로그인에 보내는 요청 form action 값 ,post 로
//                .failureUrl("로그인실패시")
//                .logout()
                .csrf().disable()
                .cors(withDefaults())    // corsConfigurationSource Bean 을 이용
                .formLogin().disable()   // CSR 방식일때 JSON 포맷으로 id,비밀번호 전송하기때문에 비활성화
                .httpBasic().disable()   // UsernamePasswordAuthenticationFilter 비활성화
                .apply(new CustomFilterConfigurer())
                .and()
                .authorizeRequests()
                .antMatchers("/members/**").permitAll()
                .antMatchers("/questions/**").permitAll()
                .antMatchers("/answers/**").hasRole("USER");

        return http.build();
    }
//    @Bean
//    public UserDetailsManager userDetailsService(){
//        UserDetails userDetails = User.withDefaultPasswordEncoder()
//                .username("godalsgh@gmail.com")
//                .password("1111")
//                .roles("USER")
//                .build();
//
//        return  new InMemoryUserDetailsManager(userDetails);
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("GET","POST","PATCH","DELETE"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;

    }

    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        // 구현한 JwtAuthenticationFilter 를 등록
        @Override
        public void configure(HttpSecurity builder) {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenizer);
            jwtAuthenticationFilter.setFilterProcessesUrl("auth/login");

            builder.addFilter(jwtAuthenticationFilter);
        }
    }
}
