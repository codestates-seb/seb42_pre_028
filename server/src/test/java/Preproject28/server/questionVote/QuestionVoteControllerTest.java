package Preproject28.server.questionVote;

import Preproject28.server.question.entity.Question;
import Preproject28.server.question.service.QuestionService;
import Preproject28.server.questionVote.controller.QuestionVoteController;
import Preproject28.server.questionVote.dto.QuestionVoteResponseDto;
import Preproject28.server.questionVote.entity.QuestionVote;
import Preproject28.server.questionVote.mapper.QuestionVoteMapper;
import Preproject28.server.questionVote.service.QuestionVoteService;
import Preproject28.server.utils.SecurityTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static Preproject28.server.utils.ApiDocumentUtils.getRequestPreProcessor;
import static Preproject28.server.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = QuestionVoteController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@Slf4j
@WithMockUser
@Import(SecurityTestConfig.class)
public class QuestionVoteControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private QuestionVoteService questionVoteService;
    @MockBean
    private QuestionService questionService;
    @MockBean
    private QuestionVoteMapper questionVoteMapper;
    @Test
    @DisplayName("질문글 추천 UP 테스트")
    public void  questionVoteUpTest() throws Exception {
        //given
        long questionId = 1;
        Question question = new Question();
        question.setQuestionId(questionId);
        QuestionVote questionVote = new QuestionVote();
        questionVote.setQuestion(question);

        QuestionVoteResponseDto response = new QuestionVoteResponseDto(QuestionVote.VoteStatus.UP, questionId, 1L, 1L);

        when(questionVoteService.questionVoteUp(anyLong())).thenReturn(questionVote); // NullPointerException 발생해서 넣어줌
        when(questionService.findQuestion(anyLong())).thenReturn(new Question());
        when(questionVoteMapper.questionVoteToQuestionResponseDto(any(),any())).thenReturn(response);

        //when
        ResultActions actions =
                mockMvc.perform(
                        post("/question-vote/{question-id}/up",questionId)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                );
        //then
        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("question-vote-up-address",
                        pathParameters(
                                parameterWithName("question-id").description("질문글 식별자")
                        )
                ))
                .andDo(document(
                        "question-vote-up",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        responseFields(
                                List.of(
                                        fieldWithPath("data.voteStatus").type(JsonFieldType.STRING).description("내 추천 상태"),
                                        fieldWithPath("data.questionId").type(JsonFieldType.NUMBER).description("(추천한)질문글 식별자"),
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("(추천한)회원 식별자"),
                                        fieldWithPath("data.questionVoteTotalCount").type(JsonFieldType.NUMBER).description("질문의 총 추천수")
                                )
                        ))
                );
    }
    @Test
    @DisplayName("질문글 추천 Down 테스트")
    public void  questionVoteDownTest() throws Exception {
        //given
        long questionId = 1;
        Question question = new Question();
        question.setQuestionId(questionId);
        QuestionVote questionVote = new QuestionVote();
        questionVote.setQuestion(question);

        QuestionVoteResponseDto response = new QuestionVoteResponseDto(QuestionVote.VoteStatus.DOWN, questionId, 1L, 1L);

        when(questionVoteService.questionVoteDown(anyLong())).thenReturn(questionVote); // NullPointerException 발생해서 넣어줌
        when(questionService.findQuestion(anyLong())).thenReturn(new Question());
        when(questionVoteMapper.questionVoteToQuestionResponseDto(any(), any())).thenReturn(response);

        //when
        ResultActions actions =
                mockMvc.perform(
                        post("/question-vote/{question-id}/down", questionId)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                );
        //then
        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("question-vote-down-address",
                        pathParameters(
                                parameterWithName("question-id").description("질문글 식별자")
                        )
                ))
                .andDo(document(
                        "question-vote-down",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        responseFields(
                                List.of(
                                        fieldWithPath("data.voteStatus").type(JsonFieldType.STRING).description("내 추천 상태"),
                                        fieldWithPath("data.questionId").type(JsonFieldType.NUMBER).description("(추천한)질문글 식별자"),
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("(추천한)회원 식별자"),
                                        fieldWithPath("data.questionVoteTotalCount").type(JsonFieldType.NUMBER).description("질문의 총 추천수")
                                )
                        ))
                );
    }
}
