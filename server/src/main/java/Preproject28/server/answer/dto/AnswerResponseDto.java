package Preproject28.server.answer.dto;


import Preproject28.server.answer.entity.Answer;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerResponseDto {
    private Long answerId;
    private String content;
    private int voteCount;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Answer.AdoptStatus adoptStatus;
    private long questionId;
    private long memberId;


}
