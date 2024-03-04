package mapoAttendance.attendanceCheck.service;

import lombok.RequiredArgsConstructor;
import mapoAttendance.attendanceCheck.domain.Classes;
import mapoAttendance.attendanceCheck.domain.Member;
import mapoAttendance.attendanceCheck.domain.Registration;
import mapoAttendance.attendanceCheck.repository.ClassesRepository;
import mapoAttendance.attendanceCheck.repository.MemberRepository;
import mapoAttendance.attendanceCheck.repository.RegistrationRepository;
import mapoAttendance.attendanceCheck.repository.RegistrationSearch;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final ClassesRepository classesRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long register(Long memberId, Long classId) {
        Member member = memberRepository.findOne(memberId);
        Classes classes = classesRepository.findOne(classId);

        findDuplicateClassMember(classes, member);
        Registration registration = Registration.createRegistration(classes, member);

        registrationRepository.save(registration);

        return registration.getId();
    }
    private void findDuplicateClassMember(Classes classes, Member member) {
        List<Registration> registrations = classes.getRegistrations();
        for (Registration registration : registrations) {
            if(member.getCode() == registration.getMember().getCode()){
                throw new IllegalStateException("이미 존재하는 회원입니다.");

            }
        }
    }


    @Transactional
    public void cancelRegistration(Long registrationId) {
        Registration registration = registrationRepository.findOne(registrationId);
        registration.cancel();
        registrationRepository.delete(registration);

    }




    public Registration findOne(Long registrationId) {
        return registrationRepository.findOne(registrationId);
    }

    public List<Registration> findRegistrations(RegistrationSearch rs){
        return registrationRepository.findAllByCriteria(rs);
    }
}
