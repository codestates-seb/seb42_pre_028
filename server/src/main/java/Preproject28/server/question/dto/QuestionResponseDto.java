package Preproject28.server.question.dto;


import Preproject28.server.answer.dto.AnswerInfoResponseDto;
import Preproject28.server.answer.dto.AnswerResponseDto;
import Preproject28.server.member.dto.LoginUserInfoResponseDto;
import Preproject28.server.member.dto.MemberInfoResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
public class QuestionResponseDto {
    private Long questionId;
    private String title;
    private List<String> problemBody;
    private List<String> expectingBody;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private int viewCount;
    private int voteCount;
    private int answerCount;
    private MemberInfoResponseDto member;
    private List<AnswerInfoResponseDto> answers;
    private List<String> tag;
    private LoginUserInfoResponseDto loginUserInfo;
}
