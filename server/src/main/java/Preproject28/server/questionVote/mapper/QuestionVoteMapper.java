package Preproject28.server.questionVote.mapper;

import Preproject28.server.question.entity.Question;
import Preproject28.server.questionVote.dto.QuestionVoteResponseDto;
import Preproject28.server.questionVote.entity.QuestionVote;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionVoteMapper {
    default QuestionVoteResponseDto questionVoteToQuestionResponseDto(QuestionVote questionVote, Question question){

        QuestionVoteResponseDto questionVoteResponseDto = new QuestionVoteResponseDto();

        questionVoteResponseDto.setVoteStatus(questionVote.getVoteStatus());
        questionVoteResponseDto.setQuestionId(question.getQuestionId());
        questionVoteResponseDto.setMemberId(questionVote.getMember().getMemberId());
        questionVoteResponseDto.setQuestionVoteTotalCount(question.getVoteCount());

        return questionVoteResponseDto;
    }
}
