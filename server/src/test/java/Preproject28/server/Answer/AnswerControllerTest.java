package Preproject28.server.Answer;


import Preproject28.server.answer.dto.AnswerPostDto;
import Preproject28.server.answer.dto.AnswerResponseDto;
import Preproject28.server.answer.entity.Answer;
import Preproject28.server.answer.mapper.AnswerMapper;
import Preproject28.server.answer.service.AnswerService;
import Preproject28.server.member.entity.Member;
import Preproject28.server.member.service.MemberService;
import Preproject28.server.question.entity.Question;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AnswerControllerTest.class)
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
    @MockBean
    private Question question;

    @MockBean
    private Member member;


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
                1,
                1);

        when(memberService.findMember(anyInt())).thenReturn(new Member());
        when(answerMapper.answerPostDtoToAnswer(any())).thenReturn(new Answer());
        when(answerService.createAnswer(any())).thenReturn(new Answer());
        when(answerMapper.answerToAnswerResponseDto(any())).thenReturn(response);
        log.info(response.toString());

        ResultActions actions = mockMvc.perform(post("/answer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .with(csrf()));

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
}
