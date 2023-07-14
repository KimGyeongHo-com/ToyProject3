package team7.example.ToyProject3.service;

import lombok.AllArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;
import team7.example.ToyProject3.domain.User;
import team7.example.ToyProject3.domain.UserRole;
import team7.example.ToyProject3.repository.AdminRepImpl;
import team7.example.ToyProject3.repository.AdminRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminService {

    private final SqlSessionFactory sqlSessionFactory;


    // 유저 리스트
    public List<User> getAllUsers() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            AdminRepImpl adminRepimpl = sqlSession.getMapper(AdminRepImpl.class);
            return adminRepimpl.getAllUsers();
        }
    }


    // 유저 등급 변경
    public void updateUserRole(Long userId, UserRole newRole) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            AdminRepository adminRepository = sqlSession.getMapper(AdminRepository.class);
            adminRepository.updateUserRole(userId, newRole);
            sqlSession.commit();
        }
    }

    // 이메일 발송
    public void sendEmailToUser(Long userId, String subject, String message) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            AdminRepository adminRepository = sqlSession.getMapper(AdminRepository.class);
            adminRepository.sendEmailToUser(userId, subject, message);
            sqlSession.commit();
        }
    }

    // 블랙리스트 업데이트
    public void updateBlacklistUserRole(Long userId, UserRole newRole) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            AdminRepository adminRepository = sqlSession.getMapper(AdminRepository.class);
            adminRepository.updateBlacklistUserRole(userId, newRole);
            sqlSession.commit();
        }
    }

    // 게시글 삭제
    public void deleteBoardById(Integer id) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            AdminRepository adminRepository = sqlSession.getMapper(AdminRepository.class);
            adminRepository.deleteBoardById(id);
            sqlSession.commit();
        }
    }

}