package Preproject28.server.member.dto;

import Preproject28.server.question.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class MemberQuestionResponseDto {
    private List<Question> questions = new ArrayList<>();

}
