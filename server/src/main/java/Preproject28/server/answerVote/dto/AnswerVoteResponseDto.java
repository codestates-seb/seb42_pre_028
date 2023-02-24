package Preproject28.server.answerVote.dto;

import Preproject28.server.answerVote.entity.AnswerVote;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class AnswerVoteResponseDto {
        private AnswerVote.VoteStatus voteStatus;
        private Long answerId;
        private Long memberId;
        private Long answerVoteTotalCount;
    }
