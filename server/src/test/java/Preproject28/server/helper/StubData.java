package Preproject28.server.helper;

import Preproject28.server.answer.dto.AnswerInfoResponseDto;
import Preproject28.server.answer.entity.Answer;
import Preproject28.server.answerVote.dto.LoginUserAnswerVoteResponseDto;
import Preproject28.server.answerVote.entity.AnswerVote;
import Preproject28.server.member.dto.response.LoginMemberVoteInfo;
import Preproject28.server.member.dto.response.MemberInfoResponseDto;
import Preproject28.server.member.entity.Member;
import Preproject28.server.question.dto.response.QuestionDetailPageResponseDto;
import Preproject28.server.question.dto.response.QuestionResponseDto;
import Preproject28.server.question.dto.response.QuestionTotalPageResponseDto;
import Preproject28.server.question.entity.Question;
import Preproject28.server.questionVote.entity.QuestionVote;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class StubData {
    public static final LocalDateTime timeSample = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    public static final List<String> contentBodySample = new ArrayList<>();
    public static final List<String> tagList = new ArrayList<>();

    public static final LoginUserAnswerVoteResponseDto loginUserAnswerVoteResponseDto = new LoginUserAnswerVoteResponseDto();
    public static final List<LoginUserAnswerVoteResponseDto> loginUserAnswerVoteResponseDtos = new ArrayList<>();
    public static final LoginMemberVoteInfo loginMemberVoteInfo = new LoginMemberVoteInfo();
    public static final MemberInfoResponseDto memberInfoResponseDto = new MemberInfoResponseDto();
    public static final AnswerInfoResponseDto answerInfoResponseDto = new AnswerInfoResponseDto();
    public static final List<AnswerInfoResponseDto> answerInfoResponseDtos = new ArrayList<>();
    public static final QuestionResponseDto questionResponseDto = new QuestionResponseDto();
    public static final QuestionTotalPageResponseDto questionTotalPageResponseDto = new QuestionTotalPageResponseDto();
    public static final List<QuestionTotalPageResponseDto> questionTotalPageResponseDtos = new ArrayList<>();
    public static final QuestionDetailPageResponseDto questionDetailPageResponseDto = new QuestionDetailPageResponseDto();
    public static final List<Question> searchQList  = new ArrayList<>();
    public static final Question question = new Question();
    public static final Member member = new Member();
    public static final Answer answer = new Answer();

    public static void init() {


        contentBodySample.add("답변글 내용 List 예시 1번");
        contentBodySample.add("답변글 내용 List 예시 2번");
        contentBodySample.add("답변글 내용 List 예시 3번");

        tagList.add("Tag List 형식예시 (1)");
        tagList.add("Tag List 형식예시 (2)");
        tagList.add("Tag List 형식예시 (3)");

        loginUserAnswerVoteResponseDto.setAnswerId(1L);
        loginUserAnswerVoteResponseDto.setVoteStatus(AnswerVote.VoteStatus.UP);

        loginUserAnswerVoteResponseDtos.add(loginUserAnswerVoteResponseDto);
        loginUserAnswerVoteResponseDtos.add(loginUserAnswerVoteResponseDto);

        loginMemberVoteInfo.setMemberId(1L);
        loginMemberVoteInfo.setEmail("ghdrlfehd@gmail.com");
        loginMemberVoteInfo.setQuestionId(1L);
        loginMemberVoteInfo.setQuestionvoteStatus(QuestionVote.VoteStatus.UP);
        loginMemberVoteInfo.setAnswerVoteStatus(loginUserAnswerVoteResponseDtos);


        memberInfoResponseDto.setMemberId(1L);
        memberInfoResponseDto.setDisplayName("홍길동");
        memberInfoResponseDto.setEmail("ghdrlfehd@gmail.com");
        memberInfoResponseDto.setIconImageUrl("null");
        memberInfoResponseDto.setCreatedAt(timeSample);
        memberInfoResponseDto.setMyQuestionCount(5);
        memberInfoResponseDto.setMyAnswerCount(5);

        answerInfoResponseDto.setAnswerId(1L);
        answerInfoResponseDto.setQuestionId(1L);
        answerInfoResponseDto.setMember(memberInfoResponseDto);
        answerInfoResponseDto.setContent(contentBodySample);
        answerInfoResponseDto.setVoteCount(1);
        answerInfoResponseDto.setCreatedAt(timeSample);
        answerInfoResponseDto.setModifiedAt(timeSample);
        answerInfoResponseDto.setAdoptStatus(Answer.AdoptStatus.FALSE);

        answerInfoResponseDtos.add(answerInfoResponseDto);
        answerInfoResponseDtos.add(answerInfoResponseDto);

        questionTotalPageResponseDto.setQuestionId(1L);
        questionTotalPageResponseDto.setTitle("질문제목");
        questionTotalPageResponseDto.setProblemBody(contentBodySample);
        questionTotalPageResponseDto.setExpectingBody(contentBodySample);
        questionTotalPageResponseDto.setCreatedAt(timeSample);
        questionTotalPageResponseDto.setModifiedAt(timeSample);
        questionTotalPageResponseDto.setViewCount(1);
        questionTotalPageResponseDto.setVoteCount(1);
        questionTotalPageResponseDto.setMember(memberInfoResponseDto);
        questionTotalPageResponseDto.setTag(tagList);

        questionTotalPageResponseDtos.add(questionTotalPageResponseDto);
        questionTotalPageResponseDtos.add(questionTotalPageResponseDto);

        questionDetailPageResponseDto.setQuestionId(1L);
        questionDetailPageResponseDto.setTitle("질문제목");
        questionDetailPageResponseDto.setProblemBody(contentBodySample);
        questionDetailPageResponseDto.setExpectingBody(contentBodySample);
        questionDetailPageResponseDto.setCreatedAt(timeSample);
        questionDetailPageResponseDto.setModifiedAt(timeSample);
        questionDetailPageResponseDto.setViewCount(1);
        questionDetailPageResponseDto.setVoteCount(1);
        questionDetailPageResponseDto.setMember(memberInfoResponseDto);
        questionDetailPageResponseDto.setTag(tagList);
        questionDetailPageResponseDto.setAnswers(answerInfoResponseDtos);

        questionResponseDto.setQuestionId(1L);
        questionResponseDto.setTitle("질문제목");
        questionResponseDto.setProblemBody(contentBodySample);
        questionResponseDto.setExpectingBody(contentBodySample);
        questionResponseDto.setCreatedAt(timeSample);
        questionResponseDto.setModifiedAt(timeSample);
        questionResponseDto.setViewCount(1);
        questionResponseDto.setVoteCount(1);
        questionResponseDto.setMember(memberInfoResponseDto);
        questionResponseDto.setTag(tagList);

        question.setQuestionId(1L);
        question.setTitle("example");
        question.setMember(member);

        searchQList.add(question);
        searchQList.add(question);
        searchQList.add(question);

        member.setMemberId(1L);
        member.setEmail("ghdrlfehd@gmail.com");
        member.setDisplayName("홍길동");

        answer.setAnswerId(1L);
        answer.setMember(member);
    }
}
