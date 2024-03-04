package mapoAttendance.attendanceCheck.service;

import lombok.RequiredArgsConstructor;
import mapoAttendance.attendanceCheck.domain.Classes;
import mapoAttendance.attendanceCheck.domain.Member;
import mapoAttendance.attendanceCheck.domain.Registration;
import mapoAttendance.attendanceCheck.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long saveMember(Member member) {
        Member m = validateDuplicateClasses(member); //증복 회원 번호 검사
        Long id = memberRepository.save(m);
        return id;
    }

    private Member validateDuplicateClasses(Member member) {
        List<Member> findMembers = memberRepository.findByCode(member.getCode());
        if (!findMembers.isEmpty()) {
//            throw new IllegalStateException("이미 존재하는 회원입니다.");
            return findMembers.get(0);
        } else {
            return member;
        }
    }
    @Transactional
    public Member updateMember(Long memberId, Member param) {
        Member findItem = memberRepository.findOne(memberId);


        findItem.setName(param.getName());
        findItem.setCode(param.getCode());

        return findItem;
    }
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    public List<Member> findByName(String name) {
        return memberRepository.findByName(name);
    }

    public List<Member> findByCode(String code) {
        return memberRepository.findByCode(code);
    }



}
