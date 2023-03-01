package Preproject28.server.security.auths.handler;

import Preproject28.server.member.entity.Member;
import Preproject28.server.member.service.MemberService;
import Preproject28.server.security.auths.jwt.JwtTokenizer;
import Preproject28.server.security.auths.utils.CustomAuthorityUtils;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class OAuth2MemberSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;
    private final MemberService memberService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        var oAuth2User = (OAuth2User) authentication.getPrincipal();
        String name = String.valueOf(oAuth2User.getAttributes().get("name"));
        String email = String.valueOf(oAuth2User.getAttributes().get("email"));

        List<String> authorities = authorityUtils.createRoles(email);

        saveMember(name,email);
        redirect(request, response, email, authorities);

    }

    private void saveMember(String name,String email){
        Member member = new Member();
        member.setEmail(email);
        member.setDisplayName(name);
        memberService.createMember(member);
    }

    private void redirect(HttpServletRequest request, HttpServletResponse response, String email, List<String> authorities) throws IOException {
        String accessToken = delegateAccessToken(email, authorities);
        String refreshToken = delegateRefreshToken(email);

        response.setHeader("Authorization","Bearer " + accessToken);
        response.setHeader("Refresh", refreshToken);

        String uri = createURI(request, accessToken, refreshToken).toString();
        getRedirectStrategy().sendRedirect(request, response, uri);
    }

    private String delegateAccessToken(String email, List<String> authorities) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", email);
        claims.put("roles", authorities);

        String subject = email;
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());

        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

        return accessToken;
    }

    private String delegateRefreshToken(String email) {
        String subject = email;
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getRefreshTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);
        return refreshToken;
    }

    private URI createURI(HttpServletRequest request, String accessToken, String refreshToken) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("access_token", accessToken);
        queryParams.add("refresh_token", refreshToken);

        String serverName = request.getServerName();

        return UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host(serverName)
//                .port(80)
                .path("/auth/login")
                .queryParams(queryParams)
                .build()
                .toUri();
    }
}
