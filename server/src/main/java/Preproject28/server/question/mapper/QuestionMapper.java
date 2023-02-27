package Preproject28.server.question.mapper;


import Preproject28.server.answer.dto.AnswerInfoResponseDto;
import Preproject28.server.answer.entity.Answer;
import Preproject28.server.question.dto.*;
import Preproject28.server.question.entity.Question;
import Preproject28.server.member.dto.MemberInfoResponseDto;
import Preproject28.server.member.entity.Member;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
Question questionPostDtoToQuestion(QuestionPostDto questionPostDto);
Question questionPatchDtoToQuestion(QuestionPatchDto questionPatchDto);
default QuestionResponseDto questionToQuestionResponseDto(Question question){
    if ( question == null ) {
        return null;
    }

    QuestionResponseDto questionResponseDto = new QuestionResponseDto();

    questionResponseDto.setQuestionId( question.getQuestionId() );
    questionResponseDto.setTitle( question.getTitle() );
    List<String> list = question.getProblemBody();
    if ( list != null ) {
        questionResponseDto.setProblemBody( new ArrayList<String>( list ) );
    }
    List<String> list1 = question.getExpectingBody();
    if ( list1 != null ) {
        questionResponseDto.setExpectingBody( new ArrayList<String>( list1 ) );
    }
    questionResponseDto.setCreatedAt( question.getCreatedAt() );
    questionResponseDto.setModifiedAt( question.getModifiedAt() );
    questionResponseDto.setViewCount( (int) question.getViewCount() );
    questionResponseDto.setVoteCount( (int) question.getVoteCount() );
    questionResponseDto.setAnswerCount(question.getAnswers().size());
    questionResponseDto.setMember(memberToMemberInfoResponseDto(question.getMember() ) );
    questionResponseDto.setAnswers(answerListToAnswerInfoResponseDtoList(question.getAnswers()));
    List<String> list3 = question.getTag();
    if ( list3 != null ) {
        questionResponseDto.setTag( new ArrayList<String>( list3 ) );
    }

    return questionResponseDto;
} //답변 까지보이는것
default QuestionInfoResponseDto questionToQuestionInfoResponseDto(Question question){
    {
        if ( question == null ) {
            return null;
        }

        QuestionInfoResponseDto.QuestionInfoResponseDtoBuilder questionInfoResponseDto = QuestionInfoResponseDto.builder();

        questionInfoResponseDto.questionId( question.getQuestionId() );
        questionInfoResponseDto.title( question.getTitle() );
        List<String> list = question.getProblemBody();
        if ( list != null ) {
            questionInfoResponseDto.problemBody( new ArrayList<String>( list ) );
        }
        List<String> list1 = question.getExpectingBody();
        if ( list1 != null ) {
            questionInfoResponseDto.expectingBody( new ArrayList<String>( list1 ) );
        }
        questionInfoResponseDto.createdAt( question.getCreatedAt() );
        questionInfoResponseDto.modifiedAt( question.getModifiedAt() );
        questionInfoResponseDto.viewCount( (int) question.getViewCount() );
        questionInfoResponseDto.voteCount( (int) question.getVoteCount() );
        questionInfoResponseDto.answerCount(question.getAnswers().size());
        questionInfoResponseDto.member( memberToMemberInfoResponseDto( question.getMember() ) );
        List<String> list2 = question.getTag();
        if ( list2 != null ) {
            questionInfoResponseDto.tag( new ArrayList<String>( list2 ) );
        }

        return questionInfoResponseDto.build();
    }
} //답변 안보이는것
List<QuestionInfoResponseDto> questionToQuestionResponseInfoDtos(List<Question> questions);


    private MemberInfoResponseDto memberToMemberInfoResponseDto(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberInfoResponseDto memberInfoResponseDto = new MemberInfoResponseDto();

        memberInfoResponseDto.setMemberId( member.getMemberId() );
        memberInfoResponseDto.setDisplayName( member.getDisplayName() );
        memberInfoResponseDto.setEmail( member.getEmail() );
        memberInfoResponseDto.setCreatedAt( member.getCreatedAt() );
        memberInfoResponseDto.setIconImageUrl( member.getIconImageUrl() );

        return memberInfoResponseDto;
    }

    private AnswerInfoResponseDto answerToAnswerInfoResponseDto(Answer answer) {
        if ( answer == null ) {
            return null;
        }

        AnswerInfoResponseDto.AnswerInfoResponseDtoBuilder answerInfoResponseDto = AnswerInfoResponseDto.builder();

        answerInfoResponseDto.answerId( answer.getAnswerId() );
        answerInfoResponseDto.member( memberToMemberInfoResponseDto( answer.getMember() ) );
        List<String> list = answer.getContent();
        if ( list != null ) {
            answerInfoResponseDto.content( new ArrayList<String>( list ) );
        }
        answerInfoResponseDto.voteCount( (int) answer.getVoteCount() );
        answerInfoResponseDto.createdAt( answer.getCreatedAt() );
        answerInfoResponseDto.modifiedAt( answer.getModifiedAt() );
        answerInfoResponseDto.adoptStatus( answer.getAdoptStatus() );

        return answerInfoResponseDto.build();
    }

    private List<AnswerInfoResponseDto> answerListToAnswerInfoResponseDtoList(List<Answer> list) {
        if ( list == null ) {
            return null;
        }

        List<AnswerInfoResponseDto> list1 = new ArrayList<AnswerInfoResponseDto>( list.size() );
        for ( Answer answer : list ) {
            list1.add( answerToAnswerInfoResponseDto( answer ) );
        }

        return list1;
    }
}
