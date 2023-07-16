package team7.example.ToyProject3.service;

import lombok.AllArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;
import team7.example.ToyProject3.domain.Role;
import team7.example.ToyProject3.domain.User;
import team7.example.ToyProject3.dto.AdminBoardDto;
import team7.example.ToyProject3.repository.AdminRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminService {

    private final SqlSessionFactory sqlSessionFactory;


    // 유저 리스트
    public List<User> getAllUsers() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            AdminRepository adminRepository = sqlSession.getMapper(AdminRepository.class);
            return adminRepository.getAllUsers();
        }
    }

    // 유저 등급 변경
    public void updateRoleById(Long id, Role role) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            AdminRepository adminRepository = sqlSession.getMapper(AdminRepository.class);
            adminRepository.updateRoleById(id, role);
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

    // 게시글 목록 보기
    public List<AdminBoardDto> getAllBoards() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            AdminRepository adminRepository = sqlSession.getMapper(AdminRepository.class);
            return adminRepository.getAllBoards();
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