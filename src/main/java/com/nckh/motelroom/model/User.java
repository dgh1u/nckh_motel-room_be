package com.nckh.motelroom.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address")
    private String address;

    @Column(name = "block")
    private Boolean block;

    @Column(name = "password")
    private String password;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "is_super_admin")
    private Boolean isSuperAdmin = false;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Lob
    @Column(name = "b64")
    private String b64;

    @Column(name = "file_type", length = 50)
    private String fileType;

    @Column(name = "balance")
    private Integer balance = 0;

    @Column(name = "otp")
    private String otp;

    @Column(name = "otp_generated_time")
    private Instant otpGeneratedTime;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (role != null) {
            role.getPermissions().forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getPermissionId()))); // lay den quyen
            authorities.add(new SimpleGrantedAuthority(role.getRoleId())); //lay den vai tro
        }
        return authorities;
    }
}
