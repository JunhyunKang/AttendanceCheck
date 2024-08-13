package mapoAttendance.attendanceCheck.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import mapoAttendance.attendanceCheck.domain.Classes;
import mapoAttendance.attendanceCheck.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;

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

    public void delete(Long classId){
        final Query query1 = em.createQuery("DELETE Registration r WHERE r.classes.id = :classId");
        final Query query2 = em.createQuery("DELETE Attendance a WHERE a.classes.id = :classId");
        final Query query3 = em.createQuery("DELETE Classes c WHERE c.id = :classId");

        Stream.of(query1, query2, query3)
                .map(query -> query.setParameter("classId", classId))
                .forEach(Query::executeUpdate);
    }


}
