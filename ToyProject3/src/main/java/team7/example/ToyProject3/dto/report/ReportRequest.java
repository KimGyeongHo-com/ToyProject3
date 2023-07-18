package team7.example.ToyProject3.dto.report;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

public class ReportRequest {

    @Getter
    @Setter
    public static class reportBoardDTO {
        @NotEmpty(message = "신고 사진은 필수입니다.")
        private MultipartFile file;
        @NotEmpty(message = "신고 설명은 공백이어서는 안됩니다.")
        private String description;
    }

}
