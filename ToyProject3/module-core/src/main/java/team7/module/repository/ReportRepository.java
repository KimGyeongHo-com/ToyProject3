package team7.module.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import team7.module.domain.report.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
    boolean existsReportByBoardIdAndUserId(Long boardId, Long UserId);
}
