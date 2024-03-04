package mapoAttendance.attendanceCheck.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mapoAttendance.attendanceCheck.exception.NotEnoughException;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Classes {
    @Id
    @GeneratedValue
    @Column(name = "class_id")
    private Long id;

    private String name;

    private Long number;

    @OneToMany(mappedBy = "classes")
    private List<Attendance> attendances = new ArrayList<>();

    public static Classes createClasses(String name, Long max) {
        Classes classes = new Classes();

        classes.setName(name);
        classes.setNumber(max);
        return classes;
    }

    //과목시간?

    @OneToMany(mappedBy = "classes")
    private List<Registration> registrations = new ArrayList<>();

    public Long reduceNumber() {
        return --number;
    }

    public Long addNumber() {
        return ++number;
    }


}
