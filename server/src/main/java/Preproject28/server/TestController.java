package Preproject28.server;

import Preproject28.server.security.auths.handler.CustomHttpServletRequestWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public String getTest(HttpServletRequest request){
        String clientIp = new CustomHttpServletRequestWrapper(request).getRemoteAddr();
        return "서버 GET 작동 상태확인 + 접속 IP " + clientIp;
    }
    @PostMapping
    public String postTest(){
        return "서버 POST 연결 상태 확인";
    }
}
