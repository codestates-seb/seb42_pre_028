package Preproject28.server.question.mapper;


import Preproject28.server.answer.entity.Answer;
import Preproject28.server.member.dto.response.MemberInfoResponseDto;
import Preproject28.server.question.dto.*;
import Preproject28.server.question.dto.response.QuestionTotalPageResponseDto;
import Preproject28.server.question.dto.response.QuestionDetailPageResponseDto;
import Preproject28.server.question.dto.response.QuestionResponseDto;
import Preproject28.server.question.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    Question questionPostDtoToQuestion(QuestionPostDto questionPostDto);
    Question questionPatchDtoToQuestion(QuestionPatchDto questionPatchDto);
    QuestionResponseDto questionToQuestionResponseDto(Question question);
    @Mapping(source = "answers", target = "answerCount", qualifiedByName = "countAnswers") // 수동구현 대신하는 신기술
    QuestionDetailPageResponseDto questionToQuestionDetailPageResponseDto(Question question);
    @Mapping(source = "answers", target = "answerCount", qualifiedByName = "countAnswers")
    QuestionTotalPageResponseDto questionToQuestionTotalPageResponseDto(Question question);
    @Named("countAnswers")
    default long countAnswers(List<Answer> answers) {return answers.size();}
    List<QuestionTotalPageResponseDto> questionToQuestionTotalPageResponseDtos(List<Question> questions);

}
