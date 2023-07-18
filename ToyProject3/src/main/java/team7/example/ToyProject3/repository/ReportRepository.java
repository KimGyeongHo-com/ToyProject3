package team7.example.ToyProject3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team7.example.ToyProject3.domain.report.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
    boolean existsReportByBoardIdAndUserId(Long boardId, Long UserId);
}
