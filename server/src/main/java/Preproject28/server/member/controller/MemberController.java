package Preproject28.server.member.controller;

import Preproject28.server.member.dto.*;
import Preproject28.server.member.entity.Member;
import Preproject28.server.member.mapper.MemberMapper;
import Preproject28.server.member.service.MemberService;
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
    public ResponseEntity postMember(@RequestBody @Valid MemberPostDto requestBody) {
        Member member = mapper.memberPostDtoToMember(requestBody);
        Member createdMember = memberService.createMember(member);
        MemberInfoResponseDto response = mapper.memberToMemberInfoResponse(createdMember);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember(@RequestBody @Valid MemberPatchDto requestBody, @PathVariable("member-id") long memberId) {
        Member member = mapper.memberPatchDtoToMember(requestBody);
        member.setMemberId(memberId);
        Member updateMember = memberService.updateMember(member);
        MemberInfoResponseDto response = mapper.memberToMemberInfoResponse(updateMember);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 본인만 삭제가능 & 삭제후 확인과정 추가
     * @param memberId
     * @return
     */
    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(@PathVariable("member-id") long memberId) {

        boolean deleteStatus = memberService.deleteMember(memberId);

        return deleteStatus ? new ResponseEntity<>("삭제완료",HttpStatus.OK) : new ResponseEntity<>("삭제실패",HttpStatus.INTERNAL_SERVER_ERROR);
        //테스트시 게시글을 등록한 회원은 삭제할때 다른테이블과 연관되어있어 삭제시 오류뜸 cascadeType 어노테이션 처리 필요
    }

    /**
     * 회원 정보 ( 이메일 , 유저네임 , 이미지 url ) 만 확인하하는 코드.
     * @param memberId
     */
    @GetMapping("/{member-id}/info")
    public ResponseEntity getMemberInfo(@PathVariable("member-id") long memberId){
        Member findMember = memberService.findMember(memberId);
        MemberInfoResponseDto response = mapper.memberToMemberInfoResponse(findMember);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 내가 쓴 글 조회
     * @param memberId
     * @return
     */
    @GetMapping("/{member-id}/question")
    public ResponseEntity getMemberQuestion(@PathVariable("member-id") long memberId) {
        Member findMember = memberService.findMember(memberId);
        MemberQuestionResponseDto response = mapper.memberToMemberQuestionResponse(findMember);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 내가 쓴 답변 조회
     * @param memberId
     * @return
     */
    @GetMapping("/{member-id}/answer")
    public ResponseEntity getMemberAnswer(@PathVariable("member-id") long memberId) {
        Member findMember = memberService.findMember(memberId);
        MemberAnswersResponseDto response = mapper.memberToMemberAnswerResponse(findMember);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
