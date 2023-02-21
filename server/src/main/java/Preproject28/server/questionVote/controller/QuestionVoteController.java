package Preproject28.server.questionVote.controller;

import Preproject28.server.questionVote.mapper.QuestionVoteMapper;
import Preproject28.server.questionVote.service.QuestionVoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/question/vote")
@RequiredArgsConstructor
@Validated
public class QuestionVoteController {
    private final QuestionVoteService questionVoteService;
    private final QuestionVoteMapper questionVoteMapper;


}
