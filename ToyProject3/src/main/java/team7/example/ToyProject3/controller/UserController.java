package team7.example.ToyProject3.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import team7.example.ToyProject3.dto.UserDto;
import team7.example.ToyProject3.service.UserService;

@Controller
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "/index";
    }

    // 회원가입 페이지
    @GetMapping("/signup")
    public String dispSignup() {
        return "/signup";
    }

    // 회원가입 처리
    @PostMapping("/signup")
    public String execSignup(UserDto userDto) {
        userService.joinUser(userDto);
        return "login";
    }

    // 로그인 페이지
    @GetMapping("/login")
    public String dispLogin() {
        return "login";
    }

    // 로그인 결과 페이지
    @GetMapping("/login/result")
    public String dispLoginResult() {
        return "/loginSuccess";
    }

    // 로그아웃 결과 페이지
    @GetMapping("/logout/result")
    public String dispLogout() {
        return "/logout";
    }

    // 접근 거부 페이지
    @GetMapping("/denied")
    public String dispDenied() {
        return "/denied";
    }

    // 내 정보 페이지
    @GetMapping("/info")
    public String dispMyInfo() {
        return "/info";
    }

    // 어드민 페이지
    @GetMapping("/admin")
    public String dispAdmin() {
        return "/admin";
    }

//    //로그인
//    @PostMapping("/user/login")
//    public String login(UserDto userDto) {
//        userDto.toEntity();
//        userService.loadUserByUsername(userDto.getEmail());
//        return "redirect:/user/login/result";
//    }

}