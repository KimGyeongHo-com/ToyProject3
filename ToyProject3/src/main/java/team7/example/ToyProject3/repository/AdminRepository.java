package team7.example.ToyProject3.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import team7.example.ToyProject3.domain.User;
import team7.example.ToyProject3.domain.UserRole;

import java.util.List;

//@Repository
@Mapper
public interface AdminRepository {

    List<User> getAllUsers();

    void updateUserRole(@Param("userId") Long userId, @Param("newRole") UserRole newRole);

    void sendEmailToUser(@Param("userId") Long userId, @Param("subject") String subject, @Param("message") String message);

    void updateBlacklistUserRole(@Param("userId") Long userId, @Param("newRole") UserRole newRole);

    Integer deleteBoardById(Integer id);
}