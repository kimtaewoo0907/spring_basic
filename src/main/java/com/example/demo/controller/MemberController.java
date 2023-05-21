package com.example.demo.controller;

import com.example.demo.domain.Member;
import com.example.demo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
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
    // input 값을 form-data로 받는 형식
    public String memberCreate(@RequestParam(value = "name")String name,
                               @RequestParam(value = "email")String email,
                               @RequestParam(value = "password")String password) throws SQLException {
    // Member 객체를 만들어서 MemberService 매개변수로 전달
        Member member1 = new Member();
        member1.setName(name);
        member1.setEmail(email);
        member1.setPassword(password);
        // Member는 class를 new하여 객체를 만드는 반면에
        // MemberService는 객체를 만들지 않고 바로 사용하고 있다
        // 이는 MemberService는 Component를 통해 싱글톤으로 만들어져 있기 때문
        // 싱글톤으로 만들어진 Component는 객체를 생성하는 것이 아니라 주입(DI)을 받아 사용
        memberService.create(member1);
        return "redirect:/";
    }

    @GetMapping("members")
    public String memberFindAll(Model model) throws SQLException {
        List<Member> members = memberService.findAll();
        model.addAttribute("memberList", members);
        return "member/member-list";
    }

    @GetMapping("member")
    public String memberFindById(@RequestParam(value = "id")Long myId, Model model) throws SQLException {
        Member member = memberService.findById(myId);
        System.out.println(member.getName());
        System.out.println(member.getEmail());
        model.addAttribute("member", member);
        return "member/member-detail";
    }

    @GetMapping("/")
    public String home() {
        return "member/member-home";
    }

    @PostMapping("members/update")
    public String memberUpdate(@RequestParam(value = "id")String id,
                                @RequestParam(value = "name")String name,
                               @RequestParam(value = "email")String email,
                               @RequestParam(value = "password")String password) throws Exception {
        Member member = new Member();
        member.setId(Long.parseLong(id));
        member.setName(name);
        member.setEmail(email);
        member.setPassword(password);
        memberService.update(member);
        return "redirect:/";
    }

}
