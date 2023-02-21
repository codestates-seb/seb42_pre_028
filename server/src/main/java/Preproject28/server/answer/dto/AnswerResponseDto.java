package Preproject28.server.answer.dto;


import Preproject28.server.member.entity.Member;
import Preproject28.server.question.entity.Question;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class AnswerResponseDto {
    private Long answerId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Member member;
    private Question question;

}
