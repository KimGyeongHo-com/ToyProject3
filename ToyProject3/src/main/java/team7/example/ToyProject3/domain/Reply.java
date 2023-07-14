package team7.example.ToyProject3.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.*;
import javax.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;

@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reply")
public class Reply {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_id")
	private Board board;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private Reply parentReply;

	@OneToMany(mappedBy = "parentReply", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Reply> childReplies = new ArrayList<>();

	@Column(nullable = false, length = 50)
	private String content;

	@UpdateTimestamp
	@Column(name = "created_at", updatable = false)
	private Date createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Date updatedAt;

	public void addChildReply(Reply reply) {
		if (reply.getParentReply() != this) {
			reply.setParentReply(this);
			this.childReplies.add(reply);
		}
	}

}