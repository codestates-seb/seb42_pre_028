package Preproject28.server.questionVote.service;

import Preproject28.server.answerVote.entity.AnswerVote;
import Preproject28.server.member.entity.Member;
import Preproject28.server.member.service.MemberService;
import Preproject28.server.question.entity.Question;
import Preproject28.server.question.service.QuestionService;
import Preproject28.server.questionVote.controller.QuestionVoteController;
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

    public void questionVoteUp(long questionId) {
        String loginMemberEmail = SecurityContextHolder.getContext().getAuthentication().getName(); //토큰에서 유저확인.
        Question question = questionService.findQuestion(questionId);
        Member member = memberService.findverifiedMemberByEmail(loginMemberEmail);

        QuestionVote questionVote = findQuestionVote(question, member);
        if(questionVote.getVoteStatus().equals(QuestionVote.VoteStatus.UP)){
            questionVote.setVoteStatus(QuestionVote.VoteStatus.NONE);
            question.setVoteCount(question.getVoteCount()-1);
        } else {
            questionVote.setVoteStatus(QuestionVote.VoteStatus.UP);
            question.setVoteCount(question.getVoteCount() +1);
        }
        questionVoteRepository.save(questionVote);
    }


    public void questionVoteDown(long questionId) {
        String loginMemberEmail = SecurityContextHolder.getContext().getAuthentication().getName(); //토큰에서 유저확인.
        Question question = questionService.findQuestion(questionId);
        Member member = memberService.findverifiedMemberByEmail(loginMemberEmail);

        QuestionVote questionVote = findQuestionVote(question, member);
        if(questionVote.getVoteStatus().equals(QuestionVote.VoteStatus.DOWN)){
            questionVote.setVoteStatus(QuestionVote.VoteStatus.NONE);
            question.setVoteCount(question.getVoteCount()+1);
        } else {
            questionVote.setVoteStatus(QuestionVote.VoteStatus.DOWN);
            question.setVoteCount(question.getVoteCount() -1);
        }
        questionVoteRepository.save(questionVote);
    }

    public QuestionVote findQuestionVote (Question question, Member member){
        Optional<QuestionVote> findQuestionVote = questionVoteRepository.findByQuestionAndMember(question, member);
        return findQuestionVote.orElseGet(()-> new QuestionVote(question, member, QuestionVote.VoteStatus.NONE));
    }
}
