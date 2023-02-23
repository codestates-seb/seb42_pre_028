package Preproject28.server.answer.controller;

import Preproject28.server.Dto.MultiResponseDto;
import Preproject28.server.Dto.SingleResponseDto;
import Preproject28.server.answer.dto.AnswerPatchDto;
import Preproject28.server.answer.dto.AnswerPostDto;
import Preproject28.server.answer.dto.AnswerResponseDto;
import Preproject28.server.answer.entity.Answer;
import Preproject28.server.answer.mapper.AnswerMapper;
import Preproject28.server.answer.service.AnswerService;
import Preproject28.server.member.entity.Member;
import Preproject28.server.member.service.MemberService;
import Preproject28.server.question.entity.Question;
import Preproject28.server.question.mapper.QuestionMapper;
import Preproject28.server.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/answer")
@RequiredArgsConstructor
public class AnswerController {
//    private final QuestionService questionService;
//    private final QuestionMapper questionMapper;
    private final MemberService memberService;
    private final AnswerMapper answerMapper;
    private final AnswerService answerService;

    @PostMapping
    public ResponseEntity postAnswer(@Valid @RequestBody AnswerPostDto answerPostDto){

        Answer answer = answerMapper.answerPostDtoToAnswer(answerPostDto);
        Member member = memberService.findMember(answerPostDto.getMemberId());

        answer.setMember(member);
        Answer responseContent = answerService.createAnswer(answer);
        AnswerResponseDto response = answerMapper.answerToAnswerResponseDto(responseContent);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
//    @PatchMapping("/{answer-id}")
    @GetMapping("/{answer-id}")
    public ResponseEntity getAnswer(@PathVariable("answer-id")long answerId){
        Answer answer = answerService.findAnswer(answerId);
        return new ResponseEntity<>(new SingleResponseDto<>
                (answerMapper.answerToAnswerResponseDto(answer)),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getAnswers(@RequestParam int page, @RequestParam int size){
        Page<Answer> pageAnswers = answerService.findAnswers(page,size);
        List<Answer> answers = pageAnswers.getContent();
        return new ResponseEntity<>(new MultiResponseDto<>(answerMapper.answerToAnswerResponseDtos(answers),pageAnswers),HttpStatus.OK);
    }
    @DeleteMapping("/{answer-id}")
    public ResponseEntity deleteAnswer(@PathVariable("answer-id")long AId) {
        answerService.deleteAnswer(AId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PatchMapping("/{answer-id}")
    public ResponseEntity patchAnswer(@PathVariable("answer-id") long AId, @RequestBody AnswerPatchDto answerPatchDto){
        answerPatchDto.setAnswerId(AId);
        Answer answer = answerService.updateAnswer(answerMapper.answerPatchDtoToAnswer(answerPatchDto));

        return new ResponseEntity<>(
                new SingleResponseDto<>(answerMapper.answerToAnswerResponseDto(answer)),HttpStatus.OK);
    }
}
