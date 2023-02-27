package Preproject28.server.member.controller;

import Preproject28.server.member.dto.*;
import Preproject28.server.member.entity.Member;
import Preproject28.server.member.mapper.MemberMapper;
import Preproject28.server.member.service.MemberService;
import Preproject28.server.util.dto.SingleResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
@Validated
public class MemberController {

    private final MemberMapper mapper;
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<?> postMember(@RequestBody @Valid MemberPostDto requestBody) {
        Member member = mapper.memberPostDtoToMember(requestBody);
        Member createdMember = memberService.createMember(member);
        MemberInfoResponseDto response = mapper.memberToMemberInfoResponse(createdMember);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.CREATED);
    }

    @PatchMapping("/{member-id}")
    public ResponseEntity<?> patchMember(@RequestBody @Valid MemberPatchDto requestBody, @PathVariable("member-id") long memberId) {
        //토큰으로 본인확인 코드 추가필요
        Member member = mapper.memberPatchDtoToMember(requestBody);
        member.setMemberId(memberId);
        Member updateMember = memberService.updateMember(member);
        MemberInfoResponseDto response = mapper.memberToMemberInfoResponse(updateMember);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    /**
     * 본인만 삭제가능 & 삭제후 확인과정 추가
     */
    @DeleteMapping("/{member-id}")
    public ResponseEntity<?> deleteMember(@PathVariable("member-id") long memberId) {
        //삭제 성공하면 304 응답값이 감 -> 성공은했지만 리턴값 필요없을때 가는 코드
        //삭제 실패하면
        memberService.deleteMember(memberId);
        //테스트시 게시글을 등록한 회원은 삭제할때 다른테이블과 연관되어있어 삭제시 오류뜸 @cascadeType 어노테이션 처리 필요
        return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
    }

    /**
     * 회원 정보 ( 이메일 , 유저네임 , 이미지 url ) 만 확인하하는 코드.
     */
    @GetMapping("/{memberEmail}/info")
    public ResponseEntity<?> getMemberInfo(@PathVariable("memberEmail") String  memberEmail){
        //토큰만으로 memberId & 유저정보 돌려주는식으로 구현
        //로그인했을때 로그인한 Email ->
        // 127.0.0.1:8080/members/{email}/info
        Member findMember = memberService.findMemberByEmail(memberEmail);
        MemberInfoResponseDto response = mapper.memberToMemberInfoResponse(findMember);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    /**
     * 내가 쓴 글 조회
     */
    @GetMapping("/{member-id}/question")
    public ResponseEntity<?> getMemberQuestion(@PathVariable("member-id") long memberId) {
        Member findMember = memberService.findMember(memberId);
        MemberQuestionResponseDto response = mapper.memberToMemberQuestionResponse(findMember);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    /**
     * 내가 쓴 답변 조회
     */
    @GetMapping("/{member-id}/answer")
    public ResponseEntity<?> getMemberAnswer(@PathVariable("member-id") long memberId) {
        Member findMember = memberService.findMember(memberId);
        MemberAnswersResponseDto response = mapper.memberToMemberAnswerResponse(findMember);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }
}
