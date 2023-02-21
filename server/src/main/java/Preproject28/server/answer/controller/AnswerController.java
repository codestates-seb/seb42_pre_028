package Preproject28.server.answer.controller;

import Preproject28.server.answer.dto.AnswerPostDto;
import Preproject28.server.answer.dto.AnswerResponseDto;
import Preproject28.server.answer.entity.Answer;
import Preproject28.server.answer.mapper.AnswerMapper;
import Preproject28.server.answer.service.AnswerService;
import Preproject28.server.member.entity.Member;
import Preproject28.server.member.service.MemberService;
import Preproject28.server.question.entity.Question;
import Preproject28.server.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/answers")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerMapper answerMapper;
    private final AnswerService answerService;
    private final MemberService memberService;
    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity postAnswer(@RequestBody AnswerPostDto answerPostDto){
        Answer answer = answerMapper.answerPostDtoToAnswer(answerPostDto);

        Member member = memberService.findverifiedMember(answerPostDto.getMemberId());
        Question question = questionService.findQuestion(answerPostDto.getQuestionId());
        answer.setMember(member);
        answer.setQuestion(question);

        Answer createdAnswer = answerService.createAnswer(answer);
        AnswerResponseDto response = answerMapper.answerToAnswerResponseDto(createdAnswer);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public String getAnswer(){
        return "인증된?";
    }
}
