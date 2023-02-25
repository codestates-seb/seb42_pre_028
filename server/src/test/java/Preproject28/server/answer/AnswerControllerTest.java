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
import Preproject28.server.question.entity.Question;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static Preproject28.server.utils.ApiDocumentUtils.getRequestPreProcessor;
import static Preproject28.server.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import com.google.gson.Gson;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
    private AnswerMapper mapper;
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
        when(mapper.answerPostDtoToAnswer(any())).thenReturn(new Answer());
        when(answerService.createAnswer(any())).thenReturn(new Answer());
        when(mapper.answerToAnswerResponseDto(any())).thenReturn(response);
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
        when(mapper.answerPatchDtoToAnswer(any())).thenReturn(new Answer());
        when(answerService.updateAnswer(any())).thenReturn(new Answer());
        when(mapper.answerToAnswerResponseDto(any())).thenReturn(response);
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
    @Test
    @DisplayName("getAnswerTest")
    public void getAnswerTest() throws Exception {

        //given

        long answerId = 1L;
        AnswerResponseDto response = new AnswerResponseDto(1L,
                "답변변경",
                3,
                now,
                modified,
                false,
                1L,
                1L);

        when(answerService.findAnswer(Mockito.anyLong())).thenReturn(new Answer());
        when(mapper.answerToAnswerResponseDto(Mockito.any(Answer.class))).thenReturn(response);


        //when

        ResultActions actions = mockMvc.perform(
                get("/answer/{answer-id}", answerId)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()));
        //then

        actions.andExpect(status().isOk())
                .andExpect(jsonPath("data.answerId").value(answerId))
                .andDo(document(
                        "get-Answer",
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("answer-id").description("조회한 답변 번호")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data.answerId").type(JsonFieldType.NUMBER).description("답변 아이디"),
                                        fieldWithPath("data.content").type(JsonFieldType.STRING).description("제목"),
                                        fieldWithPath("data.voteCount").type(JsonFieldType.NUMBER).description("추천수->엔티티로 대체"),
                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("생성시간"),
                                        fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("수정시간"),
                                        fieldWithPath("data.adoptStatus").type(JsonFieldType.BOOLEAN).description("조회수"),
                                        fieldWithPath("data.questionId").type(JsonFieldType.NUMBER).description("질문 엔티티"),
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("멤버 엔티티")
                                )
                        )
                ));

    }

    @Test
    @DisplayName("gtAnswersTest")
    public void getAnswersTest() throws Exception {

        //given

        int page = 1;
        int size = 15;

        MultiValueMap<String,String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("page", String.valueOf((page)));
        queryParams.add("size", String.valueOf((size)));

        Answer answer1 = new Answer();
        answer1.setAnswerId(1L);
        answer1.setContent("answer1");
        answer1.setMember(new Member());
        answer1.setQuestion(new Question());

        Answer answer2 = new Answer();
        answer2.setAnswerId(2L);
        answer2.setContent("answer2");
        answer2.setMember(new Member());
        answer2.setQuestion(new Question());

        Page<Answer> pages = new PageImpl<>(List.of(answer1, answer2),
                PageRequest.of(1, 15, Sort.by("answerId").descending()), 2);

        AnswerResponseDto answerResponse1 = new AnswerResponseDto(
                1L,
                "answer1",
                3,
                now,
                modified,
                false,
                1L,
                1L
        );

        AnswerResponseDto answerResponse2 = new AnswerResponseDto(
                2L,
                "answer2",
                2,
                now,
                modified,
                false,
                2L,
                2L
        );

        List<AnswerResponseDto> responses = List.of(answerResponse1, answerResponse2);


        when(answerService.findAnswers(Mockito.anyInt(),Mockito.anyInt())).thenReturn(pages);
        when(mapper.answerToAnswerResponseDtos(Mockito.anyList())).thenReturn(responses);

        //when

        ResultActions actions =
                mockMvc.perform(
                        get("/answer")
//                                .content(patchJson)
                                .params(queryParams)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
//                                .with(csrf())
                );

        //then

        actions
                .andExpect(status().isOk())
                .andDo(document("get-answers",
                        getResponsePreProcessor(),
                        requestParameters(
                                List.of(
                                        parameterWithName("page").description("페이지번호"),
                                        parameterWithName("size").description("페이지크기")
                                )
                        ),
//                        pathParameters(
//                                parameterWithName("question-id").description("질문 아이디")
//                        ),
                        responseFields(
                                List.of(
//                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("결과 데이터").optional(),
                                        fieldWithPath("data[].answerId").type(JsonFieldType.NUMBER).description("답변 아이디"),
                                        fieldWithPath("data[].content").type(JsonFieldType.STRING).description("제목"),
                                        fieldWithPath("data[].voteCount").type(JsonFieldType.NUMBER).description("추천수->엔티티로 대체"),
                                        fieldWithPath("data[].createdAt").type(JsonFieldType.STRING).description("생성시간"),
                                        fieldWithPath("data[].modifiedAt").type(JsonFieldType.STRING).description("수정시간"),
                                        fieldWithPath("data[].adoptStatus").type(JsonFieldType.BOOLEAN).description("조회수"),
                                        fieldWithPath("data[].questionId").type(JsonFieldType.NUMBER).description("질문 엔티티"),
                                        fieldWithPath("data[].memberId").type(JsonFieldType.NUMBER).description("멤버 엔티티"),
                                        fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("페이지 정보"),
                                        fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("페이지 정보"),
                                        fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("페이지 정보"),
                                        fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("페이지 정보")

                                )
                        )
                ));

    }
}
