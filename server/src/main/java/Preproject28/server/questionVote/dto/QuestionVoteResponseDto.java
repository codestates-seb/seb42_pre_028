package Preproject28.server.questionVote.dto;

import Preproject28.server.questionVote.entity.QuestionVote;
import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class QuestionVoteResponseDto {
    private QuestionVote.VoteStatus voteStatus;
    private Long questionId;
    private Long memberId;
    private Long questionVoteTotalCount;

}
