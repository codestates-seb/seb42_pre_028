package Preproject28.server.question.mapper;


import Preproject28.server.answer.entity.Answer;
import Preproject28.server.member.dto.response.MemberInfoResponseDto;
import Preproject28.server.member.entity.Member;
import Preproject28.server.question.dto.*;
import Preproject28.server.question.dto.response.QuestionTotalPageResponseDto;
import Preproject28.server.question.dto.response.QuestionDetailPageResponseDto;
import Preproject28.server.question.dto.response.QuestionResponseDto;
import Preproject28.server.question.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuestionMapper {
    Question questionPostDtoToQuestion(QuestionPostDto questionPostDto);
    Question questionPatchDtoToQuestion(QuestionPatchDto questionPatchDto);
    QuestionResponseDto questionToQuestionResponseDto(Question question);
    @Mapping(source = "answers", target = "answerCount", qualifiedByName = "countAnswers") // 수동구현 대신하는 신기술
    QuestionDetailPageResponseDto questionToQuestionDetailPageResponseDto(Question question);
    @Mapping(source = "answers", target = "answerCount", qualifiedByName = "countAnswers")
    @Mapping(source = "member.questions", target = "member.myQuestionCount", qualifiedByName = "countQuestions")
    @Mapping(source = "member.answers", target = "member.myAnswerCount", qualifiedByName = "countAnswers")
    QuestionTotalPageResponseDto questionToQuestionTotalPageResponseDto(Question question);

    @Named("countQuestions")
    default long countQuestions(List<Question> questions) { return questions.size(); }
    @Named("countAnswers")
    default long countAnswers(List<Answer> answers) { return answers.size(); }
    List<QuestionTotalPageResponseDto> questionToQuestionTotalPageResponseDtos(List<Question> questions);

}
