package Preproject28.server.member.dto.response;

import Preproject28.server.question.dto.response.QuestionTotalPageResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * MyPage 내가쓴 질문글 조회용
 */
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class MemberQuestionResponseDto {
    private List<QuestionTotalPageResponseDto> questions = new ArrayList<>();

}
