package Preproject28.server.member.mapper;

import Preproject28.server.member.dto.*;
import Preproject28.server.member.entity.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    Member memberPostDtoToMember(MemberPostDto memberPostDto);
    Member memberPatchDtoToMember(MemberPatchDto memberPatchDto);
    MemberInfoResponseDto memberToMemberInfoResponse(Member member);
    MemberQuestionResponseDto memberToMemberQuestionResponse(Member member);
    MemberAnswersResponseDto memberToMemberAnswerResponse(Member member);
}
