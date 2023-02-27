package Preproject28.server.answer.dto;


import Preproject28.server.answer.entity.Answer;
import Preproject28.server.member.dto.response.MemberInfoResponseDto;
import Preproject28.server.question.dto.response.QuestionTotalPageResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class AnswerResponseDto {
    private Long answerId;
    private List<String> content;
    private int voteCount;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Answer.AdoptStatus adoptStatus;
    private MemberInfoResponseDto member;
    private QuestionTotalPageResponseDto question;
}
