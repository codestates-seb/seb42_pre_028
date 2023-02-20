package Preproject28.server.Question.mapper;

import Preproject28.server.Question.dto.QuestionPatchDto;
import Preproject28.server.Question.dto.QuestionPostDto;
import Preproject28.server.Question.dto.QuestionResponseDto;
import Preproject28.server.Question.entity.Question;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
Question questionPostDtoToQuestion(QuestionPostDto questionPostDto);
Question questionPatchDtoToQuestion(QuestionPatchDto questionPatchDto);
QuestionResponseDto questionToQuestionResponseDto(Question question);
List<QuestionResponseDto> questionToQuestionResponseDtos(List<Question> questions);

}
