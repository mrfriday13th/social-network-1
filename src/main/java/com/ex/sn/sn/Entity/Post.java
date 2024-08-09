package com.ex.sn.sn.Entity;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
@Table(name="posts")
public class Post extends AbstractEntity {
    public enum Status{
    	PUBLIC,
    	PRIVATE
    }
	


    @Column(name = "content")
    private String content;

    @Column(name = "iamge_url")
    private String imageUrl;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status = Status.PUBLIC;

//    @Column(name = "is_soft_deleted")
//    private Boolean isSoftDeleted;
//
//    @Column(name = "created_at")
//    private Timestamp createdAt;
//
//    @Column(name = "updated_at")
//    private Timestamp updatedAt;
//
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    
    @OneToMany(mappedBy= "post")
    private List<Comment> comments = new ArrayList<>() ;

    @OneToMany(mappedBy = "post")
    private List<FileUpload> fileUploads = new ArrayList<>() ;
    
    

}
