package Preproject28.server.questionVote.controller;

import Preproject28.server.questionVote.mapper.QuestionVoteMapper;
import Preproject28.server.questionVote.service.QuestionVoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/question/vote")
@RequiredArgsConstructor
@Validated
public class QuestionVoteController {
    private final QuestionVoteService questionVoteService;

    @PostMapping("/{question-id}/up")
    public void questionVoteUpPost(@PathVariable("question-id") long questionId) {
        questionVoteService.questionVoteUp(questionId);
    }

    @PostMapping("/{question-id}/down")
    public void questionVoteDownPost(@PathVariable("question-id") long questionId) {
        questionVoteService.questionVoteDown(questionId);
    }


}
