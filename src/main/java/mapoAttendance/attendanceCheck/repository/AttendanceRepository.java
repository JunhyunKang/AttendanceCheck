package mapoAttendance.attendanceCheck.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import mapoAttendance.attendanceCheck.domain.Attendance;
import mapoAttendance.attendanceCheck.domain.Classes;
import mapoAttendance.attendanceCheck.domain.Member;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AttendanceRepository {
    private final EntityManager em;

    @Transactional
    public void save(Attendance attendance) {
        em.persist(attendance);

    }

    public Attendance findOne(Long id) {
        return em.find(Attendance.class, id);
    }


    public List<Attendance> findAll() {
        return em.createQuery("select a from Attendance a", Attendance.class).getResultList();
    }

    public void delete(Attendance attendance){
        em.remove(attendance);}

}
