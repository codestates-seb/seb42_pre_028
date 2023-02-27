package Preproject28.server.question.dto.response;


import Preproject28.server.member.dto.response.MemberInfoResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 질문 등록 & 수정시
 *
 * 질문의 기본정보값만 반환
 */
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
public class QuestionResponseDto {
    private Long questionId;
    private MemberInfoResponseDto member;
    private String title;
    private List<String> problemBody;
    private List<String> expectingBody;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private long viewCount;
    private long voteCount;
    private List<String> tag;
}
