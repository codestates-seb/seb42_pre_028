package Preproject28.server.answer.dto;

import Preproject28.server.member.entity.Member;
import Preproject28.server.question.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class AnswerPostDto {
    private long answerId;

    private String content;

    private long memberId;
}
