package Preproject28.server.answer;


import Preproject28.server.answer.controller.AnswerController;
import Preproject28.server.answer.dto.AnswerPatchDto;
import Preproject28.server.answer.dto.AnswerPostDto;
import Preproject28.server.answer.dto.AnswerResponseDto;
import Preproject28.server.answer.entity.Answer;
import Preproject28.server.answer.mapper.AnswerMapper;
import Preproject28.server.answer.service.AnswerService;
import Preproject28.server.member.entity.Member;
import Preproject28.server.member.service.MemberService;
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
import com.google.gson.Gson;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AnswerController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@WithMockUser
@Slf4j
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
    private AnswerMapper answerMapper;
//
//    @MockBean
//    private Question question;
//
//    @MockBean
//    private Member member;


    LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    LocalDateTime modified = LocalDateTime.of(2023,02,1,1,1);

    @Test
    @DisplayName("PostAnswerTest")
    public void postAnswerTest() throws Exception{
        AnswerPostDto mockPost = new AnswerPostDto(1L,
                "답변 1",1L
            );

        String content = gson.toJson(mockPost);
        AnswerResponseDto response = new AnswerResponseDto(1L,
                "답변 1",
                2,
                now,
                modified,
                false,
                1L,
                1L);

        when(memberService.findMember(anyInt())).thenReturn(new Member());
        when(answerMapper.answerPostDtoToAnswer(any())).thenReturn(new Answer());
        when(answerService.createAnswer(any())).thenReturn(new Answer());
        when(answerMapper.answerToAnswerResponseDto(any())).thenReturn(response);
        log.info(response.toString());

        ResultActions actions = mockMvc.perform(post("/answer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .with(csrf())
        );

        actions.andExpect(status().isCreated()).andDo(document("post-Answer",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestFields(
                        List.of(
                                fieldWithPath("answerId").type(JsonFieldType.NUMBER).description("질문 아이디"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("답변내용"),
                                fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("멤버 아이디")
                        )
                ),
                responseFields(
                        List.of(
                                fieldWithPath("answerId").type(JsonFieldType.NUMBER).description("답변 아이디"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("voteCount").type(JsonFieldType.NUMBER).description("추천수->엔티티로 대체"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성시간"),
                                fieldWithPath("modifiedAt").type(JsonFieldType.STRING).description("수정시간"),
                                fieldWithPath("adoptStatus").type(JsonFieldType.BOOLEAN).description("조회수"),
                                fieldWithPath("questionId").type(JsonFieldType.NUMBER).description("질문 엔티티"),
                                fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("멤버 엔티티")
                        )
                ))
        );

    }
    @Test
    @DisplayName("patchAnswerTest")
    public void patchAnswerTest() throws Exception{
        AnswerPatchDto answerPatchDto = new AnswerPatchDto(1L,
                "변경 답변");
        AnswerResponseDto response = new AnswerResponseDto(1L,
                "답변변경",
                3,
                now,
                modified,
                false,
                1L,
                1L);
        String patchJson = gson.toJson(answerPatchDto);

        long answerId = 1L;
        when(answerMapper.answerPatchDtoToAnswer(any())).thenReturn(new Answer());
        when(answerService.updateAnswer(any())).thenReturn(new Answer());
        when(answerMapper.answerToAnswerResponseDto(any())).thenReturn(response);
//언제
        ResultActions actions = mockMvc.perform(patch("/answer/{answer-id}",answerId)
                .content(patchJson)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
        );
//그리고
        actions.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("patch-answer",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestFields(
                                List.of(
                                        fieldWithPath("answerId").type(JsonFieldType.NUMBER).description("대답 아이디"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("대답 본문")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data.answerId").type(JsonFieldType.NUMBER).description("답변 아이디"),
                                        fieldWithPath("data.content").type(JsonFieldType.STRING).description("제목"),
                                        fieldWithPath("data.voteCount").type(JsonFieldType.NUMBER).description("추천수->엔티티로 대체"),
                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("생성시간"),
                                        fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("수정시간"),
                                        fieldWithPath("data.adoptStatus").type(JsonFieldType.BOOLEAN).description("조회수"),
                                        fieldWithPath("data.questionId").type(JsonFieldType.NUMBER).description("질문 ID"),
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("작성자 ID")
                                )
                        )


                ));

    }
}
