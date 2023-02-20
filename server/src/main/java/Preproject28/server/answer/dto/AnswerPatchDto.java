package Preproject28.server.answer.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AnswerPatchDto {

    private Long answerId;


    private String content;


    private LocalDateTime createdAt;


    private LocalDateTime modifiedAt;


    private long memberId;


    private String answers;;
}
