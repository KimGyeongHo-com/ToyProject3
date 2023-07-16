package team7.example.ToyProject3.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import team7.example.ToyProject3.domain.Board;
import team7.example.ToyProject3.domain.Role;
import team7.example.ToyProject3.domain.User;
import team7.example.ToyProject3.domain.UserRole;
import team7.example.ToyProject3.dto.AdminBoardDto;

import java.util.List;

//@Repository
@Mapper
public interface AdminRepository {

    List<User> getAllUsers();

    void updateRoleById(Long id, Role role);

    void sendEmailToUser(@Param("userId") Long userId, @Param("subject") String subject, @Param("message") String message);


}