package team7.example.ToyProject3.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class KakaoToken {
    private String accessToken;
    private String tokenType;
    private String refreshToken;
    private int expiresIn;
    private String scope;
    private int refreshTokenExpiresIn;
}