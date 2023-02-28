package Preproject28.server.question;

import Preproject28.server.answer.dto.AnswerInfoResponseDto;
import Preproject28.server.answer.entity.Answer;
import Preproject28.server.answer.mapper.AnswerMapper;
import Preproject28.server.answer.service.AnswerService;
import Preproject28.server.helper.StubData;
import Preproject28.server.member.dto.response.MemberInfoResponseDto;
import Preproject28.server.member.entity.Member;
import Preproject28.server.member.service.MemberService;
import Preproject28.server.question.controller.QuestionController;
import Preproject28.server.question.dto.response.QuestionTotalPageResponseDto;
import Preproject28.server.question.dto.QuestionPatchDto;
import Preproject28.server.question.dto.QuestionPostDto;
import Preproject28.server.question.dto.response.QuestionResponseDto;
import Preproject28.server.question.entity.Question;
import Preproject28.server.question.mapper.QuestionMapper;
import Preproject28.server.question.service.QuestionService;
import Preproject28.server.utils.SecurityTestConfig;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static Preproject28.server.helper.StubData.*;
import static Preproject28.server.utils.ApiDocumentUtils.getRequestPreProcessor;
import static Preproject28.server.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = QuestionController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@WithMockUser
@Slf4j
@Import(SecurityTestConfig.class)
public class QuestionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private QuestionService questionService;
    @MockBean
    private MemberService memberService;
    @MockBean
    private AnswerService answerService;
    @MockBean
    private QuestionMapper questionMapper;
    @MockBean
    private AnswerMapper answerMapper;
    @Autowired
    private Gson gson;

    @BeforeAll
    public static void init() {
        StubData.init();

    }

    @Test
    @DisplayName("질문글 등록 테스트")
    public void postQuestionTest() throws Exception {
        QuestionPostDto mockPost = new QuestionPostDto(1L, "질문 제목", contentBodySample, contentBodySample, tagList);
        String content = gson.toJson(mockPost);

        when(memberService.findMember(anyInt())).thenReturn(new Member());
        when(questionMapper.questionPostDtoToQuestion(any())).thenReturn(new Question());
        when(questionService.createQuestion(any())).thenReturn(new Question());
        when(questionMapper.questionToQuestionResponseDto(any())).thenReturn(questionResponseDto);

        ResultActions actions = mockMvc.perform(post("/question")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        );

        actions.andExpect(status().isCreated()).andDo(document("post-question",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestFields(
                                List.of(
                                        fieldWithPath("questionId").type(JsonFieldType.NUMBER).description("질문글 식별자"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("질문글 제목"),
                                        fieldWithPath("problemBody").type(JsonFieldType.ARRAY).description("질문글 본문 - 문제점(LIST)"),
                                        fieldWithPath("expectingBody").type(JsonFieldType.ARRAY).description("질문글 본문 - 해결시(LIST)"),
                                        fieldWithPath("tag").type(JsonFieldType.ARRAY).description("태그(LIST)")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data.questionId").type(JsonFieldType.NUMBER).description("질문글 식별자"),
                                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("질문글 제목"),
                                        fieldWithPath("data.problemBody").type(JsonFieldType.ARRAY).description("질문글 본문 - 문제점(LIST)"),
                                        fieldWithPath("data.expectingBody").type(JsonFieldType.ARRAY).description("질문글 본문 - 해결시(LIST)"),
                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("질문글 생성시간"),
                                        fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("질문글 수정시간"),
                                        fieldWithPath("data.viewCount").type(JsonFieldType.NUMBER).description("질문글 조회수"),
                                        fieldWithPath("data.voteCount").type(JsonFieldType.NUMBER).description("질문글 추천수"),
                                        fieldWithPath("data.member.memberId").type(JsonFieldType.NUMBER).description("질문글 작성자 식별자"),
                                        fieldWithPath("data.member.displayName").type(JsonFieldType.STRING).description("질문글 작성자 이름"),
                                        fieldWithPath("data.member.email").type(JsonFieldType.STRING).description("질문글 작성자 이메일"),
                                        fieldWithPath("data.member.myQuestionCount").type(JsonFieldType.NUMBER).description("질문글 작성자 질문글 전체 갯수"),
                                        fieldWithPath("data.member.myAnswerCount").type(JsonFieldType.NUMBER).description("질문글 작성자 답글 전체 갯수"),
                                        fieldWithPath("data.member.iconImageUrl").type(JsonFieldType.STRING).description("질문글 작성자 이미지 URL"),
                                        fieldWithPath("data.member.createdAt").type(JsonFieldType.STRING).description("질문글 작성자 회원가입 시간"),
                                        fieldWithPath("data.tag").type(JsonFieldType.ARRAY).description("태그(LIST)")
                                )
                        ))
                );

    }
    @Test
    @DisplayName("질문글 수정 테스트")
    public void patchQuestionTest() throws Exception {
        QuestionPatchDto mockPatch = new QuestionPatchDto(1L, tagList,"질문 제목수정", contentBodySample, contentBodySample);
        questionResponseDto.setTitle("질문 제목 수정");
        String patchJson = gson.toJson(mockPatch);

        long questionId = 1L;
        when(questionMapper.questionPatchDtoToQuestion(any())).thenReturn(new Question());
        when(questionService.updateQuestion(any())).thenReturn(new Question());
        when(questionMapper.questionToQuestionResponseDto(any())).thenReturn(questionResponseDto);
//언제
        ResultActions actions = mockMvc.perform(patch("/question/{question-id}",questionId)
                .content(patchJson)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        );
//그리고
        actions.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("patch-question",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("question-id").description("질문글 식별자")
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("questionId").type(JsonFieldType.NUMBER).description("질문글 식별자"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("질문글 제목"),
                                        fieldWithPath("problemBody").type(JsonFieldType.ARRAY).description("질문글 본문 - 문제점(LIST)"),
                                        fieldWithPath("expectingBody").type(JsonFieldType.ARRAY).description("질문글 본문 - 해결시(LIST)"),
                                        fieldWithPath("tag").type(JsonFieldType.ARRAY).description("태그(LIST)")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data.questionId").type(JsonFieldType.NUMBER).description("질문글 식별자"),
                                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("질문글 제목"),
                                        fieldWithPath("data.problemBody").type(JsonFieldType.ARRAY).description("질문글 본문 - 문제점(LIST)"),
                                        fieldWithPath("data.expectingBody").type(JsonFieldType.ARRAY).description("질문글 본문 - 해결시(LIST)"),
                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("질문글 생성시간"),
                                        fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("질문글 수정시간"),
                                        fieldWithPath("data.viewCount").type(JsonFieldType.NUMBER).description("질문글 조회수"),
                                        fieldWithPath("data.voteCount").type(JsonFieldType.NUMBER).description("질문글 추천수"),
                                        fieldWithPath("data.member.memberId").type(JsonFieldType.NUMBER).description("질문글 작성자 식별자"),
                                        fieldWithPath("data.member.displayName").type(JsonFieldType.STRING).description("질문글 작성자 이름"),
                                        fieldWithPath("data.member.email").type(JsonFieldType.STRING).description("질문글 작성자 이메일"),
                                        fieldWithPath("data.member.myQuestionCount").type(JsonFieldType.NUMBER).description("질문글 작성자 질문글 전체 갯수"),
                                        fieldWithPath("data.member.myAnswerCount").type(JsonFieldType.NUMBER).description("질문글 작성자 답글 전체 갯수"),
                                        fieldWithPath("data.member.iconImageUrl").type(JsonFieldType.STRING).description("질문글 작성자 이미지 URL"),
                                        fieldWithPath("data.member.createdAt").type(JsonFieldType.STRING).description("질문글 작성자 회원가입 시간"),
                                        fieldWithPath("data.tag").type(JsonFieldType.ARRAY).description("태그(LIST)")
                                )
                        )
                ));
    }
    @Test
    @DisplayName("질문글 1건 조회 테스트")
    public void getQuestionTest() throws Exception {

        long questionId = 1L;

        when(questionService.findQuestion(anyInt())).thenReturn(new Question());
        when(memberService.setMemberVoteStatus(any(),any())).thenReturn(loginMemberVoteInfo);
        when(questionMapper.questionToQuestionDetailPageResponseDto(any())).thenReturn(questionDetailPageResponseDto);

        ResultActions actions = mockMvc.perform(get("/question/{question-id}",questionId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        );

        actions.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-question",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("question-id").description("질문글 식별자")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data.questionId").type(JsonFieldType.NUMBER).description("질문글 식별자"),
                                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("질문글 제목"),
                                        fieldWithPath("data.problemBody").type(JsonFieldType.ARRAY).description("질문글 본문 - 문제점(LIST)"),
                                        fieldWithPath("data.expectingBody").type(JsonFieldType.ARRAY).description("질문글 본문 - 해결시(LIST)"),
                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("질문글 생성시간"),
                                        fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("질문글 수정시간"),
                                        fieldWithPath("data.viewCount").type(JsonFieldType.NUMBER).description("질문글 조회수"),
                                        fieldWithPath("data.voteCount").type(JsonFieldType.NUMBER).description("질문글 추천수"),
                                        fieldWithPath("data.member.memberId").type(JsonFieldType.NUMBER).description("질문글 작성자 식별자"),
                                        fieldWithPath("data.member.displayName").type(JsonFieldType.STRING).description("질문글 작성자 이름"),
                                        fieldWithPath("data.member.email").type(JsonFieldType.STRING).description("질문글 작성자 이메일"),
                                        fieldWithPath("data.member.myQuestionCount").type(JsonFieldType.NUMBER).description("질문글 작성자 질문글 전체 갯수"),
                                        fieldWithPath("data.member.myAnswerCount").type(JsonFieldType.NUMBER).description("질문글 작성자 답변 전체 갯수"),
                                        fieldWithPath("data.member.iconImageUrl").type(JsonFieldType.STRING).description("질문글 작성자 이미지 URL"),
                                        fieldWithPath("data.member.createdAt").type(JsonFieldType.STRING).description("질문글 작성자 회원가입 시간"),
                                        fieldWithPath("data.tag").type(JsonFieldType.ARRAY).description("질문글 태그(LIST)"),
                                        fieldWithPath("data.answerCount").type(JsonFieldType.NUMBER).description("질문글의 답변 전체 갯수"),
                                        fieldWithPath("data.answers.[].answerId").type(JsonFieldType.NUMBER).description("답변글 식별자"),
                                        fieldWithPath("data.answers.[].questionId").type(JsonFieldType.NUMBER).description("답변글의 질문글 생성자"),
                                        fieldWithPath("data.answers.[].member.memberId").type(JsonFieldType.NUMBER).description("답변작성자 식별자"),
                                        fieldWithPath("data.answers.[].member.displayName").type(JsonFieldType.STRING).description("답변작성자 이름"),
                                        fieldWithPath("data.answers.[].member.email").type(JsonFieldType.STRING).description("답변작성자 이메일"),
                                        fieldWithPath("data.answers.[].member.myQuestionCount").type(JsonFieldType.NUMBER).description("답변작성자 질문글 전체 갯수"),
                                        fieldWithPath("data.answers.[].member.myAnswerCount").type(JsonFieldType.NUMBER).description("답변작성자 답변 전체 갯수"),
                                        fieldWithPath("data.answers.[].member.iconImageUrl").type(JsonFieldType.STRING).description("답변작성자 이미지 URL"),
                                        fieldWithPath("data.answers.[].member.createdAt").type(JsonFieldType.STRING).description("답변작성자 회원가입 시간"),
                                        fieldWithPath("data.answers.[].content").type(JsonFieldType.ARRAY).description("답변글 본문"),
                                        fieldWithPath("data.answers.[].voteCount").type(JsonFieldType.NUMBER).description("답변글 추천수"),
                                        fieldWithPath("data.answers.[].createdAt").type(JsonFieldType.STRING).description("답변글 생성 시간"),
                                        fieldWithPath("data.answers.[].modifiedAt").type(JsonFieldType.STRING).description("답변글 수정 시간"),
                                        fieldWithPath("data.answers.[].adoptStatus").type(JsonFieldType.STRING).description("답변글 채택여부"),
                                        fieldWithPath("data.loginUserInfo.memberId").type(JsonFieldType.NUMBER).description("현재 로그인된 회원 식별자"),
                                        fieldWithPath("data.loginUserInfo.email").type(JsonFieldType.STRING).description("현재 로그인된 회원 이메일"),
                                        fieldWithPath("data.loginUserInfo.questionId").type(JsonFieldType.NUMBER).description("현재 보고있는 질문글 식별자(확인용)"),
                                        fieldWithPath("data.loginUserInfo.questionvoteStatus").type(JsonFieldType.STRING).description("로그인된 회원의 현재 질문글 추천상태값"),
                                        fieldWithPath("data.loginUserInfo.answerVoteStatus.[].answerId").type(JsonFieldType.NUMBER).description("답변 식별자 (현재 질문글의 답변만 나오는지 체크해주세요)"),
                                        fieldWithPath("data.loginUserInfo.answerVoteStatus.[].voteStatus").type(JsonFieldType.STRING).description("로그인된 회원의 위 답변 ID 값의 추천상태값")

                                )
                        )
                ));
    }
    @Test
    @DisplayName("질문글 전체 조회 테스트")
    public void getQuestionsTest() throws Exception {


        when(questionService.findQuestions(anyInt(),anyInt())).thenReturn(new PageImpl<>(new ArrayList<>(List.of(new Question())), PageRequest.of(1,1),1));
        when(questionMapper.questionToQuestionTotalPageResponseDtos(any())).thenReturn(questionTotalPageResponseDtos);


        ResultActions actions =
                mockMvc.perform(
                        get("/question/")
                                .param("page", "5")
                                .param("size", "10")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                );


        actions.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-questions",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestParameters(
                                parameterWithName("page").description("페이지 요청"),
                                parameterWithName("size").description("페이지당 출력갯수 지정")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data.[].questionId").type(JsonFieldType.NUMBER).description("질문글 식별자"),
                                        fieldWithPath("data.[].title").type(JsonFieldType.STRING).description("질문글 제목"),
                                        fieldWithPath("data.[].problemBody").type(JsonFieldType.ARRAY).description("질문글 본문 - 문제점(LIST)"),
                                        fieldWithPath("data.[].expectingBody").type(JsonFieldType.ARRAY).description("질문글 본문 - 해결시(LIST)"),
                                        fieldWithPath("data.[].createdAt").type(JsonFieldType.STRING).description("질문글 생성시간"),
                                        fieldWithPath("data.[].modifiedAt").type(JsonFieldType.STRING).description("질문글 수정시간"),
                                        fieldWithPath("data.[].viewCount").type(JsonFieldType.NUMBER).description("질문글 조회수"),
                                        fieldWithPath("data.[].voteCount").type(JsonFieldType.NUMBER).description("질문글 추천수"),
                                        fieldWithPath("data.[].answerCount").type(JsonFieldType.NUMBER).description("질문글에 달린 답변갯수"),
                                        fieldWithPath("data.[].adoptAnswerId").type(JsonFieldType.NUMBER).description("질문글에 채택된 답변 식별자 (0이면 채택없음)"),
                                        fieldWithPath("data.[].member.memberId").type(JsonFieldType.NUMBER).description("질문글 작성자 식별자"),
                                        fieldWithPath("data.[].member.displayName").type(JsonFieldType.STRING).description("질문글 작성자 이름"),
                                        fieldWithPath("data.[].member.email").type(JsonFieldType.STRING).description("질문글 작성자 이메일"),
                                        fieldWithPath("data.[].member.createdAt").type(JsonFieldType.STRING).description("질문글 작성자 회원가입 시간"),
                                        fieldWithPath("data.[].member.iconImageUrl").type(JsonFieldType.STRING).description("질문글 작성자 이미지 url"),
                                        fieldWithPath("data.[].member.myQuestionCount").type(JsonFieldType.NUMBER).description("질문글 작성자 질문글 전체 갯수"),
                                        fieldWithPath("data.[].member.myAnswerCount").type(JsonFieldType.NUMBER).description("질문글 작성자 답변글 전체 갯수"),
                                        fieldWithPath("data.[].tag").type(JsonFieldType.ARRAY).description("태그(LIST)"),
                                        fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("페이지정보 - 현재 페이지"),
                                        fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("페이지정보 - 페이지당 출력 갯수"),
                                        fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("페이지정보 - 전체 질문글수"),
                                        fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("페이지정보 - 전체 페이지수")
                                )
                        )
                ));
    }
    @Test
    @DisplayName("질문글 삭제")
    public void deleteQuestionTest() throws Exception {
        //given
        long questionId = 1;
        boolean deleteStatus = true;

        when(memberService.findMemberByEmail(anyString())).thenReturn(new Member());
        when(questionService.deleteQuestion(anyLong(),any())).thenReturn(deleteStatus);
        //when
        ResultActions actions = mockMvc.perform(
                delete("/question/{question-id}/", questionId)
                        .accept(MediaType.APPLICATION_JSON)
        );
        //then
        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document(
                        "delete-question",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("question-id").description("질문글 식별자")
                        )
                ));
    }

    @Test
    @DisplayName("질문글의 답변 채택기능 테스트")
    public void adoptAnswerToQuestionTest() throws Exception {
        long questionId = 1L;
        long answerId = 1L;
        //given
        when(memberService.findMemberByEmail(anyString())).thenReturn(new Member());
        when(answerService.findAnswer(anyLong())).thenReturn(new Answer());
        when(questionService.findQuestion(anyLong())).thenReturn(new Question());
        when(answerMapper.answerToAnswerInfoResponseDto(any())).thenReturn(answerInfoResponseDto);

        //when
        ResultActions actions =
                mockMvc.perform(
                        post("/question/{question-id}/adopt-answer/{answer-id}",questionId,answerId)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
//                                .with(csrf()) test 전용 SecurityConfig 추가후 삭제
                );

        //then
        actions.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("adopt-answer",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("question-id").description("질문글 식별자"),
                                parameterWithName("answer-id").description("답변글 식별자")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data.questionId").type(JsonFieldType.NUMBER).description("질문글 식별자"),
                                        fieldWithPath("data.answerId").type(JsonFieldType.NUMBER).description("답변 식별자"),
                                        fieldWithPath("data.content").type(JsonFieldType.ARRAY).description("답변 내용"),
                                        fieldWithPath("data.adoptStatus").type(JsonFieldType.STRING).description("답변 채택여부"),
                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("답변 생성시간"),
                                        fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("답변 수정시간"),
                                        fieldWithPath("data.voteCount").type(JsonFieldType.NUMBER).description("답변 추천수"),
                                        fieldWithPath("data.member.memberId").type(JsonFieldType.NUMBER).description("답변 작성자 식별자"),
                                        fieldWithPath("data.member.displayName").type(JsonFieldType.STRING).description("답변 작성자 이름"),
                                        fieldWithPath("data.member.email").type(JsonFieldType.STRING).description("답변 작성자 이메일"),
                                        fieldWithPath("data.member.createdAt").type(JsonFieldType.STRING).description("답변 작성자 회원가입 시간"),
                                        fieldWithPath("data.member.iconImageUrl").type(JsonFieldType.STRING).description("답변 작성자 이미지 url"),
                                        fieldWithPath("data.member.myQuestionCount").type(JsonFieldType.NUMBER).description("답변 작성자 질문글 전체 갯수"),
                                        fieldWithPath("data.member.myAnswerCount").type(JsonFieldType.NUMBER).description("답변 작성자 답변글 전체 갯수")
                                )
                        )
                ));
    }
}



