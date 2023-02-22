package Preproject28.server.Answer;


import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;

@WebMvcTest(controllers = AnswerControllerTest.class)
@MockBean(JpaMetamodelMappingContext.class)
public class AnswerControllerTest {
}
