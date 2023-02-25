package Preproject28.server.answer;


import Preproject28.server.answer.controller.AnswerController;
import Preproject28.server.answer.dto.AnswerPatchDto;
import Preproject28.server.answer.dto.AnswerPostDto;
import Preproject28.server.answer.dto.AnswerResponseDto;
import Preproject28.server.answer.entity.Answer;
import Preproject28.server.answer.mapper.AnswerMapper;
import Preproject28.server.answer.service.AnswerService;
import Preproject28.server.member.dto.MemberInfoResponseDto;
import Preproject28.server.member.entity.Member;
import Preproject28.server.member.service.MemberService;
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
import com.google.gson.Gson;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
    private AnswerMapper answerMapper;


    LocalDateTime createAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    LocalDateTime modifiedAt = LocalDateTime.of(2023,2,1,1,1);

    public List<String> contentList() {
        List<String> contentList = new ArrayList<>();
        contentList.add("답변글 내용 List 예시 1번");
        contentList.add("답변글 내용 List 예시 2번");
        contentList.add("답변글 내용 List 예시 3번");
        return contentList;
    }
    @Test
    @DisplayName("답변글 등록 테스트")
    public void postAnswerTest() throws Exception{
        AnswerPostDto mockPost = new AnswerPostDto(contentList());

        String content = gson.toJson(mockPost);
        AnswerResponseDto response = new AnswerResponseDto(1L,
                contentList(),
                2,
                createAt,
                modifiedAt,
                Answer.AdoptStatus.FALSE,
                new MemberInfoResponseDto(1L,
                        "홍길동",
                        "ghdrlfehd@gmail.com",
                        createAt));

        when(memberService.findMember(anyInt())).thenReturn(new Member());
        when(answerMapper.answerPostDtoToAnswer(any())).thenReturn(new Answer());
        when(answerService.createAnswer(any())).thenReturn(new Answer());
        when(answerMapper.answerToAnswerResponseDto(any())).thenReturn(response);
        log.info(response.toString());

        ResultActions actions = mockMvc.perform(post("/answer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .with(csrf())
        );

        actions.andExpect(status().isCreated()).andDo(document("post-answer",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestFields(
                        List.of(
                                fieldWithPath("content").type(JsonFieldType.ARRAY).description("답변글 본문(LIST)")
                        )
                ),
                responseFields(
                        List.of(
                                fieldWithPath("data.answerId").type(JsonFieldType.NUMBER).description("답변글 식별자"),
                                fieldWithPath("data.content").type(JsonFieldType.ARRAY).description("답변글 본문(LIST)"),
                                fieldWithPath("data.voteCount").type(JsonFieldType.NUMBER).description("답변글 추천수"),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("답변글 생성 시간"),
                                fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("답변글 수정 시간"),
                                fieldWithPath("data.adoptStatus").type(JsonFieldType.STRING).description("답변글 채택여부"),
                                fieldWithPath("data.member.memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                fieldWithPath("data.member.displayName").type(JsonFieldType.STRING).description("회원 이름"),
                                fieldWithPath("data.member.email").type(JsonFieldType.STRING).description("회원 이메일"),
                                fieldWithPath("data.member.createdAt").type(JsonFieldType.STRING).description("회원가입 시간")
                        )
                ))
        );

    }
    @Test
    @DisplayName("답변글 수정 테스트")
    public void patchAnswerTest() throws Exception{
        AnswerPatchDto answerPatchDto = new AnswerPatchDto(1L,
                contentList());
        AnswerResponseDto response = new AnswerResponseDto(1L,
                contentList(),
                3,
                createAt,
                modifiedAt,
                Answer.AdoptStatus.FALSE,
                new MemberInfoResponseDto(1L,
                        "홍길동",
                        "ghdrlfehd@gmail.com",
                        createAt));
        String patchJson = gson.toJson(answerPatchDto);

        long answerId = 1L;
        when(answerMapper.answerPatchDtoToAnswer(any())).thenReturn(new Answer());
        when(answerService.updateAnswer(any())).thenReturn(new Answer());
        when(answerMapper.answerToAnswerResponseDto(any())).thenReturn(response);
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
                                        fieldWithPath("answerId").type(JsonFieldType.NUMBER).description("답변글 식별자"),
                                        fieldWithPath("content").type(JsonFieldType.ARRAY).description("답변글 본문(LIST)")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data.answerId").type(JsonFieldType.NUMBER).description("답변글 식별자"),
                                        fieldWithPath("data.content").type(JsonFieldType.ARRAY).description("답변글 본문(LIST)"),
                                        fieldWithPath("data.voteCount").type(JsonFieldType.NUMBER).description("답변글 추천수"),
                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("답변글 생성 시간"),
                                        fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("답변글 수정 시간"),
                                        fieldWithPath("data.adoptStatus").type(JsonFieldType.STRING).description("답변글 채택여부"),
                                        fieldWithPath("data.member.memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("data.member.displayName").type(JsonFieldType.STRING).description("회원 이름"),
                                        fieldWithPath("data.member.email").type(JsonFieldType.STRING).description("회원 이메일"),
                                        fieldWithPath("data.member.createdAt").type(JsonFieldType.STRING).description("회원가입 시간")
                                )
                        )
                ));

    }
}
