package mapoAttendance.attendanceCheck.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassesForm {

    @NotEmpty(message = "과목명을 입력해주세요.")
    private String name;

    private Long number;

}
