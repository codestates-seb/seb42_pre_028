package Preproject28.server.questionVote.mapper;

import Preproject28.server.questionVote.dto.QuestionVotePatchDto;
import Preproject28.server.questionVote.dto.QuestionVotePostDto;
import Preproject28.server.questionVote.dto.QuestionVoteResponseDto;
import Preproject28.server.questionVote.entity.QuestionVote;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionVoteMapper {
    QuestionVote questionVotePostDtoToQuestion(QuestionVotePostDto questionVotePostDto);
    QuestionVote questionVotePatchDtoToQuestion(QuestionVotePatchDto questionVotePatchDto);
    QuestionVoteResponseDto questionVoteToQuestionVoteResponseDto(QuestionVote questionVote);
}
