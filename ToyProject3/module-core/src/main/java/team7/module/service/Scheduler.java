package team7.module.service;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team7.module.domain.user.User;
import team7.module.domain.user.UserRole;
import team7.module.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class Scheduler {

	private final UserRepository userRepository;

	@Transactional
	@Scheduled(fixedDelay = 60000)
	public void changeUserRole() {

		List<User> newbiesWithTenOrMoreBoards = userRepository.findByUserRoleAndBoardCount(UserRole.NORMAL, 10);

		for (User user : newbiesWithTenOrMoreBoards) {
			user.setUserRole(UserRole.VIP);
		}

		userRepository.saveAll(newbiesWithTenOrMoreBoards);
	}
}
