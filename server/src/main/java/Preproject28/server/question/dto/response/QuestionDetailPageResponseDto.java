package Preproject28.server.question.dto.response;


import Preproject28.server.answer.dto.AnswerInfoResponseDto;
import Preproject28.server.member.dto.response.LoginMemberVoteInfo;
import Preproject28.server.member.dto.response.MemberInfoResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 *  질문글 상세페이지
 *
 *  질문글 정보 & 질문글 작성자 정보
 *  답변들 & 답변의 작성자 정보
 *  로그인한사람의 질문글 & 답변글 추천여부
 *  채택된 답변글 ID
 *
 *  + 로그인한사람의 정보 (질문글 & 답변글 추천여부)
 */
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
public class QuestionDetailPageResponseDto {
    private Long questionId;
    private MemberInfoResponseDto member;
    private String title;
    private List<String> problemBody;
    private List<String> expectingBody;
    private List<String> tag;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private long viewCount;
    private long voteCount;

    private long answerCount;
    private List<AnswerInfoResponseDto> answers;
    private LoginMemberVoteInfo loginUserInfo;
}
