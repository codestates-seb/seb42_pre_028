package Preproject28.server.Question.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class QuestionPatchDto {

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
