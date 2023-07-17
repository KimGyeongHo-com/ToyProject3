package team7.example.ToyProject3.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import team7.example.ToyProject3.service.OAuth2GoogleService;
import team7.example.ToyProject3.service.UserService;


@Configuration
@EnableWebSecurity // Spring Security 설정할 클래스라고 정의
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final OAuth2GoogleService oAuth2GoogleService;

    // 비밀번호 암호화 : 스프링 시큐리티에서 제공하는 비밀번호 암호화 객체
    // Service에서 비밀번호를 암호화 할 수 있도록 Bean으로 등록.
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    /*
    configure() 메서드를 오버라이딩 하여 Security 설정을 잡아줌
    WebSecurity = FilterChainProxy를 생성하는 필터.
    */

    // 인증 무시 -> static 디렉토리의 파일들은 항상 인증 무시 (통과)
    // 해당 경로의 파일은 무조건 무시하도록 설정.
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
    }

//     Request 가 들어오는 경우 권한 설정 & 로그인 & 로그아웃 처리
//     HttpServletRequest에 따라 접근을 제한하는 부분.
//     anyMatchers() 메서드로 특정 경로를 지정하며, permitAll, hasRole 메서드로 역할에 따른 접근 설정
//     즉, 어떤 페이지는 admin만, 어떤 페이지는 user만, 그외 페이즈들은 모든 사람이 이용할 수 있게 설정
//     세션, 토큰, 쿠키의 차이를 알고 싶다면 유튜브 노마드 코더의 영상을 보시면 이해가 편합니다. (저도 봄)
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN") // admin 페이지
                .antMatchers("/info").hasRole("USER") // 마이페이지는 user 로그인 된 사람만 접근 가능
                .antMatchers("/**").permitAll() // 을 제외한 모든 페이지는 접근 가능함.
                .antMatchers("/callback").permitAll()
                .and()
                .formLogin()// 로그인 부분, form 기반으로 인증, 로그인 정보는 기본적으로 HttpSession을 이용
                // /login 경로로 접근하면 시큐리티에서 제공하는 loginFrom()사용 가능
                .loginPage("/login") // 로그인페이지 지정
                .defaultSuccessUrl("/login/result")// 로그인이 성공됐을 때 이동되는 페이지, 컨트롤러에서 매핑 돼야함.
                .permitAll() // 로그인 페이지는 모두 접근 가능.
                .usernameParameter("email")
                .passwordParameter("password")//-> name=username input으로 기본 인식, 바꿀 수 있음.

                .and()
                .logout()//로그아웃 부분
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))// 로그아웃의 기본(/logout)이 아닌 다른 url로 정의
                .logoutSuccessUrl("/logout/result")
                .invalidateHttpSession(true) //http 세션을 초기화
                .and()
                .oauth2Login()
                    .loginPage("/login")
                    .defaultSuccessUrl("/")
                    .userInfoEndpoint()
                        .userService(oAuth2GoogleService);
    }

    // 모든 인증을 처리하기 위한 AuthenticationManager
    // 시큐리티에서 모든 인증은 AuthenticationManager을 통해 이뤄지며, 생성하기 위해서는 AuthenticationManagerBuiler 사용
    // 로그인 처리(인증)을 위해서는 UserDetailService를 통해서 필요한 정보들을 가져옴 -> UserService
    // 서비스 클래스를 보면 이해가 되실 듯.
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder()); // 비밀번호 암호화
    }


}