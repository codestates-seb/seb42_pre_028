package Preproject28.server.questionVote.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@Builder
public class QuestionVoteResponseDto {
    private Long questionVoteId;

    private String answerStatus;

    private Long questionId;

    private Long memberId;

}
