package Preproject28.server.member;

import Preproject28.server.answer.entity.Answer;
import Preproject28.server.answer.mapper.AnswerMapper;
import Preproject28.server.answer.service.AnswerService;
import Preproject28.server.helper.StubData;
import Preproject28.server.member.controller.MemberController;
import Preproject28.server.member.dto.*;
import Preproject28.server.member.entity.Member;
import Preproject28.server.member.mapper.MemberMapper;
import Preproject28.server.member.service.MemberService;
import Preproject28.server.question.entity.Question;
import Preproject28.server.question.mapper.QuestionMapper;
import Preproject28.server.question.service.QuestionService;
import Preproject28.server.utils.SecurityTestConfig;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static Preproject28.server.helper.StubData.*;
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


@WebMvcTest(controllers = MemberController.class)
@Slf4j
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@WithMockUser
@Import(SecurityTestConfig.class)
public class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MemberService memberService;
    @MockBean
    private AnswerService answerService;
    @MockBean
    private QuestionService questionService;
    @MockBean
    private QuestionMapper questionMapper;
    @MockBean
    private MemberMapper memberMapper;
    @MockBean
    private AnswerMapper answerMapper;
    @Autowired
    private Gson gson;

    @BeforeEach
    public void init() {
        StubData.init();
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void postMemberTest() throws Exception {
        //given
        MemberPostDto requestPost = new MemberPostDto("홍길동", "ghdrlfehd@gmail.com", "1111");
        String requestJson = gson.toJson(requestPost);

        when(memberMapper.memberPostDtoToMember(any())).thenReturn(new Member());
        when(memberService.createMember(any())).thenReturn(new Member());
        when(memberMapper.memberToMemberInfoResponse(any())).thenReturn(memberInfoResponseDto);
        //when

        ResultActions actions = mockMvc.perform(post("/members")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        );
        //then
        actions
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document(
                        "post-member",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestFields(
                                List.of(
                                        fieldWithPath("displayName").type(JsonFieldType.STRING).description("회원 이름"),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("회원 이메일"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("회원 비밀번호")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("data.displayName").type(JsonFieldType.STRING).description("회원 이름"),
                                        fieldWithPath("data.email").type(JsonFieldType.STRING).description("회원 이메일"),
                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("회원가입 시간"),
                                        fieldWithPath("data.iconImageUrl").type(JsonFieldType.STRING).description("회원 아이콘 uri"),
                                        fieldWithPath("data.myQuestionCount").type(JsonFieldType.NUMBER).description("회원의 전체 질문글 갯수"),
                                        fieldWithPath("data.myAnswerCount").type(JsonFieldType.NUMBER).description("회원의 전체 답변 갯수")
                                )
                )
                ));
    }
    @Test
    @DisplayName("멤버 수정테스트")
    public void patchMemberTest() throws Exception {
        //given
        MemberPatchDto requestPatch = new MemberPatchDto("홍길동", "1111");
        String requestJson = gson.toJson(requestPatch);

        long memberId = 1L;

        when(memberMapper.memberPatchDtoToMember(any())).thenReturn(new Member());
        when(memberService.updateMember(any())).thenReturn(new Member());
        when(memberMapper.memberToMemberInfoResponse(any())).thenReturn(memberInfoResponseDto);

        //when
        ResultActions actions = mockMvc.perform(patch("/members/{member-id}",memberId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        );
        //then
        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document(
                        "patch-member",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("member-id").description("회원 식별자")
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("displayName").type(JsonFieldType.STRING).description("회원 이름"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("회원 비밀번호")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("data.displayName").type(JsonFieldType.STRING).description("회원 이름"),
                                        fieldWithPath("data.email").type(JsonFieldType.STRING).description("회원 이메일"),
                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("회원가입 시간"),
                                        fieldWithPath("data.iconImageUrl").type(JsonFieldType.STRING).description("회원 아이콘 uri"),
                                        fieldWithPath("data.myQuestionCount").type(JsonFieldType.NUMBER).description("회원의 전체 질문글 갯수"),
                                        fieldWithPath("data.myAnswerCount").type(JsonFieldType.NUMBER).description("회원의 전체 답변 갯수")
                                )
                        )
                ));
    }

    //내가쓴 글 조회
    @Test
    @DisplayName("내가쓴 질문글 조회 테스트")
    public void getMemberQuestionTest() throws Exception {
        //given
        Member member = new Member();
        member.setMemberId(1L);

        when(questionService.findQuestionsByMemberId(anyLong(),anyInt(),anyInt())).thenReturn(new PageImpl<>(new ArrayList<>(List.of(new Question())), PageRequest.of(1,5),1));
        when(questionMapper.questionToQuestionTotalPageResponseDtos(any())).thenReturn(questionTotalPageResponseDtos);
        //when
        ResultActions actions = mockMvc.perform(
                get("/members/{member-id}/question", member.getMemberId())
                        .param("page", "1")
                        .param("size", "5")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));
        //then
        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document(
                        "get-member-question",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("member-id").description("회원 식별자")
                        ),
                        requestParameters(
                                parameterWithName("page").description("페이지 요청"),
                                parameterWithName("size").description("페이지당 출력갯수 지정")
                        ),
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
                                        fieldWithPath("data.[].answerCount").type(JsonFieldType.NUMBER).description("질문글에 달린 답변갯수"),
                                        fieldWithPath("data.[].adoptAnswerId").type(JsonFieldType.NUMBER).description("질문글에 채택된 답변 식별자 (0이면 채택없음)"),
                                        fieldWithPath("data.[].member.memberId").type(JsonFieldType.NUMBER).description("질문글 작성자 회원 식별자"),
                                        fieldWithPath("data.[].member.displayName").type(JsonFieldType.STRING).description("질문글 작성자 회원 이름"),
                                        fieldWithPath("data.[].member.email").type(JsonFieldType.STRING).description("질문글 작성자 회원 이메일"),
                                        fieldWithPath("data.[].member.createdAt").type(JsonFieldType.STRING).description("질문글 작성자 회원가입 시간"),
                                        fieldWithPath("data.[].member.iconImageUrl").type(JsonFieldType.STRING).description("질문글 작성자 회원 이미지 url"),
                                        fieldWithPath("data.[].member.myQuestionCount").type(JsonFieldType.NUMBER).description("질문글 작성자 질문글 전체 갯수"),
                                        fieldWithPath("data.[].member.myAnswerCount").type(JsonFieldType.NUMBER).description("질문글 작성자 답변글 전체 갯수"),
                                        fieldWithPath("data.[].tag").type(JsonFieldType.ARRAY).description("태그(LIST)"),
                                        fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("페이지정보 - 현재 페이지"),
                                        fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("페이지정보 - 페이지당 출력 갯수"),
                                        fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("페이지정보 - 전체 질문글수"),
                                        fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("페이지정보 - 전체 페이지수")
                                )
                        )
                ));
    }
    //내가쓴 답변 조회
    @Test
    @DisplayName("내가쓴 답변글 조회 테스트")
    public void getMemberAnswerTest() throws Exception {
        //given
        Member member = new Member();
        member.setMemberId(1L);



        when(answerService.findAnswersByMemberId(anyLong(),anyInt(),anyInt())).thenReturn(new PageImpl<>(new ArrayList<>(List.of(new Answer())), PageRequest.of(1,5),1));
        when(answerMapper.answerToAnswerInfoResponseDtos(any())).thenReturn(answerInfoResponseDtos);
        //when
        ResultActions actions = mockMvc.perform(
                get("/members/{member-id}/answer", member.getMemberId())
                        .param("page", "1")
                        .param("size", "5")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));
        //then
        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document(
                        "get-member-answer",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("member-id").description("회원 식별자")
                        ),
                        requestParameters(
                                parameterWithName("page").description("페이지 요청"),
                                parameterWithName("size").description("페이지당 출력갯수 지정")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data.[].answerId").type(JsonFieldType.NUMBER).description("답변글 식별자"),
                                        fieldWithPath("data.[].questionId").type(JsonFieldType.NUMBER).description("답변의 질문글 식별자"),
                                        fieldWithPath("data.[].content").type(JsonFieldType.ARRAY).description("답변글 본문"),
                                        fieldWithPath("data.[].voteCount").type(JsonFieldType.NUMBER).description("답변글 추천수"),
                                        fieldWithPath("data.[].createdAt").type(JsonFieldType.STRING).description("답변글 생성 시간"),
                                        fieldWithPath("data.[].modifiedAt").type(JsonFieldType.STRING).description("답변글 수정 시간"),
                                        fieldWithPath("data.[].adoptStatus").type(JsonFieldType.STRING).description("답변글 채택여부"),
                                        fieldWithPath("data.[].member.memberId").type(JsonFieldType.NUMBER).description("답변 작성자 식별자"),
                                        fieldWithPath("data.[].member.displayName").type(JsonFieldType.STRING).description("답변 작성자 이름"),
                                        fieldWithPath("data.[].member.email").type(JsonFieldType.STRING).description("답변 작성자 이메일"),
                                        fieldWithPath("data.[].member.createdAt").type(JsonFieldType.STRING).description("답변 작성자 생성 시간"),
                                        fieldWithPath("data.[].member.iconImageUrl").type(JsonFieldType.STRING).description("답변 작성자 이미지 url"),
                                        fieldWithPath("data.[].member.myQuestionCount").type(JsonFieldType.NUMBER).description("답변 작성자 질문글 전체 갯수"),
                                        fieldWithPath("data.[].member.myAnswerCount").type(JsonFieldType.NUMBER).description("답변 작성자 답변글 전체 갯수"),
                                        fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("페이지정보 - 현재 페이지"),
                                        fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("페이지정보 - 페이지당 출력 갯수"),
                                        fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("페이지정보 - 전체 질문글수"),
                                        fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("페이지정보 - 전체 페이지수")

                                )
                        )
                ));
    }
    //내 정보 조회
    @Test
    @DisplayName("내 정보 조회")
    public void getMemberInfoTest() throws Exception {
        //given
        MemberPostDto requestPost = new MemberPostDto("홍길동", "ghdrlfehd@gmail.com", "1111");
        String requestJson = gson.toJson(requestPost);
        String memberEmail = "ghdrlfehd@gmail.com";

        when(memberService.findMemberByEmail(anyString())).thenReturn(new Member());
        when(memberMapper.memberToMemberInfoResponse(any())).thenReturn(memberInfoResponseDto);
        //when
        ResultActions actions = mockMvc.perform(
                get("/members/{member-email}/info", memberEmail)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson));
        //then
        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document(
                        "get-member-info",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("member-email").description("회원 이메일")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("data.displayName").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("회원가입 시간"),
                                        fieldWithPath("data.iconImageUrl").type(JsonFieldType.STRING).description("회원 아이콘 uri"),
                                        fieldWithPath("data.myQuestionCount").type(JsonFieldType.NUMBER).description("회원의 전체 질문글 갯수"),
                                        fieldWithPath("data.myAnswerCount").type(JsonFieldType.NUMBER).description("회원의 전체 답변 갯수")
                                )
                        )
                ));
    }
    //회원 탈퇴
    @Test
    @DisplayName("회원 탈퇴")
    public void deleteMemberTest() throws Exception {
        //given
        long memberId = 1;
        boolean deleteStatus = true;
//        when(memberService.deleteMember(anyLong())).thenReturn(deleteStatus);
        //when
        ResultActions actions = mockMvc.perform(
                delete("/members/{member-id}/", memberId)
                        .accept(MediaType.APPLICATION_JSON)
        );
        //then
        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document(
                        "delete-member",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("member-id").description("회원 식별자")
                        )
                        ));
    }

}