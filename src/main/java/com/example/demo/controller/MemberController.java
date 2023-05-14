package com.example.demo.controller;

import com.example.demo.Domain.Member;
import com.example.demo.Service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MemberController {

    // MemberService를 주입
    // PostMapping을 통해 MemberService를 호출하는 Method 생성
    @Autowired
    private MemberService memberService;
    // json으로 post요청이 들어왔을 때는 data를 꺼내기 위해 RequestBody 사용
//    @PostMapping("members")
//    @ResponseBody
//    // input 값을 json으로 받는 형식
//    public String memberCreate(@RequestBody Member member) {
//    // Member 객체를 만들어서 MemberService 매개변수로 전달
//        Member member1 = new Member();
//        member1.setName(member.getName());
//        member1.setEmail(member.getEmail());
//        member1.setPassword(member.getPassword());
//        memberService.create(member1);
//        return "ok";
//    }

    @GetMapping("members/new")
    public String memberCreateForm() {
        return "member/member-register";
    }

    @PostMapping("members/new")
    @ResponseBody
    // input 값을 form-data로 받는 형식
    public String memberCreate(@RequestParam(value = "name")String name,
                               @RequestParam(value = "email")String email,
                               @RequestParam(value = "password")String password) {
    // Member 객체를 만들어서 MemberService 매개변수로 전달
        Member member1 = new Member();
        member1.setName(name);
        member1.setEmail(email);
        member1.setPassword(password);
        memberService.create(member1);
        return "ok";
    }

    @GetMapping("members")
    public String memberFindAll(Model model) {
        List<Member> members = memberService.findAll();
        model.addAttribute("memberList", members);
        return "member/member-list";
    }

    @GetMapping("member")
    public String memberFindById(@RequestParam(value = "id")Long myId, Model model) {
        Member member = memberService.findById(myId);
        System.out.println(member.getName());
        System.out.println(member.getEmail());
        model.addAttribute("member", member);
        return "member/member-detail";
    }

}
