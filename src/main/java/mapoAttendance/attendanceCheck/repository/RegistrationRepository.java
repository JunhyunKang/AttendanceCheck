package mapoAttendance.attendanceCheck.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import mapoAttendance.attendanceCheck.domain.Classes;
import mapoAttendance.attendanceCheck.domain.Member;
import mapoAttendance.attendanceCheck.domain.Registration;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RegistrationRepository {
    private final EntityManager em;

    @Transactional

    public void save(Registration registration) {
        em.persist(registration);
    }

    public Registration findOne(Long id) {
        return em.find(Registration.class, id);
    }

    public void delete(Registration registration){
        em.remove(registration);}
//    public List<Order> findAll(OrderSearch orderSearch) {
//        return em.createQuery("select o from Order o join o.member m where o.status = :status and m.name like :name",
//                Order.class).setParameter("status", orderSearch.getOrderStatus())
//                .setParameter("name", orderSearch.getMemberName())
//                .setMaxResults(1000) //최대 1000권
//                .getResultList();
//
//    }

    /**
     *
     * criteria 로 처리
     */
    public List<Registration> findAllByCriteria(RegistrationSearch rs) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Registration> cq = cb.createQuery(Registration.class);
        Root<Registration> j = cq.from(Registration.class);
        Join<Registration, Member> m = j.join("member", JoinType.INNER); //회원과 조인
        Join<Registration, Classes> c = j.join("classes", JoinType.INNER); //과목과 조인
        List<Predicate> criteria = new ArrayList<>();

        //회원 이름 검색
        if (StringUtils.hasText(rs.getMemberName())) {
            Predicate name =
                    cb.like(m.<String>get("name"), "%" +
                            rs.getMemberName() + "%");
            criteria.add(name);
        }
        //과목 이름 검색
        if (StringUtils.hasText(rs.getClassName())) {
            Predicate name =
                    cb.like(c.<String>get("name"), "%" +
                            rs.getClassName() + "%");
            criteria.add(name);
        }
        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Registration> query = em.createQuery(cq).setMaxResults(1000); //최대 1000건
        return query.getResultList();
    }
}
