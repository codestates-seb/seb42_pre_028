package Preproject28.server.member.dto.response;

import Preproject28.server.answer.dto.AnswerInfoResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
/**
 * MyPage 내가쓴 답변 조회용
 */
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class MemberAnswersResponseDto {
    private List<AnswerInfoResponseDto> answers = new ArrayList<>();

}
