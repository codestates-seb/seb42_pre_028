package Preproject28.server.question.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class QuestionResponseDto {
    private Long questionId;


    private String title;


    private String problemBody;


    private String expectingBody;


    private LocalDateTime createdAt;


    private LocalDateTime modifiedAt;


    private long viewCount;


    private long voteCount;


    private long memberId;


    private String answers;
}
