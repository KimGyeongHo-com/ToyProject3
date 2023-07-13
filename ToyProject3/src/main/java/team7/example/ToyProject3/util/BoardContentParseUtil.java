package team7.example.ToyProject3.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import team7.example.ToyProject3.dto.board.BoardRequest;

import java.io.File;
import java.util.Base64;
import java.util.UUID;

public class BoardContentParseUtil {

    public static BoardRequest.saveBoardDTO parse(BoardRequest.saveBoardDTO saveBoardDTO) {
        String content = saveBoardDTO.getContent();
        System.out.println(content);
        Document document = Jsoup.parse(content);
        Elements elements = document.select("img");
        for (Element element : elements) {
            String fileData = element.attr("src");
            String fileName = element.attr("data-filename");
            String uniqueFileName = UUID.randomUUID() + fileName;
            saveBoardDTO.setThumbnail(fileData);
        }
        return saveBoardDTO;
    }
}
