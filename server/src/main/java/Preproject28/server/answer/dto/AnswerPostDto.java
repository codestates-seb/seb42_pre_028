package Preproject28.server.answer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AnswerPostDto {
    private String content;
    private long questionId;
    private long memberId;
}
