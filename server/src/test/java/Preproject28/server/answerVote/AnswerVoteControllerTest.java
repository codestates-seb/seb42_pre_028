package Preproject28.server.answerVote;

import Preproject28.server.answerVote.controller.AnswerVoteController;
import Preproject28.server.answerVote.entity.AnswerVote;
import Preproject28.server.answerVote.service.AnswerVoteService;
import Preproject28.server.member.service.MemberService;
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

import java.util.List;

import static Preproject28.server.utils.ApiDocumentUtils.getRequestPreProcessor;
import static Preproject28.server.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
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

    @Autowired
    private Gson gson;

    @MockBean
    private AnswerVoteService answerVoteService;


    @Test
    public void answerVoteControllerTest() throws Exception {
        //given
        long answerId = 1;

        //when
        ResultActions actions =
                mockMvc.perform(
                        post("/answer-vote/{answer-id}/up",answerId)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf())
                );
        //then
        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document(
                        "answer-vote-up",
                        getRequestPreProcessor(),
                        getResponsePreProcessor()
                ));
    }


}
