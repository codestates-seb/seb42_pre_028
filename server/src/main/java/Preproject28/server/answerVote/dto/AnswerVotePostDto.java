package Preproject28.server.answerVote.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerVotePostDto {
    private Long answerVoteId;

    private String answerStatus;

    private Long answerId;

    private Long memberId;
}
