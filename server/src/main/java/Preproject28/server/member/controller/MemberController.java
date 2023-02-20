package Preproject28.server.member.controller;

import Preproject28.server.member.dto.MemberPostDto;
import Preproject28.server.member.dto.MemberResponseDto;
import Preproject28.server.member.entity.Member;
import Preproject28.server.member.service.MemberService;
import Preproject28.server.member.mapper.MemberMapper;
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

    @GetMapping
    public String getMember(){
        return "member로 들어왔음";
    }
}
