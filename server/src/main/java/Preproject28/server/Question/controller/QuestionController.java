package Preproject28.server.question.controller;

import Preproject28.server.question.dto.QuestionPostDto;
import Preproject28.server.question.dto.QuestionResponseDto;
import Preproject28.server.question.entity.Question;
import Preproject28.server.question.mapper.QuestionMapper;
import Preproject28.server.question.service.QuestionService;
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
    private final QuestionService questionService;
    private final QuestionMapper questionMapper;

    @PostMapping
    public ResponseEntity postQuestion(@Valid @RequestBody QuestionPostDto questionPostDto){
        Question question = questionService.createQuestion(questionMapper.questionPostDtoToQuestion(questionPostDto));
        QuestionResponseDto response = questionMapper.questionToQuestionResponseDto(question);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
