package team7.example.ToyProject3.repository;

import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import team7.example.ToyProject3.domain.User;
import team7.example.ToyProject3.domain.UserRole;

import java.util.List;

@Mapper
@Repository
@AllArgsConstructor
public class AdminRepImpl implements AdminRepository{

    private final SqlSession sqlSession;

    @Override
    public List<User> getAllUsers() {
        return sqlSession.selectList("team7.example.ToyProject3.repository.AdminRepository.getAllUsers");
    }

    @Override
    public void updateUserRole(Long userId, UserRole newRole) {

    }

    @Override
    public void sendEmailToUser(Long userId, String subject, String message) {

    }

    @Override
    public void updateBlacklistUserRole(Long userId, UserRole newRole) {

    }

    @Override
    public Integer deleteBoardById(Integer id) {
        return null;
    }
}
