package Preproject28.server.answerVote.service;

import Preproject28.server.answer.entity.Answer;
import Preproject28.server.answer.service.AnswerService;
import Preproject28.server.answerVote.entity.AnswerVote;
import Preproject28.server.answerVote.repository.AnswerVoteRepository;
import Preproject28.server.member.entity.Member;
import Preproject28.server.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerVoteService {

    private final AnswerVoteRepository answerVoteRepository;
    private final AnswerService answerService;
    private final MemberService memberService;

    public void answerVoteUp(long answerId) {
        String loginMemberEmail = SecurityContextHolder.getContext().getAuthentication().getName(); // 토큰에서 유저 email 확인
        Answer answer = answerService.findAnswer(answerId);
        Member member = memberService.findverifiedMemberByEmail(loginMemberEmail);

        AnswerVote answerVote = findAnswerVote(answer, member); // 추천이 처음이면 만들고, 있으면 변경
        if(answerVote.getVoteStatus().equals(AnswerVote.VoteStatus.UP)) {
            // 업 두번 누르면 NONE 으로,
            answerVote.setVoteStatus(AnswerVote.VoteStatus.NONE);
            answer.setVoteCount(answer.getVoteCount() - 1);
        } else {
            // NONE 이거나 DOWN 상태면 UP 으로 변경
            answerVote.setVoteStatus(AnswerVote.VoteStatus.UP);
            answer.setVoteCount(answer.getVoteCount() + 1);
        }
        answerVoteRepository.save(answerVote);
    }

    public void answerVoteDown(long answerId) {
        String loginMemberEmail = SecurityContextHolder.getContext().getAuthentication().getName(); // 토큰에서 유저 email 확인
        Answer answer = answerService.findAnswer(answerId);
        Member member = memberService.findverifiedMemberByEmail(loginMemberEmail);

        AnswerVote answerVote = findAnswerVote(answer, member); // 추천이 처음이면 만들고, 있으면 변경
        if(answerVote.getVoteStatus().equals(AnswerVote.VoteStatus.DOWN)) {
            // 업 두번 누르면 NONE 으로
            answerVote.setVoteStatus(AnswerVote.VoteStatus.NONE);
            answer.setVoteCount(answer.getVoteCount() + 1);
        } else {
            // NONE 이거나 UP 상태면 DOWN 으로 변경
            answerVote.setVoteStatus(AnswerVote.VoteStatus.DOWN);
            answer.setVoteCount(answer.getVoteCount() - 1);
        }
        answerVoteRepository.save(answerVote);
    }

    public AnswerVote findAnswerVote(Answer answer,Member member) { // 추천이 처음이면 만들고, 있으면 변경
        Optional<AnswerVote> findAnswerVote = answerVoteRepository.findByAnswerAndMember(answer, member);
        return findAnswerVote.orElseGet(() -> new AnswerVote(answer, member, AnswerVote.VoteStatus.NONE));
        //없으면 생성,있으면 반환
    }
}
