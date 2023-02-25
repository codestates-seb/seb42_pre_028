package Preproject28.server.answerVote.mapper;

import Preproject28.server.answer.entity.Answer;
import Preproject28.server.answerVote.dto.AnswerVoteResponseDto;
import Preproject28.server.answerVote.entity.AnswerVote;
import Preproject28.server.question.entity.Question;
import Preproject28.server.questionVote.dto.QuestionVoteResponseDto;
import Preproject28.server.questionVote.entity.QuestionVote;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnswerVoteMapper {
    default AnswerVoteResponseDto questionVoteToQuestionResponseDto(AnswerVote answerVote, Answer answer){

        AnswerVoteResponseDto answerVoteResponseDto = new AnswerVoteResponseDto();

        answerVoteResponseDto.setAnswerId(answerVote.getAnswer().getAnswerId());
        answerVoteResponseDto.setVoteStatus(answerVote.getVoteStatus());
        answerVoteResponseDto.setMemberId(answer.getMember().getMemberId());
        answerVoteResponseDto.setAnswerVoteTotalCount(answer.getVoteCount());

        return answerVoteResponseDto;
    }
}
