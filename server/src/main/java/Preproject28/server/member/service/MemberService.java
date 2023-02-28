package Preproject28.server.member.service;

import Preproject28.server.answer.entity.Answer;
import Preproject28.server.answerVote.dto.LoginUserAnswerVoteResponseDto;
import Preproject28.server.answerVote.entity.AnswerVote;
import Preproject28.server.error.exception.BusinessLogicException;
import Preproject28.server.error.exception.ExceptionCode;
import Preproject28.server.member.dto.response.LoginMemberVoteInfo;
import Preproject28.server.member.entity.Member;
import Preproject28.server.member.repository.MemberRepository;
import Preproject28.server.question.entity.Question;
import Preproject28.server.questionVote.entity.QuestionVote;
import Preproject28.server.security.auths.utils.CustomAuthorityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Lazy
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthorityUtils authorityUtils;

    public Member createMember(Member member) {
        member.setPassword(passwordEncoding(member.getPassword()));
        List<String> roles = authorityUtils.createRoles(member.getEmail());
        member.setRoles(roles);
        return memberRepository.save(member);
    }

    public Member updateMember(Member member) {
        Member findMember = findverifiedMember(member.getMemberId());
        Optional.ofNullable(member.getDisplayName()).ifPresent(findMember::setDisplayName);
        Optional.ofNullable(member.getPassword()).ifPresent(password-> findMember.setPassword(passwordEncoding(password)));
        Optional.ofNullable(member.getIconImageUrl()).ifPresent(findMember::setIconImageUrl);

        return memberRepository.save(findMember);
    }

    public Member findMember(long memberId) {
        return findverifiedMember(memberId);
    }

    public void deleteMember(long memberId) {
        String loginEmail = SecurityContextHolder.getContext().getAuthentication().getName(); // 토큰에서 유저 email 확인

        Member findMember = findverifiedMember(memberId);

        if(Objects.equals(findMember.getEmail(), loginEmail)) { // 토큰의 아이디, 삭제요청 id 확인해서 일치할때만 삭제가능하게
            memberRepository.deleteById(memberId);
        } else {
            throw new BusinessLogicException(ExceptionCode.NOT_AUTHORIZED); //에러 새로 추가해야함
        }

    }

    private Member findverifiedMember(long memberId) {
        Optional<Member> memberOptional = memberRepository.findById(memberId);
        return memberOptional.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }
    private String passwordEncoding(String password) {
        return passwordEncoder.encode(password);
    }

    public Member findMemberByEmail(String memberEmail) {
        Optional<Member> memberOptional = memberRepository.findByEmail(memberEmail);
        return memberOptional.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    //해당 게시글에서 게시글&답변에 추천여부 확인
    public LoginMemberVoteInfo setMemberVoteStatus(Member member, Question question) {
        LoginMemberVoteInfo loginMemberVoteInfo = new LoginMemberVoteInfo();

        loginMemberVoteInfo.setMemberId(member.getMemberId() );
        loginMemberVoteInfo.setEmail(member.getEmail() );
        loginMemberVoteInfo.setQuestionId( question.getQuestionId() );

        ArrayList<LoginUserAnswerVoteResponseDto> loginUserAnswerVoteResponseDtos = new ArrayList<>();
        List<QuestionVote> questionVotes = member.getQuestionVotes();
        List<AnswerVote> answerVotes = member.getAnswerVotes();
        List<Answer> questionAnswers = question.getAnswers();

        for(QuestionVote questionVote : questionVotes) {
            if(Objects.equals(questionVote.getQuestion().getQuestionId(), question.getQuestionId())) {
                loginMemberVoteInfo.setQuestionvoteStatus(questionVote.getVoteStatus());
                break;
            }
        }

        for (Answer questionAnswer : questionAnswers) {
            for (AnswerVote answerVote : answerVotes) {
                if(Objects.equals(questionAnswer.getAnswerId(), answerVote.getAnswer().getAnswerId())){
                    LoginUserAnswerVoteResponseDto loginUserAnswerVote = new LoginUserAnswerVoteResponseDto(answerVote.getAnswer().getAnswerId(), answerVote.getVoteStatus());
                    loginUserAnswerVoteResponseDtos.add(loginUserAnswerVote);
                }
            }
        }
        loginMemberVoteInfo.setAnswerVoteStatus(loginUserAnswerVoteResponseDtos);
        return loginMemberVoteInfo;
    }
}