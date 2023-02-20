package Preproject28.server.answer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/answers")
@RequiredArgsConstructor
public class AnswerController {
    @GetMapping
    public String getAnswer(){
        return "인증된?";
    }
}
