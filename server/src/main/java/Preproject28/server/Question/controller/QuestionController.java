package Preproject28.server.Question.controller;

import Preproject28.server.Question.dto.QuestionPostDto;
import Preproject28.server.Question.dto.QuestionResponseDto;
import Preproject28.server.Question.entity.Question;
import Preproject28.server.Question.mapper.QuestionMapper;
import Preproject28.server.Question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
@Validated
public class QuestionController {
    private QuestionService questionService;

    private QuestionMapper questionMapper;

    public QuestionController (QuestionService questionService, QuestionMapper questionMapper){
        this.questionMapper = questionMapper;
        this.questionService = questionService;
    }


    @PostMapping
    public ResponseEntity postQuestion(@Valid @RequestBody QuestionPostDto questionPostDto){
        Question question = questionService.createQuestion(questionMapper.questionPostDtoToQuestion(questionPostDto));
        QuestionResponseDto response = questionMapper.questionToQuestionResponseDto(question);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
