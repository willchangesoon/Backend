package com.es3.es3backend.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tb_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String name;

    private String password;

    private String mobile;

    private String address;

    private String profile_img;

    @Builder
    public User(String email, String name, String password, String mobile, String address, String profile_img) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.mobile = mobile;
        this.address = address;
        this.profile_img = profile_img;
    }
}

