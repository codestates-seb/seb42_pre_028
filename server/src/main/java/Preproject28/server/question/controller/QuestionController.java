package Preproject28.server.question.controller;

import Preproject28.server.answer.dto.AnswerInfoResponseDto;
import Preproject28.server.answer.entity.Answer;
import Preproject28.server.answer.mapper.AnswerMapper;
import Preproject28.server.answer.service.AnswerService;
import Preproject28.server.member.dto.response.LoginMemberVoteInfo;
import Preproject28.server.member.entity.Member;
import Preproject28.server.question.dto.response.QuestionDetailPageResponseDto;
import Preproject28.server.question.dto.response.QuestionTotalPageResponseDto;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
@Validated
@Slf4j
public class QuestionController {
    private final QuestionService questionService;
    private final QuestionMapper questionMapper;
    private final AnswerMapper answerMapper;
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
        memberService.memberValidation(loginMemberFindByToken(), questionService.findQuestion(questionId).getMember().getMemberId()); // 작성자 & 로그인된 회원 검증

        questionPatchDto.setQuestionId(questionId);

        Question question = questionService.updateQuestion(questionMapper.questionPatchDtoToQuestion(questionPatchDto));
        QuestionResponseDto response = questionMapper.questionToQuestionResponseDto(question);

        return new ResponseEntity<>(new SingleResponseDto<>(response),HttpStatus.OK);
    }


    //질문글 상세페이지 (1건조회)
    @GetMapping("/{question-id}")
    public ResponseEntity<?> getQuestion(@PathVariable("question-id") long questionId){

        String loginEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        Question question = questionService.findQuestion(questionId);
        questionService.setViewCount(question); //조회수기능  1번당 1씩 올라가게 (임시)
        QuestionDetailPageResponseDto response = questionMapper.questionToQuestionDetailPageResponseDto(question);

        //로그인 되어있으면 response 에 질문글&답글 추천상태 추가
        if(!Objects.equals(loginEmail, "anonymousUser")) {
            Member loginMember = loginMemberFindByToken();
            LoginMemberVoteInfo loginMemberVoteInfo = memberService.setMemberVoteStatus(loginMember, question);
            response.setLoginUserInfo(loginMemberVoteInfo);
        }

        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    //질문글 검색 리스트페이지
    @GetMapping
    public ResponseEntity<?> getQuestions(@RequestParam int page,@RequestParam int size){
        Page<Question> pageQuestions = questionService.findQuestions(page,size);
        List<Question> questions = pageQuestions.getContent();
        List<QuestionTotalPageResponseDto> responses = questionMapper.questionToQuestionTotalPageResponseDtos(questions);

        return new ResponseEntity<>(new MultiResponseDto<>(responses,pageQuestions), HttpStatus.OK);
    }

    //내가쓴글 조회
    @GetMapping("/{member-id}/question")
    public ResponseEntity<?> getMemberQuestion(@PathVariable("member-id") long memberId, @RequestParam int page, @RequestParam int size) {

        memberService.memberValidation(loginMemberFindByToken(), memberId); // 작성자 & 로그인된 회원 검증

        //페이지네이션 으로 질문글전체조회와 리스폰값 명세 통일(요청사항)
        Page<Question> pageQuestions = questionService.findQuestionsByMemberId(memberId, page, size);
        List<Question> questions = pageQuestions.getContent();
        List<QuestionTotalPageResponseDto> responses = questionMapper.questionToQuestionTotalPageResponseDtos(questions);

        return new ResponseEntity<>(new MultiResponseDto<>(responses, pageQuestions), HttpStatus.OK);
    }

    @DeleteMapping("/{question-id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable("question-id") long questionId){

        String loginEmail = SecurityContextHolder.getContext().getAuthentication().getName(); // 토큰에서 유저 email 확인
        Member member = memberService.findMemberByEmail(loginEmail);
        boolean deleteStatus = questionService.deleteQuestion(questionId, member);

        return deleteStatus ? new ResponseEntity<>("삭제완료",HttpStatus.OK) : new ResponseEntity<>("삭제실패",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 답변 채택기능
     * @param questionId = 질문글 식별자
     * @param answerId = 답변글 식별자
     * @return ResponseEntity
     */
    @PostMapping("{question-id}/adopt-answer/{answer-id}")
    public ResponseEntity<?> adoptAnswerToQuestion(@PathVariable("question-id") long questionId, @PathVariable("answer-id") long answerId) {

        //답변 채택시 update 된 답변의 정보만 response 요청
        Member member = loginMemberFindByToken();
        Answer answer = answerService.findAnswer(answerId);
        questionService.adoptAnswer(questionId, answer, member);
        Answer adoptedAnswer = answerService.findAnswer(answerId);
        AnswerInfoResponseDto response = answerMapper.answerToAnswerInfoResponseDto(adoptedAnswer);

        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    //로그인된 사용자 확인
    private Member loginMemberFindByToken(){
        String loginEmail = SecurityContextHolder.getContext().getAuthentication().getName(); // 토큰에서 유저 email 확인
        return memberService.findMemberByEmail(loginEmail);
    }

    //게시글 전체조회 검색&필터기능
    @GetMapping("/search")
    public ResponseEntity<?> searchQuestion(@RequestParam(value = "title" , required = false) String title,
                                            @RequestParam(value = "filter", required = false) String filter,
                                            @RequestParam int page, @RequestParam int size) {

        // createdAt , viewCount , voteCount
        if(filter == null) filter = "questionId";
        //필터기능을 메서드로 만들고 -> 검색내용이 있으면 필터기능에 검색값 넣고 리턴 , 검색값 없으면 전체 리턴

        if(title == null) {
            //여기가 검색안했을때
            Page<Question> pageQuestions = questionService.searchQuestion(filter, page, size);
            List<Question> questions = pageQuestions.getContent();
            List<QuestionTotalPageResponseDto> responses = questionMapper.questionToQuestionTotalPageResponseDtos(questions);

            return new ResponseEntity<>(new MultiResponseDto<>(responses, pageQuestions), HttpStatus.OK);
        }else {
            //여기가 제목 검색값 있을때
            Page<Question> pageQuestions = questionService.searchQuestion(title, filter, page, size);
            List<Question> questions = pageQuestions.getContent();
            List<QuestionTotalPageResponseDto> responses = questionMapper.questionToQuestionTotalPageResponseDtos(questions);

            return new ResponseEntity<>(new MultiResponseDto<>(responses, pageQuestions), HttpStatus.OK);
        }

    }

}

