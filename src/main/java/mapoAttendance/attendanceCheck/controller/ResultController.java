package mapoAttendance.attendanceCheck.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mapoAttendance.attendanceCheck.domain.Attendance;
import mapoAttendance.attendanceCheck.domain.Classes;
import mapoAttendance.attendanceCheck.domain.Member;
import mapoAttendance.attendanceCheck.domain.Registration;
import mapoAttendance.attendanceCheck.repository.AttendanceRepository;
import mapoAttendance.attendanceCheck.service.ClassesService;
import mapoAttendance.attendanceCheck.service.MemberService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ResultController {

    private final ClassesService classesService;
    private final MemberService memberService;

    Attendance attendance;
    AttendanceRepository attendanceRepository;

    @GetMapping("/check")
    public String showClasses(Model model) {
        List<Classes> classes = classesService.findClasses();
        model.addAttribute("classes", classes);
        return "check/classList";
    }

    @GetMapping("/check/{id}/result")
    public String showResult(@PathVariable("id") Long classId, Model model) {
        Classes classes = classesService.findOne(classId);
        List<Attendance> attendances = classes.getAttendances();

//        List<Member> members = new ArrayList<>();
//        HashMap<Attendance, LocalDateTime> hashMap = new HashMap<>();

//        for (Attendance attendance : attendances) {
//            log.info("member code: " + memberService.findOne(attendance.getMemberId()).getCode());
//            members.add(memberService.findOne(attendance.getMemberId()));
////            System.out.println("!!!!!!!!!!" + attendance.getAttendDate());
//
////            hashMap.put(attendance, attendance.getAttendDate());
//
//        }
        model.addAttribute("attendances", attendances);
//        model.addAttribute("hashMap", hashMap);

        return "check/memberList";


    }

    @GetMapping("/excel/{id}/download")
    public void excelDownload(@PathVariable("id") Long classId, HttpServletResponse response) throws IOException {

        Classes classes = classesService.findOne(classId);
        List<Attendance> attendances = classes.getAttendances();


//        Workbook wb = new HSSFWorkbook();
        Workbook wb = new XSSFWorkbook();
        LocalDate localDate = LocalDate.now();
        String date = localDate.toString();
        Sheet sheet = wb.createSheet(date);
        Row row = null;
        Cell cell = null;
        int rowNum = 0;

        

        // Header
        row = sheet.createRow(rowNum++);
        cell = row.createCell(0);
        cell.setCellValue("이름");
        cell = row.createCell(1);
        cell.setCellValue("회원번호");
        cell = row.createCell(2);
        cell.setCellValue("날짜");

        // Body
        for (Attendance attendance : attendances) {
            row = sheet.createRow(rowNum++);
            cell = row.createCell(0);
            cell.setCellValue(attendance.getMemberName());
            cell = row.createCell(1);
            cell.setCellValue(attendance.getMemberCode());
            cell = row.createCell(2);
            cell.setCellValue(attendance.getAttendDate().toLocalDate().toString());
        }
//        for (int i=0; i<3; i++) {
//
//        }

        String fileName = URLEncoder.encode(classes.getName(), "UTF-8") + "_" + date + ".xlsx";

        // 컨텐츠 타입과 파일명 지정
        response.setContentType("ms-vnd/excel");
//        response.setHeader("Content-Disposition", "attachment;filename=example.xls");
        response.setHeader("Content-Disposition", "attachment;filename="+fileName);

        // Excel File Output
        wb.write(response.getOutputStream());
        wb.close();
    }
}
