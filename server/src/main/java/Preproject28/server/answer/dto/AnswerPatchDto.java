package Preproject28.server.answer.dto;


import Preproject28.server.member.entity.Member;
import Preproject28.server.question.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerPatchDto {
    private Long answerId;
    private List<String> content;

}
