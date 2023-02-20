package Preproject28.server.security.auths.filter;

import Preproject28.server.security.auths.utils.CustomAuthorityUtils;
import Preproject28.server.security.auths.jwt.JwtTokenizer;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * JWT 를 받았을때 검증하는 필터
 */
@RequiredArgsConstructor
@Slf4j
public class JwtVerificationFilter extends OncePerRequestFilter {
    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        try {
            Map<String, Object> claims = verifyJws(request);
            setAuthenticationToContext(claims);

        } catch (SignatureException se) {
            request.setAttribute("exception",se);
        } catch (ExpiredJwtException ee) {
            request.setAttribute("exception",ee);
        } catch (Exception e) {
            request.setAttribute("exception", e);
        }

        filterChain.doFilter(request, response);
    }

    private void setAuthenticationToContext(Map<String, Object> claims) {
        // JWT 의 정보를 SecurityContextHolder 에 저장함
        String username = (String) claims.get("username");
        List<GrantedAuthority> authorities = authorityUtils.createAuthorities((List) claims.get("roles"));
        log.info("# 저장될 정보 - " + username);
        log.info("# 권한 - " + authorities.toString());
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // Authorization 의 값이 없거나, 값에 시작이 Bearer 아닐때 이필터를 건너뛰도록 함.
        String authorization = request.getHeader("Authorization");

        return authorization == null || !authorization.startsWith("Bearer");
    }

    private Map<String,Object> verifyJws(HttpServletRequest request) {
        String jws = request.getHeader("Authorization").replace("Bearer ", ""); // 헤더에서 추출
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey()); // 검증 SecretKey 가지고옴
        Map<String,Object> claims = jwtTokenizer.getClaims(jws, base64EncodedSecretKey).getBody(); // 검증 키로 Claims 파싱

        return claims; // 파싱 성공 == 검증 성공
    }
}
