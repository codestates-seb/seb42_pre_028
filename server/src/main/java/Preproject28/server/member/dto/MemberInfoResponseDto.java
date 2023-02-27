package Preproject28.server.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class MemberInfoResponseDto {
    private Long memberId;
    private String displayName;
    private String email;
    private LocalDateTime createdAt;
    // 내가 작성한 댓글 숫자 추가 되게
    private int myQuestionCount; // 이거 로직구현해야함
    private int myAnswerCount; // 이거 로직구현해야함
    private String iconImageUrl;
}
