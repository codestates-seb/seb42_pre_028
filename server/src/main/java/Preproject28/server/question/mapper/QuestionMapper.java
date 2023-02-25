package Preproject28.server.question.mapper;


import Preproject28.server.question.dto.QuestionPatchDto;
import Preproject28.server.question.dto.QuestionPostDto;
import Preproject28.server.question.dto.QuestionResponseDto;
import Preproject28.server.question.dto.QuestionInfoResponseDto;
import Preproject28.server.question.entity.Question;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
Question questionPostDtoToQuestion(QuestionPostDto questionPostDto);
Question questionPatchDtoToQuestion(QuestionPatchDto questionPatchDto);
QuestionResponseDto questionToQuestionResponseDto(Question question); //답변 까지보이는것
QuestionInfoResponseDto questionToQuestionInfoResponseDto(Question question); //답변 안보이는것
List<QuestionInfoResponseDto> questionToQuestionResponseInfoDtos(List<Question> questions);

}
