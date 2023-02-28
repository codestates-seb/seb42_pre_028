package Preproject28.server.member.dto.response;

import Preproject28.server.answerVote.dto.LoginUserAnswerVoteResponseDto;
import Preproject28.server.questionVote.entity.QuestionVote;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 *  질문글 상세페이지용
 *
 *  로그인 회원정보
 *  해당 질문글에 추천상태
 *  해당 질문글의 답변들의 추천상태
 */
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
public class LoginMemberVoteInfo {
    private Long memberId;
    private String email;
    private Long questionId;
    private QuestionVote.VoteStatus questionvoteStatus;
    private List<LoginUserAnswerVoteResponseDto> answerVoteStatus = new ArrayList<>();

}
