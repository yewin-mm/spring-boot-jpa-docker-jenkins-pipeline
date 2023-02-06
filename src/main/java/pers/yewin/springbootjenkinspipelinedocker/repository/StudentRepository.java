package pers.yewin.springbootjenkinspipelinedocker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pers.yewin.springbootjenkinspipelinedocker.entity.Student;

import javax.transaction.Transactional;

/**
 * @author: Ye Win
 * @created: 19/12/2022
 * @project: spring-boot-jenkins-pipeline-docker
 * @package: pers.yewin.springbootjenkinspipelinedocker.repository
 */

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query(nativeQuery = true, value = "delete from student s order by id desc limit 1")
    @Modifying
    @Transactional
    void deleteLastRow();

    @Query(nativeQuery = true, value = "select * from student order by id desc limit 1")
    Student getLastRow();
}
