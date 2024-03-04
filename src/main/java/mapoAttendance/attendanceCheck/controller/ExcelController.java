package mapoAttendance.attendanceCheck.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mapoAttendance.attendanceCheck.domain.Classes;
import mapoAttendance.attendanceCheck.domain.Member;
import mapoAttendance.attendanceCheck.domain.Registration;
import mapoAttendance.attendanceCheck.service.ClassesService;
import mapoAttendance.attendanceCheck.service.MemberService;
import mapoAttendance.attendanceCheck.service.RegistrationService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ExcelController {
    private final MemberService memberService;
    private final ClassesService classesService;
    private final RegistrationService registrationService;

    Long classId;

    @GetMapping("/classes/{id}/register")
    public String goToUpload(@PathVariable("id") Long classId) {
        this.classId = classId;
        return "classes/excelUpload";

    }

    @PostMapping("/excel/read")
    public String readExcel(@RequestParam("file") MultipartFile file, Model model)
            throws IOException { // 2

        List<ExcelForm> dataList = new ArrayList<>();


        Workbook workbook = null;
        workbook = new XSSFWorkbook(file.getInputStream());


        Sheet worksheet = workbook.getSheetAt(0);

        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) { // 4

            Row row = worksheet.getRow(i);

            ExcelForm data = new ExcelForm();

            data.setMemberName(row.getCell(0).getStringCellValue());
            data.setMemberCode(row.getCell(1).getStringCellValue());

            dataList.add(data);

            Member member = Member.createMember(row.getCell(0).getStringCellValue(), row.getCell(1).getStringCellValue());
            Long memberId = memberService.saveMember(member);
            log.info("회원번호 중복인 것은 이미 있던 id 돌려줌");

            Long regiId = registrationService.register(memberId, classId);
            log.info("해당 수업에 이미 존재하는 회웤번호는 에러, 한 회원번호가 여러 수업으로 들어갈 수는 있음");
        }

/**
 * 일단...
 */




//        List<Registration> registrations = classesService.findOne(classId).getRegistrations();

//        model.addAttribute("registrations", registrations);
//        model.addAttribute("classId", classId);

        return "redirect:/classes";

    }
}
