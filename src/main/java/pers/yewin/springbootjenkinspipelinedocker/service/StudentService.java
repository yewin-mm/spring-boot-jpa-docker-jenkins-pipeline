package pers.yewin.springbootjenkinspipelinedocker.service;

import pers.yewin.springbootjenkinspipelinedocker.entity.Student;

import java.util.List;
import java.util.Optional;

/**
 * @author: Ye Win
 * @created: 19/12/2022
 * @project: spring-boot-jenkins-pipeline-docker
 * @package: pers.yewin.springbootjenkinspipelinedocker.service
 */

public interface StudentService {

    Student addStudent(Student student);
    List<Student> findAll();
    Optional<Student> findById(long id);

    /** can have other methods */

}
