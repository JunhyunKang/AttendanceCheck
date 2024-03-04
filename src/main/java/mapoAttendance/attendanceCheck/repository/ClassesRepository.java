package mapoAttendance.attendanceCheck.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import mapoAttendance.attendanceCheck.domain.Classes;
import mapoAttendance.attendanceCheck.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ClassesRepository {
    //EntityManager은 autoWired로 의존관계 인젝션 안 되고 persistenceContext로 인젝션 된다.
    private final EntityManager em;

    public void save(Classes classes) {
        em.persist(classes);

    }

    public Classes findOne(Long id) {
        return em.find(Classes.class, id);
    }

    public List<Classes> findAll() {
        return em.createQuery("select c from Classes c", Classes.class).getResultList();

    }

    public List<Classes> findByName(String name) {
        return em.createQuery("select c from Classes c where c.name = :name", Classes.class)
                .setParameter("name", name)
                .getResultList();
    }

    public void delete(Classes classes){
        em.remove(classes);}
}
