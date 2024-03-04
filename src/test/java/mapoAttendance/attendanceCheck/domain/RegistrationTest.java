package mapoAttendance.attendanceCheck.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class RegistrationTest {

    Classes classes = new Classes();
    Member member = new Member();

    @Test
    public void 도메인_테스트() throws Exception {
        classes.setName("classA");
        classes.setNumber(50L);
        member.setCode("A1234");
        member.setName("Kim");
        Registration registration = Registration.createRegistration(classes, member);
        assertThat(classes.getNumber()).isEqualTo(49L);
        assertThat(registration.getMember().getName()).isEqualTo("Kim");
        assertThat(registration.getClasses().getName()).isEqualTo("classA");
        assertThat(classes.getRegistrations().get(0)).isEqualTo(registration);


        Member member1 = new Member();
        member1.setName("Hong");
        member1.setCode("A4321");
        Registration registration1 = Registration.createRegistration(classes, member1);

        registration.cancel();

        assertThat(classes.getNumber()).isEqualTo(49L);
        Registration r = classes.getRegistrations().get(0);
        assertThat(r.getMember().getName()).isEqualTo("Hong");

//        org.junit.jupiter.api.Assertions.assertThrows(IndexOutOfBoundsException.class,
//                () -> {classes.getRegistrations().get(0);}
//                ,"존재하는 멤버 사라져야함");
    }
}