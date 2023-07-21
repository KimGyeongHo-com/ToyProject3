package team7.module.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import team7.module.domain.user.Role;
import team7.module.domain.user.User;
import team7.module.domain.user.UserAdaptor;
import team7.module.dto.KakaoToken;
import team7.module.dto.OAuthProfile;
import team7.module.dto.UserDto;
import team7.module.repository.UserRepository;
import team7.module.service.UserService;
import team7.module.util.Fetch;


import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final HttpSession session;

    @GetMapping("/")
    public String index() {
        return "/index";
    }

    // 회원가입 페이지
    @GetMapping("/signup")
    public String dispSignup() {
        return "/signup";
    }


    @PostMapping("/signup")
    public String execSignup(@Valid UserDto userDto, Model model){
        userService.joinUser(userDto);
        return "redirect:/login";
    }


    // 로그인 페이지
    @GetMapping("/login")
    public String dispLogin() {
        return "login";
    }

    @GetMapping("/callback")
    public String callBack(String code) throws JsonProcessingException {
        // 1. code 값 존재 유무 확인
        if (code == null || code.isEmpty()) {
            return null;
        }
        // 2. code 값 카카오 전달 -> access token 받기
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "501e9b1cb3cc07189495642751b8a4c1"); // restapi로 쓰시면 됩니다(카카오)
        body.add("redirect_uri", "http://localhost:8080/callback"); // 2차 검증
        body.add("code", code); // 핵심

        ResponseEntity<String> codeEntity = Fetch.kakao("https://kauth.kakao.com/oauth/token", HttpMethod.POST, body);

        // 3. access token으로 카카오의 홍길동 resource 접근 가능해짐 -> access token을 파싱하고
        ObjectMapper om = new ObjectMapper();
        om.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        KakaoToken kakaoToken = om.readValue(codeEntity.getBody(), KakaoToken.class);

        // 4. access token으로 email 정보 받기 (ssar@gmail.com)
        ResponseEntity<String> tokenEntity = Fetch.kakao("https://kapi.kakao.com/v2/user/me", HttpMethod.POST, kakaoToken.getAccessToken());
        OAuthProfile oAuthProfile = om.readValue(tokenEntity.getBody(), OAuthProfile.class);

        // 5. 해당 provider_id 값으로 회원가입되어 있는 user의 username 정보가 있는지 DB 조회 (X)
        Optional<User> user = userRepository.findByName("kakao_" + oAuthProfile.getId());

        // 5. 이미 등록된 회원이면 로그인 처리
        if (user.isPresent()) {
            System.out.println("디버그: 이미 등록된 회원입니다. 로그인을 진행합니다.");
            UserDetails userDetails = userService.loadUserByUsername(user.get().getEmail());
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "/index";
        }

        // 6. 등록되지 않은 회원이면 회원가입 후 로그인 처리
        if (user.isEmpty()) {
            System.out.println("디버그: 등록되지 않은 회원입니다. 회원가입 후 로그인을 진행합니다.");

            //변수 준비
            UUID uuid = UUID.randomUUID();
            String uuidString = uuid.toString().replaceAll("-", "");
            String firstFiveChars = uuidString.substring(0, 8);
            //회원가입 과정
            UserDto userDto = new UserDto();
            userDto.setEmail(oAuthProfile.getKakaoAccount().getEmail());
            userDto.setName("kakao_" + oAuthProfile.getId());
            userDto.setPassword(uuid.toString());
            userDto.setRole(Role.USER);
            userDto.setRole(Role.USER);
            userDto.setNickname("kakao_" + firstFiveChars);
            userService.joinUser(userDto);

            //권한 설정
            User newUser = userRepository.findByName("kakao_" + oAuthProfile.getId()).orElse(null);
            UserDetails userDetails = userService.loadUserByUsername(newUser.getEmail());
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);


            return "/index";
        }

        return null;

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

    // 회원 정보 수정 페이지
    @GetMapping("/updateUser")
    public String showUpdate(UserDto userDto, Model model,@AuthenticationPrincipal UserAdaptor userAdaptor){
        User user = userAdaptor.getUser();
        userDto.setNickname(user.getNickname());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());

        model.addAttribute("email", userDto.getEmail());
        model.addAttribute("nickname",userDto.getNickname());
        model.addAttribute("name", userDto.getName());
        return "/updateUser";
    }

    // 회원 정보 수정
    @PostMapping("/updateUser")
    public String updateUser(@Valid UserDto userDto,Model model,@AuthenticationPrincipal UserAdaptor userAdaptor){
        User user = userRepository.findByEmail(userAdaptor.getUsername()).orElse(null);
//        User user = userAdaptor.getUser();
        userService.updateUser(user,userDto);

        return "/index";
    }

    // 내 정보 페이지
    @GetMapping("/info")
    public String dispMyInfo(Model model, @AuthenticationPrincipal UserAdaptor userAdaptor) {
        User user = userAdaptor.getUser();
        model.addAttribute("name",user.getName());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("role",user.getRole());
        model.addAttribute("userRole",user.getUserRole());
        model.addAttribute("nickname",user.getNickname());
        return "/myinfo";
    }

    // 어드민 페이지
    @GetMapping("/admin")
    public String dispAdmin() {
        return "admin";
    }


}