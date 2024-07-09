package com.petproject.youtubeclone.models;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> list = new ArrayList<>();

        list.add(new SimpleGrantedAuthority(user.getRole().name()));

        return list;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }


    public String getUsername() {
        return user.getEmail();
    }
    public int getUserId() {
        return user.getUserId();
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
    public String getChannelName() {
        return user.getChannelName();
    }
    public String getPhotosImagePath() {
        return user.getPhotosImagePath();
    }
}
