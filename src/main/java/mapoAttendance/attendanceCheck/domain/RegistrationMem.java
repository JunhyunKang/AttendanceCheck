//package mapoAttendance.attendanceCheck.domain;
//
//import jakarta.persistence.*;
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import static jakarta.persistence.FetchType.LAZY;
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class RegistrationMem {
//    @Id
//    @GeneratedValue
//    @Column(name = "registrationMem_id")
//    private Long id;
//
//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "member_id")
//    private Member member;
//
//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "registration_id")
//    private Registration registration;
//
//    private int count; //등록인원수
//
////    protected OrderItem(){
////    } -> 롬복으로 @ 로가
//
//    // == 생성 메소드 ==
//    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
//        OrderItem orderItem = new OrderItem();
//        orderItem.setItem(item);
//        orderItem.setOrderPrice(orderPrice);
//        orderItem.setCount(count);
//
//        item.removeStock(count);
//        return orderItem;
//    }
//
//    // ==비지니스로직 ==
//    public void cancel() {
//        getItem().addStock(count);
//    }
//
//
//    // == 조회로직 == 주문 상품 전체 가격조회
//    public int getTotalPrice() {
//        return getOrderPrice() * getCount();
//    }
//}
