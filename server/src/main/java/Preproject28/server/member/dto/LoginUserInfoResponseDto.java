package Preproject28.server.member.dto;

import Preproject28.server.answerVote.entity.AnswerVote;
import Preproject28.server.questionVote.entity.QuestionVote;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
public class LoginUserInfoResponseDto {
    private Long memberId;
    private String displayName;
    private String email;
    private LocalDateTime createdAt;
    private String iconImageUrl;
    private List<QuestionVote> questionVotes;
    private List<AnswerVote> answerVotes;

    private QuestionVote thisQuestionVoteIs;
    private AnswerVote thisAnswerVoteIs;
}
