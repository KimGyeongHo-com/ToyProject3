package team7.example.ToyProject3.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import team7.example.ToyProject3.domain.Role;
import team7.example.ToyProject3.domain.User;
import team7.example.ToyProject3.domain.UserRole;
import team7.example.ToyProject3.dto.AdminBoardDto;
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
    @PostMapping("/users/{id}/role")
    public String updateUserRole(@PathVariable Long id, @RequestParam Role role) {
        adminService.updateRoleById(id, role);
        return "redirect:/users";
    }



}
