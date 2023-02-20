package Preproject28.server.QuestionControllerTest;

import Preproject28.server.Question.dto.QuestionPostDto;
import Preproject28.server.Question.dto.QuestionResponseDto;
import Preproject28.server.Question.entity.Question;
import Preproject28.server.Question.mapper.QuestionMapper;
import Preproject28.server.Question.service.QuestionService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuestionControllerTest.class)
@Slf4j
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class QuestionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private QuestionService questionService;
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
                now,
                modified,
                1,
                2,
                1L,
                "리스트로 대체");

        String content = gson.toJson(mockPost);
        QuestionResponseDto response = new QuestionResponseDto(1L,
                "질문 1",
                "문제 바디",
                "에상 바디",
                now,
                modified,
                1,
                2,
                1L,
                "리스트로 대체");

        given(mapper.questionPostDtoToQuestion(Mockito.any(QuestionPostDto.class))).willReturn(new Question());
        given(questionService.createQuestion(Mockito.any(Question.class))).willReturn(new Question());
        given(mapper.questionToQuestionResponseDto(Mockito.any(Question.class))).willReturn(response);

        //
        ResultActions actions =
                mockMvc.perform(post("/question").accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        actions.andExpect(status().isCreated()).andDo(document("post-Question",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                        List.of(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("problemBody").type(JsonFieldType.STRING).description("문제본문"),
                                fieldWithPath("expectingBody").type(JsonFieldType.STRING).description("예상본문")
//                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성시간"),
//                                fieldWithPath("modifiedAt").type(JsonFieldType.STRING).description("수정시간"),
//                                fieldWithPath("viewCount").type(JsonFieldType.STRING).description("조회수"),
//                                fieldWithPath("voteCount").type(JsonFieldType.STRING).description("추천수"),
//                                fieldWithPath("memberId").type(JsonFieldType.STRING).description("멤버 아이디"),
//                                fieldWithPath("answers").type(JsonFieldType.STRING).description("답 아이디 리스트")


                        )
                ),
                responseFields(
                        List.of(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("problemBody").type(JsonFieldType.STRING).description("문제본문"),
                                fieldWithPath("expectingBody").type(JsonFieldType.STRING).description("예상본문")
//                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성시간"),
//                                fieldWithPath("modifiedAt").type(JsonFieldType.STRING).description("수정시간"),
//                                fieldWithPath("viewCount").type(JsonFieldType.STRING).description("조회수"),
//                                fieldWithPath("voteCount").type(JsonFieldType.STRING).description("추천수"),
//                                fieldWithPath("memberId").type(JsonFieldType.STRING).description("멤버 아이디"),
//                                fieldWithPath("answers").type(JsonFieldType.STRING).description("답 아이디 리스트")
                        )
                ))
        );

    }




}
