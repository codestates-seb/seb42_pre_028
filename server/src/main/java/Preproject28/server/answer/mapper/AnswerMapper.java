package Preproject28.server.answer.mapper;

import Preproject28.server.answer.dto.AnswerInfoResponseDto;
import Preproject28.server.answer.dto.AnswerPatchDto;
import Preproject28.server.answer.dto.AnswerPostDto;
import Preproject28.server.answer.dto.AnswerResponseDto;
import Preproject28.server.answer.entity.Answer;
import Preproject28.server.member.dto.response.MemberInfoResponseDto;
import Preproject28.server.question.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AnswerMapper {
    Answer answerPostDtoToAnswer(AnswerPostDto answerPostDto);
    Answer answerPatchDtoToAnswer(AnswerPatchDto answerPatchDto);
    @Mapping(source = "question", target = "questionId", qualifiedByName = "setQuestionId")
    @Mapping(source = "member.questions", target = "member.myQuestionCount", qualifiedByName = "countQuestions")
    @Mapping(source = "member.answers", target = "member.myAnswerCount", qualifiedByName = "countAnswers")
    AnswerInfoResponseDto answerToAnswerInfoResponseDto(Answer answer);
    @Named("countQuestions")
    default long countQuestions(List<Question> questions) { return questions.size(); }
    @Named("countAnswers")
    default long countAnswers(List<Answer> answers) { return answers.size(); }
    @Named("setQuestionId")
    default long setQuestionId(Question question) { return question.getQuestionId(); }
    List<AnswerResponseDto> answerToAnswerResponseDtos(List<Answer> answers);
    List<AnswerInfoResponseDto> answerToAnswerInfoResponseDtos(List<Answer> answers);

}
