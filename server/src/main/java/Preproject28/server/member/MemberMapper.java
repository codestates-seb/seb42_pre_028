package Preproject28.server.member;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    Member memberPostDtoToMember(MemberDto.Post memberPostDto);
    MemberDto.Response memberToMemberResponse(Member member);
}
