package team7.example.ToyProject3.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import team7.example.ToyProject3.domain.UserAdaptor;
import team7.example.ToyProject3.dto.board.BoardRequest;
import team7.example.ToyProject3.dto.board.BoardResponse;
import team7.example.ToyProject3.service.BoardService;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;

    @GetMapping( "/board")
    public String boardList(
            @RequestParam(defaultValue = "sprout") String boardType,
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "0") Integer page,
            Model model
    ) {
        Page<BoardResponse.BoardListDTO> boardList = boardService.findAll(boardType, search, page);
        model.addAttribute("boards", boardList);
        System.out.println(boardList.getPageable().first());
        model.addAttribute("totalPage", IntStream
                .range(Math.min(0, Math.abs(page - 5)), Math.min(boardList.getTotalPages(), Math.min(0, Math.abs(page - 5)) + 10))
                .boxed()
                .collect(Collectors.toList())
        );
        return "/board/boardList";
    }

    @PostMapping("/board/save")
    public String save(
            BoardRequest.saveBoardDTO saveBoardDTO,
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

    @GetMapping("/boardForm")
    public String boardForm() {
        return "/board/boardForm";
    }
}
