package mapoAttendance.attendanceCheck.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mapoAttendance.attendanceCheck.repository.DeleteAll;
import mapoAttendance.attendanceCheck.service.ClassesService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    @RequestMapping("/")
    public String home() {
        log.info("home controller");
        return "home";
    }

    private final DeleteAll deleteAll;

    @PostMapping("/delete")
    public String deleteAll() {
        deleteAll.delete();

        return "redirect:";
    }


}
