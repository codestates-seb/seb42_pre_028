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
}
