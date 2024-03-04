package mapoAttendance.attendanceCheck.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mapoAttendance.attendanceCheck.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    private String code;

    @OneToMany(mappedBy = "member")
    private List<Registration> registrations = new ArrayList<>();

    public static Member createMember(String name, String code) {
        Member member = new Member();
        member.setName(name);
//        validateDuplicateClasses(classes);
        member.setCode(code);
        return member;
    }
//    private void validateDuplicateClasses(Member member) {
//        List<Member> members = MemberRepository.findByName(member.getName());
//        if (!members.isEmpty()) {
//            throw new IllegalStateException("이미 존재하는 수업입니다.");
//        }
//    }


//    @OneToMany(mappedBy = "member")
//    private List<Registration> registrations = new ArrayList<>();

//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "member_id")
//    private Member member;


//    @OneToMany
//    @JoinColumn(name = "registration_id")
//    private Registration registration;

//    protected OrderItem(){
//    } -> 롬복으로 @ 로가

    // == 생성 메소드 ==
//    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
//        OrderItem orderItem = new OrderItem();
//        orderItem.setItem(item);
//        orderItem.setOrderPrice(orderPrice);
//        orderItem.setCount(count);
//
//        item.removeStock(count);
//        return orderItem;
//    }

}
