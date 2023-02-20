package Preproject28.server.member.controller;

import Preproject28.server.member.dto.MemberPatchDto;
import Preproject28.server.member.dto.MemberPostDto;
import Preproject28.server.member.dto.MemberQuestionResponseDto;
import Preproject28.server.member.dto.MemberResponseDto;
import Preproject28.server.member.entity.Member;
import Preproject28.server.member.mapper.MemberMapper;
import Preproject28.server.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberMapper mapper;
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity postMember(@RequestBody MemberPostDto requestBody) {
        Member member = mapper.memberPostDtoToMember(requestBody);
        Member createdMember = memberService.createMember(member);
        MemberResponseDto response = mapper.memberToMemberResponse(createdMember);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember(@RequestBody MemberPatchDto requestBody, @PathVariable("member-id") long memberId) {
        Member member = mapper.memberPatchDtoToMember(requestBody);
        member.setMemberId(memberId);
        Member updateMember = memberService.updateMember(member);
        MemberResponseDto response = mapper.memberToMemberResponse(updateMember);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{member-id}")
    public void deleteMember(@PathVariable("member-id") long memberId) {
        memberService.deleteMember(memberId);
    }

    /**
     * 회원 정보 ( 이메일 , 유저네임 , 이미지 url ) 만 확인하하는 코드.
     * @param memberId
     */
    @GetMapping("/{member-id}")
    public ResponseEntity getMember(@PathVariable("member-id") long memberId){
        Member findMember = memberService.findMember(memberId);
        MemberResponseDto response = mapper.memberToMemberResponse(findMember);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    //내가쓴글 조회
    @GetMapping("/{member-id}/question")
    public ResponseEntity getMemberQuestion(@PathVariable("member-id") long memberId) {
        Member findMember = memberService.findMember(memberId);
        MemberQuestionResponseDto response = mapper.memberToMemberQuestionResponse(findMember);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    //내가쓴 답변 조회

}
