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

import static Preproject28.server.utils.ApiDocumentUtils.getRequestPreProcessor;
import static Preproject28.server.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
    @DisplayName("???????????? ?????????")
    public void postMemberTest() throws Exception {
        //given
        MemberPostDto requestPost = new MemberPostDto("?????????", "godalsgh@gmail.com", "1111");
        String requestJson = gson.toJson(requestPost);

        MemberInfoResponseDto response = new MemberInfoResponseDto(1L, "?????????", "godalsgh@gmail.com",createdAt);

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
                                        fieldWithPath("displayName").type(JsonFieldType.STRING).description("??????").optional(),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("?????????"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("????????????")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("displayName").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("?????????"),
                                        fieldWithPath("createdAt").type(JsonFieldType.STRING).description("???????????? ??????")
                                )
                )
                ));
    }
    @Test
    @DisplayName("?????? ???????????????")
    public void patchMemberTest() throws Exception {
        //given
        MemberPatchDto requestPatch = new MemberPatchDto("?????????", "1111");
        String requestJson = gson.toJson(requestPatch);

        long memberId = 1L;
        MemberInfoResponseDto response = new MemberInfoResponseDto(memberId, "?????????", "godalsgh@gmail.com",createdAt);

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
                        requestFields(
                                List.of(
                                        fieldWithPath("displayName").type(JsonFieldType.STRING).description("??????").optional(),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("????????????")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("?????????"),
                                        fieldWithPath("displayName").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("createdAt").type(JsonFieldType.STRING).description("???????????? ??????")
                                )
                        )
                ));
    }

    //????????? ??? ??????
    @Test
    @DisplayName("????????? ????????? ?????? ?????????")
    public void getMemberQuestionTest() throws Exception {
        //given
        Member member = new Member();
        member.setMemberId(1L);

        Question question = new Question();
        question.setQuestionId(1L);
        question.setTitle("????????? ??????");
        question.setProblemBody("?????? ??????");
        question.setExpectingBody("?????? ??????");
        question.setCreatedAt(createdAt);
        question.setModifiedAt(createdAt);
        question.setMember(member);
        question.setAnswers("????????????");
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
                                        fieldWithPath("questions[]questionId").type(JsonFieldType.NUMBER).description("????????? ????????? ID"),
                                        fieldWithPath("questions[]title").type(JsonFieldType.STRING).description("????????? ??????"),
                                        fieldWithPath("questions[]problemBody").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("questions[]expectingBody").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("questions[]createdAt").type(JsonFieldType.STRING).description("????????????"),
                                        fieldWithPath("questions[]modifiedAt").type(JsonFieldType.STRING).description("????????????"),
                                        fieldWithPath("questions[]viewCount").type(JsonFieldType.NUMBER).description("?????????"),
                                        fieldWithPath("questions[]voteCount").type(JsonFieldType.NUMBER).description("?????????"),
                                        fieldWithPath("questions[]answers").type(JsonFieldType.STRING).description("??????")
                                )
                        )
                ));
    }
    //????????? ?????? ??????
    @Test
    @DisplayName("????????? ????????? ?????? ?????????")
    public void getMemberAnswerTest(){
        //given
        //when
        //then
    }
    //??? ?????? ??????
    @Test
    @DisplayName("??? ?????? ??????")
    public void getMemberInfoTest() throws Exception {
        //given
        MemberPostDto requestPost = new MemberPostDto("?????????", "godalsgh@gmail.com", "1111");
        String requestJson = gson.toJson(requestPost);
        long memberId = 1;
        MemberInfoResponseDto response = new MemberInfoResponseDto(1L, "?????????", "godalsgh@gmail.com",createdAt);

        when(memberService.findMember(anyInt())).thenReturn(new Member());
        when(mapper.memberToMemberInfoResponse(any())).thenReturn(response);
        //when
        ResultActions actions = mockMvc.perform(
                get("/members/{member-id}/info", memberId)
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

                        responseFields(
                                List.of(
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("displayName").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("?????????"),
                                        fieldWithPath("createdAt").type(JsonFieldType.STRING).description("???????????? ??????")
                                )
                        )
                ));
    }
    //?????? ??????
    @Test
    @DisplayName("?????? ??????")
    public void deleteMemberTest() throws Exception {
        //given
        long memberId = 1;
        boolean deleteStatus = true;
        when(memberService.deleteMember(anyLong())).thenReturn(deleteStatus);
        //when
        ResultActions actions = mockMvc.perform(
                delete("/members/{member-id}/", memberId)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()));
        //then
        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document(
                        "delete-member",
                        getRequestPreProcessor(),
                        getResponsePreProcessor()
                        ));
    }

}