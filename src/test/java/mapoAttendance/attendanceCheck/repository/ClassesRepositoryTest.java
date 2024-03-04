//package mapoAttendance.attendanceCheck.repository;
//
//import jakarta.persistence.EntityManager;
//import mapoAttendance.attendanceCheck.domain.Classes;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class ClassesRepositoryTest {
//
//    @Test
//    public void classRepoTest() {
//
//        EntityManager entityManager;
//
//
//        Classes classes = Classes.createClasses("111111", 50L);
//        ClassesRepository classesRepository = new ClassesRepository();
//        classesRepository.save(classes);
//
//    }
//}