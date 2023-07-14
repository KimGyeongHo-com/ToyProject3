package team7.example.ToyProject3.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import team7.example.ToyProject3.domain.User;
import team7.example.ToyProject3.domain.UserRole;
import team7.example.ToyProject3.service.AdminService;

import java.util.List;


@Controller
@AllArgsConstructor
public class AdminController {

    private AdminService adminService;

    // 어드민 페이지
    @GetMapping("/admin")
    public String dispAdmin() {
        return "admin";
    }


    // 유저 리스트
    @GetMapping("/users")
    public String getAllUsers(Model model) {
        List<User> users = adminService.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    // 유저 등급 변경
    @PostMapping("/users/{userId}/role")
    public String updateUserRole(@PathVariable Long userId, @RequestParam("role") UserRole newRole) {
        adminService.updateUserRole(userId, newRole);
        return "redirect:users";
    }

    /*
    // 블랙 리스트 등록
   @PostMapping("/blacklist/{userId}/role")
   public String updateBlacklistUserRole(@PathVariable Long userId, @RequestParam("role") UserRole newRole) {
       adminService.updateBlacklistUserRole(userId, newRole);
       return "redirect:blacklist";
   }


    // 게시글 삭제
    @GetMapping("/boardList/{id}/delete")
    public String deleteBoard(@PathVariable(name = "id") Integer id) {

        adminService.deleteBoardById(id);

        return "redirect:/admin/boardList";
    }


     */
}
