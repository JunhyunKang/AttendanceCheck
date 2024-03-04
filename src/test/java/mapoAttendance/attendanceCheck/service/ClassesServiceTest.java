package mapoAttendance.attendanceCheck.service;

import ch.qos.logback.core.encoder.EchoEncoder;
import jakarta.persistence.EntityManager;
import mapoAttendance.attendanceCheck.domain.Classes;
import mapoAttendance.attendanceCheck.domain.Member;
import mapoAttendance.attendanceCheck.domain.Registration;
import mapoAttendance.attendanceCheck.repository.ClassesRepository;
import mapoAttendance.attendanceCheck.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ClassesServiceTest {

    @Autowired
    ClassesService classesService;
    @Autowired
    ClassesRepository classesRepository;
    @Autowired
    EntityManager em;

    @Test
//    @Rollback(value = false)
    public void 과목등록() throws Exception {
        Classes classes = Classes.createClasses("AAA", 50l);
        Classes classes2 = Classes.createClasses("BBB", 30l);
        Long id1 = classesService.join(classes);
        Long id2 = classesService.join(classes2);

        assertThat(classesService.findOne(id2)).isEqualTo(classes2);



    }


    @Test
    public void 과목전체조회() throws Exception {
        Classes classes = Classes.createClasses("AAA", 50l);
        Classes classes2 = Classes.createClasses("BBB", 30l);
        Long id1 = classesService.join(classes);
        Long id2 = classesService.join(classes2);
        List<Classes> find = classesService.findClasses();
        assertThat(find.get(1).getId()).isEqualTo(classes2.getId());

    }

}