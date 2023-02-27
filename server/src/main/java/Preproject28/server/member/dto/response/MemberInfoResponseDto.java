package Preproject28.server.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 내 정보
 */
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class MemberInfoResponseDto {
    private Long memberId;
    private String displayName;
    private String email;
    private String iconImageUrl;
    private LocalDateTime createdAt;

    //Mapper 수동구현 ( List.size 담아서 반환 )
    private int myQuestionCount;
    private int myAnswerCount;
}
