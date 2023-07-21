package team7.module.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import team7.module.domain.board.Board;
import team7.module.domain.report.Report;
import team7.module.domain.user.User;
import team7.module.dto.report.ReportRequest;
import team7.module.exception.BoardException;
import team7.module.exception.ErrorCode;
import team7.module.exception.ReportException;
import team7.module.repository.BoardRepository;
import team7.module.repository.ReportRepository;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final BoardRepository boardRepository;

    @Value("${my.fileDir}")
    private String storePath;

    public void report(Long boardId, User user, ReportRequest.reportBoardDTO reportBoardDTO) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardException(ErrorCode.ERROR_BOARD_ENTITY_NOT_FOUND)
        );

        validateBoardIdAndUser(boardId, user);

        MultipartFile file = reportBoardDTO.getFile();

        Report report = Report.builder()
                .filePath(saveFileLocally(file))
                .description(reportBoardDTO.getDescription())
                .board(board)
                .user(user)
                .build();

        reportRepository.save(report);
    }

    private void validateBoardIdAndUser(Long boardId, User user) {
        boolean existReport = reportRepository.existsReportByBoardIdAndUserId(boardId, user.getId());
        if (existReport) {
            throw new ReportException(ErrorCode.ALREADY_REPORTED_EXCEPTION);
        }
    }

    private String saveFileLocally(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        validExtension(fileName);
        String storeFilePath = storePath + UUID.randomUUID() + fileName;

        try {
            file.transferTo(new File(storeFilePath));
        } catch (IOException e) {
            throw new ReportException(ErrorCode.FILE_UPLOAD_FAILED);
        }

        return storeFilePath;
    }

    private void validExtension(String fileName) {
        int pos = fileName.lastIndexOf(".");
        String fileExtension = fileName.substring(pos + 1);
        if (!fileExtension.equals("jpg") && !fileExtension.equals("png")) {
            throw new ReportException(ErrorCode.UNSUPPORTED_FILE_EXTENSION);
        }
    }

}
