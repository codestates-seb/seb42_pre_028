package Preproject28.server.member;

import Preproject28.server.member.controller.MemberController;
import Preproject28.server.member.dto.MemberInfoResponseDto;
import Preproject28.server.member.dto.MemberPatchDto;
import Preproject28.server.member.dto.MemberPostDto;
import Preproject28.server.member.dto.MemberQuestionResponseDto;
import Preproject28.server.member.entity.Member;
import Preproject28.server.member.mapper.MemberMapper;
import Preproject28.server.member.service.MemberService;
import Preproject28.server.question.entity.Question;
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

import static Preproject28.server.utils.ApiDocumentUtils.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = MemberController.class)
@Slf4j
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@WithMockUser
class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MemberService memberService;
    @MockBean
    private MemberMapper mapper;
    @Autowired
    private Gson gson;
    LocalDateTime createdAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    @Test
    @DisplayName("회원가입 테스트")
    public void postMemberTest() throws Exception {
        //given
        MemberPostDto requestPost = new MemberPostDto("김민호", "godalsgh@gmail.com", "1111");
        String requestJson = gson.toJson(requestPost);

        MemberInfoResponseDto response = new MemberInfoResponseDto(1L, "김민호", "godalsgh@gmail.com",createdAt);

        when(mapper.memberPostDtoToMember(any())).thenReturn(new Member());
        when(memberService.createMember(any())).thenReturn(new Member());
        when(mapper.memberToMemberInfoResponse(any())).thenReturn(response);
        //when

        ResultActions actions = mockMvc.perform(post("/members")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .with(csrf()));
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
                                        fieldWithPath("displayName").type(JsonFieldType.STRING).description("이름").optional(),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("displayName").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("createdAt").type(JsonFieldType.STRING).description("회원가입 시간")
                                )
                )
                ));
    }
    @Test
    @DisplayName("멤버 수정테스트")
    public void patchMemberTest() throws Exception {
        //given
        MemberPatchDto requestPatch = new MemberPatchDto("김민호", "1111");
        String requestJson = gson.toJson(requestPatch);

        long memberId = 1L;
        MemberInfoResponseDto response = new MemberInfoResponseDto(memberId, "김민호", "godalsgh@gmail.com",createdAt);

        when(mapper.memberPatchDtoToMember(any())).thenReturn(new Member());
        when(memberService.updateMember(any())).thenReturn(new Member());
        when(mapper.memberToMemberInfoResponse(any())).thenReturn(response);

        //when
        ResultActions actions = mockMvc.perform(patch("/members/{member-id}",memberId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .with(csrf()));
        //then
        actions
                .andExpect(status().isOk())
//                .andExpect(header().string("Location", is(startsWith("/members"))))
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
                                        fieldWithPath("displayName").type(JsonFieldType.STRING).description("이름").optional(),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("displayName").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("createdAt").type(JsonFieldType.STRING).description("회원가입 시간")
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

        Question question = new Question();
        question.setQuestionId(1L);
        question.setTitle("질문글 제목");
        question.setProblemBody("문제 내용");
        question.setExpectingBody("시도 내용");
        question.setCreatedAt(createdAt);
        question.setModifiedAt(createdAt);
        question.setMember(member);
        question.setAnswers("답변예시");
        question.setViewCount(0);
        question.setVoteCount(0);

        MemberQuestionResponseDto response = new MemberQuestionResponseDto();
        response.setQuestions(List.of(question));
        when(memberService.findMember(anyInt())).thenReturn(new Member());
        when(mapper.memberToMemberQuestionResponse(any())).thenReturn(response);
        //when
        ResultActions actions = mockMvc.perform(
                get("/members/{member-id}/question", member.getMemberId())
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

                        responseFields(
                                List.of(
                                        fieldWithPath("questions[]questionId").type(JsonFieldType.NUMBER).description("내가쓴 질문글 ID"),
                                        fieldWithPath("questions[]title").type(JsonFieldType.STRING).description("질문글 제목"),
                                        fieldWithPath("questions[]problemBody").type(JsonFieldType.STRING).description("문제 내용"),
                                        fieldWithPath("questions[]expectingBody").type(JsonFieldType.STRING).description("시도 내용"),
                                        fieldWithPath("questions[]createdAt").type(JsonFieldType.STRING).description("작성시간"),
                                        fieldWithPath("questions[]modifiedAt").type(JsonFieldType.STRING).description("수정시간"),
                                        fieldWithPath("questions[]viewCount").type(JsonFieldType.NUMBER).description("조회수"),
                                        fieldWithPath("questions[]voteCount").type(JsonFieldType.NUMBER).description("추천수"),
                                        fieldWithPath("questions[]answers").type(JsonFieldType.STRING).description("답변")

                                )
                        )
                ));
    }
    //내가쓴 답변 조회
    @Test
    @DisplayName("내가쓴 답변글 조회 테스트")
    public void getMemberAnswerTest(){
        //given
        //when
        //then
    }
    //내 정보 조회
    @Test
    @DisplayName("내 정보 조회")
    public void getMemberInfoTest(){
        //given
        //when
        //then
    }
    //회원 탈퇴
    @Test
    @DisplayName("회원 탈퇴")
    public void deleteMemberTest(){
        //given
        //when
        //then
    }

}