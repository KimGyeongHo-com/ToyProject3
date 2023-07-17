package team7.example.ToyProject3.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import team7.example.ToyProject3.domain.Role;
import team7.example.ToyProject3.dto.AdminBoardDto;
import team7.example.ToyProject3.dto.AllUsersInfoDto;

import java.util.List;

//@Repository
@Mapper
public interface AdminRepository {

    List<AllUsersInfoDto> getAllUsers();

    void updateRoleById(Long id, Role role);

    void sendEmailToUser(@Param("userId") Long userId, @Param("subject") String subject, @Param("message") String message);

    List<AdminBoardDto> getAllBoards();

    Integer deleteBoardById(Integer id);

}