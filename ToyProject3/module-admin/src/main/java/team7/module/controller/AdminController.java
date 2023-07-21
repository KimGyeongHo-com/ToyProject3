package team7.module.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import team7.module.domain.board.BoardStatus;
import team7.module.domain.user.Role;
import team7.module.domain.user.UserRole;
import team7.module.dto.AdminBoardDto;
import team7.module.dto.AdminReportDto;
import team7.module.dto.AllUsersInfoDto;
import team7.module.sercive.AdminService;

import java.util.List;


@Controller
@AllArgsConstructor
public class AdminController {

    private AdminService adminService;

    /*
    // 어드민 페이지
    @GetMapping("/admin")
    public String dispAdmin() {
        return "admin";
    }
     */

    // 유저 리스트
    @GetMapping("/users")
    public String getAllUsers(Model model) {
        List<AllUsersInfoDto> users = adminService.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    // 유저 등급 변경
    @PostMapping("/users/{id}/userrole")
    public String updateUserRole(@PathVariable Long id, @RequestParam UserRole userrole) {
        adminService.updateRoleById(id, userrole);
        return "redirect:/users";
    }

    // 게시글 정렬
    @GetMapping("/users/orderByBoard")
    public String getAllUsersOrderByBoard(Model model) {
        List<AllUsersInfoDto> users = adminService.getAllUsersOrderByBoard();
        model.addAttribute("users", users);
        return "users";
    }

    // 댓글 정렬
    @GetMapping("/users/orderByReply")
    public String getAllUsersOrderByReply(Model model) {
        List<AllUsersInfoDto> users = adminService.getAllUsersOrderByReply();
        model.addAttribute("users", users);
        return "users";
    }

    // 게시글 리스트
    @GetMapping("/userboard")
    public String getAllBoards(Model model) {
        List<AdminBoardDto> adminBoardDto = adminService.getAllBoards();
        model.addAttribute("userboard", adminBoardDto);
        return "userboard";
    }

    // 게시글 상태 업데이트
    @PostMapping("/userboard/{id}/boardStatus")
    public String updateBoardStatus(@PathVariable Long id, @RequestParam BoardStatus boardStatus) {
        adminService.updateBoardStatus(id, boardStatus);
        return "redirect:/userboard";
    }

    // 게시글 삭제
    @PostMapping("/boardList/{id}/delete")
    public String deleteBoard(@PathVariable(name = "id") Long id) {

        adminService.deleteRepliesByBoardId(id);

        adminService.deleteBoardById(id);

        return "redirect:/userboard";
    }

    // 신고 게시판
    @GetMapping("/report")
    public String reportList(Model model) {
        List<AdminReportDto> reports = adminService.findReportsByBoardId();
        model.addAttribute("reports", reports);
        return "report";
    }

    // 신고 게시판 유저 등급 변경
    @PostMapping("/report/{id}/userrole")
    public String updateUserRoleBlack(@PathVariable Long id, @RequestParam UserRole userrole) {
        adminService.updateBlackById(id, userrole);
        return "redirect:/report";
    }

    // 신고 승인
    @PostMapping("/report/{id}/update")
    public String updateReportBlackById(@PathVariable Long id) {
        adminService.updateReportBlackById(id, Role.BLACKLIST, UserRole.BLACK);
        return "redirect:/report";
    }

    // 신고 거절
    @PostMapping("/report/{id}/delete")
    public String deleteReportByBoardId(@PathVariable(name = "id") Long id) {
        adminService.deleteReportByBoardId(id);
        return "redirect:/report";
    }

    // 블랙리스트 해제
    @PostMapping("/report/{id}/Unblacklist")
    public String updateBlacklistByBoardId(@PathVariable Long id) {
        adminService.updateBlacklistByBoardId(id, Role.USER, UserRole.NORMAL);
        return "redirect:/report";
    }
}
