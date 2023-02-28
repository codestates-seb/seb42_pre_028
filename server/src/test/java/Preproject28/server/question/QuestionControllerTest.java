package Preproject28.server.question;

import Preproject28.server.answer.dto.AnswerInfoResponseDto;
import Preproject28.server.answer.entity.Answer;
import Preproject28.server.answer.service.AnswerService;
import Preproject28.server.member.dto.response.MemberInfoResponseDto;
import Preproject28.server.member.entity.Member;
import Preproject28.server.member.service.MemberService;
import Preproject28.server.question.controller.QuestionController;
import Preproject28.server.question.dto.response.QuestionTotalPageResponseDto;
import Preproject28.server.question.dto.QuestionPatchDto;
import Preproject28.server.question.dto.QuestionPostDto;
import Preproject28.server.question.dto.response.QuestionResponseDto;
import Preproject28.server.question.entity.Question;
import Preproject28.server.question.mapper.QuestionMapper;
import Preproject28.server.question.service.QuestionService;
import Preproject28.server.utils.SecurityTestConfig;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = QuestionController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@WithMockUser
@Slf4j
@Import(SecurityTestConfig.class)
public class QuestionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private QuestionService questionService;
    @MockBean
    private MemberService memberService;
    @MockBean
    private AnswerService answerService;
    @MockBean
    private QuestionMapper questionMapper;
    @Autowired
    private Gson gson;

    //stub data 정리 ( ResponseDto 들 전체 )
    private static final LocalDateTime timeSample = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    private static final List<String> contentList = new ArrayList<>();
    private static final List<String> tagList = new ArrayList<>();
    private static final MemberInfoResponseDto member = new MemberInfoResponseDto();
    private static final List<AnswerInfoResponseDto> answers = new ArrayList<>();
    private static final QuestionResponseDto response = new QuestionResponseDto();
    private static final QuestionTotalPageResponseDto infoResponse = new QuestionTotalPageResponseDto();

    @BeforeAll
    public static void init() {
        contentList.add("본문 List 형식예시 (1)");
        contentList.add("본문 List 형식예시 (2)");
        contentList.add("본문 List 형식예시 (3)");
        tagList.add("Tag List 형식예시 (1)");
        tagList.add("Tag List 형식예시 (2)");
        tagList.add("Tag List 형식예시 (3)");
        //답변샘플
        answers.add(new AnswerInfoResponseDto(1L, 1L, member, contentList, 1, timeSample, timeSample, Answer.AdoptStatus.FALSE));
        answers.add(new AnswerInfoResponseDto(2L, 1L, member, contentList, 1, timeSample, timeSample, Answer.AdoptStatus.FALSE));
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
        when(questionMapper.questionToQuestionDetailPageResponseDto(any())).thenReturn(infoResponse);
        log.info(response.toString());

        ResultActions actions = mockMvc.perform(post("/question")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
//                .with(csrf()))
        );

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
//                .with(csrf())
        );
//그리고
        actions.andExpect(status().isOk())
                .andDo(print())
