package team7.example.ToyProject3.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import team7.example.ToyProject3.dto.board.BoardRequest;

public class BoardContentParseUtil {

    public static BoardRequest.saveBoardDTO parse(BoardRequest.saveBoardDTO saveBoardDTO) {
        String content = saveBoardDTO.getContent();
        Document document = Jsoup.parse(content);
        Elements elements = document.select("img");
        if (elements.size() == 0) {
            saveBoardDTO.setThumbnail("/upload/default.png");
            return saveBoardDTO;
        }
        String fileData = elements.get(0).attr("src");
        saveBoardDTO.setThumbnail(fileData);
        return saveBoardDTO;
    }
}
