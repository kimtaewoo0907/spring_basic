package com.example.demo.controller;

import com.example.demo.domain.Member;
import com.example.demo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.util.List;

@Controller()
public class MemberJsonController {
    @Autowired
    private MemberService memberService;


    @PostMapping("json/members/new")
    @ResponseBody
    public String memberCreate(@RequestParam(value = "name")String name,
                               @RequestParam(value = "email")String email,
                               @RequestParam(value = "password")String password) throws SQLException {
        Member member1 = new Member();
        member1.setName(name);
        member1.setEmail(email);
        member1.setPassword(password);
        memberService.create(member1);
        return "ok";
    }

    @GetMapping("json/members")
    @ResponseBody
    public List<Member> memberFindAll() throws SQLException {
        List<Member> members = memberService.findAll();
        return members;
    }

    @GetMapping("json/member")
    @ResponseBody
    public Member memberFindById(@RequestParam(value = "id")Long myId) throws SQLException {
        Member member = memberService.findById(myId);
        return member;
    }
}
