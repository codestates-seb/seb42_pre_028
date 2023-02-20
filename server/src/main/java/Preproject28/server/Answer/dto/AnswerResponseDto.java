package Preproject28.server.Answer.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class AnswerResponseDto {
    private Long answerId;


    private String content;


    private LocalDateTime createdAt;


    private LocalDateTime modifiedAt;


    private long viewCount;


    private long voteCount;


    private long memberId;


    private String answers;
}
