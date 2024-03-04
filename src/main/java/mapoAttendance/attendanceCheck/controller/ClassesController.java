package mapoAttendance.attendanceCheck.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mapoAttendance.attendanceCheck.domain.Classes;
import mapoAttendance.attendanceCheck.service.ClassesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ClassesController {
    private final ClassesService classesService;

    @GetMapping("/classes/new")
    public String createForm(Model model) {
        model.addAttribute("form", new ClassesForm());
        return "classes/createClassesForm";
    }

    @PostMapping("/classes/new")
    public String create(ClassesForm form) {
        Classes classes = Classes.createClasses(form.getName(), form.getNumber());
        classesService.join(classes);
        log.info("class 저장");
        return "redirect:/";
    }
    @GetMapping("/classes")
    public String list(Model model) {
        List<Classes> classes = classesService.findClasses();
        model.addAttribute("classes", classes);
        return "classes/classesList";
    }

}
