package Preproject28.server.member.mapper;

import Preproject28.server.answerVote.entity.AnswerVote;
import Preproject28.server.member.dto.*;
import Preproject28.server.member.entity.Member;
import Preproject28.server.questionVote.entity.QuestionVote;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    Member memberPostDtoToMember(MemberPostDto memberPostDto);
    Member memberPatchDtoToMember(MemberPatchDto memberPatchDto);
    default MemberInfoResponseDto memberToMemberInfoResponse(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberInfoResponseDto memberInfoResponseDto = new MemberInfoResponseDto();

        memberInfoResponseDto.setMemberId( member.getMemberId() );
        memberInfoResponseDto.setDisplayName( member.getDisplayName() );
        memberInfoResponseDto.setEmail( member.getEmail() );
        memberInfoResponseDto.setCreatedAt( member.getCreatedAt() );
        memberInfoResponseDto.setMyQuestionCount( member.getQuestions().size() ); // 추가
        memberInfoResponseDto.setMyAnswerCount( member.getAnswers().size() ); // 추가

        return memberInfoResponseDto;
    }
    LoginUserInfoResponseDto memberToLogInUserInfoResponseDto(Member member);

    MemberQuestionResponseDto memberToMemberQuestionResponse(Member member);
    MemberAnswersResponseDto memberToMemberAnswerResponse(Member member);
}
