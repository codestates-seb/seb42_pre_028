package Preproject28.server.question.controller;

import Preproject28.server.answer.entity.Answer;
import Preproject28.server.answer.service.AnswerService;
import Preproject28.server.member.dto.response.LoginMemberVoteInfo;
import Preproject28.server.member.entity.Member;
import Preproject28.server.question.dto.response.QuestionDetailPageResponseDto;
import Preproject28.server.question.dto.response.QuestionTotalPageResponseDto;
import Preproject28.server.question.repository.QuestionRepository;
import Preproject28.server.util.dto.MultiResponseDto;
import Preproject28.server.util.dto.SingleResponseDto;
import Preproject28.server.member.service.MemberService;
import Preproject28.server.question.dto.QuestionPatchDto;
import Preproject28.server.question.dto.QuestionPostDto;
import Preproject28.server.question.dto.response.QuestionResponseDto;
import Preproject28.server.question.entity.Question;
import Preproject28.server.question.mapper.QuestionMapper;
import Preproject28.server.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
@Validated
public class QuestionController {
    private final QuestionService questionService;
    private final QuestionMapper questionMapper;
    private final MemberService memberService;
    private final AnswerService answerService;

    @PostMapping
    public ResponseEntity<?> postQuestion(@Valid @RequestBody QuestionPostDto questionPostDto){
        Question requestQuestion = questionMapper.questionPostDtoToQuestion(questionPostDto);
        requestQuestion.setMember(loginMemberFindByToken());

        Question createdQuestion = questionService.createQuestion(requestQuestion);
        QuestionResponseDto response = questionMapper.questionToQuestionResponseDto(createdQuestion);

        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.CREATED);
    }

    @PatchMapping("/{question-id}")
    public ResponseEntity<?> patchQuestion(@PathVariable("question-id") long questionId, @Valid @RequestBody QuestionPatchDto questionPatchDto){
        // 본인조건확인
        questionPatchDto.setQuestionId(questionId);
        Question question = questionService.updateQuestion(questionMapper.questionPatchDtoToQuestion(questionPatchDto));
        QuestionResponseDto response = questionMapper.questionToQuestionResponseDto(question);

        return new ResponseEntity<>(new SingleResponseDto<>(response),HttpStatus.OK);
    }


    //질문글 상세페이지 (1건조회)
    @GetMapping("/{question-id}")
    public ResponseEntity<?> getQuestion(@PathVariable("question-id") long questionId){
        Member loginMember = loginMemberFindByToken();
        Question question = questionService.findQuestion(questionId);

        questionService.setViewCount(question); //조회수기능  1번당 1씩 올라가게 (임시)
        LoginMemberVoteInfo loginMemberVoteInfo = memberService.setMemberVoteStatus(loginMember, question);
        QuestionDetailPageResponseDto response = questionMapper.questionToQuestionDetailPageResponseDto(question);
        response.setLoginUserInfo(loginMemberVoteInfo);

        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<?> getQuestions(@RequestParam int page,@RequestParam int size){
        Page<Question> pageQuestions = questionService.findQuestions(page,size);
        List<Question> questions = pageQuestions.getContent();
        List<QuestionTotalPageResponseDto> responses = questionMapper.questionToQuestionResponseInfoDtos(questions);

        return new ResponseEntity<>(new MultiResponseDto<>(responses,pageQuestions), HttpStatus.OK);
    }

    @DeleteMapping("/{question-id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable("question-id") long questionId){
        String loginEmail = SecurityContextHolder.getContext().getAuthentication().getName(); // 토큰에서 유저 email 확인
        Member member = memberService.findMemberByEmail(loginEmail);
        boolean deleteStatus = questionService.deleteQuestion(questionId, member);

        return deleteStatus ? new ResponseEntity<>("삭제완료",HttpStatus.OK) : new ResponseEntity<>("삭제실패",HttpStatus.INTERNAL_SERVER_ERROR);
        //다른테이블과 연관되어있어 삭제시 오류뜸 @cascadeType 어노테이션 처리 필요 -> 자식들 같이삭제할껀지 설정
    }

    /**
     * 답변 채택기능
     * @param questionId = 질문글 식별자
     * @param answerId = 답변글 식별자
     * @return ResponseEntity
     */
    @PostMapping("{question-id}/adopt-answer/{answer-id}")
    public ResponseEntity<?> adoptAnswerToQuestion(@PathVariable("question-id") long questionId, @PathVariable("answer-id") long answerId) {
        //질문글 등록한사람 본인이 답변채택 할수있어야함.
        //질문자 확인
        //답변채택
        //질문에 답변 id 변경
        //답변에 상태값변경
        String loginEmail = SecurityContextHolder.getContext().getAuthentication().getName(); // 토큰에서 유저 email 확인
        Member member = loginMemberFindByToken();
        Answer answer = answerService.findAnswer(answerId);
        questionService.adoptAnswer(questionId, answer, member);
        Question question = questionService.findQuestion(questionId);
        QuestionResponseDto response = questionMapper.questionToQuestionResponseDto(question);

        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    private Member loginMemberFindByToken(){
        String loginEmail = SecurityContextHolder.getContext().getAuthentication().getName(); // 토큰에서 유저 email 확인
        return memberService.findMemberByEmail(loginEmail);
    }


    @GetMapping("/{question-name}")
    public ResponseEntity<?> searchQuestion(@PathVariable("question-name")String name , Long memberId) {
        List<Question> pageQuestions;

        pageQuestions = questionService.searchQuestion(name, memberId);

        return new ResponseEntity<>(questionMapper.questionToQuestionResponseInfoDtos(pageQuestions), HttpStatus.OK);

//        if(!sort.isEmpty()) {
//            pageQuestions = questionService.getAllQuestions(page-1, sort);
//        } else {
//            pageQuestions = questionService.getAllQuestions(page-1);
//        }
//        List <Question> questions = pageQuestions.getContent();
//
    }
}
