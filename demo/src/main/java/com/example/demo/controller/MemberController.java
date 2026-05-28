package com.example.demo.controller;

import com.example.demo.model.Member;
import com.example.demo.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@Profile("!nodb")
@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 회원가입 폼
    @GetMapping("/signup")
    public String signupForm() {
        return "member/signup"; // templates/member/signup.html
    }

    // 회원가입 처리
    @PostMapping("/signup")
    public String signup(@RequestParam String username,
                         @RequestParam String password,
                         @RequestParam String email,
                         Model model) {
        try {
            memberService.register(username, password, email);
            return "redirect:/member/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/signup";
        }
    }

    // 로그인 폼
    @GetMapping("/login")
    public String loginForm() {
        return "member/login"; // templates/member/login.html
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        try {
            Member member = memberService.login(username, password);
            session.setAttribute("loginMember", member); // 세션에 로그인 정보 저장
            return "redirect:/board"; // 로그인 성공 후 게시판으로 이동 (원하면 홈으로 바꿔도 됨)
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/login";
        }
    }

    // 로그아웃
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    // ✅ 아이디 중복 체크 API (JS에서 호출)
    @GetMapping("/check-username")
    @ResponseBody
    public Map<String, Boolean> checkUsername(@RequestParam String username) {
        boolean available = memberService.isUsernameAvailable(username);
        return Collections.singletonMap("available", available);
    }

    // ✅ 이메일 중복 체크 API
    @GetMapping("/check-email")
    @ResponseBody
    public Map<String, Boolean> checkEmail(@RequestParam String email) {
        boolean available = memberService.isEmailAvailable(email);
        return Collections.singletonMap("available", available);
    }
}
