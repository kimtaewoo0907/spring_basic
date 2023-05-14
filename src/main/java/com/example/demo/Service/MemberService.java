package com.example.demo.Service;

import com.example.demo.Domain.Member;
import com.example.demo.Repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    // service에서 repository를 주입 받기 위해서, Autowired를 사용
    @Autowired
    private MemberRepository memberRepository;

    // 회원가입(등록)
    public void create(Member member) {
        memberRepository.save(member);
    }

    // 회원목록조회
    // memberRepository.findAll()의 기본 return 타입은 List<해당객체>
    public List<Member> findAll() {
        List<Member> members = memberRepository.findAll();
        return members;
    }

    public Member findById(Long myId) {
        // Optional 객체로 return 하게 되면
        Member member = memberRepository.findById(myId).orElse(null);
        return member;
    }
}
