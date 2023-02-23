package Preproject28.server.questionVote.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class QuestionVotePostDto {
    private Long questionVoteId;

    private String answerStatus;

    private Long questionId;

    private Long memberId;
}
