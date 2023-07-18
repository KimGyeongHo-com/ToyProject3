package team7.example.ToyProject3.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import team7.example.ToyProject3.domain.user.UserAdaptor;
import team7.example.ToyProject3.dto.board.BoardRequest;
import team7.example.ToyProject3.dto.board.BoardResponse;
import team7.example.ToyProject3.service.BoardService;

import javax.validation.Valid;

@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;

    @PreAuthorize("permitAll()")
    @GetMapping( "/board")
    public String boardList(
            @RequestParam(defaultValue = "sprout") String boardType,
            @RequestParam(defaultValue = "") String search,
            @PageableDefault(size = 6, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            Model model
    ) {
        Page<BoardResponse.BoardListDTO> boardList = boardService.findAll(boardType, search, pageable);
        model.addAttribute("boards", boardList);
        model.addAttribute("boardType", boardType);

        return "/board/boardList";
    }


    @GetMapping("/saveForm")
    public String boardForm() {
        return "/board/saveForm";
    }

    @PostMapping("/board/save")
    public String save(
            @Valid BoardRequest.saveBoardDTO saveBoardDTO,
            @AuthenticationPrincipal UserAdaptor userAdaptor
    ) {
        boardService.savaBoard(saveBoardDTO, userAdaptor.getUser());
        return "redirect:/board";
    }

    @GetMapping("/board/{boardId}")
    public String detail(
            @PathVariable Long boardId,
            Model model
    ) {
        BoardResponse.BoardDetailDTO board = boardService.getDetailBoard(boardId);
        model.addAttribute("board", board);
        return "/board/boardDetail";
    }


    @GetMapping("/board/delete/{boardId}")
    public String delete(
            @PathVariable Long boardId,
            @AuthenticationPrincipal UserAdaptor userAdaptor
    ) {
        boardService.deleteBoard(boardId, userAdaptor.getUser());
        return "redirect:/board";
    }

    @GetMapping("/board/updateForm/{boardId}")
    public String updateForm(
            @PathVariable Long boardId,
            @AuthenticationPrincipal UserAdaptor userAdaptor,
            Model model
    ) {
        BoardResponse.BoardUpdateDTO board = boardService.updateBoardForm(boardId, userAdaptor.getUser());
        model.addAttribute("board", board);
        return "/board/updateForm";
    }

    @PostMapping("/board/update/{boardId}")
    public String update(
            @PathVariable Long boardId,
            BoardRequest.updateBoardDTO updateBoardDTO
    ) {
        boardService.updateBoard(updateBoardDTO, boardId);
        return "redirect:/board/{boardId}";
    }

}
