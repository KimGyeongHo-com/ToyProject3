package team7.example.ToyProject3.domain;

import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.List;

@Getter
public class UserAdaptor extends User implements Serializable {

    private team7.example.ToyProject3.domain.User user;

    public UserAdaptor(team7.example.ToyProject3.domain.User user, List<GrantedAuthority> authorities){
        super(user.getEmail(),user.getPassword(), authorities);
        this.user = user;
    }

}
