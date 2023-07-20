package team7.example.ToyProject3.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import team7.example.ToyProject3.domain.user.Role;
import team7.example.ToyProject3.domain.user.User;
import team7.example.ToyProject3.dto.UserDto;
import team7.example.ToyProject3.repository.UserRepository;
import team7.example.ToyProject3.service.OAuth2GoogleService;
import team7.example.ToyProject3.service.UserService;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OAuth2GoogleServiceImpl extends DefaultOAuth2UserService implements OAuth2GoogleService {

    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        System.out.println("oAuth2User = " + oAuth2User.getAttributes());

        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString().replaceAll("-", "");
        String firstFiveChars = uuidString.substring(0, 8);


        String email = oAuth2User.getAttribute("email");
        String firstname = oAuth2User.getAttribute("given_name");
        String lastname = oAuth2User.getAttribute("family_name");
        String name = lastname + firstname;
        Role role = Role.USER;

        //없는 회원이라면, 생성시켜줌.
        Optional<User> isUser = userRepository.findByEmail(email);
        if (isUser.isEmpty()) {
            UserDto userDto = new UserDto();
            userDto.setEmail(email);
            userDto.setName(name);
            userDto.setPassword(uuid.toString());
            userDto.setRole(role);
            userService.joinUser(userDto);
            System.out.println("생성됨");
        }

        return oAuth2User;
    }
}
