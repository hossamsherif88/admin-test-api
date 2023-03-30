package com.santechture.api.dto.admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.santechture.api.entity.Admin;
import com.santechture.api.jwt.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Arrays;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdminDto implements UserDetails {

    private Integer adminId;

    private String username;

    private String password;

    public AdminDto(Admin admin){
        setAdminId(admin.getAdminId());
        setUsername(admin.getUsername());
        setPassword(admin.getPassword());
    }
    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private Role role;
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername(){
        return username;
    }

    @Override
    public String getPassword(){
        return password;
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
