package Preproject28.server.question;

import Preproject28.server.answer.dto.AnswerResponseDto;
import Preproject28.server.answer.entity.Answer;
import Preproject28.server.member.dto.MemberInfoResponseDto;
import Preproject28.server.member.entity.Member;
import Preproject28.server.member.service.MemberService;
import Preproject28.server.question.controller.QuestionController;
import Preproject28.server.question.dto.QuestionInfoResponseDto;
import Preproject28.server.question.dto.QuestionPatchDto;
import Preproject28.server.question.dto.QuestionPostDto;
import Preproject28.server.question.dto.QuestionResponseDto;
import Preproject28.server.question.entity.Question;
import Preproject28.server.question.mapper.QuestionMapper;
import Preproject28.server.question.service.QuestionService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

import static Preproject28.server.utils.ApiDocumentUtils.getRequestPreProcessor;
import static Preproject28.server.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    private QuestionMapper questionMapper;
    @Autowired
    private Gson gson;
    private static final LocalDateTime timeSample = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    private static final List<String> contentList = new ArrayList<>();
    private static final List<String> tagList = new ArrayList<>();

    private static final MemberInfoResponseDto member = new MemberInfoResponseDto(1L, "홍길동", "ghdrlfehd@gmail.com", timeSample);
    private static final List<AnswerResponseDto> answers = new ArrayList<>();
    private static final QuestionResponseDto response = new QuestionResponseDto();
    private static final QuestionInfoResponseDto infoResponse = new QuestionInfoResponseDto();

    @BeforeAll
    public static void init() {
        contentList.add("본문 List 형식예시 (1)");
        contentList.add("본문 List 형식예시 (2)");
        contentList.add("본문 List 형식예시 (3)");
        tagList.add("Tag List 형식예시 (1)");
        tagList.add("Tag List 형식예시 (2)");
        tagList.add("Tag List 형식예시 (3)");
        //답변샘플
        answers.add(new AnswerResponseDto(1L, contentList, 1, timeSample, timeSample, Answer.AdoptStatus.FALSE,
                member));
        answers.add(new AnswerResponseDto(2L, contentList, 1, timeSample, timeSample, Answer.AdoptStatus.FALSE,
               member));
        //질문글 샘플 - 답변 까지 보이는것
        response.setQuestionId(1L);
        response.setTitle("질문제목");
        response.setProblemBody(contentList);
        response.setExpectingBody(contentList);
        response.setCreatedAt(timeSample);
        response.setModifiedAt(timeSample);
        response.setViewCount(1);
        response.setVoteCount(1);
        response.setMember(member);
        response.setAnswers(answers);
        response.setTag(tagList);
        //질문 샘플 - 답변 없는것
        infoResponse.setQuestionId(1L);
        infoResponse.setTitle("질문제목");
        infoResponse.setProblemBody(contentList);
        infoResponse.setExpectingBody(contentList);
        infoResponse.setCreatedAt(timeSample);
        infoResponse.setModifiedAt(timeSample);
        infoResponse.setViewCount(1);
        infoResponse.setVoteCount(1);
        infoResponse.setMember(member);
        infoResponse.setTag(tagList);
    }

    @Test
    @DisplayName("질문글 등록 테스트")
    public void postQuestionTest() throws Exception {
        QuestionPostDto mockPost = new QuestionPostDto(1L, "질문 제목", contentList, contentList, tagList);
        String content = gson.toJson(mockPost);

        when(memberService.findMember(anyInt())).thenReturn(new Member());
        when(questionMapper.questionPostDtoToQuestion(any())).thenReturn(new Question());
        when(questionService.createQuestion(any())).thenReturn(new Question());
        when(questionMapper.questionToQuestionInfoResponseDto(any())).thenReturn(infoResponse);
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
                                        fieldWithPath("data.tag").type(JsonFieldType.ARRAY).description("태그(LIST)")
                                )
                        ))
                );

    }
    @Test
    @DisplayName("질문글 수정 테스트")
    public void patchQuestionTest() throws Exception {
        QuestionPatchDto mockPatch = new QuestionPatchDto(1L, tagList,"질문 제목수정", contentList, contentList);
        response.setTitle("질문 제목 수정");
        String patchJson = gson.toJson(mockPatch);

        long questionId = 1L;
        when(questionMapper.questionPatchDtoToQuestion(any())).thenReturn(new Question());
        when(questionService.updateQuestion(any())).thenReturn(new Question());
        when(questionMapper.questionToQuestionResponseDto(any())).thenReturn(response);
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
                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("질문글 생성시간"),
                                        fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("질문글 수정시간"),
                                        fieldWithPath("data.viewCount").type(JsonFieldType.NUMBER).description("질문글 조회수"),
                                        fieldWithPath("data.voteCount").type(JsonFieldType.NUMBER).description("질문글 추천수"),
                                        fieldWithPath("data.member.memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("data.member.displayName").type(JsonFieldType.STRING).description("회원 이름"),
                                        fieldWithPath("data.member.email").type(JsonFieldType.STRING).description("회원 이메일"),
                                        fieldWithPath("data.member.createdAt").type(JsonFieldType.STRING).description("회원가입 시간"),
                                        fieldWithPath("data.tag").type(JsonFieldType.ARRAY).description("태그(LIST)"),
                                        fieldWithPath("data.answers.[].answerId").type(JsonFieldType.NUMBER).description("질문글에 달린 답변글 식별자"),
                                        fieldWithPath("data.answers.[].content").type(JsonFieldType.ARRAY).description("질문글에 달린 답변글 본문"),
                                        fieldWithPath("data.answers.[].voteCount").type(JsonFieldType.NUMBER).description("질문글에 달린 답변글 추천수"),
                                        fieldWithPath("data.answers.[].createdAt").type(JsonFieldType.STRING).description("질문글에 달린 답변글 생성시간"),
                                        fieldWithPath("data.answers.[].modifiedAt").type(JsonFieldType.STRING).description("질문글에 달린 답변글 수정시간"),
                                        fieldWithPath("data.answers.[].adoptStatus").type(JsonFieldType.STRING).description("질문글에 달린 답변글 채택여부"),
                                        fieldWithPath("data.answers.[].member.memberId").type(JsonFieldType.NUMBER).description("질문글에 달린 답변글 작성회원 식별자"),
                                        fieldWithPath("data.answers.[].member.displayName").type(JsonFieldType.STRING).description("질문글에 달린 답변글 작성회원 이름"),
                                        fieldWithPath("data.answers.[].member.email").type(JsonFieldType.STRING).description("질문글에 달린 답변글 작성회원 이메일"),
                                        fieldWithPath("data.answers.[].member.createdAt").type(JsonFieldType.STRING).description("질문글에 달린 답변글 작성회원 생성시간")
                                )
                        )
                ));
    }
    @Test
    @DisplayName("질문글 1건 조회 테스트")
    public void getQuestionTest() throws Exception {

        long questionId = 1L;

        when(questionService.findQuestion(anyInt())).thenReturn(new Question());
        when(questionMapper.questionToQuestionResponseDto(any())).thenReturn(response);

        ResultActions actions = mockMvc.perform(get("/question/{question-id}",questionId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        );

        actions.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-question",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
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
                                        fieldWithPath("data.member.memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("data.member.displayName").type(JsonFieldType.STRING).description("회원 이름"),
                                        fieldWithPath("data.member.email").type(JsonFieldType.STRING).description("회원 이메일"),
                                        fieldWithPath("data.member.createdAt").type(JsonFieldType.STRING).description("회원가입 시간"),
                                        fieldWithPath("data.tag").type(JsonFieldType.ARRAY).description("태그(LIST)"),
                                        fieldWithPath("data.answers.[].answerId").type(JsonFieldType.NUMBER).description("질문글에 달린 답변글 식별자"),
                                        fieldWithPath("data.answers.[].content").type(JsonFieldType.ARRAY).description("질문글에 달린 답변글 본문"),
                                        fieldWithPath("data.answers.[].voteCount").type(JsonFieldType.NUMBER).description("질문글에 달린 답변글 추천수"),
                                        fieldWithPath("data.answers.[].createdAt").type(JsonFieldType.STRING).description("질문글에 달린 답변글 생성시간"),
                                        fieldWithPath("data.answers.[].modifiedAt").type(JsonFieldType.STRING).description("질문글에 달린 답변글 수정시간"),
                                        fieldWithPath("data.answers.[].adoptStatus").type(JsonFieldType.STRING).description("질문글에 달린 답변글 채택여부"),
                                        fieldWithPath("data.answers.[].member.memberId").type(JsonFieldType.NUMBER).description("질문글에 달린 답변글 작성회원 식별자"),
                                        fieldWithPath("data.answers.[].member.displayName").type(JsonFieldType.STRING).description("질문글에 달린 답변글 작성회원 이름"),
                                        fieldWithPath("data.answers.[].member.email").type(JsonFieldType.STRING).description("질문글에 달린 답변글 작성회원 이메일"),
                                        fieldWithPath("data.answers.[].member.createdAt").type(JsonFieldType.STRING).description("질문글에 달린 답변글 작성회원 생성시간")
                                )
                        )
                ));
    }
    @Test
    @DisplayName("질문글 삭제 테스트")
    public void deleteQuestionTest() throws Exception {

        List<QuestionInfoResponseDto> responses = new ArrayList<>();
        responses.add(infoResponse);
        responses.add(infoResponse);


        when(questionService.findQuestions(anyInt(),anyInt())).thenReturn(new PageImpl<>(new ArrayList<>(List.of(new Question())), PageRequest.of(1,1),1));
        when(questionMapper.questionToQuestionResponseInfoDtos(any())).thenReturn(responses);


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
                                        fieldWithPath("data.[].member.memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("data.[].member.displayName").type(JsonFieldType.STRING).description("회원 이름"),
                                        fieldWithPath("data.[].member.email").type(JsonFieldType.STRING).description("회원 이메일"),
                                        fieldWithPath("data.[].member.createdAt").type(JsonFieldType.STRING).description("회원가입 시간"),
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
    public void deleteQuestionsTest() throws Exception {
        //given
        long questionId = 1;
        boolean deleteStatus = true;

        when(memberService.findMemberByEmail(anyString())).thenReturn(new Member());
        when(questionService.deleteQuestion(anyLong(),any())).thenReturn(deleteStatus);
        //when
        ResultActions actions = mockMvc.perform(
                delete("/question/{question-id}/", questionId)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()));
        //then
        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document(
                        "delete-question",
                        getRequestPreProcessor(),
                        getResponsePreProcessor()
                ));
    }
}



