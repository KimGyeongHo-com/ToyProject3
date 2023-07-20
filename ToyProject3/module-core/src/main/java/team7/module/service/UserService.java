package team7.module.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.Errors;
import team7.module.domain.user.User;
import team7.module.dto.UserDto;

import java.util.*;



public interface UserService extends UserDetailsService {

    public Map<String, String> validateHandling(Errors errors);

    public Long joinUser(UserDto userDto);

    public void updateUser(User user, UserDto userDto);

    public UserDetails loadUserByUsername(String email);

}
