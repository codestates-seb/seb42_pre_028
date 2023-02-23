package Preproject28.server.answerVote.controller;


import Preproject28.server.answerVote.service.AnswerVoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/answer-vote")
@RequiredArgsConstructor
public class AnswerVoteController {
    private final AnswerVoteService answerVoteService;
    //추천수
    @PostMapping("/{answer-id}/up")
    public void answerVoteUpPost(@PathVariable("answer-id") long answerId) {
        answerVoteService.answerVoteUp(answerId);
        //조건 : 로그인이 되있는사람만 (시큐리티에서 추가필요)
        //한 아이디당 한번만 가능
    }

    @PostMapping("/{answer-id}/down")
    public void answerVoteDownPost(@PathVariable("answer-id") long answerId) {
        answerVoteService.answerVoteDown(answerId);
        //조건 : 로그인이 되있는사람만 (시큐리티에서 추가필요)

        // 갱신된값 보내줘야하는지?
    }
}
