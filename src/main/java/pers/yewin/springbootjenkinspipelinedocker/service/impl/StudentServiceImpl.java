package pers.yewin.springbootjenkinspipelinedocker.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.yewin.springbootjenkinspipelinedocker.entity.Student;
import pers.yewin.springbootjenkinspipelinedocker.repository.StudentRepository;
import pers.yewin.springbootjenkinspipelinedocker.service.StudentService;

import java.util.List;
import java.util.Optional;

/**
 * @author: Ye Win
 * @created: 19/12/2022
 * @project: spring-boot-jenkins-pipeline-docker
 * @package: pers.yewin.springbootjenkinspipelinedocker.service.impl
 */

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Student addStudent(Student student) {
        student = studentRepository.save(student);
        return student;
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public Optional<Student> findById(long id) {
        return studentRepository.findById(id);
    }
}
