package Preproject28.server.answerVote.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class AnswerVoteResponseDto {
    private Long answerVoteId;

    private String answerStatus;

    private Long answerId;

    private Long memberId;
}
