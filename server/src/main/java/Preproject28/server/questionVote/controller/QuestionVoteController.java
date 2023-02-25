package Preproject28.server.questionVote.controller;

import Preproject28.server.question.entity.Question;
import Preproject28.server.question.service.QuestionService;
import Preproject28.server.questionVote.dto.QuestionVoteResponseDto;
import Preproject28.server.questionVote.entity.QuestionVote;
import Preproject28.server.questionVote.mapper.QuestionVoteMapper;
import Preproject28.server.questionVote.service.QuestionVoteService;
import Preproject28.server.util.dto.SingleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/question-vote")
@RequiredArgsConstructor
@Validated
public class QuestionVoteController {
    private final QuestionVoteService questionVoteService;
    private final QuestionService questionService;
    private final QuestionVoteMapper questionVoteMapper;

    @PostMapping("/{question-id}/up")
    public ResponseEntity<?> questionVoteUpPost(@PathVariable("question-id") long questionId) {
        QuestionVote questionVote = questionVoteService.questionVoteUp(questionId);
        Question question = questionService.findQuestion(questionVote.getQuestion().getQuestionId());
        QuestionVoteResponseDto response = questionVoteMapper.questionVoteToQuestionResponseDto(questionVote, question);

        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @PostMapping("/{question-id}/down")
    public ResponseEntity<?> questionVoteDownPost(@PathVariable("question-id") long questionId) {
        QuestionVote questionVote = questionVoteService.questionVoteDown(questionId);
        Question question = questionService.findQuestion(questionVote.getQuestion().getQuestionId());
        QuestionVoteResponseDto response = questionVoteMapper.questionVoteToQuestionResponseDto(questionVote, question);

        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }


}
