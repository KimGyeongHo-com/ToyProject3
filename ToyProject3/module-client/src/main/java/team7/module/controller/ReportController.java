package team7.module.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import team7.module.domain.user.UserAdaptor;
import team7.module.dto.report.ReportRequest;
import team7.module.service.ReportService;


@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
@Controller
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/reportForm/{boardId}")
    public String reportForm(
            @PathVariable Long boardId,
            Model model
    ) {
        model.addAttribute("boardId", boardId);
        return "/board/boardReportForm";
    }

    // TODO report photo
    @PostMapping("/report/{boardId}")
    public String report(
            @PathVariable Long boardId,
            @AuthenticationPrincipal UserAdaptor userAdaptor,
            ReportRequest.reportBoardDTO reportBoardDTO
    ) {
        reportService.report(boardId, userAdaptor.getUser(), reportBoardDTO);
        return "/board/boardReportForm";
    }
}
