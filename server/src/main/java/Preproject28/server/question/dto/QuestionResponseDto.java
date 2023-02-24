package Preproject28.server.question.dto;


import Preproject28.server.answer.entity.Answer;
import Preproject28.server.member.dto.MemberQuestionResponseDto;
import Preproject28.server.member.entity.Member;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
//@ToString
public class QuestionResponseDto {
    private Long questionId;
    private String title;
    private List<String> problemBody;
    private List<String> expectingBody;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private int viewCount;
    private int voteCount;
    private MemberQuestionResponseDto member;
    private List<Answer> answer;
    private List<String> tag;
}
