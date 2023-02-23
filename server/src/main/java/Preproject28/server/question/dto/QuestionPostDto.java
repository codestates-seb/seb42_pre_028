package Preproject28.server.question.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class QuestionPostDto {
    private Long questionId;


    private String title;


    private String problemBody;


    private String expectingBody;


    private long memberId;

}
