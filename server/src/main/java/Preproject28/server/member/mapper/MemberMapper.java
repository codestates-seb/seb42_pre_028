package Preproject28.server.member.mapper;

import Preproject28.server.member.dto.MemberPostDto;
import Preproject28.server.member.dto.MemberResponseDto;
import Preproject28.server.member.entity.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    Member memberPostDtoToMember(MemberPostDto memberPostDto);
    MemberResponseDto memberToMemberResponse(Member member);
}
