package team7.module.domain.user;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
public class UserAdaptor extends org.springframework.security.core.userdetails.User implements Serializable {

    private User user;

    public UserAdaptor(team7.module.domain.user.User user, List<GrantedAuthority> authorities){
        super(user.getEmail(),user.getPassword(), authorities);
        this.user = user;
    }

    private List<GrantedAuthority> getAuthorities(Role role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.name()));
        return authorities;
    }

}

