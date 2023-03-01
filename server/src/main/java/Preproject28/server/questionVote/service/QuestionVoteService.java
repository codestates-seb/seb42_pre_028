package Preproject28.server.questionVote.service;

import Preproject28.server.member.entity.Member;
import Preproject28.server.member.service.MemberService;
import Preproject28.server.question.entity.Question;
import Preproject28.server.question.service.QuestionService;
import Preproject28.server.questionVote.entity.QuestionVote;
import Preproject28.server.questionVote.repository.QuestionVoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionVoteService {

    private final QuestionVoteRepository questionVoteRepository;

    private final QuestionService questionService;

    private final MemberService memberService;

    public QuestionVote questionVoteUp(long questionId) {
        String loginMemberEmail = SecurityContextHolder.getContext().getAuthentication().getName(); //토큰에서 유저확인.
        Question question = questionService.findQuestion(questionId);
        Member member = memberService.findMemberByEmail(loginMemberEmail);

        QuestionVote questionVote = findQuestionVote(question, member); // 현재 상태값 불러오기

        //로그인된사람, 상태값이 있는상태
        if(questionVote.getVoteStatus().equals(QuestionVote.VoteStatus.UP)){ //만약에 UP 이면
            questionVote.setVoteStatus(QuestionVote.VoteStatus.NONE); // UP 취소로 동작 (NONE 으로 변경)
            question.setVoteCount(question.getVoteCount() -1); // 게시글의 카운트수 -1
        } else if (questionVote.getVoteStatus().equals(QuestionVote.VoteStatus.NONE)) { // NONE 상태면
            questionVote.setVoteStatus(QuestionVote.VoteStatus.UP); // UP 으로 변경
            question.setVoteCount(question.getVoteCount() +1); // 게시글 카운트 수 +1
        } else { //down 상태면
            questionVote.setVoteStatus(QuestionVote.VoteStatus.UP); // DOWN -> UP 으로 변경
            question.setVoteCount(question.getVoteCount() +2); // 게시글 카운트 수 + 2 (DOWN -> UP)이라서
        }
        return questionVoteRepository.save(questionVote);
    }


    public QuestionVote questionVoteDown(long questionId) {
        String loginMemberEmail = SecurityContextHolder.getContext().getAuthentication().getName(); //토큰에서 유저확인
        Question question = questionService.findQuestion(questionId);
        Member member = memberService.findMemberByEmail(loginMemberEmail);

        QuestionVote questionVote = findQuestionVote(question, member);
        if(questionVote.getVoteStatus().equals(QuestionVote.VoteStatus.DOWN)){ //만약에 DOWN 이면
            questionVote.setVoteStatus(QuestionVote.VoteStatus.NONE); // DOWN 취소로 동작 (NONE 으로 변경)
            question.setVoteCount(question.getVoteCount() +1); // 게시글의 카운트수 +1
        } else if (questionVote.getVoteStatus().equals(QuestionVote.VoteStatus.NONE)) { // NONE 상태면
            questionVote.setVoteStatus(QuestionVote.VoteStatus.DOWN); // DOWN 으로 변경
            question.setVoteCount(question.getVoteCount() -1); // 게시글 카운트 수 +1
        } else { //down 상태면
            questionVote.setVoteStatus(QuestionVote.VoteStatus.DOWN); // UP -> DOWN 으로 변경
            question.setVoteCount(question.getVoteCount() -2); // 게시글 카운트 수 + 2 (DOWN -> UP)이라서
        }
        return questionVoteRepository.save(questionVote);
    }

    public QuestionVote findQuestionVote (Question question, Member member){
        Optional<QuestionVote> findQuestionVote = questionVoteRepository.findByQuestionAndMember(question, member);
        return findQuestionVote.orElseGet(()-> new QuestionVote(question, member, QuestionVote.VoteStatus.NONE));
    }
}
