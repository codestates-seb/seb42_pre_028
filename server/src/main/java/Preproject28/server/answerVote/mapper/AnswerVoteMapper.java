package Preproject28.server.answerVote.mapper;

import Preproject28.server.answerVote.dto.AnswerVotePathDto;
import Preproject28.server.answerVote.dto.AnswerVotePostDto;
import Preproject28.server.answerVote.dto.AnswerVoteResponseDto;
import Preproject28.server.answerVote.entity.AnswerVote;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnswerVoteMapper {
    AnswerVote answerVotePostDtoToAnswerVote(AnswerVotePostDto answerVotePostDto);
    AnswerVote answerVotePatchDtoToAnswerVote(AnswerVotePathDto answerVotePathDto);
    AnswerVoteResponseDto answerVoteToAnswerResponseDto(AnswerVote answerVote);
}
