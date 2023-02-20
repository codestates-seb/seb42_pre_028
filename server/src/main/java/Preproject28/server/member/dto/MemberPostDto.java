package Preproject28.server.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter @AllArgsConstructor
public class MemberPostDto {
    @NotBlank(message = "닉네임 : 값이 필요합니다.")
    private String displayName;
    @NotBlank(message = "email : 값이 필요합니다.")
    @Email(message = "email 형식으로 작성해주세요.")
    private String email;
    @NotBlank(message = "password : 값이 필요합니다.")
    private String password;
}
