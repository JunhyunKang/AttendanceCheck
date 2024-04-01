package mapoAttendance.attendanceCheck.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mapoAttendance.attendanceCheck.domain.Attendance;
import mapoAttendance.attendanceCheck.domain.Classes;
import mapoAttendance.attendanceCheck.domain.Member;
import mapoAttendance.attendanceCheck.domain.Registration;
import mapoAttendance.attendanceCheck.repository.AttendanceRepository;
import mapoAttendance.attendanceCheck.service.ClassesService;
import mapoAttendance.attendanceCheck.service.MemberService;
import mapoAttendance.attendanceCheck.service.RegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class QrController {

    private final RegistrationService registrationService;
    private final MemberService memberService;
    private final ClassesService classesService;
    private final AttendanceRepository attendanceRepository;

    //    @GetMapping("/classes/new")
//    public String createForm(Model model) {
//        model.addAttribute("form", new ClassesForm());
//        return "classes/createClassesForm";
//    }
//
//    @PostMapping("/classes/new")
//    public String create(ClassesForm form) {
//        Classes classes = Classes.createClasses(form.getName(), form.getNumber());
//        classesService.join(classes);
//        log.info("class 저장");
//        return "redirect:/";
//    }
//    @GetMapping("/classes")
//    public String list(Model model) {
//        List<Classes> classes = classesService.findClasses();
//        model.addAttribute("classes", classes);
//        return "classes/classesList";
//    }



    @GetMapping("/attendance")
    public String list(Model model) {
        List<Classes> classes = classesService.findClasses();
        model.addAttribute("classes", classes);
        return "attendance/classList";
    }

    @GetMapping("/attendance/{id}/check")
    public String scanQr(@PathVariable("id") Long classId, Model model) {
        model.addAttribute("form", new MemForm());
        return "attendance/qrCheck";
    }

    private int checkMemberName(String name, List<Registration> registration) {
        for (Registration regi : registration) {
            if (regi.getMember().getName().equals(name)) {
                return 1;
            }
        }
        return 0;

    }
    private int checkMemberCode(String code, List<Registration> registration) {
        for (Registration regi : registration) {
            if (regi.getMember().getCode().equals(code)) {
                return 1;
            }
        }
        return 0;

    }
    @PostMapping("/attendance/{id}/check")
    public String checkAttendance(@PathVariable("id") Long classId, MemForm form, HttpServletRequest request, Model model) {
        Classes classes = classesService.findOne(classId);
        List<Registration> registration = classes.getRegistrations();


        String referer = request.getHeader("Referer");


        if (checkMemberName(form.getName(), registration)!=0) {
            log.info("이름으로 출석");
            List<Member> memberList = memberService.findByName(form.getName());
            if (memberList.size() > 1) {
//                System.out.println("같은 이름 두명!");
                model.addAttribute("members", memberList);
                model.addAttribute("classId", classId);
                return "attendance/dupMemberCheck";
            }
            Member member = memberList.get(0);

            Attendance attendance;// = Attendance.createAttendance(classes, member);
            if ((attendance = Attendance.createAttendance(classes, member)) == null) {
                return "redirect:"+referer;
//                return showMessageAndRedirect(new MessageDto("이미 출석한 회원입니다!!!", referer, RequestMethod.GET, null), model);

            }
            attendanceRepository.save(attendance);
            return "redirect:"+ referer;
        }

        if (checkMemberCode(form.getCode(), registration) != 0) {
            log.info("회원번호로 출석");
            List<Member> memberList = memberService.findByCode(form.getCode());
            if (memberList.size() > 1) {
//                System.out.println("같은 회원번호 두명!");
                model.addAttribute("members", memberList);
                model.addAttribute("classId", classId);
                return "attendance/dupMemberCheck";
            }
            Member member = memberList.get(0);

            Attendance attendance;// = Attendance.createAttendance(classes, member);
            if ((attendance = Attendance.createAttendance(classes, member)) == null) {
                return "redirect:"+referer;

//                return showMessageAndRedirect(new MessageDto("이미 출석한 회원입니다!!!", referer, RequestMethod.GET, null), model);

            }
            attendanceRepository.save(attendance);
            return "redirect:" + referer;

        }

        log.info("없는 멤버!!!");
        log.info("********없는 멤버!!!!*********  이름:"+ form.getName()+" 회원번호:"+form.getCode());
        return showMessageAndRedirect(new MessageDto("등록되지 않은 회원입니다!!!   |   회원번호:"+form.getCode(), referer, RequestMethod.GET, null), model);
        //없으면 빨간 화면이나 엑스 표시 띄우
//        return "redirect:"+ referer;




    }
    // 사용자에게 메시지를 전달하고, 페이지를 리다이렉트 한다.
    private String showMessageAndRedirect(final MessageDto params, Model model) {
        model.addAttribute("params", params);
        return "messageRedirect";
    }


    @GetMapping("/attendance/{id}/checkDup/{memberId}")
    public String checkDupAttendance(@PathVariable("id") Long classId, @PathVariable("memberId") Long memberId, MemForm form,  Model model) {
        Classes classes = classesService.findOne(classId);
        log.info("중복이름 출석!!!");
        Member member = memberService.findOne(memberId);

        Attendance attendance;// = Attendance.createAttendance(classes, member);
        if ((attendance = Attendance.createAttendance(classes, member)) == null) {
            return "redirect:/attendance/" + classId + "/check";
//            return showMessageAndRedirect(new MessageDto("이미 출석한 회원입니다!!!", "/attendance/"+classId+"/check", RequestMethod.GET, null), model);

        }
        attendanceRepository.save(attendance);
        log.info("중복이름 출석 확인!!!");

        return "redirect:/attendance/"+classId+"/check";
    }




}
