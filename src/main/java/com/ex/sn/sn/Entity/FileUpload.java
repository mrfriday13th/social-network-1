package com.ex.sn.sn.Entity;
import java.security.Timestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
@Table(name = "file_uploads")
public class FileUpload extends AbstractEntity {
//	@Id
//	@Column(name = "id")
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	private Long id;

	@Column(name = "url_file")
	private String urlFile;

//	@Column(name = "created_at")
//	private Timestamp createdAt;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post;

	// @OneToOne
	// @JoinColumn(name = "comment_id")
	// @JsonManagedReference
	// private Comment comment;

}
