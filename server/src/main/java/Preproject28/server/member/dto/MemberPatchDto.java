package Preproject28.server.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberPatchDto {
    @NotBlank(message = "닉네임 : 값이 필요합니다.")
    private String displayName;
    @NotBlank(message = "password : 값이 필요합니다.")
    private String password;
}
