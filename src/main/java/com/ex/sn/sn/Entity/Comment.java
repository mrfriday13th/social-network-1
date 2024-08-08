package com.ex.sn.sn.Entity;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="comments")
public class Comment extends AbstractEntity {

//    @Id
//    @Column(name = "id")
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private int Id;
//
//    @Column(name = "created_at")
//    private Timestamp createdAt;
//
//    @Column(name = "updated_at")
//    private Timestamp updatedAt;
@OneToOne(mappedBy = "comment", cascade = CascadeType.REMOVE)
@JsonBackReference
private FileUpload fileUpload;
    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    private List<Reaction> reactions = new ArrayList<>();

    @Column(name = "content")
    private  String content;
    
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
   
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
