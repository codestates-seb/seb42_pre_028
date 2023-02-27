package Preproject28.server.answerVote.dto;

import Preproject28.server.answerVote.entity.AnswerVote;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 *  질문글 상세페이지용
 *
 *  로그인한 유저의 답변 추천상태 확인
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserAnswerVoteResponseDto {
    private Long answerId;
    private AnswerVote.VoteStatus voteStatus;
}
