package Preproject28.server.answerVote.mapper;

import Preproject28.server.answer.entity.Answer;
import Preproject28.server.answerVote.dto.AnswerVoteResponseDto;
import Preproject28.server.answerVote.entity.AnswerVote;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnswerVoteMapper {
    default AnswerVoteResponseDto answerVoteToAnswerResponseDto(AnswerVote answerVote, Answer answer){

        AnswerVoteResponseDto answerVoteResponseDto = new AnswerVoteResponseDto();

        answerVoteResponseDto.setAnswerId(answerVote.getAnswer().getAnswerId());
        answerVoteResponseDto.setVoteStatus(answerVote.getVoteStatus());
        answerVoteResponseDto.setMemberId(answer.getMember().getMemberId());
        answerVoteResponseDto.setAnswerVoteTotalCount(answer.getVoteCount());

        return answerVoteResponseDto;
    }
}
