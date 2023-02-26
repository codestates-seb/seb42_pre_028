package Preproject28.server.answer;


import Preproject28.server.answer.controller.AnswerController;
import Preproject28.server.answer.dto.AnswerInfoResponseDto;
import Preproject28.server.answer.dto.AnswerPatchDto;
import Preproject28.server.answer.dto.AnswerPostDto;
import Preproject28.server.answer.entity.Answer;
import Preproject28.server.answer.mapper.AnswerMapper;
import Preproject28.server.answer.service.AnswerService;
import Preproject28.server.member.dto.MemberInfoResponseDto;
import Preproject28.server.member.entity.Member;
import Preproject28.server.member.service.MemberService;
import Preproject28.server.question.dto.QuestionInfoResponseDto;
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

import static Preproject28.server.utils.ApiDocumentUtils.getRequestPreProcessor;
import static Preproject28.server.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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


    public static final LocalDateTime timeSample = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    public static final List<String> contentList = new ArrayList<>();
    private static final MemberInfoResponseDto member = new MemberInfoResponseDto(1L, "홍길동", "ghdrlfehd@gmail.com", timeSample);
    private static final List<String> tagList = new ArrayList<>();
    private static final AnswerInfoResponseDto response = new AnswerInfoResponseDto();
    private static final QuestionInfoResponseDto question = new QuestionInfoResponseDto();

    @BeforeAll
    public static void init() {
        contentList.add("답변글 내용 List 예시 1번");
        contentList.add("답변글 내용 List 예시 2번");
        contentList.add("답변글 내용 List 예시 3번");
        tagList.add("Tag List 형식예시 (1)");
        tagList.add("Tag List 형식예시 (2)");
        tagList.add("Tag List 형식예시 (3)");
        //질문 샘플 - 답변 없는것
        question.setQuestionId(1L);
        question.setTitle("질문제목");
        question.setProblemBody(contentList);
        question.setExpectingBody(contentList);
        question.setCreatedAt(timeSample);
        question.setModifiedAt(timeSample);
        question.setViewCount(1);
        question.setVoteCount(1);
        question.setMember(member);
        question.setTag(tagList);

        response.setAnswerId(1L);
        response.setQuestionId(question.getQuestionId());
        response.setMember(member);
        response.setContent(contentList);
        response.setVoteCount(1);
        response.setCreatedAt(timeSample);
        response.setModifiedAt(timeSample);
        response.setAdoptStatus(Answer.AdoptStatus.FALSE);

    }
    @Test
    @DisplayName("답변글 등록 테스트")
    public void postAnswerTest() throws Exception{
        AnswerPostDto mockPost = new AnswerPostDto(1L, contentList);

        String content = gson.toJson(mockPost);

        when(memberService.findMember(anyInt())).thenReturn(new Member());
        when(answerMapper.answerPostDtoToAnswer(any())).thenReturn(new Answer());
        when(answerService.createAnswer(any())).thenReturn(new Answer());
        when(answerMapper.answerToAnswerInfoResponseDto(any())).thenReturn(response);
        log.info(response.toString());

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
                                fieldWithPath("data.content").type(JsonFieldType.ARRAY).description("답변글 본문(LIST)"),
                                fieldWithPath("data.voteCount").type(JsonFieldType.NUMBER).description("답변글 추천수"),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("답변글 생성 시간"),
                                fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("답변글 수정 시간"),
                                fieldWithPath("data.adoptStatus").type(JsonFieldType.STRING).description("답변글 채택여부"),
                                fieldWithPath("data.member.memberId").type(JsonFieldType.NUMBER).description("답변작성 회원 식별자"),
                                fieldWithPath("data.member.displayName").type(JsonFieldType.STRING).description("답변작성 회원 이름"),
                                fieldWithPath("data.member.email").type(JsonFieldType.STRING).description("답변작성 회원 이메일"),
                                fieldWithPath("data.member.createdAt").type(JsonFieldType.STRING).description("답변작성 회원 생성 시간"),
                                fieldWithPath("data.questionId").type(JsonFieldType.NUMBER).description("답변달린 질문글 식별자")
                        )
                ))
        );

    }
    @Test
    @DisplayName("답변글 수정 테스트")
    public void patchAnswerTest() throws Exception{
        AnswerPatchDto answerPatchDto = new AnswerPatchDto(1L,
                contentList);
        String patchJson = gson.toJson(answerPatchDto);

        long answerId = 1L;
        when(answerMapper.answerPatchDtoToAnswer(any())).thenReturn(new Answer());
        when(answerService.updateAnswer(any())).thenReturn(new Answer());
        when(answerMapper.answerToAnswerInfoResponseDto(any())).thenReturn(response);
//언제
        ResultActions actions = mockMvc.perform(patch("/answer/{answer-id}",answerId)
                .content(patchJson)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        );
//그리고
        actions.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("patch-answer-address",
                        pathParameters(
                                parameterWithName("answer-id").description("답변글 식별자")
                        )
                ))
                .andDo(document("patch-answer",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestFields(
                                List.of(
                                        fieldWithPath("answerId").type(JsonFieldType.NUMBER).description("답변글 식별자"),
                                        fieldWithPath("content").type(JsonFieldType.ARRAY).description("답변글 본문(LIST)")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data.answerId").type(JsonFieldType.NUMBER).description("답변글 식별자"),
                                        fieldWithPath("data.content").type(JsonFieldType.ARRAY).description("답변글 본문(LIST)"),
                                        fieldWithPath("data.voteCount").type(JsonFieldType.NUMBER).description("답변글 추천수"),
                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("답변글 생성 시간"),
                                        fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("답변글 수정 시간"),
                                        fieldWithPath("data.adoptStatus").type(JsonFieldType.STRING).description("답변글 채택여부"),
                                        fieldWithPath("data.member.memberId").type(JsonFieldType.NUMBER).description("답변작성 회원 식별자"),
                                        fieldWithPath("data.member.displayName").type(JsonFieldType.STRING).description("답변작성 회원 이름"),
                                        fieldWithPath("data.member.email").type(JsonFieldType.STRING).description("답변작성 회원 이메일"),
                                        fieldWithPath("data.member.createdAt").type(JsonFieldType.STRING).description("답변작성 회원 생성 시간"),
                                        fieldWithPath("data.questionId").type(JsonFieldType.NUMBER).description("답변달린 질문글 식별자")
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
                .andDo(document("delete-answer-address",
                        pathParameters(
                                parameterWithName("answer-id").description("답변글 식별자")
                        )
                ))
                .andDo(document(
                        "delete-answer",
                        getRequestPreProcessor(),
                        getResponsePreProcessor()
                ));
    }
}
