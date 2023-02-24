package Preproject28.server.question;

import Preproject28.server.answer.dto.AnswerPatchDto;
import Preproject28.server.answer.dto.AnswerResponseDto;
import Preproject28.server.answer.entity.Answer;
import Preproject28.server.member.entity.Member;
import Preproject28.server.member.service.MemberService;
import Preproject28.server.question.controller.QuestionController;
import Preproject28.server.question.dto.QuestionPatchDto;
import Preproject28.server.question.dto.QuestionPostDto;
import Preproject28.server.question.dto.QuestionResponseDto;
import Preproject28.server.question.entity.Question;
import Preproject28.server.question.mapper.QuestionMapper;
import Preproject28.server.question.service.QuestionService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static Preproject28.server.utils.ApiDocumentUtils.getRequestPreProcessor;
import static Preproject28.server.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = QuestionController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@WithMockUser
@Slf4j
public class QuestionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private QuestionService questionService;
    @MockBean
    private MemberService memberService;
    @MockBean
    private QuestionMapper mapper;
    @Autowired
    private Gson gson;
    LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    LocalDateTime modified = LocalDateTime.of(2023,02,1,1,1);
    @Test
    @DisplayName("PostQuestionTest")
    public void postQuestionTest() throws Exception{
        QuestionPostDto mockPost = new QuestionPostDto(1L,
                "질문 1",
                "문제 바디",
                "에상 바디",
                1);

        String content = gson.toJson(mockPost);
        QuestionResponseDto response = new QuestionResponseDto(1L,
                "질문 1",
                "문제 바디",
                "에상 바디",
                now,
                modified,
                1,
                1,
                1, "답 아이디 리스트");

        when(memberService.findMember(anyInt())).thenReturn(new Member());
        when(mapper.questionPostDtoToQuestion(any())).thenReturn(new Question());
        when(questionService.createQuestion(any())).thenReturn(new Question());
        when(mapper.questionToQuestionResponseDto(any())).thenReturn(response);
        log.info(response.toString());

        ResultActions actions = mockMvc.perform(post("/question")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .with(csrf()));

        actions.andExpect(status().isCreated()).andDo(document("post-Question",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestFields(
                                List.of(
                                        fieldWithPath("questionId").type(JsonFieldType.NUMBER).description("질문 아이디"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                        fieldWithPath("problemBody").type(JsonFieldType.STRING).description("문제본문"),
                                        fieldWithPath("expectingBody").type(JsonFieldType.STRING).description("예상본문"),
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("멤버 아이디")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("questionId").type(JsonFieldType.NUMBER).description("질문 아이디"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                        fieldWithPath("problemBody").type(JsonFieldType.STRING).description("문제본문"),
                                        fieldWithPath("expectingBody").type(JsonFieldType.STRING).description("예상본문"),
                                        fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성시간"),
                                        fieldWithPath("modifiedAt").type(JsonFieldType.STRING).description("수정시간"),
                                        fieldWithPath("viewCount").type(JsonFieldType.NUMBER).description("조회수"),
                                        fieldWithPath("voteCount").type(JsonFieldType.NUMBER).description("추천수"),
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("멤버 아이디"),
                                        fieldWithPath("answerId").type(JsonFieldType.STRING).description("답 아이디 리스트")
                                )
                        ))
                );

    }
    @Test
    @DisplayName("patchQuestionTest")
    public void patchQuestionTest() throws Exception{
        QuestionPatchDto mockPatch = new QuestionPatchDto(1L,
                "질문 1",
                "문제 바디",
                "에상 바디"
                );
        QuestionResponseDto response = new QuestionResponseDto(1L,
                "질문제목",
                "문제 바디","예상 바디",
                now,
                modified,
                1,
                1,
                1L,"답 아이디 리스트");
        String patchJson = gson.toJson(mockPatch);

        long questionId = 1L;
        when(mapper.questionPatchDtoToQuestion(any())).thenReturn(new Question());
        when(questionService.updateQuestion(any())).thenReturn(new Question());
        when(mapper.questionToQuestionResponseDto(any())).thenReturn(response);
//언제
        ResultActions actions = mockMvc.perform(patch("/question/{question-id}",questionId)
                .content(patchJson)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
        );
//그리고
        actions.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("patch-question",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestFields(
                                List.of(
                                        fieldWithPath("questionId").type(JsonFieldType.NUMBER).description("질문 아이디"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("질문 제목"),
                                        fieldWithPath("problemBody").type(JsonFieldType.STRING).description("질문 본문"),
                                        fieldWithPath("expectingBody").type(JsonFieldType.STRING).description("예상본문")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data.questionId").type(JsonFieldType.NUMBER).description("질문 아이디").optional(),
                                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("제목").optional(),
                                        fieldWithPath("data.problemBody").type(JsonFieldType.STRING).description("문제본문").optional(),
                                        fieldWithPath("data.expectingBody").type(JsonFieldType.STRING).description("예상본문").optional(),
                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("생성시간").optional(),
                                        fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("수정시간").optional(),
                                        fieldWithPath("data.viewCount").type(JsonFieldType.NUMBER).description("조회수").optional(),
                                        fieldWithPath("data.voteCount").type(JsonFieldType.NUMBER).description("추천수").optional(),
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("멤버 아이디").optional(),
                                        fieldWithPath("data.answerId").type(JsonFieldType.STRING).description("답 아이디 리스트").optional()
                                )
                        )
                ));
    }
}



