package Preproject28.server.question.mapper;


import Preproject28.server.question.dto.*;
import Preproject28.server.question.dto.response.QuestionTotalPageResponseDto;
import Preproject28.server.question.dto.response.QuestionDetailPageResponseDto;
import Preproject28.server.question.dto.response.QuestionResponseDto;
import Preproject28.server.question.entity.Question;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    Question questionPostDtoToQuestion(QuestionPostDto questionPostDto);
    Question questionPatchDtoToQuestion(QuestionPatchDto questionPatchDto);
    QuestionResponseDto questionToQuestionResponseDto(Question question);
    QuestionDetailPageResponseDto questionToQuestionDetailPageResponseDto(Question question);
    List<QuestionTotalPageResponseDto> questionToQuestionResponseInfoDtos(List<Question> questions);
    QuestionTotalPageResponseDto questionToQuestionTotalPageResponseDto(Question question);

}
