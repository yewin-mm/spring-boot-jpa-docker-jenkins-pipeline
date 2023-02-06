package pers.yewin.springbootjenkinspipelinedocker;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pers.yewin.springbootjenkinspipelinedocker.entity.Student;
import pers.yewin.springbootjenkinspipelinedocker.service.StudentService;
import pers.yewin.springbootjenkinspipelinedocker.util.JsonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author: Ye Win
 * @created: 19/12/2022
 * @project: spring-boot-jenkins-pipeline-docker
 * @package: pers.yewin.springbootjenkinspipelinedocker
 */

@WebMvcTest
public class StudentApplicationUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean // tell spring context to student service as mock bean
    private StudentService studentService;



    @Test
    public void testPostExample() throws Exception {
        Student student = new Student(1L, "Ye Win", "+959252656065");

        Mockito.when(studentService.addStudent(ArgumentMatchers.any())).thenReturn(student);

        // another sample
        /*given(studentService.addStudent(any(Student.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));*/

        mockMvc.perform(post("/addStudent").contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(student))).andExpect(status().isCreated());
                /*.andExpect(jsonPath("$.id", Matchers.equalTo(1)))
                .andExpect(jsonPath("$.name", Matchers.equalTo("Ye Win")))
                .andExpect(jsonPath("$.phoneNo", Matchers.equalTo("+959252656065")));*/


        // another sample
        // when - we are going to test acton
        /*ResultActions response = mockMvc.perform(post("/addStudent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(student)));*/

        // then - verify the status
        /*response.andDo(print()).
                andExpect(status().isCreated());*/
    }

    @Test
    public void testFindAllStudent() throws Exception {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1L, "Ye Win", "+959252656065"));
        students.add(new Student(2L, "Mr. Ye Win", "09252656065"));

        Mockito.when(studentService.findAll()).thenReturn(students);
        mockMvc.perform(get("/findAllStudent")).andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].name", Matchers.equalTo("Ye Win")))
                .andExpect(jsonPath("$[0].phoneNo", Matchers.equalTo("+959252656065")))
                .andExpect(jsonPath("$[1].name", Matchers.equalTo("Mr. Ye Win")))
                .andExpect(jsonPath("$[1].phoneNo", Matchers.equalTo("09252656065")));
    }


    @Test
    public void testFindByIdStudent() throws Exception {
        Student student = Student.builder().
                id(1L).name("Ye Win").phoneNo("+959252656065").build();

        Mockito.when(studentService.findById(1)).thenReturn(Optional.ofNullable(student));
        mockMvc.perform(get("/findById/1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.equalTo("Ye Win")))
                .andExpect(jsonPath("$.phoneNo", Matchers.equalTo("+959252656065")));
    }
}
