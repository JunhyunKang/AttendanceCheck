package mapoAttendance.attendanceCheck.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mapoAttendance.attendanceCheck.repository.DeleteAll;
import mapoAttendance.attendanceCheck.service.ClassesService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
    public String deleteAll(@RequestParam("password") String password) {
//        deleteAll.delete();


        if (validatePassword(password)) {
            // 삭제 로직
            deleteAll.delete();
            return "redirect:"; // 삭제 성공 후 이동할 페이지
        } else {
            return "redirect:"; // 비밀번호 오류 시 이동할 페이지
        }
    }

    private boolean validatePassword(String password) {
        String correctPassword = ""; // 실제 비밀번호로 변경
        return password.equals(correctPassword);
    }


}
