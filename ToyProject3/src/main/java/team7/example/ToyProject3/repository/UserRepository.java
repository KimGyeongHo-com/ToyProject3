package team7.example.ToyProject3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import team7.example.ToyProject3.domain.user.User;
import team7.example.ToyProject3.domain.user.UserRole;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByNickname(String nickname);
    Optional<User> findByName(String name);
    @Query("SELECT u FROM User u WHERE u.userRole = :userRole AND u.boardCount >= :boardCount")
    List<User> findByUserRoleAndBoardCount(UserRole userRole, int boardCount);

}