//                .andDo(document("patch-question-address",
//                        pathParameters(
//                                parameterWithName("question-id").description("질문글 식별자")
//                        )
//                ))
                .andDo(document("patch-question",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("question-id").description("질문글 식별자")
                        ),
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
                                        fieldWithPath("data.member.myQuestionCount").type(JsonFieldType.NUMBER).description("회원이 작성한 질문글 수"),
                                        fieldWithPath("data.member.myAnswerCount").type(JsonFieldType.NUMBER).description("회원이 작성한 답글 수"),
                                        fieldWithPath("data.member.iconImageUrl").type(JsonFieldType.STRING).description("회원 이미지 URL"),
                                        fieldWithPath("data.member.createdAt").type(JsonFieldType.STRING).description("회원가입 시간"),
                                        fieldWithPath("data.tag").type(JsonFieldType.ARRAY).description("태그(LIST)"),
                                        fieldWithPath("data.answerCount").type(JsonFieldType.NUMBER).description("질문글에 달린 답변글 갯"),
                                        fieldWithPath("data.answers.[].answerId").type(JsonFieldType.NUMBER).description("질문글에 달린 답변글 식별자"),
                                        fieldWithPath("data.answers.[].questionId").type(JsonFieldType.NUMBER).description("답변의 질문글 식별자"),
                                        fieldWithPath("data.answers.[].content").type(JsonFieldType.ARRAY).description("질문글에 달린 답변글 본문"),
                                        fieldWithPath("data.answers.[].voteCount").type(JsonFieldType.NUMBER).description("질문글에 달린 답변글 추천수"),
                                        fieldWithPath("data.answers.[].createdAt").type(JsonFieldType.STRING).description("질문글에 달린 답변글 생성시간"),
                                        fieldWithPath("data.answers.[].modifiedAt").type(JsonFieldType.STRING).description("질문글에 달린 답변글 수정시간"),
                                        fieldWithPath("data.answers.[].adoptStatus").type(JsonFieldType.STRING).description("질문글에 달린 답변글 채택여부"),
                                        fieldWithPath("data.answers.[].member.memberId").type(JsonFieldType.NUMBER).description("질문글에 달린 답변글 작성회원 식별자"),
                                        fieldWithPath("data.answers.[].member.displayName").type(JsonFieldType.STRING).description("질문글에 달린 답변글 작성회원 이름"),
                                        fieldWithPath("data.answers.[].member.email").type(JsonFieldType.STRING).description("질문글에 달린 답변글 작성회원 이메일"),
                                        fieldWithPath("data.answers.[].member.myQuestionCount").type(JsonFieldType.NUMBER).description("회원이 작성한 질문글 수"),
                                        fieldWithPath("data.answers.[].member.myAnswerCount").type(JsonFieldType.NUMBER).description("회원이 작성한 답글 수"),
                                        fieldWithPath("data.answers.[].member.iconImageUrl").type(JsonFieldType.STRING).description("회원 이미지 URL"),
                                        fieldWithPath("data.answers.[].member.createdAt").type(JsonFieldType.STRING).description("질문글에 달린 답변글 작성회원 생성시간")
                                )
                        )
                ));
    }
    @Test
    @DisplayName("질문글 1건 조회 테스트")
    public void getQuestionTest() throws Exception {

        long questionId = 1L;
        MemberInfoResponseDto memberInfoResponseDto = new MemberInfoResponseDto();
        memberInfoResponseDto.setMemberId(1L);
        response.setMember(memberInfoResponseDto);

        when(questionService.findQuestion(anyInt())).thenReturn(new Question());
        when(questionMapper.questionToQuestionResponseDto(any())).thenReturn(response);

        ResultActions actions = mockMvc.perform(get("/question/{question-id}",questionId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        );

        actions.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-question-address",
                        pathParameters(
                                parameterWithName("question-id").description("질문글 식별자")
                        )
                ))
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
                                        fieldWithPath("data.answers.[].questionId").type(JsonFieldType.NUMBER).description("답변의 질문글 식별자"),
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
    @DisplayName("질문글 전체 조회 테스트")
    public void getQuestionsTest() throws Exception {

        List<QuestionTotalPageResponseDto> responses = new ArrayList<>();
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
    public void deleteQuestionTest() throws Exception {
        //given
        long questionId = 1;
        boolean deleteStatus = true;

        when(memberService.findMemberByEmail(anyString())).thenReturn(new Member());
        when(questionService.deleteQuestion(anyLong(),any())).thenReturn(deleteStatus);
        //when
        ResultActions actions = mockMvc.perform(
                delete("/question/{question-id}/", questionId)
                        .accept(MediaType.APPLICATION_JSON)
        );
        //then
        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("delete-question-address",
                        pathParameters(
                                parameterWithName("question-id").description("질문글 식별자")
                        )
                ))
                .andDo(document(
                        "delete-question",
                        getRequestPreProcessor(),
                        getResponsePreProcessor()
                ));
    }

    @Test
    @DisplayName("질문글의 답변 채택기능 테스트")
    public void adoptAnswerToQuestionTest() throws Exception {
        long questionId = 1L;
        long answerId = 1L;
        //given
        when(memberService.findMemberByEmail(anyString())).thenReturn(new Member());
        when(answerService.findAnswer(anyLong())).thenReturn(new Answer());
        when(questionService.findQuestion(anyLong())).thenReturn(new Question());
        when(questionMapper.questionToQuestionResponseDto(any())).thenReturn(response);

        //when
        ResultActions actions =
                mockMvc.perform(
                        post("/question/{question-id}/adopt-answer/{answer-id}",questionId,answerId)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
//                                .with(csrf()) test 전용 SecurityConfig 추가후 삭제
                );

        //then
        actions.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("adopt-answer-address",
                        pathParameters(
                                parameterWithName("question-id").description("질문글 식별자"),
                                parameterWithName("answer-id").description("답변글 식별자")
                        )
                ))
                .andDo(document("adopt-answer",
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
                                        fieldWithPath("data.answers.[].questionId").type(JsonFieldType.NUMBER).description("답변의 질문글 식별자"),
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
    @DisplayName("질문글정렬")
    public void sortQuestionTest() throws Exception {
        List<Question> questions = new ArrayList<>();
        Question question1 = new Question();
        question1.setId(1L);
        question1.setTitle("Question 1");
        questions.add(question1);
        Question question2 = new Question();
        question2.setId(2L);
        question2.setTitle("Question 2");
        questions.add(question2);

        Page<Question> pageQuestions = new PageImpl<>(questions, PageRequest.of(0, 15), 2);

        //given
        when(questionService.getAllQuestions(Mockito.anyInt(), Mockito.anyString())).thenReturn(pageQuestions);

        //when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/{question-id}", 1)
                        .param("page", "1")
                        .param("sort", "createdAt"));

        actions.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("sort-question-address",
                        pathParameters(
                                parameterWithName("question-id").description("질문글 식별자")
                        )
                ))
                .andDo(document("sort-question",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        RequestDocumentation.requestParameters(
                                RequestDocumentation.parameterWithName("page").description("페이지 번호"),
                                RequestDocumentation.parameterWithName("sort").description("정렬기준")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data.questions[].questionId").type(JsonFieldType.NUMBER).description("질문글 식별자"),
                                        fieldWithPath("data.questions[].title").type(JsonFieldType.STRING).description("질문글 제목"),
                                        fieldWithPath("data.questions[].problemBody").type(JsonFieldType.STRING).description("질문글 본문"),
                                        fieldWithPath("data.questions[].expectingBody").type(JsonFieldType.STRING).description("질문글 본문"),
                                        fieldWithPath("data.questions[].createdAt").type(JsonFieldType.STRING).description("질문글 생성시간"),
                                        fieldWithPath("data.questions[].modifiedAt").type(JsonFieldType.STRING).description("질문글 수정시간"),
                                        fieldWithPath("data.questions[].viewCount").type(JsonFieldType.NUMBER).description("질문글 조회수"),
                                        fieldWithPath("data.questions[].voteCount").type(JsonFieldType.NUMBER).description("질문글 추천수"),
                                        fieldWithPath("data.questions[].answerCount").type(JsonFieldType.NUMBER).description("답변 수"),
                                        fieldWithPath("data.questions[].member").type(JsonFieldType.NUMBER).description("회원식별자"),
                                        fieldWithPath("data.questions[].tag").type(JsonFieldType.ARRAY).description("태그(List)"),
                                        fieldWithPath("data.page.totalElements").type(JsonFieldType.NUMBER).description("전체 데이터 수"),
                                        fieldWithPath("data.page.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수"),
                                        fieldWithPath("data.page.number").type(JsonFieldType.NUMBER).description("현재 페이지 번호")
                                )
                        )));
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.questions[0].questionId").value(1L))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.questions[0].title").value("Question 1"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.questions[1].questionId").value(2L))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.questions[1].title").value("Question 2"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.page.totalElements").value(2))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.page.totalPages").value(1))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.page.number").value(1))
//                .andDo(MockMvcRestDocumentation.document("question/get",
//                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
//                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
//                        RequestDocumentation.requestParameters(
//                                RequestDocumentation.parameterWithName("page").description("페이지 번호 (1부터 시작)"),
//                                RequestDocumentation.parameterWithName("sort").description("정렬할 컬럼 (기본값: 생성시간)")
//                        ),
//                        responseFields(
//                                fieldWithPath("data.questions[].questionId").description("질문글 식별자"),
//                                fieldWithPath("data.questions[].title").description("질문글 제목"),
//                                fieldWithPath("data.questions[].problemBody").description("질문글 본문"),
//                                fieldWithPath("data.questions[].expectingBody").description("질문글 본문"),
//                                fieldWithPath("data.questions[].createdAt").description("질문글 생성시간"),
//                                fieldWithPath("data.questions[].modifiedAt").description("질문글 수정시간"),
//                                fieldWithPath("data.questions[].viewCount").description("질문글 조회수"),
//                                fieldWithPath("data.questions[].voteCount").description("질문글 추천수"),
//                                fieldWithPath("data.questions[].answerCount").description("답변 수"),
//                                fieldWithPath("data.questions[].member").description("회원식별자"),
//                                fieldWithPath("data.questions[].tag").description("태그(List)"),
//                                fieldWithPath("data.page.totalElements").description("전체 데이터 수"),
//                                fieldWithPath("data.page.totalPages").description("전체 페이지 수"),
//                                fieldWithPath("data.page.number").description("현재 페이지 번호")
//                        )
//                ));





        Mockito.verify(questionService).getAllQuestions(0, "createdAt");
    }
}



