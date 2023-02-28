package Preproject28.server.answer.controller;

import Preproject28.server.answer.dto.AnswerInfoResponseDto;
import Preproject28.server.question.service.QuestionService;
import Preproject28.server.util.dto.MultiResponseDto;
import Preproject28.server.util.dto.SingleResponseDto;
import Preproject28.server.answer.dto.AnswerPatchDto;
import Preproject28.server.answer.dto.AnswerPostDto;
import Preproject28.server.answer.dto.AnswerResponseDto;
import Preproject28.server.answer.entity.Answer;
import Preproject28.server.answer.mapper.AnswerMapper;
import Preproject28.server.answer.service.AnswerService;
import Preproject28.server.member.entity.Member;
import Preproject28.server.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/answer")
@RequiredArgsConstructor
public class AnswerController {
    private final QuestionService questionService;
    private final AnswerMapper answerMapper;
    private final MemberService memberService;
    private final AnswerService answerService;

    @PostMapping
    public ResponseEntity<?> postAnswer(@Valid @RequestBody AnswerPostDto answerPostDto){

        Answer postDtoToAnswer = answerMapper.answerPostDtoToAnswer(answerPostDto);
        String loginMemberId = SecurityContextHolder.getContext().getAuthentication().getName(); // 토큰에서 유저 email 확인
        postDtoToAnswer.setMember(memberService.findMemberByEmail(loginMemberId));
        postDtoToAnswer.setQuestion(questionService.findQuestion(answerPostDto.getQuestionId()));

        Answer answer = answerService.createAnswer(postDtoToAnswer);
        AnswerInfoResponseDto response = answerMapper.answerToAnswerInfoResponseDto(answer);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.CREATED);
    }

    @PatchMapping("/{answer-id}")
    public ResponseEntity<?> patchAnswer(@PathVariable("answer-id") long answerId, @RequestBody AnswerPatchDto answerPatchDto) {
        answerPatchDto.setAnswerId(answerId);
        memberService.memberValidation(loginMemberFindByToken(), answerService.findAnswer(answerId).getMember().getMemberId()); //본인검증

        Answer answer = answerService.updateAnswer(answerMapper.answerPatchDtoToAnswer(answerPatchDto));
        AnswerInfoResponseDto response = answerMapper.answerToAnswerInfoResponseDto(answer);

        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @GetMapping("/{answer-id}")
    public ResponseEntity<?> getAnswer(@PathVariable("answer-id")long answerId) {
        Answer answer = answerService.findAnswer(answerId);
        AnswerInfoResponseDto response = answerMapper.answerToAnswerInfoResponseDto(answer);
        return new ResponseEntity<>(new SingleResponseDto<>(response),HttpStatus.OK);
    }

    //내가쓴 댓글조회
    @GetMapping("/{member-id}/answer")
    public ResponseEntity<?> getMemberAnswer(@PathVariable("member-id") long memberId, @RequestParam int page, @RequestParam int size) {

        //페이지네이션 으로 질문글전체조회와 리스폰값 명세 통일(요청사항)
        Page<Answer> pageAnswers = answerService.findAnswersByMemberId(memberId, page, size);
        List<Answer> answers = pageAnswers.getContent();
        List<AnswerInfoResponseDto> responses = answerMapper.answerToAnswerInfoResponseDtos(answers);

        return new ResponseEntity<>(new MultiResponseDto<>(responses, pageAnswers), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAnswers(@RequestParam int page, @RequestParam int size){
        Page<Answer> pageAnswers = answerService.findAnswers(page,size);
        List<Answer> answers = pageAnswers.getContent();
        List<AnswerResponseDto> response = answerMapper.answerToAnswerResponseDtos(answers);
        return new ResponseEntity<>(new MultiResponseDto<>(response, pageAnswers),HttpStatus.OK);
    }

    @DeleteMapping("/{answer-id}")
    public ResponseEntity<?> deleteAnswer(@PathVariable("answer-id")long answerId) {
        String loginEmail = SecurityContextHolder.getContext().getAuthentication().getName(); // 토큰에서 유저 email 확인
        Member member = memberService.findMemberByEmail(loginEmail);
        boolean deleteStatus = answerService.deleteAnswer(answerId, member);
        return deleteStatus ? new ResponseEntity<>("삭제완료", HttpStatus.OK) : new ResponseEntity<>("삭제실패",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Member loginMemberFindByToken() {
        String loginEmail = SecurityContextHolder.getContext().getAuthentication().getName(); // 토큰에서 유저 email 확인
        return memberService.findMemberByEmail(loginEmail);
    }
}
