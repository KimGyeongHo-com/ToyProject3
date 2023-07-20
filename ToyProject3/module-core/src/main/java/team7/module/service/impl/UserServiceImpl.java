package team7.module.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import team7.module.domain.user.Role;
import team7.module.domain.user.User;
import team7.module.domain.user.UserAdaptor;
import team7.module.dto.UserDto;
import team7.module.exception.ErrorCode;
import team7.module.exception.UserException;
import team7.module.repository.UserRepository;
import team7.module.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.*;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final HttpSession session;
    private final UserRepository userRepository;

    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }

        return validatorResult;
    }


    @Transactional
    public Long joinUser(UserDto userDto) {
        // 비밀번호 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        //이메일이 admin 계정이라면,
        if (userDto.getEmail().equals("admin@example.com"))
            userDto.setRole(Role.ADMIN);
        else
            userDto.setRole(Role.USER);

        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new UserException(ErrorCode.ERROR_DUPLICATED_EMAIL_EXCEPTION);
        }

        if (userRepository.findByNickname(userDto.getNickname()).isPresent()) {
            throw new UserException(ErrorCode.ERROR_DUPLICATED_NICKNAME_EXCEPTION);
        }


        return userRepository.save(userDto.toEntity(userDto)).getId();
    }

    @Transactional
    public void updateUser(User user, UserDto userDto){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


        if (userRepository.findByEmail(userDto.getEmail()).isPresent() ^ userDto.getEmail().equals(user.getEmail())) {
            throw new UserException(ErrorCode.ERROR_DUPLICATED_EMAIL_EXCEPTION);
        }

        if (userRepository.findByNickname(userDto.getNickname()).isPresent() ^ userDto.getNickname().equals(user.getNickname())) {
            throw new UserException(ErrorCode.ERROR_DUPLICATED_NICKNAME_EXCEPTION);
        }


        user.setEmail(userDto.getEmail());
        user.setNickname(userDto.getNickname());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userWrapper = userRepository.findByEmail(email);
        User user = userWrapper.get();

        List<GrantedAuthority> authorities = new ArrayList<>();
        //어드민 계정과 일치한다면 ADMIN을 부여하도록 합니다.
        if (("admin@example.com").equals(email)) {
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(Role.USER.getValue()));
        }

        return new UserAdaptor(user,authorities);
    }
}
