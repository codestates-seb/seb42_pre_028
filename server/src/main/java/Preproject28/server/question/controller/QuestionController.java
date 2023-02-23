package Preproject28.server.question.controller;

import Preproject28.server.util.dto.MultiResponseDto;
import Preproject28.server.util.dto.SingleResponseDto;
import Preproject28.server.member.entity.Member;
import Preproject28.server.member.service.MemberService;
import Preproject28.server.question.dto.QuestionPatchDto;
import Preproject28.server.question.dto.QuestionPostDto;
import Preproject28.server.question.dto.QuestionResponseDto;
import Preproject28.server.question.entity.Question;
import Preproject28.server.question.mapper.QuestionMapper;
import Preproject28.server.question.service.QuestionService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/question")
@Validated
public class QuestionController {
    private final QuestionService questionService;
    private final QuestionMapper questionMapper;
    private final MemberService memberService;


    public QuestionController(QuestionService questionService, QuestionMapper questionMapper, MemberService memberService){

        this.questionMapper = questionMapper;
        this.questionService =questionService;
        this.memberService = memberService;

    }

    @PostMapping
    public ResponseEntity postQuestion(@Valid @RequestBody QuestionPostDto questionPostDto){

        Question question = questionMapper.questionPostDtoToQuestion(questionPostDto);
        Member member = memberService.findMember(questionPostDto.getMemberId());

        question.setMember(member);
        Question responseContent = questionService.createQuestion(question);
        QuestionResponseDto response = questionMapper.questionToQuestionResponseDto(responseContent);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PatchMapping("/{question-id}")
    public ResponseEntity patchQuestion(@PathVariable("question-id") long QId, @Valid @RequestBody QuestionPatchDto questionPatchDto){
        questionPatchDto.setQuestionId(QId);
        Question question = questionService.updateQuestion(questionMapper.questionPatchDtoToQuestion(questionPatchDto));

        return new ResponseEntity<>(
                new SingleResponseDto<>(questionMapper.questionToQuestionResponseDto(question)),HttpStatus.OK
        );
    }


    @GetMapping("/{question-id}")
    public ResponseEntity getQuestion(@PathVariable("question-id") long QId){
        Question question = questionService.findQuestion(QId);

        return new ResponseEntity<>(new SingleResponseDto<>(questionMapper.questionToQuestionResponseDto(question)),HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity getQuestions(@RequestParam int page,@RequestParam int size){
        Page<Question> pageQuestions = questionService.findQuestions(page,size);
        List<Question> questions = pageQuestions.getContent();

        return new ResponseEntity<>(new MultiResponseDto<>(questionMapper.questionToQuestionResponseDtos(questions),pageQuestions),HttpStatus.OK);
    }

    @DeleteMapping("/{question-id}")
    public ResponseEntity deleteQuestion(@PathVariable("coffee-id") long questionId){
        questionService.deleteQuestion(questionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
