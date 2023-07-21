package team7.module.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import team7.module.domain.board.BoardStatus;
import team7.module.domain.user.Role;
import team7.module.domain.user.User;
import team7.module.domain.user.UserRole;
import team7.module.dto.AdminBoardDto;
import team7.module.dto.AdminReportDto;
import team7.module.dto.AllUsersInfoDto;

import java.util.List;


//@Repository
@Mapper
public interface AdminRepository {

    List<AllUsersInfoDto> getAllUsers();

    User findUserById(Long id);

    void updateRoleById(Long id, UserRole userrole);

    void sendEmailToUser(@Param("userId") Long userId, @Param("subject") String subject, @Param("message") String message);

    List<AdminBoardDto> getAllBoards();

    List<AllUsersInfoDto> getAllUsersOrderByBoard();

    List<AllUsersInfoDto> getAllUsersOrderByReply();

    Long deleteBoardById(Long id);

    Long deleteRepliesByBoardId(Long id);

    void updateBoardStatus(Long id, BoardStatus boardStatus);

    List<AdminReportDto> findReportsByBoardId();

    void updateBlackById(Long id, UserRole userrole);

    void updateReportBlackById(Long id, Role role);

    void deleteReportByBoardId(Long id);

    void updateBlacklistByBoardId(Long id, Role role, UserRole userRole);


}