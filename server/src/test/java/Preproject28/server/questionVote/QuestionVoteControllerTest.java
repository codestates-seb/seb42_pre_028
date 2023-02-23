package Preproject28.server.questionVote;

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
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static Preproject28.server.utils.ApiDocumentUtils.getRequestPreProcessor;
import static Preproject28.server.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = QuestionVoteController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@Slf4j
@WithMockUser
public class QuestionVoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private QuestionVoteService questionVoteService;

    @MockBean
    private MemberService memberService;

    @Test
    public void  questionVoteControllerTest() throws Exception {
        //given
        long questionId = 1;

        //when
        ResultActions actions =
                mockMvc.perform(
                        post("/question-vote/{question-id}/up",questionId)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf())
                );
        //then
        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document(
                        "question-vote-up",
                        getRequestPreProcessor(),
                        getResponsePreProcessor()
                ));
    }
//
//    @Test
//    public void QuestionVoteControllerTest() throws Exception {
//        given(questionVoteService.questionVoteUp(Mockito.anyLong())).willreturn(new questionVote());
//        given(mapper.questionVoteToQuestionRsponseDto(Mockito.any(QuestionVote.class))).willReturn(response);
//
//        ResultActions actions =
//                mockMvc.perform(
//                        post("/question/vote/{question-id}/up",questionId)
//                                .with(csrf())
//                );
//
//        actions
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("likes").value(response.getLikes()))
//                .andDo(document("up-question",
//                        pathParameters(
//                                parameterWithName("question-id").description("질문 식별자")
//                        ),
//                        responseFields(
//                                fieldWithPath("likes").type(JsonFieldType.NUMBER).description("추천 수")
//                        )
//                        ));
//    }
//
//    @Test
//    public void QuestionVoteControllerTest() throws Exception {
//        given(questionVoteService.questionVoteUp(Mockito.anyLong())).willreturn(new questionVoteUp());
//        given(mapper.questionVoteToQuestionRsponseDto(Mockito.any(QuestionVote.class))).willReturn(response);
//
//        ResultActions actions =
//                mockMvc.perform(
//                        post("/question/vote/{question-id}/down",questionId)
//                                .with(csrf())
//                );
//
//        actions
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("likes").value(response.getLikes()))
//                .andDo(document("down-question",
//                        pathParameters(
//                                parameterWithName("question-id").description("질문 식별자")
//                        ),
//                        responseFields(
//                                fieldWithPath("likes").type(JsonFieldType.NUMBER).description("추천 수")
//                        )
//                ));
//    }



}
