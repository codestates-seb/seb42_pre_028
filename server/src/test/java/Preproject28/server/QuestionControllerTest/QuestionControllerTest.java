package Preproject28.server.QuestionControllerTest;

import Preproject28.server.question.dto.QuestionPostDto;
import Preproject28.server.question.mapper.QuestionMapper;
import Preproject28.server.question.service.QuestionService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.ZoneId;

@WebMvcTest(QuestionControllerTest.class)
@Slf4j
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class QuestionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private QuestionService questionService;
    @MockBean
    private QuestionMapper mapper;
    @Autowired
    private Gson gson;

    LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    LocalDateTime modified = LocalDateTime.of(2023,02,1,1,1);
    @Test
    @DisplayName("PostQuestionTest")
    public void postQuestionTest() throws Exception{
//        QuestionPostDto mockPost = new QuestionPostDto(1L,"질문 1", "문제 바디","에상 바디",now, modified,1,2,1L,"대답부분-> 리스트로 대체");

    }




}
