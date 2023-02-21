package Preproject28.server.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class SecurityCorsConfig {
    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("http://localhost:3000");
        config.addExposedHeader("Authorization");
        config.addAllowedHeader("*"); //특정 header만 허용
        config.addAllowedMethod("GET"); //특정 메소드만 허용
        config.addAllowedMethod("POST"); //특정 메소드만 허용
        config.addAllowedMethod("DELETE"); //특정 메소드만 허용
        config.addAllowedMethod("PATCH"); //특정 메소드만 허용
        source.registerCorsConfiguration("/**", config); //corsConfiguration으로 등록

        return new CorsFilter(source);
    }
}
