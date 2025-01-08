package com.es3.es3backend.user.domain;

import com.es3.es3backend.constants.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tb_users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String name;

    private String password;

    private String mobile;

    private String address;

    private String profile_img;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Builder
    public User(String email, String name, String password, String mobile, String address, String profile_img) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.mobile = mobile;
        this.address = address;
        this.profile_img = profile_img;
        this.role = Role.USER;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.getRoles()));
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}

