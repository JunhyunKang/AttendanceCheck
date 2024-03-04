package mapoAttendance.attendanceCheck.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mapoAttendance.attendanceCheck.exception.NotEnoughException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Registration {
    @Id
    @GeneratedValue
    @Column(name = "registration_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "class_id") //-> FK=member_id
    private Classes classes;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

//    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "attendance_id")
//    private Attendance attendance;

//    @Enumerated(EnumType.STRING)
//    private OrderStatus status; //주문 상태 [ORDER, CANCEL]

    //==연관관 메서드==//
    public void setClasses(Classes classes) {
        this.classes = classes;
        classes.getRegistrations().add(this);
    }

    public void setMember(Member member) {
        this.member = member;
        member.getRegistrations().add(this);
    }

//    public void addMember(Member member) {
//        members.add(member);
//        member.setRegistration(this);
//    }

//    public void setDelivery(Delivery delivery) {
//        this.delivery = delivery;
//        delivery.setOrder(this);
//    }

    // == 생성 메서드 ==
    public static Registration createRegistration(Classes classes, Member member) {
        Registration registration = new Registration();
        registration.setClasses(classes);
        registration.setMember(member);
        classes.reduceNumber();
        return registration;
    }

    // == 비지니스 로직 ==

    /**
     * 취소
     */
    public void cancel() {
        Classes classes = getClasses();
        List<Registration> registrations = classes.getRegistrations();
        if (registrations.isEmpty()) {
            throw new NotEnoughException("No member and registration");
        }
        registrations.remove(this);


        Member member = getMember();
        List<Registration> r = member.getRegistrations();
        if (r.isEmpty()) {
            throw new NotEnoughException("No member and registration at member");
        }
        r.remove(this);

        classes.addNumber();

    }



    // ==조회 로직 ==
    /**
     * 전체 주문 가격 조회
     */
//    public int getTotalMember() {
////                return orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum(); 자바8 이용한 것
//        int totalPrice =0;
//        for (OrderItem orderItem : orderItems) {
//            totalPrice += orderItem.getTotalPrice();
//        }
//        return totalPrice;
//
//    }

}
