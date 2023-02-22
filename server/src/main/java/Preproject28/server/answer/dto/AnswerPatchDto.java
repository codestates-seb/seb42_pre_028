package Preproject28.server.answer.dto;


import Preproject28.server.member.entity.Member;
import Preproject28.server.question.entity.Question;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AnswerPatchDto {

    private Long answerId;

    private String content;

    private int voteCount;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private boolean adoptStatus;

    private Question question;


    private Member member;
}
