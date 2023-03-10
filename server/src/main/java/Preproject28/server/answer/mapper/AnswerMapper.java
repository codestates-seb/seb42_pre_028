package Preproject28.server.answer.mapper;

import Preproject28.server.answer.dto.AnswerPatchDto;
import Preproject28.server.answer.dto.AnswerPostDto;
import Preproject28.server.answer.dto.AnswerResponseDto;
import Preproject28.server.answer.entity.Answer;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AnswerMapper {
Answer answerPostDtoToAnswer(AnswerPostDto answerPostDto);
Answer answerPatchDtoToAnswer(AnswerPatchDto answerPatchDto);
AnswerResponseDto answerToAnswerResponseDto(Answer answer);
List<AnswerResponseDto> answerToAnswerResponseDtos(List<Answer> answers);

}
