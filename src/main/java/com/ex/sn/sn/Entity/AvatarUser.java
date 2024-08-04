
package com.ex.sn.sn.Entity;


import java.security.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "avatar_user")
public class AvatarUser {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id ;
	
	@Column(name="url_image")
	private String urlImage ;

	@Column(name = "created_at")
    private Timestamp createdAt;

	@Column(name = "is_soft_deleted")
	private Boolean isSoftDeleted ;
	
	@ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
