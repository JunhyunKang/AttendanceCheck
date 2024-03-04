package mapoAttendance.attendanceCheck.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mapoAttendance.attendanceCheck.domain.Classes;
import mapoAttendance.attendanceCheck.domain.Member;
import mapoAttendance.attendanceCheck.domain.Registration;
import mapoAttendance.attendanceCheck.service.ClassesService;
import mapoAttendance.attendanceCheck.service.MemberService;
import mapoAttendance.attendanceCheck.service.RegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class RegistrationController {

    private final RegistrationService registrationService;
    private final MemberService memberService;
    private final ClassesService classesService;


    @GetMapping("/classes/{id}/edit")
    public String memberList(@PathVariable("id") Long classId, Model model) {


        Classes classes = classesService.findOne(classId);
        List<Registration> registrations = classes.getRegistrations();
        List<Member> members = new ArrayList<>();
        for (Registration registration : registrations) {
            members.add(registration.getMember());
        }
        model.addAttribute("registrations", registrations);
        model.addAttribute("classId", classId);
        log.info("멤버리스트 뿌리기 위해 해당 수업 registration 정보 모두 넘김");

        return "classes/memberList";


    }


    @PostMapping("/classes/{regiId}/delete")
    public String deleteMember(@PathVariable("regiId") Long registrationId) {
        registrationService.cancelRegistration(registrationId);
        log.info("registration 취소 하고 db에서 삭제");

        return "redirect:/classes";
    }


    @GetMapping("/classes/{id}/edit/registration")
    public String createForm(@PathVariable("id") Long classId, Model model) {
        MemberForm memberForm = new MemberForm();
        model.addAttribute("form", memberForm);
        return "classes/updateClassMembers";
    }

    @PostMapping("/classes/{id}/edit/registration")
    public String registerMember(@PathVariable("id") Long classId, MemberForm form, Model model) {
        Member member = Member.createMember(form.getName(), form.getCode());
        Long memberId = memberService.saveMember(member);
        log.info("회원번호 중복인 것은 이미 있던 id 돌려줌");

        Long regiId = registrationService.register(memberId, classId);
        log.info("해당 수업에 이미 존재하는 회웤번호는 에러, 한 회원번호가 여러 수업으로 들어갈 수는 있음");


        List<Registration> registrations = classesService.findOne(classId).getRegistrations();

        model.addAttribute("registrations", registrations);
        model.addAttribute("classId", classId);
        return "classes/memberList";
    }


}
