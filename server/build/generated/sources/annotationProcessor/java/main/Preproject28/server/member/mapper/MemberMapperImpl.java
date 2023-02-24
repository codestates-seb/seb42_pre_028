package Preproject28.server.member.mapper;

import Preproject28.server.answer.entity.Answer;
import Preproject28.server.member.dto.MemberAnswersResponseDto;
import Preproject28.server.member.dto.MemberInfoResponseDto;
import Preproject28.server.member.dto.MemberPatchDto;
import Preproject28.server.member.dto.MemberPostDto;
import Preproject28.server.member.dto.MemberQuestionResponseDto;
import Preproject28.server.member.entity.Member;
import Preproject28.server.question.entity.Question;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-24T09:54:54+0900",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.6.jar, environment: Java 11.0.17 (Azul Systems, Inc.)"
)
@Component
public class MemberMapperImpl implements MemberMapper {

    @Override
    public Member memberPostDtoToMember(MemberPostDto memberPostDto) {
        if ( memberPostDto == null ) {
            return null;
        }

        Member member = new Member();

        member.setDisplayName( memberPostDto.getDisplayName() );
        member.setEmail( memberPostDto.getEmail() );
        member.setPassword( memberPostDto.getPassword() );

        return member;
    }

    @Override
    public Member memberPatchDtoToMember(MemberPatchDto memberPatchDto) {
        if ( memberPatchDto == null ) {
            return null;
        }

        Member member = new Member();

        member.setDisplayName( memberPatchDto.getDisplayName() );
        member.setPassword( memberPatchDto.getPassword() );

        return member;
    }

    @Override
    public MemberInfoResponseDto memberToMemberInfoResponse(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberInfoResponseDto memberInfoResponseDto = new MemberInfoResponseDto();

        memberInfoResponseDto.setMemberId( member.getMemberId() );
        memberInfoResponseDto.setDisplayName( member.getDisplayName() );
        memberInfoResponseDto.setEmail( member.getEmail() );
        memberInfoResponseDto.setCreatedAt( member.getCreatedAt() );

        return memberInfoResponseDto;
    }

    @Override
    public MemberQuestionResponseDto memberToMemberQuestionResponse(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberQuestionResponseDto memberQuestionResponseDto = new MemberQuestionResponseDto();

        List<Question> list = member.getQuestions();
        if ( list != null ) {
            memberQuestionResponseDto.setQuestions( new ArrayList<Question>( list ) );
        }

        return memberQuestionResponseDto;
    }

    @Override
    public MemberAnswersResponseDto memberToMemberAnswerResponse(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberAnswersResponseDto memberAnswersResponseDto = new MemberAnswersResponseDto();

        List<Answer> list = member.getAnswers();
        if ( list != null ) {
            memberAnswersResponseDto.setAnswers( new ArrayList<Answer>( list ) );
        }

        return memberAnswersResponseDto;
    }
}
