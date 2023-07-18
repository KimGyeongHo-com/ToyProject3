package team7.example.ToyProject3.domain;

import lombok.Getter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
public class UserAdaptor extends User implements Serializable {

    private team7.example.ToyProject3.domain.User user;

    public UserAdaptor(team7.example.ToyProject3.domain.User user, List<GrantedAuthority> authorities){
        super(user.getEmail(),user.getPassword(), authorities);
        this.user = user;
    }

    private List<GrantedAuthority> getAuthorities(Role role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.name()));
        return authorities;
    }

}

