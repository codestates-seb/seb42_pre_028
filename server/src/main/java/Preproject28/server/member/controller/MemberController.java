package Preproject28.server.member.controller;

import Preproject28.server.answer.dto.AnswerInfoResponseDto;
import Preproject28.server.answer.entity.Answer;
import Preproject28.server.answer.mapper.AnswerMapper;
import Preproject28.server.answer.service.AnswerService;
import Preproject28.server.member.dto.*;
import Preproject28.server.member.dto.response.MemberInfoResponseDto;
import Preproject28.server.member.entity.Member;
import Preproject28.server.member.mapper.MemberMapper;
import Preproject28.server.member.service.MemberService;
import Preproject28.server.question.dto.response.QuestionTotalPageResponseDto;
import Preproject28.server.question.entity.Question;
import Preproject28.server.question.mapper.QuestionMapper;
import Preproject28.server.question.service.QuestionService;
import Preproject28.server.util.dto.MultiResponseDto;
import Preproject28.server.util.dto.SingleResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
@Validated
public class MemberController {
    private final MemberMapper memberMapper;
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<?> postMember(@RequestBody @Valid MemberPostDto requestBody) {
        Member member = memberMapper.memberPostDtoToMember(requestBody);
        Member createdMember = memberService.createMember(member);
        MemberInfoResponseDto response = memberMapper.memberToMemberInfoResponse(createdMember);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.CREATED);
    }

    @PatchMapping("/{member-id}")
    public ResponseEntity<?> patchMember(@RequestBody @Valid MemberPatchDto requestBody, @PathVariable("member-id") long memberId) {
        //토큰으로 본인확인 코드 추가필요
        Member member = memberMapper.memberPatchDtoToMember(requestBody);
        member.setMemberId(memberId);
        Member updateMember = memberService.updateMember(member);
        MemberInfoResponseDto response = memberMapper.memberToMemberInfoResponse(updateMember);
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
        MemberInfoResponseDto response = memberMapper.memberToMemberInfoResponse(findMember);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @GetMapping("/token")
    public ResponseEntity<?> giveMemberInfo(Long memberId) {
        Member member = memberService.findMember(memberId);
        MemberInfoResponseDto response = memberMapper.memberToMemberInfoResponse(member);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

}
