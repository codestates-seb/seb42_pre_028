package Preproject28.server.AnswerVoteControllerTest;

import Preproject28.server.answer.entity.Answer;
import Preproject28.server.answerVote.controller.AnswerVoteController;
import Preproject28.server.answerVote.entity.AnswerVote;
import Preproject28.server.answerVote.service.AnswerVoteService;
import Preproject28.server.member.service.MemberService;
import Preproject28.server.questionVote.controller.QuestionVoteController;
import Preproject28.server.questionVote.entity.QuestionVote;
import Preproject28.server.questionVote.service.QuestionVoteService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Autowired
    private Gson gson;

    @MockBean
    private AnswerVoteService answerVoteService;

    @MockBean
    private MemberService memberService;

    @Test
    public void AnswerVoteControllerTest() throws Exception {
        given(answerVoteService.answerVoteUp(Mockito.anyLong())).willreturn(new answerVote());
        given(mapper.answerVoteToAnswerRsponseDto.(Mockito.any(AnswerVote.class))).willReturn(response);

        ResultActions actions =
                mockMvc.perform(
                        post("/answer-vote/{question-id}/up",answerId)
                                .with(csrf())
                );

        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("likes").value(response.getLikes()))
                .andDo(document("up-answer",
                        pathParameters(
                                parameterWithName("answer-id").description("답변 식별자")
                        ),
                        responseFields(
                                fieldWithPath("likes").type(JsonFieldType.NUMBER).description("추천 수")
                        )
                ));
    }

    @Test
    public void AnswerVoteControllerTest() throws Exception {
        given(answerVoteService.answerVoteDownPost(Mockito.anyLong())).willreturn(new answerVote());
        given(mapper.answerVoteToAnswerRsponseDto.(Mockito.any(AnswerVote.class))).willReturn(response);

        ResultActions actions =
                mockMvc.perform(
                        post("/answer-vote/{question-id}/up",answerId)
                                .with(csrf())
                );

        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("likes").value(response.getLikes()))
                .andDo(document("down-answer",
                        pathParameters(
                                parameterWithName("answer-id").description("답변 식별자")
                        ),
                        responseFields(
                                fieldWithPath("likes").type(JsonFieldType.NUMBER).description("추천 수")
                        )
                ));
    }


}
