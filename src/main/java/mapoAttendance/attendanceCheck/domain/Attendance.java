package mapoAttendance.attendanceCheck.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mapoAttendance.attendanceCheck.exception.NotEnoughException;
import mapoAttendance.attendanceCheck.exception.NotRegisterException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.type.descriptor.DateTimeUtils;

import java.time.*;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static java.lang.Enum.valueOf;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Attendance {

    @Id
    @GeneratedValue
    @Column(name = "attendance_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus attendanceStatus;
    //
//    @ManyToOne(fetch = LAZY)
//    private Classes classes;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "class_id") //-> FK=member_id
    private Classes classes;


    private Long memberId;
    private String memberCode;
    private String memberName;

    //날짜? LocalDateTime.now()       private LocalDateTime attendDate;//출석 시간
    private LocalDateTime attendDate;

    public void addClasses(Classes classes) {
        this.classes = classes;
        classes.getAttendances().add(this);
    }

    public static Attendance createAttendance(Classes classes, Member member) {
        if (validateAttendance(classes, member) != 0) {
            return null;
        }
        Attendance attendance = new Attendance();
        attendance.addClasses(classes);
        attendance.setMemberId(member.getId());
        attendance.setMemberCode(member.getCode());
        attendance.setMemberName(member.getName());
//        ZoneId z = ZoneId.of("Asia/Seoul"); // Or get the JVM’s current default time zone: ZoneId.systemDefault()
//        ZonedDateTime zonedDateTime = ZonedDateTime.now(z);
        attendance.setAttendDate(LocalDateTime.now());

//        System.out.println(LocalDateTime.now().toString());

//        System.out.println("!!!"+LocalDateTime.now());
        attendance.attendanceStatus = AttendanceStatus.ATTENDANCE;
        return attendance;
    }

    private static void makeAttendanceExel(Attendance attendance) {
        //.xlsx 확장자 지원
        XSSFWorkbook xssfWb = null; // .xlsx
        XSSFSheet xssfSheet = null; // .xlsx
        XSSFRow xssfRow = null; // .xlsx
        XSSFCell xssfCell = null;// .xlsx

    }


    private static long validateAttendance(Classes classes, Member member) {
        List<Attendance> attendances = classes.getAttendances();
        int check = 0;
        for (Attendance attendance : attendances) {
            if (LocalDate.now().isEqual(attendance.getAttendDate().toLocalDate())) {
                if (attendance.getMemberCode().equals(member.getCode())) {
                    check++;
                }
            }
        }

        if (check > 0) {
            return 1;
//            throw new NotRegisterException("이미 출석한 회원입니다.");
        }
        return 0;
    }
    public void delete() {
        Classes classes = getClasses();
        List<Attendance> attendances = classes.getAttendances();
        if (attendances.isEmpty()) {
            throw new NotEnoughException("No member and registration");
        }
        attendances.remove(this);


    }


}
