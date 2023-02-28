package Preproject28.server.answer;


import Preproject28.server.answer.controller.AnswerController;
import Preproject28.server.answer.dto.AnswerInfoResponseDto;
import Preproject28.server.answer.dto.AnswerPatchDto;
import Preproject28.server.answer.dto.AnswerPostDto;
import Preproject28.server.answer.entity.Answer;
import Preproject28.server.answer.mapper.AnswerMapper;
import Preproject28.server.answer.service.AnswerService;
import Preproject28.server.helper.StubData;
import Preproject28.server.member.dto.response.MemberInfoResponseDto;
import Preproject28.server.member.entity.Member;
import Preproject28.server.member.service.MemberService;
import Preproject28.server.question.dto.response.QuestionTotalPageResponseDto;
import Preproject28.server.question.service.QuestionService;
import Preproject28.server.util.dto.SingleResponseDto;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

@WebMvcTest(controllers = AnswerController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@WithMockUser
@Slf4j
@Import(SecurityTestConfig.class)
public class AnswerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Gson gson;
    @MockBean
    private MemberService memberService;
    @MockBean
    private AnswerService answerService;
    @MockBean
    private QuestionService questionService;
    @MockBean
    private AnswerMapper answerMapper;


    @BeforeAll
    public static void init() {
        StubData.init();
    }
    @Test
    @DisplayName("답변글 등록 테스트")
    public void postAnswerTest() throws Exception{
        AnswerPostDto mockPost = new AnswerPostDto(1L, contentBodySample);

        String content = gson.toJson(mockPost);

        when(memberService.findMember(anyInt())).thenReturn(new Member());
        when(answerMapper.answerPostDtoToAnswer(any())).thenReturn(new Answer());
        when(answerService.createAnswer(any())).thenReturn(new Answer());
        when(answerMapper.answerToAnswerInfoResponseDto(any())).thenReturn(answerInfoResponseDto);

        ResultActions actions = mockMvc.perform(post("/answer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        );

        actions.andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("post-answer",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestFields(
                        List.of(
                                fieldWithPath("questionId").type(JsonFieldType.NUMBER).description("답변작성하는 질문글 식별자"),
                                fieldWithPath("content").type(JsonFieldType.ARRAY).description("답변글 본문(LIST)")
                        )
                ),
                responseFields(
                        List.of(
                                fieldWithPath("data.answerId").type(JsonFieldType.NUMBER).description("답변글 식별자"),
                                fieldWithPath("data.questionId").type(JsonFieldType.NUMBER).description("답변달린 질문글 식별자"),
                                fieldWithPath("data.content").type(JsonFieldType.ARRAY).description("답변글 본문(LIST)"),
                                fieldWithPath("data.voteCount").type(JsonFieldType.NUMBER).description("답변글 추천수"),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("답변글 생성 시간"),
                                fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("답변글 수정 시간"),
                                fieldWithPath("data.adoptStatus").type(JsonFieldType.STRING).description("답변글 채택여부"),
                                fieldWithPath("data.member.memberId").type(JsonFieldType.NUMBER).description("답변작성 회원 식별자"),
                                fieldWithPath("data.member.displayName").type(JsonFieldType.STRING).description("답변작성 회원 이름"),
                                fieldWithPath("data.member.email").type(JsonFieldType.STRING).description("답변작성 회원 이메일"),
                                fieldWithPath("data.member.iconImageUrl").type(JsonFieldType.STRING).description("답변작성 아이콘 uri"),
                                fieldWithPath("data.member.createdAt").type(JsonFieldType.STRING).description("답변작성 회원 생성 시간"),
                                fieldWithPath("data.member.myQuestionCount").type(JsonFieldType.NUMBER).description("답변작성 회원 전체 질문글 갯수"),
                                fieldWithPath("data.member.myAnswerCount").type(JsonFieldType.NUMBER).description("답변작성 회원 전체 답변 갯수")
                        )
                ))
        );

    }
    @Test
    @DisplayName("답변글 수정 테스트")
    public void patchAnswerTest() throws Exception{
        AnswerPatchDto answerPatchDto = new AnswerPatchDto(1L,
                contentBodySample);
        String request = gson.toJson(answerPatchDto);

        long answerId = 1L;
        when(answerService.findAnswer(anyLong())).thenReturn(answer);
        when(answerMapper.answerPatchDtoToAnswer(any())).thenReturn(new Answer());
        when(answerService.updateAnswer(any())).thenReturn(new Answer());
        when(answerMapper.answerToAnswerInfoResponseDto(any())).thenReturn(answerInfoResponseDto);
//언제
        ResultActions actions = mockMvc.perform(patch("/answer/{answer-id}",answerId)
                .content(request)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        );
//그리고
        actions.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("patch-answer",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("answer-id").description("답변글 식별자")
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("answerId").type(JsonFieldType.NUMBER).description("답변글 식별자"),
                                        fieldWithPath("content").type(JsonFieldType.ARRAY).description("답변글 본문(LIST)")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data.answerId").type(JsonFieldType.NUMBER).description("답변글 식별자"),
                                        fieldWithPath("data.questionId").type(JsonFieldType.NUMBER).description("답변달린 질문글 식별자"),
                                        fieldWithPath("data.content").type(JsonFieldType.ARRAY).description("답변글 본문(LIST)"),
                                        fieldWithPath("data.voteCount").type(JsonFieldType.NUMBER).description("답변글 추천수"),
                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("답변글 생성 시간"),
                                        fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("답변글 수정 시간"),
                                        fieldWithPath("data.adoptStatus").type(JsonFieldType.STRING).description("답변글 채택여부"),
                                        fieldWithPath("data.member.memberId").type(JsonFieldType.NUMBER).description("답변작성 회원 식별자"),
                                        fieldWithPath("data.member.displayName").type(JsonFieldType.STRING).description("답변작성 회원 이름"),
                                        fieldWithPath("data.member.email").type(JsonFieldType.STRING).description("답변작성 회원 이메일"),
                                        fieldWithPath("data.member.iconImageUrl").type(JsonFieldType.STRING).description("답변작성 아이콘 uri"),
                                        fieldWithPath("data.member.createdAt").type(JsonFieldType.STRING).description("답변작성 회원 생성 시간"),
                                        fieldWithPath("data.member.myQuestionCount").type(JsonFieldType.NUMBER).description("답변작성 회원 전체 질문글 갯수"),
                                        fieldWithPath("data.member.myAnswerCount").type(JsonFieldType.NUMBER).description("답변작성 회원 전체 답변 갯수")
                                )
                        )
                ));
    }
    @Test
    @DisplayName("답변글 삭제")
    public void deleteAnswerTest() throws Exception {
        //given
        long answerId = 1;
        boolean deleteStatus = true;
        when(memberService.findMemberByEmail(anyString())).thenReturn(new Member());
        when(answerService.deleteAnswer(anyLong(),any())).thenReturn(deleteStatus);
        //when
        ResultActions actions = mockMvc.perform(
                delete("/answer/{answer-id}/", answerId)
                        .accept(MediaType.APPLICATION_JSON));
        //then
        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document(
                        "delete-answer",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("answer-id").description("답변글 식별자")
                        )
                ));
    }
    @Test
    @DisplayName("답변글 한건 조회")
    public void getAnswerTest() throws Exception {
        //given
        long answerId = 1L;
        when(answerService.findAnswer(anyLong())).thenReturn(new Answer());
        when(answerMapper.answerToAnswerInfoResponseDto(any())).thenReturn(answerInfoResponseDto);

        //when
        ResultActions actions = mockMvc.perform(
                get("/answer/{answer-id}", answerId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));
        //then
        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document(
                        "get-answer",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("answer-id").description("회원 식별자")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data.answerId").type(JsonFieldType.NUMBER).description("답변글 식별자"),
                                        fieldWithPath("data.questionId").type(JsonFieldType.NUMBER).description("답변의 질문글 식별자"),
                                        fieldWithPath("data.content").type(JsonFieldType.ARRAY).description("답변글 본문"),
                                        fieldWithPath("data.voteCount").type(JsonFieldType.NUMBER).description("답변글 추천수"),
                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("답변글 생성 시간"),
                                        fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("답변글 수정 시간"),
                                        fieldWithPath("data.adoptStatus").type(JsonFieldType.STRING).description("답변글 채택여부"),
                                        fieldWithPath("data.member.memberId").type(JsonFieldType.NUMBER).description("답변 작성자 식별자"),
                                        fieldWithPath("data.member.displayName").type(JsonFieldType.STRING).description("답변 작성자 이름"),
                                        fieldWithPath("data.member.email").type(JsonFieldType.STRING).description("답변 작성자 이메일"),
                                        fieldWithPath("data.member.createdAt").type(JsonFieldType.STRING).description("답변 작성자 생성 시간"),
                                        fieldWithPath("data.member.iconImageUrl").type(JsonFieldType.STRING).description("답변 작성자 이미지 url"),
                                        fieldWithPath("data.member.myQuestionCount").type(JsonFieldType.NUMBER).description("답변 작성자 질문글 전체 갯수"),
                                        fieldWithPath("data.member.myAnswerCount").type(JsonFieldType.NUMBER).description("답변 작성자 답변글 전체 갯수")
                                )
                        )
                ));
    }

    @Test
    @DisplayName("내가쓴 답변글 조회 테스트")
    public void getMemberAnswerTest() throws Exception {
        //given
        Member member = new Member();
        member.setMemberId(1L);



        when(answerService.findAnswersByMemberId(anyLong(),anyInt(),anyInt())).thenReturn(new PageImpl<>(new ArrayList<>(List.of(new Answer())), PageRequest.of(1,5),1));
        when(answerMapper.answerToAnswerInfoResponseDtos(any())).thenReturn(answerInfoResponseDtos);
        //when
        ResultActions actions = mockMvc.perform(
                get("/answer/{member-id}/answer", member.getMemberId())
                        .param("page", "1")
                        .param("size", "5")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));
        //then
        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document(
                        "get-answer-my-answers",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("member-id").description("회원 식별자")
                        ),
                        requestParameters(
                                parameterWithName("page").description("페이지 요청"),
                                parameterWithName("size").description("페이지당 출력갯수 지정")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data.[].answerId").type(JsonFieldType.NUMBER).description("답변글 식별자"),
                                        fieldWithPath("data.[].questionId").type(JsonFieldType.NUMBER).description("답변의 질문글 식별자"),
                                        fieldWithPath("data.[].content").type(JsonFieldType.ARRAY).description("답변글 본문"),
                                        fieldWithPath("data.[].voteCount").type(JsonFieldType.NUMBER).description("답변글 추천수"),
                                        fieldWithPath("data.[].createdAt").type(JsonFieldType.STRING).description("답변글 생성 시간"),
                                        fieldWithPath("data.[].modifiedAt").type(JsonFieldType.STRING).description("답변글 수정 시간"),
                                        fieldWithPath("data.[].adoptStatus").type(JsonFieldType.STRING).description("답변글 채택여부"),
                                        fieldWithPath("data.[].member.memberId").type(JsonFieldType.NUMBER).description("답변 작성자 식별자"),
                                        fieldWithPath("data.[].member.displayName").type(JsonFieldType.STRING).description("답변 작성자 이름"),
                                        fieldWithPath("data.[].member.email").type(JsonFieldType.STRING).description("답변 작성자 이메일"),
                                        fieldWithPath("data.[].member.createdAt").type(JsonFieldType.STRING).description("답변 작성자 생성 시간"),
                                        fieldWithPath("data.[].member.iconImageUrl").type(JsonFieldType.STRING).description("답변 작성자 이미지 url"),
                                        fieldWithPath("data.[].member.myQuestionCount").type(JsonFieldType.NUMBER).description("답변 작성자 질문글 전체 갯수"),
                                        fieldWithPath("data.[].member.myAnswerCount").type(JsonFieldType.NUMBER).description("답변 작성자 답변글 전체 갯수"),
                                        fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("페이지정보 - 현재 페이지"),
                                        fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("페이지정보 - 페이지당 출력 갯수"),
                                        fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("페이지정보 - 전체 질문글수"),
                                        fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("페이지정보 - 전체 페이지수")

                                )
                        )
                ));
    }
}
