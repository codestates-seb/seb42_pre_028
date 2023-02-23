package Preproject28.server.answer.dto;


import Preproject28.server.member.entity.Member;
import Preproject28.server.question.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerPatchDto {

    private Long answerId;

    private String content;

    private int voteCount;

    private LocalDateTime modifiedAt;

    private boolean adoptStatus;

    private long questionId;

    private long memberId;
}
