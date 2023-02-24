package Preproject28.server.question.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class QuestionPatchDto {

    private Long questionId;


    private String title;

    private List<String> problemBody;

    private List<String> expectingBody;

//
//    private LocalDateTime createdAt;
//
//
//    private LocalDateTime modifiedAt;
//
//
//    private long viewCount;
//
//
//    private long voteCount;
//
//
//    private long memberId;
//
//
//    private String answers;
}
