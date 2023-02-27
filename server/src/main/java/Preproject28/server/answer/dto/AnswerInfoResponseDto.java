package Preproject28.server.answer.dto;

import Preproject28.server.answer.entity.Answer;
import Preproject28.server.member.dto.response.MemberInfoResponseDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
public class AnswerInfoResponseDto {
    private Long answerId;
    private Long questionId;
    private MemberInfoResponseDto member; // 질문자 회원정보
    private List<String> content;
    private int voteCount;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Answer.AdoptStatus adoptStatus;
}
