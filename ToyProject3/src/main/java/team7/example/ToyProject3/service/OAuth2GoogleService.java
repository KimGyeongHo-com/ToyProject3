package team7.example.ToyProject3.service;

import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;


public interface OAuth2GoogleService {

    public OAuth2User loadUser(OAuth2UserRequest userRequest);


}


