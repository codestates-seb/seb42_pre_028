package Preproject28.server.member.mapper;

import Preproject28.server.member.dto.*;
import Preproject28.server.member.dto.response.LoginMemberVoteInfo;
import Preproject28.server.member.dto.response.MemberInfoResponseDto;
import Preproject28.server.member.entity.Member;
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
        memberInfoResponseDto.setMyQuestionCount( member.getQuestions().size() );     //Mapper 수동구현 ( List.size 담아서 반환 )
        memberInfoResponseDto.setMyAnswerCount( member.getAnswers().size() );         //Mapper 수동구현 ( List.size 담아서 반환 )

        return memberInfoResponseDto;
    }
    LoginMemberVoteInfo memberToLogInUserInfoResponseDto(Member member);

}
