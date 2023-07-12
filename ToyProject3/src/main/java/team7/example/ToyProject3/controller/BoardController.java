package team7.example.ToyProject3.controller;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import team7.example.ToyProject3.domain.Member;
import team7.example.ToyProject3.dto.board.BoardRequest;
import team7.example.ToyProject3.dto.board.BoardResponse;
import team7.example.ToyProject3.repository.MemberRepository;
import team7.example.ToyProject3.service.BoardService;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;
    private final MemberRepository memberRepository;

    @PostMapping("/board/save")
    public String save(
            BoardRequest.saveBoardDTO saveBoardDTO)
//            Member member) {
    {
        // TODO 23.07.11 변경 필요
        String content = saveBoardDTO.getContent();
        Document document = Jsoup.parse(content);
        Elements elements = document.select("img");
        for (Element element : elements) {
            String fileData = element.attr("src");
            String fileName = element.attr("data-filename");
            String uniqueFileName = UUID.randomUUID() + fileName;
            saveBoardDTO.setThumbnail(fileData);
        }

        Member member = Member.builder()
                .email("sssar@naver.com")
                .nickName("jeep")
                .build();
        memberRepository.save(member);

        boardService.savaBoard(saveBoardDTO, member);

        return "redirect:/board";
    }

    @GetMapping({"/", "/board"})
    public String boardList(
            @RequestParam(defaultValue = "sprout") String boardType,
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "0") Integer page,
            Model model
    ) {
        Page<BoardResponse.BoardListDTO> boardList = boardService.findAll(boardType, search, page);
        model.addAttribute("boardList", boardList);
        model.addAttribute("totalPage", IntStream
                .range(0, boardList.getTotalPages())
                .boxed()
                .collect(Collectors.toList())
        );

        return "/board/boardList";
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
            @PathVariable Long boardId
    ) {
        boardService.deleteBoard(boardId);
        return "redirect:/board";
    }

    @GetMapping("/board/updateForm/{boardId}")
    public String updateForm(
            @PathVariable Long boardId,
            Model model
    ) {
        BoardResponse.BoardUpdateDTO board = boardService.findByIdBoard(boardId);
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
