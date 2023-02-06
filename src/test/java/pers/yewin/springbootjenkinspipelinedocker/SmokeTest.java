package pers.yewin.springbootjenkinspipelinedocker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pers.yewin.springbootjenkinspipelinedocker.controller.StudentController;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author: Ye Win
 * @created: 19/12/2022
 * @project: spring-boot-jenkins-pipeline-docker
 * @package: pers.yewin.springbootjenkinspipelinedocker
 */

@SpringBootTest
public class SmokeTest {

    @Autowired
    private StudentController controller;

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }
}
