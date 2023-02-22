package Preproject28.server.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @AllArgsConstructor
@NoArgsConstructor
@Setter
public class MemberPostDto {
    private String displayName;
    private String email;
    private String password;
}
