package Preproject28.server.answerVote.controller;

import Preproject28.server.answerVote.mapper.AnswerVoteMapper;
import Preproject28.server.answerVote.service.AnswerVoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/answer/vote")
@RequiredArgsConstructor
public class AnswerVoteController {
    private final AnswerVoteService answerVoteService;
    private final AnswerVoteMapper answerVoteMapper;
}
