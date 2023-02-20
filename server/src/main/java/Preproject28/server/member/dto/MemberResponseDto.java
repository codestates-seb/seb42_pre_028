package Preproject28.server.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class MemberResponseDto {
    private Long memberId;
    private String displayName;
    private String email;
}
