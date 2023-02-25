package Preproject28.server.answer.dto;

import Preproject28.server.member.entity.Member;
import Preproject28.server.question.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AnswerPostDto {
    private long answerId;

    private List<String> content;

    private long memberId;
}
