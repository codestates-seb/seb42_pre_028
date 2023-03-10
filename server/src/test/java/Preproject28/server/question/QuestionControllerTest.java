package Preproject28.server.question;

import Preproject28.server.member.entity.Member;
import Preproject28.server.member.service.MemberService;
import Preproject28.server.question.controller.QuestionController;
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
                "?????? 1",
                "?????? ??????",
                "?????? ??????",
                1);

        String content = gson.toJson(mockPost);
        QuestionResponseDto response = new QuestionResponseDto(1L,
                "?????? 1",
                "?????? ??????",
                "?????? ??????",
                now,
                modified,
                1,
                1,
                1,
                "???????????? ??????");

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
                                        fieldWithPath("questionId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("problemBody").type(JsonFieldType.STRING).description("????????????"),
                                        fieldWithPath("expectingBody").type(JsonFieldType.STRING).description("????????????"),
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("?????? ?????????")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("questionId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("problemBody").type(JsonFieldType.STRING).description("????????????"),
                                        fieldWithPath("expectingBody").type(JsonFieldType.STRING).description("????????????"),
                                        fieldWithPath("createdAt").type(JsonFieldType.STRING).description("????????????"),
                                        fieldWithPath("modifiedAt").type(JsonFieldType.STRING).description("????????????"),
                                        fieldWithPath("viewCount").type(JsonFieldType.NUMBER).description("?????????"),
                                        fieldWithPath("voteCount").type(JsonFieldType.NUMBER).description("?????????"),
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("answers").type(JsonFieldType.STRING).description("??? ????????? ?????????")
                                )
                        ))
                );

    }
    @Test
    @DisplayName("postQuestionTest")
    public void patchQuestionTest() throws Exception{
        long memberId = 1L;

    }




}