package Preproject28.server.member.dto;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
public class MemberPatchDto {
    @NotBlank(message = "닉네임 : 값이 필요합니다.")
    private String displayName;
    @NotBlank(message = "password : 값이 필요합니다.")
    private String password;
}
