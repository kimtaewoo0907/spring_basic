package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;

@Controller
public class HelloController {

    // http://localhost:8080/hello로 요청시 hello메서드에서 처리
    // http는 국제 통신 프로토콜이다. https는 (s = secure)보안이 강화된 통신 프로토콜
    // port란 한 IP내에 여러 프로그램을 구분짓는 단위. 집 주소가 ip, 각 집의 방문이 port
    // data만을 return할 때는 ResponseBody를 사용한다
    @GetMapping("hello")
    @ResponseBody
    public String hello(){
        return "hello world";
    }

    // jsp.thymeleaf같은 템플릿 엔진을 사용하여 화면을 return 할 때는 ResponseBody를 사용하면 안 된다.
    // 그리고 model이라는 객체를 data를 담아 return xxx를 하여 xxx.html파일로 데이터를 보낸다.
    @GetMapping("hello-thymeleaf")
    public String hello2(Model model){
        model.addAttribute("getdata","hello2 world");
        return "hello-th";
    }

    // 데이터를 첨부시키지 않고, 화면만을 렌더링(준다) 할 수도 있다
    @GetMapping("hello-html")
    public String helloHtml(){
        return "hello-get-req";
    }

    @GetMapping("hello-post-form-req")
    public String helloGetFormReq(){
        return "hello-post-form-req";
    }

    // html의 form 형식으로 post 요청
    // form 형식의 경우 parameter로 데이터가 넘어오므로, RequestParam으로 받아줘야 한다
    @PostMapping("hello-post-form-req")
    @ResponseBody
    public String helloPostFormReq(@RequestParam(value="name")String myname,
                                 @RequestParam(value="email")String myemail,
                                 @RequestParam(value="password")String mypassword) {
        System.out.println("이름 : " + myname);
        System.out.println("이메일 : " + myemail);
        System.out.println("비밀번호 : " + mypassword);
        return "ok";
    }

    // 테스트를 할 때에, localhost:8080/hello-parameter?test=hello
    @GetMapping("hello-parameter")
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String helloParameter(@RequestParam(value = "test")String mytest) {
//        if(true) {
//            throw new AccessDeniedException("권한이 없습니다");
//        }
        System.out.println("클라이언트가 보내온 parameter는? " + mytest);
        return "ok";
    }

    // json으로 post요청을 하기 위한 화면 return
    @GetMapping("hello-get-json-req")
    public String helloGetJsonReq() {
        return "hello-post-json-req";
    }

    // json으로 post요청이 들어왔을 때는 data를 꺼내기 위해 RequestBody 사용
    @PostMapping("hello-json")
    @ResponseBody
    public String helloJson(@RequestBody Hello hello) {
        System.out.println("이름 : " + hello.getName());
        System.out.println("이메일 : " + hello.getEmail());
        System.out.println("비밀번호 : " + hello.getPassword());
        return "ok";
    }

    // ResponseBody 어노테이션이 붙어있고, return타입(아래에서는 GoodBye)이 객체이면 spring이 json형태로 변환해준다
    @PostMapping("hello-json-response")
    @ResponseBody
    public GoodBye helloJsonResponse(@RequestBody Hello hello) {
        System.out.println("이름 : " + hello.getName());
        System.out.println("이메일 : " + hello.getEmail());
        System.out.println("비밀번호 : " + hello.getPassword());
        GoodBye goodBye1 = new GoodBye();
        goodBye1.setName(hello.getName());
        goodBye1.setEmail(hello.getEmail());
        goodBye1.setComments("Thank you");
        return goodBye1;
    }
    // 사용자가 서버로 데이터를 보내는 방식에는 총 3가지가 있다
    // 1.?를 통해 parameter 값을 넣어 보내는 방식 : 대부분 get 요청시 사용
    // 2.form 태그 안에 data를 넣어 보내는 방식 : post 요청시 사용
    // (보안이 강화되고, url에 데이터가 찍히지 않는다. 그런데, 내부적으로는 ?key1=value&key2=value2의 형식을 취한다)
    // 3.json 데이터 형식으로 서버로 보내는 방식 : post 요청시 사용
    // json데이터란 {"key1":"value1", "key2":"value2"}의 형식을 취하는 데이터이다
    // 현대적인 웹 서비스에서 대부분의 데이터를 주고받을 때 json을 사용한다
    // json html의 form 태그에 넣어서 보내는 방식이 아니다 보니, Ajax, react 이런 프레임워크를 사용하게 된다
}
