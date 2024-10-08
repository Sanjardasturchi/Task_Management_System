package com.example.config.details;

import com.example.entity.ProfileEntity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Getter
public class CustomUserDetails implements UserDetails {
    private String id;
    private String phone;
    private String name;
    private String surname;
    private String password;
    private List<SimpleGrantedAuthority> roleList = new LinkedList<>();


    public CustomUserDetails(ProfileEntity entity) {
        this.id = entity.getId();
        this.name = entity.getFullName();
//        this.password = entity.getPassword();
        roleList.add(new SimpleGrantedAuthority(entity.getRole().name()));
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roleList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return phone;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}