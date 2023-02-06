package pers.yewin.springbootjenkinspipelinedocker.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pers.yewin.springbootjenkinspipelinedocker.entity.Student;
import pers.yewin.springbootjenkinspipelinedocker.service.StudentService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * @author: Ye Win
 * @created: 19/12/2022
 * @project: spring-boot-jenkins-pipeline-docker
 * @package: pers.yewin.springbootjenkinspipelinedocker.controller
 */

@RestController
@Slf4j
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/addStudent")
    public ResponseEntity<Student> addStudent(@RequestBody Student student){
        try{
            log.info("Request data: {}", student);
            student = studentService.addStudent(student);
            log.info("Response data: {}", student);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(
                    "/{id}").buildAndExpand(student.getId()).toUri();
            return ResponseEntity.created(location).build();

        }catch (Exception e){
            log.error("Error: {}", e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }

    }

    @GetMapping("/findAllStudent")
    public ResponseEntity<List<Student>> findAllStudent(){
        try{
            List<Student> studentList = studentService.findAll();
            log.info("Response data: {}", studentList);
            return ResponseEntity.ok(studentList);

        }catch (Exception e){
            log.error("Error: {}", e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }

    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Student> addStudent(@PathVariable ("id") long id){
        try{
            log.info("Request data, id: {}", id);
            Optional<Student> opStudent = studentService.findById(id);

            log.info("Response data: {}", opStudent);
            return opStudent.map(ResponseEntity::ok).orElseGet( () -> ResponseEntity.notFound().build());

        }catch (Exception e){
            log.error("Error: {}", e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }

    }

    /** can have other methods */
}
