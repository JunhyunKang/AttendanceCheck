package mapoAttendance.attendanceCheck.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
public class DeleteAll {
    private final EntityManager em;

    @Transactional
    public void delete(){
        final Query query1 = em.createQuery("DELETE Registration");
        final Query query2 = em.createQuery("DELETE Attendance");
        final Query query3 = em.createQuery("DELETE Classes ");
        final Query query4 = em.createQuery("DELETE Member ");

        query1.executeUpdate();
        query2.executeUpdate();
        query3.executeUpdate();
        query4.executeUpdate();
//
//        Stream.of(query1, query2, query3, query4)
//                .forEach(Query::executeUpdate);
    }
}
