package Preproject28.server.questionVote.mapper;

import Preproject28.server.answerVote.entity.AnswerVote;
import Preproject28.server.member.entity.Member;
import Preproject28.server.question.entity.Question;
import Preproject28.server.questionVote.dto.QuestionVotePatchDto;
import Preproject28.server.questionVote.dto.QuestionVotePostDto;
import Preproject28.server.questionVote.dto.QuestionVoteResponseDto;
import Preproject28.server.questionVote.entity.QuestionVote;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-21T20:45:19+0900",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.6.jar, environment: Java 11.0.17 (Azul Systems, Inc.)"
)
@Component
public class QuestionVoteMapperImpl implements QuestionVoteMapper {

    @Override
    public QuestionVote questionVotePostDtoToQuestionVote(QuestionVotePostDto questionVotePostDto) {
        if ( questionVotePostDto == null ) {
            return null;
        }

        QuestionVote questionVote = new QuestionVote();

        questionVote.setQuestionVoteId( questionVotePostDto.getQuestionVoteId() );

        return questionVote;
    }

    @Override
    public QuestionVote questionVotePatchDtoToQuestionVote(QuestionVotePatchDto questionVotePatchDto) {
        if ( questionVotePatchDto == null ) {
            return null;
        }

        QuestionVote questionVote = new QuestionVote();

        questionVote.setQuestionVoteId( questionVotePatchDto.getQuestionVoteId() );

        return questionVote;
    }

    @Override
    public QuestionVoteResponseDto questionVoteToQuestionResponseDto(QuestionVote questionVote) {
        if ( questionVote == null ) {
            return null;
        }

        QuestionVoteResponseDto.QuestionVoteResponseDtoBuilder questionVoteResponseDto = QuestionVoteResponseDto.builder();

        questionVoteResponseDto.questionVoteId( questionVote.getQuestionVoteId() );

        return questionVoteResponseDto.build();
    }
}
