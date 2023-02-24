package Preproject28.server.question.dto;


import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
//@ToString
public class QuestionResponseDto {
    private Long questionId;
    private String title;
    private String problemBody;
    private String expectingBody;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private int viewCount;
    private int voteCount;
    private long memberId;
    private String answerId;
}
