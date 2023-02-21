package Preproject28.server.answer.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AnswerPostDto {
    private String content;
    private long questionId;
    private long memberId;
}
