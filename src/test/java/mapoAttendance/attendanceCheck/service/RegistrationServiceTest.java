package mapoAttendance.attendanceCheck.service;

import jakarta.persistence.EntityManager;
import mapoAttendance.attendanceCheck.domain.Classes;
import mapoAttendance.attendanceCheck.domain.Member;
import mapoAttendance.attendanceCheck.domain.Registration;
import mapoAttendance.attendanceCheck.repository.MemberRepository;
import mapoAttendance.attendanceCheck.repository.RegistrationRepository;
import mapoAttendance.attendanceCheck.repository.RegistrationSearch;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.parser.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class RegistrationServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    RegistrationRepository registrationRepository;
    @Autowired
    RegistrationService registrationService;


    @Test
    public void 수강신청() throws Exception {
        Classes classes = Classes.createClasses("AAA", 50l);
        em.persist(classes);
        Member member = Member.createMember("Kim", "A1234");
        em.persist(member);
        Long registerId = registrationService.register(member.getId(), classes.getId());

        Registration registration = registrationRepository.findOne(registerId);

        assertThat(registerId).as("registration id와 같아야함").isEqualTo(registration.getId());
        assertThat(member.getId()).as("멤버 id가 같아야함").isEqualTo(registration.getMember().getId());
        assertThat(classes.getId()).as("classId가 같아야함").isEqualTo(registration.getClasses().getId());
        assertThat(classes.getRegistrations().get(0).getId()).as("등록 id가 같아야함").isEqualTo(registration.getId());
        assertThat(member.getRegistrations().get(0).getId()).as("등록 id가 같아야함").isEqualTo(registration.getId());

    }

    @Test
    public void 수강조회() throws Exception {

        Classes classes = Classes.createClasses("AAA", 50l);
        em.persist(classes);
        Member member = Member.createMember("Kim", "A1234");
        em.persist(member);
        Long registerId = registrationService.register(member.getId(), classes.getId());
        RegistrationSearch rs = new RegistrationSearch();
        rs.setMemberName("Kim");
        List<Registration> r = registrationService.findRegistrations(rs);
        assertThat(r.get(0).getMember().getId()).as("멤버 id와 같아야함").isEqualTo(member.getId());

    }


    @Test
    public void 수강취소() throws Exception {

        Classes classes = Classes.createClasses("AAA", 50l);
        em.persist(classes);
        Member member = Member.createMember("Kim", "A1234");
        em.persist(member);
        Member member2 = Member.createMember("Kang", "A2322");
        em.persist(member2);
        Member member3 = Member.createMember("Yoo", "A2425");
        em.persist(member3);

        Long registerId1 = registrationService.register(member.getId(), classes.getId());
        Long registerId2 = registrationService.register(member2.getId(), classes.getId());
        Long registerId3 = registrationService.register(member3.getId(), classes.getId());


        registrationService.cancelRegistration(registerId2);
        List<Registration> finds = classes.getRegistrations();
        assertThat((finds.get(1)).getId()).isEqualTo(member3.getId());
        assertThrows(IndexOutOfBoundsException.class,
                ()->{member2.getRegistrations().get(0);},
                "수강목록에서 취소되어야함");
    }


}