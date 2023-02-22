package Preproject28.server;

import Preproject28.server.security.auths.MemberDetailsService;
import Preproject28.server.security.auths.handler.CustomHttpServletRequestWrapper;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class JwtMemberDto {
        private Long memberId;
        private String email;
        private List<String> roles;
    }

    @GetMapping
    public ResponseEntity getTest(HttpServletRequest request,
                                  @AuthenticationPrincipal JwtMemberDto member){
        String clientIp = new CustomHttpServletRequestWrapper(request).getRemoteAddr();
        if(member == null) {
            String nullResponse = "서버 GET 작동 상태확인 + 접속 IP " + clientIp;
            return new ResponseEntity<>(nullResponse, HttpStatus.OK);
        } else {
            String response = "서버 GET 작동 상태확인 + 접속 IP " + clientIp + "현재 로그인정보" + member.getEmail();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
    @PostMapping
    public String postTest(){
        return "서버 POST 연결 상태 확인";
    }
}
