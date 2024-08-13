package mapoAttendance.attendanceCheck.service;

import lombok.RequiredArgsConstructor;
import mapoAttendance.attendanceCheck.domain.Attendance;
import mapoAttendance.attendanceCheck.domain.Classes;
import mapoAttendance.attendanceCheck.domain.Registration;
import mapoAttendance.attendanceCheck.repository.AttendanceRepository;
import mapoAttendance.attendanceCheck.repository.ClassesRepository;
import mapoAttendance.attendanceCheck.repository.RegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClassesService {

    private final ClassesRepository classesRepository;
    private final AttendanceRepository attendanceRepository;
    private final RegistrationRepository registrationRepository;

    @Transactional // -> 예는 등록하는 애라 readOnly 하면 안
    public Long join(Classes classes) {
        validateDuplicateClasses(classes); //증복과목 검사
        classesRepository.save(classes);
        return classes.getId();
    }

    private void validateDuplicateClasses(Classes classes) {
        List<Classes> findClasses = classesRepository.findByName(classes.getName());
        if (!findClasses.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 수업입니다.");
        }
    }

    public List<Classes> findClasses() {

        return classesRepository.findAll();
    }

    public Classes findOne(Long classesId) {
        return classesRepository.findOne(classesId);
    }

    @Transactional
    public void deleteClass(Long classId) {
        classesRepository.delete(classId);



    }


}
