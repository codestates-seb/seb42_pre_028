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