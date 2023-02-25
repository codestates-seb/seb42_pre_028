package Preproject28.server.question;

import Preproject28.server.member.dto.MemberInfoResponseDto;
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
import java.util.ArrayList;
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
    LocalDateTime createAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    LocalDateTime modified = LocalDateTime.of(2023,2,1,1,1);

    public List<String> questionProblemBody() {
        List<String> contentList = new ArrayList<>();
        contentList.add("질문글 List 문제 예시 1번");
        contentList.add("질문글 List 문제 예시 2번");
        contentList.add("질문글 List 문제 예시 3번");
        return contentList;
    }
    public List<String> questionExpectingBody() {
        List<String> contentList = new ArrayList<>();
        contentList.add("질문글 List 도전 예시 1번");
        contentList.add("질문글 List 도전 예시 2번");
        contentList.add("질문글 List 도전 예시 3번");
        return contentList;
    }

    public List<String> tagList() {
        List<String> tagList = new ArrayList<>();
        tagList.add("tag List 예시 1번");
        tagList.add("tag List 예시 2번");
        tagList.add("tag List 예시 3번");
        return tagList;
    }
    @Test
    @DisplayName("질문글 등록 테스트")
    public void postQuestionTest() throws Exception {
        QuestionPostDto mockPost = new QuestionPostDto(1L,
                "질문 제목",
                questionProblemBody(),
                questionExpectingBody()
                ,tagList());

        String content = gson.toJson(mockPost);
        QuestionResponseDto response = new QuestionResponseDto(1L,
                "질문 제목",
                questionProblemBody(),
                questionExpectingBody(),
                createAt,
                modified,
                1,
                1,
                new MemberInfoResponseDto(
                        1L,
                        "김민호",
                        "godalsgh@gmail.com",
                        createAt
                ),
                new ArrayList<>(),
                tagList());

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
                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("질문글 생성 시간"),
                                        fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("질문글 수정 시간"),
                                        fieldWithPath("data.viewCount").type(JsonFieldType.NUMBER).description("질문글 조회수"),
                                        fieldWithPath("data.voteCount").type(JsonFieldType.NUMBER).description("질문글 추천수"),
                                        fieldWithPath("data.member.memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("data.member.displayName").type(JsonFieldType.STRING).description("회원 이름"),
                                        fieldWithPath("data.member.email").type(JsonFieldType.STRING).description("회원 이메일"),
                                        fieldWithPath("data.member.createdAt").type(JsonFieldType.STRING).description("회원가입 시간"),
                                        fieldWithPath("data.answers").type(JsonFieldType.ARRAY).description("질문글에 달린 답변글(LIST)"),
                                        fieldWithPath("data.tag").type(JsonFieldType.ARRAY).description("태그(LIST)")
                                )
                        ))
                );

    }
    @Test
    @DisplayName("질문글 수정 테스트")
    public void patchQuestionTest() throws Exception {
        QuestionPatchDto mockPatch = new QuestionPatchDto(1L,
                tagList(),"질문 제목수정",
                questionProblemBody(),
                questionExpectingBody()
                );
        QuestionResponseDto response = new QuestionResponseDto(1L,
                "질문 제목수정",
                questionProblemBody(),
                questionExpectingBody(),
                createAt,
                modified,
                1,
                1,
                new MemberInfoResponseDto(
                        1L,
                        "김민호",
                        "godalsgh@gmail.com",
                        createAt
                ),
                new ArrayList<>(),
                tagList());
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
                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("질문글 생성 시간"),
                                        fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("질문글 수정 시간"),
                                        fieldWithPath("data.viewCount").type(JsonFieldType.NUMBER).description("질문글 조회수"),
                                        fieldWithPath("data.voteCount").type(JsonFieldType.NUMBER).description("질문글 추천수"),
                                        fieldWithPath("data.member.memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("data.member.displayName").type(JsonFieldType.STRING).description("회원 이름"),
                                        fieldWithPath("data.member.email").type(JsonFieldType.STRING).description("회원 이메일"),
                                        fieldWithPath("data.member.createdAt").type(JsonFieldType.STRING).description("회원가입 시간"),
                                        fieldWithPath("data.answers").type(JsonFieldType.ARRAY).description("질문글에 달린 답변글(LIST)"),
                                        fieldWithPath("data.tag").type(JsonFieldType.ARRAY).description("태그(LIST)")
                                )
                        )
                ));
    }

}



