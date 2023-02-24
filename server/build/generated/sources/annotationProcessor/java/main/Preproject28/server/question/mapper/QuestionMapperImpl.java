package Preproject28.server.question.mapper;

import Preproject28.server.question.dto.QuestionPatchDto;
import Preproject28.server.question.dto.QuestionPostDto;
import Preproject28.server.question.dto.QuestionResponseDto;
import Preproject28.server.question.entity.Question;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-24T16:02:13+0900",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.6.jar, environment: Java 11.0.17 (Azul Systems, Inc.)"
)
@Component
public class QuestionMapperImpl implements QuestionMapper {

    @Override
    public Question questionPostDtoToQuestion(QuestionPostDto questionPostDto) {
        if ( questionPostDto == null ) {
            return null;
        }

        Question question = new Question();

        question.setQuestionId( questionPostDto.getQuestionId() );
        question.setTitle( questionPostDto.getTitle() );
        List<String> list = questionPostDto.getProblemBody();
        if ( list != null ) {
            question.setProblemBody( new ArrayList<String>( list ) );
        }
        List<String> list1 = questionPostDto.getExpectingBody();
        if ( list1 != null ) {
            question.setExpectingBody( new ArrayList<String>( list1 ) );
        }
        List<String> list2 = questionPostDto.getTag();
        if ( list2 != null ) {
            question.setTag( new ArrayList<String>( list2 ) );
        }

        return question;
    }

    @Override
    public Question questionPatchDtoToQuestion(QuestionPatchDto questionPatchDto) {
        if ( questionPatchDto == null ) {
            return null;
        }

        Question question = new Question();

        question.setQuestionId( questionPatchDto.getQuestionId() );
        question.setTitle( questionPatchDto.getTitle() );
        List<String> list = questionPatchDto.getProblemBody();
        if ( list != null ) {
            question.setProblemBody( new ArrayList<String>( list ) );
        }
        List<String> list1 = questionPatchDto.getExpectingBody();
        if ( list1 != null ) {
            question.setExpectingBody( new ArrayList<String>( list1 ) );
        }
        List<String> list2 = questionPatchDto.getTag();
        if ( list2 != null ) {
            question.setTag( new ArrayList<String>( list2 ) );
        }

        return question;
    }

    @Override
    public QuestionResponseDto questionToQuestionResponseDto(Question question) {
        if ( question == null ) {
            return null;
        }

        QuestionResponseDto.QuestionResponseDtoBuilder questionResponseDto = QuestionResponseDto.builder();

        questionResponseDto.questionId( question.getQuestionId() );
        questionResponseDto.title( question.getTitle() );
        List<String> list = question.getProblemBody();
        if ( list != null ) {
            questionResponseDto.problemBody( new ArrayList<String>( list ) );
        }
        List<String> list1 = question.getExpectingBody();
        if ( list1 != null ) {
            questionResponseDto.expectingBody( new ArrayList<String>( list1 ) );
        }
        questionResponseDto.createdAt( question.getCreatedAt() );
        questionResponseDto.modifiedAt( question.getModifiedAt() );
        questionResponseDto.viewCount( (int) question.getViewCount() );
        questionResponseDto.voteCount( (int) question.getVoteCount() );
        List<String> list2 = question.getTag();
        if ( list2 != null ) {
            questionResponseDto.tag( new ArrayList<String>( list2 ) );
        }

        return questionResponseDto.build();
    }

    @Override
    public List<QuestionResponseDto> questionToQuestionResponseDtos(List<Question> questions) {
        if ( questions == null ) {
            return null;
        }

        List<QuestionResponseDto> list = new ArrayList<QuestionResponseDto>( questions.size() );
        for ( Question question : questions ) {
            list.add( questionToQuestionResponseDto( question ) );
        }

        return list;
    }
}
