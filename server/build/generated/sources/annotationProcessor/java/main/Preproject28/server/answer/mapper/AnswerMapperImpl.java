package Preproject28.server.answer.mapper;

import Preproject28.server.answer.dto.AnswerPatchDto;
import Preproject28.server.answer.dto.AnswerPostDto;
import Preproject28.server.answer.dto.AnswerResponseDto;
import Preproject28.server.answer.entity.Answer;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-24T16:12:18+0900",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.6.jar, environment: Java 11.0.17 (Azul Systems, Inc.)"
)
@Component
public class AnswerMapperImpl implements AnswerMapper {

    @Override
    public Answer answerPostDtoToAnswer(AnswerPostDto answerPostDto) {
        if ( answerPostDto == null ) {
            return null;
        }

        Answer answer = new Answer();

        answer.setAnswerId( answerPostDto.getAnswerId() );
        List<String> list = answerPostDto.getContent();
        if ( list != null ) {
            answer.setContent( new ArrayList<String>( list ) );
        }

        return answer;
    }

    @Override
    public Answer answerPatchDtoToAnswer(AnswerPatchDto answerPatchDto) {
        if ( answerPatchDto == null ) {
            return null;
        }

        Answer answer = new Answer();

        answer.setAnswerId( answerPatchDto.getAnswerId() );
        List<String> list = answerPatchDto.getContent();
        if ( list != null ) {
            answer.setContent( new ArrayList<String>( list ) );
        }

        return answer;
    }

    @Override
    public AnswerResponseDto answerToAnswerResponseDto(Answer answer) {
        if ( answer == null ) {
            return null;
        }

        AnswerResponseDto.AnswerResponseDtoBuilder answerResponseDto = AnswerResponseDto.builder();

        answerResponseDto.answerId( answer.getAnswerId() );
        List<String> list = answer.getContent();
        if ( list != null ) {
            answerResponseDto.content( new ArrayList<String>( list ) );
        }
        answerResponseDto.voteCount( answer.getVoteCount() );
        answerResponseDto.createdAt( answer.getCreatedAt() );
        answerResponseDto.modifiedAt( answer.getModifiedAt() );
        answerResponseDto.adoptStatus( answer.isAdoptStatus() );

        return answerResponseDto.build();
    }

    @Override
    public List<AnswerResponseDto> answerToAnswerResponseDtos(List<Answer> answers) {
        if ( answers == null ) {
            return null;
        }

        List<AnswerResponseDto> list = new ArrayList<AnswerResponseDto>( answers.size() );
        for ( Answer answer : answers ) {
            list.add( answerToAnswerResponseDto( answer ) );
        }

        return list;
    }
}
