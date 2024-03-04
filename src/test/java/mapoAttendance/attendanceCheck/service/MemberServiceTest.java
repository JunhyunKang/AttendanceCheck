package mapoAttendance.attendanceCheck.service;

import jakarta.persistence.EntityManager;
import mapoAttendance.attendanceCheck.domain.Member;
import mapoAttendance.attendanceCheck.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    EntityManager em;

    @Test
    public void 멤버등록중복체크() {
        Member member = Member.createMember("Kim", "A1234");
        memberService.saveMember(member);
        Member member2 = Member.createMember("Kang", "A1234");
        assertThrows(IllegalStateException.class,
                ()->{memberService.saveMember(member2);},
                "중복 회원코드는 에러가 나야함");

    }

    @Test
    public void 멤버정보수정() {
        Member member = Member.createMember("Kim", "A1234");
        Long memberId = memberService.saveMember(member);
        member.setCode("A4321");
        member.setName("Kang");
        memberService.updateMember(memberId, member);
        Member member1 = memberRepository.findOne(memberId);
        assertThat(member1.getId()).isEqualTo(member.getId());
        assertThat(member1.getCode()).as("수정된 회원번호와 같아야함").isSameAs("A4321");
        assertThat(member1.getName()).as("수정된 이름과 같아야함").isSameAs("Kang");

    }




}