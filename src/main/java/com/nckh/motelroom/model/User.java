package com.nckh.motelroom.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "address")
    private String address;

    @Column(name = "block")
    private Boolean block;

    @Column(name = "password")
    private String password;

    @Column(name = "phone", length = 20)
    private String phone;

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
    private Double balance;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
//    @JoinColumn(name = "role_id")
//    @JoinTable(
//            name = "user_roles",
//            joinColumns = @JoinColumn(
//                    name = "user_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(
//                    name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Mặc định mình sẽ để tất cả là ROLE_USER. Để demo cho đơn giản.
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        if (roles != null) {
            roles.forEach(role -> {
                list.add(new SimpleGrantedAuthority(role.getRoleName()));
                role.getPermissions().forEach(permission -> {
                    list.add(new SimpleGrantedAuthority(permission.getPermissionName()));
                });
            });
        }
        return list;
    }
}