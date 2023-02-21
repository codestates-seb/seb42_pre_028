package Preproject28.server;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public String getTest(){
        return "서버 GET 작동 상태확인";
    }
    @PostMapping
    public String postTest(){
        return "서버 POST 연결 상태 확인";
    }
}
