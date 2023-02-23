package Preproject28.server.security.auths.handler;

import Preproject28.server.member.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 로그인 성공시 핸들러
 */
@Slf4j
public class MemberAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Member member = (Member) authentication.getPrincipal();
        String clientIp = new CustomHttpServletRequestWrapper(request).getRemoteAddr();
        log.info(" # 로그인 성공" + " ID : " + member.getEmail() + " , 요청 IP : " + clientIp);

//        response.setHeader("test", "login"); // 그냥 실험
    }
}
