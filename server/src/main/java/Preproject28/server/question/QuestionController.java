package Preproject28.server.question;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/questions")
public class QuestionController {


    @GetMapping
    public String getQuestionPage(){
        return "로그인안해도 가능";
    }

    @GetMapping("/1")
    public String getQuestionPage2(){
        return "로그인 필요";
    }
}
