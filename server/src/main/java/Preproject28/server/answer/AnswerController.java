package Preproject28.server.answer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/answers")
public class AnswerController{
    @GetMapping
    public String getAnswer(){
        return "인증된?";
    }
}
