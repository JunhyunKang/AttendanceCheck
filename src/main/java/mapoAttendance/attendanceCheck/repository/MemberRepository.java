package mapoAttendance.attendanceCheck.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import mapoAttendance.attendanceCheck.domain.Member;
import mapoAttendance.attendanceCheck.domain.Registration;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;
    public Long save(Member member) {
        if (member.getId() == null) {
            em.persist(member);
        } else {
            em.merge(member);
        }
        return member.getId();

    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();    }

    public List<Member> findByCode(String code) {
        return em.createQuery("select m from Member m where m.code = :code", Member.class)
                .setParameter("code", code)
                .getResultList();
    }
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

}
