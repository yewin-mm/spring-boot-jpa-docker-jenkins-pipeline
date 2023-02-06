package pers.yewin.springbootjenkinspipelinedocker;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pers.yewin.springbootjenkinspipelinedocker.entity.Student;
import pers.yewin.springbootjenkinspipelinedocker.repository.StudentRepository;
import pers.yewin.springbootjenkinspipelinedocker.util.JsonUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @author: Ye Win
 * @created: 19/12/2022
 * @project: spring-boot-jenkins-pipeline-docker
 * @package: pers.yewin.springbootjenkinspipelinedocker
 */


//@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SpringBootJenkinsPipelineDockerApplication.class)
@AutoConfigureMockMvc
public class StudentApplicationIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private StudentRepository studentRepository;

    @AfterEach
    public void resetDb() {
        studentRepository.deleteLastRow();
    }

    @Test
    public void whenCreateEmployee_thenCheckData() throws Exception {
        String name = "Mr.Ye Win";
        Student student = Student.builder()
                            .name("Mr.Ye Win")
                            .phoneNo("+959252656065").build();

        mvc.perform(post("/addStudent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(student)));

        Student found = studentRepository.getLastRow();
        assertEquals(found.getName(), name);
    }
}
