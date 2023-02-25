package Preproject28.server.answerVote.controller;


import Preproject28.server.answer.entity.Answer;
import Preproject28.server.answer.service.AnswerService;
import Preproject28.server.answerVote.dto.AnswerVoteResponseDto;
import Preproject28.server.answerVote.entity.AnswerVote;
import Preproject28.server.answerVote.mapper.AnswerVoteMapper;
import Preproject28.server.answerVote.service.AnswerVoteService;
import Preproject28.server.util.dto.SingleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/answer-vote")
@RequiredArgsConstructor
public class AnswerVoteController {
    private final AnswerVoteService answerVoteService;
    private final AnswerService answerService;
    private final AnswerVoteMapper answerVoteMapper;
    //추천수
    @PostMapping("/{answer-id}/up")
    public ResponseEntity<?> answerVoteUpPost(@PathVariable("answer-id") long answerId) {
        AnswerVote answerVote = answerVoteService.answerVoteUp(answerId);
        Answer answer = answerService.findAnswer(answerVote.getAnswer().getAnswerId());
        AnswerVoteResponseDto response = answerVoteMapper.answerVoteToAnswerResponseDto(answerVote, answer);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @PostMapping("/{answer-id}/down")
    public ResponseEntity<?> answerVoteDownPost(@PathVariable("answer-id") long answerId) {
        AnswerVote answerVote = answerVoteService.answerVoteDown(answerId);
        Answer answer = answerService.findAnswer(answerVote.getAnswer().getAnswerId());
        AnswerVoteResponseDto response = answerVoteMapper.answerVoteToAnswerResponseDto(answerVote, answer);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }
}
