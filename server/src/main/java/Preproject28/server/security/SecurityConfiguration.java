package Preproject28.server.security;

import Preproject28.server.security.auths.handler.MemberAccessDeniedHandler;
import Preproject28.server.security.auths.handler.MemberAuthenticationEntryPoint;
import Preproject28.server.security.auths.utils.CustomAuthorityUtils;
import Preproject28.server.security.auths.jwt.JwtTokenizer;
import Preproject28.server.security.auths.filter.JwtAuthenticationFilter;
import Preproject28.server.security.auths.filter.JwtVerificationFilter;
import Preproject28.server.security.auths.handler.MemberAuthenticationFailureHandler;
import Preproject28.server.security.auths.handler.MemberAuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final CustomAuthorityUtils customAuthorityUtils; // JWT 검증필터 DI 를 위해 추가

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().sameOrigin()
                .and()
                .csrf().disable()
                .cors(withDefaults())    // corsConfigurationSource Bean 을 이용
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 세션 생성 비활성화 (토큰방식) JwtVerificationFilter 에서 SecurityContextHolder 에 추가되게 해놓음
                .and()
                .formLogin().disable()   // CSR 방식일때 JSON 포맷으로 id,비밀번호 전송하기때문에 비활성화
                .httpBasic().disable()   // UsernamePasswordAuthenticationFilter 비활성화
                .exceptionHandling()
                .authenticationEntryPoint(new MemberAuthenticationEntryPoint())
                .accessDeniedHandler(new MemberAccessDeniedHandler())
                .and()
                .apply(new CustomFilterConfigurer())
                .and()
                .authorizeRequests()
//                .antMatchers(HttpMethod.OPTIONS, "/**/*").permitAll() // 얘는 필요없음
                ///{memberEmail}/info GET 요청을 [권한(관리자,유저) 있는사람만]
                .anyRequest().permitAll();
        // [관리자/회원] / 비회원 어떤요청은 비회원은 못하게 하거나 하는것들 체크  / 권한 나눠야 하는게있나?

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("GET","POST","PATCH","DELETE","OPTIONS"));
        configuration.addExposedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        // JWT 를 구현하기위해 만든 필터를 추가함 + 로그인시 Url 을 설정해줌
        @Override
        public void configure(HttpSecurity builder) {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenizer);
            jwtAuthenticationFilter.setFilterProcessesUrl("/auth/login");
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler());
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());

            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, customAuthorityUtils);

            builder.addFilter(jwtAuthenticationFilter)
                    .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
        }
    }
}
