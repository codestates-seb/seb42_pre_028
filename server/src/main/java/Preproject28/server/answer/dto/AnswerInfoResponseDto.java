package Preproject28.server.answer.dto;

import Preproject28.server.answer.entity.Answer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class AnswerInfoResponseDto {
    private Long answerId;
    private Long questionId;
    private List<String> content;
    private int voteCount;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Answer.AdoptStatus adoptStatus;
}
