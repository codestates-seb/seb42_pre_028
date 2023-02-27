package Preproject28.server.question.dto.response;


import Preproject28.server.member.dto.response.MemberInfoResponseDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 질문글 전체 페이지
 *
 * 질문자의 답변갯수
 * 채택된 질문글 ID 값
 */
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class QuestionTotalPageResponseDto {
    private Long questionId;
    private String title;
    private List<String> problemBody;
    private List<String> expectingBody;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private long viewCount;
    private long voteCount;
    private long answerCount;

    private long adoptAnswerId;
    private MemberInfoResponseDto member;
    private List<String> tag;
}
