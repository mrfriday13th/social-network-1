package com.ex.sn.sn.Entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import java.security.Timestamp;
import java.util.List;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EnableAutoConfiguration
@Table(name = "users")
public class User extends AbstractEntity {
//
//    @Id
//    @Column(name = "id")
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
 
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @Column(name = "cunrrent_avatar_id")
    private String cunrrentAvatarId;
      
//    @Column(name = "created_at")
//    private Timestamp createdAt;
//
//    @Column(name = "updated_at")
//    private Timestamp updatedAt;
    
    ////////////relationship///////////
    
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private InforUser inforUser;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AvatarUser> avatarUsers = new ArrayList<>();
     
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
    joinColumns = @JoinColumn(name= "user_id"),
    inverseJoinColumns = @JoinColumn(name="role_id"))
    private List<Role> roles = new ArrayList<>();
    
    @OneToOne(mappedBy = "user")
    private Friendship friendships;
    
    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();


}
