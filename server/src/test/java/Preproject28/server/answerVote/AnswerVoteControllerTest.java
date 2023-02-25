package Preproject28.server.answerVote;

import Preproject28.server.answer.entity.Answer;
import Preproject28.server.answer.service.AnswerService;
import Preproject28.server.answerVote.controller.AnswerVoteController;
import Preproject28.server.answerVote.dto.AnswerVoteResponseDto;
import Preproject28.server.answerVote.entity.AnswerVote;
import Preproject28.server.answerVote.mapper.AnswerVoteMapper;
import Preproject28.server.answerVote.service.AnswerVoteService;
import Preproject28.server.member.entity.Member;
import Preproject28.server.member.service.MemberService;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static Preproject28.server.utils.ApiDocumentUtils.getRequestPreProcessor;
import static Preproject28.server.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AnswerVoteController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@Slf4j
@WithMockUser
public class AnswerVoteControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AnswerVoteService answerVoteService;
    @MockBean
    private AnswerService answerService;
    @MockBean
    private AnswerVoteMapper answerVoteMapper;

    @Test
    @DisplayName("답변글 추천 UP 테스트")
    public void answerVoteUpTest() throws Exception {
        //given
        long answerId = 1;
        Answer answer = new Answer();
        answer.setAnswerId(answerId);
        AnswerVote answerVote = new AnswerVote();
        answerVote.setAnswer(answer);

        AnswerVoteResponseDto response = new AnswerVoteResponseDto(AnswerVote.VoteStatus.UP, answerId, 1L, 1L);

        when(answerVoteService.answerVoteUp(anyLong())).thenReturn(answerVote); // NullPointerException 발생해서 넣어줌
        when(answerService.findAnswer(anyLong())).thenReturn(new Answer());
        when(answerVoteMapper.answerVoteToAnswerResponseDto(any(),any())).thenReturn(response);

        //when
        ResultActions actions =
                mockMvc.perform(
                        post("/answer-vote/{answer-id}/up",answerId)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf())
                );
        //then
        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document(
                        "answer-vote-up",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        responseFields(
                                List.of(
                                        fieldWithPath("data.voteStatus").type(JsonFieldType.STRING).description("내 추천 상태"),
                                        fieldWithPath("data.answerId").type(JsonFieldType.NUMBER).description("(추천한)답변 식별자"),
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("(추천한)회원 식별자"),
                                        fieldWithPath("data.answerVoteTotalCount").type(JsonFieldType.NUMBER).description("답변의 총 추천수")
                                )
                        ))
                );
    }

    @Test
    @DisplayName("답변글 추천 Down 테스트")
    public void answerVoteDownTest() throws Exception {
        //given
        long answerId = 1;
        Answer answer = new Answer();
        answer.setAnswerId(answerId);
        AnswerVote answerVote = new AnswerVote();
        answerVote.setAnswer(answer);

        AnswerVoteResponseDto response = new AnswerVoteResponseDto(AnswerVote.VoteStatus.DOWN, answerId, 1L, 1L);

        when(answerVoteService.answerVoteDown(anyLong())).thenReturn(answerVote); // NullPointerException 발생해서 넣어줌
        when(answerService.findAnswer(anyLong())).thenReturn(new Answer());
        when(answerVoteMapper.answerVoteToAnswerResponseDto(any(),any())).thenReturn(response);

        //when
        ResultActions actions =
                mockMvc.perform(
                        post("/answer-vote/{answer-id}/down",answerId)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf())
                );
        //then
        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document(
                        "answer-vote-down",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        responseFields(
                                List.of(
                                        fieldWithPath("data.voteStatus").type(JsonFieldType.STRING).description("내 추천 상태"),
                                        fieldWithPath("data.answerId").type(JsonFieldType.NUMBER).description("(추천한)답변 식별자"),
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("(추천한)회원 식별자"),
                                        fieldWithPath("data.answerVoteTotalCount").type(JsonFieldType.NUMBER).description("답변의 총 추천수")
                                )
                        ))
                );
    }


}
