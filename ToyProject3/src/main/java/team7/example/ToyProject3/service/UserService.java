package team7.example.ToyProject3.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.Errors;
import team7.example.ToyProject3.domain.user.User;
import team7.example.ToyProject3.dto.UserDto;
import java.util.*;



public interface UserService extends UserDetailsService {

    public Map<String, String> validateHandling(Errors errors);

    public Long joinUser(UserDto userDto);

    public void updateUser(User user, UserDto userDto);

    public UserDetails loadUserByUsername(String email);

}
