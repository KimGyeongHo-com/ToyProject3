package team7.module.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lombok.*;

import javax.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;
import team7.module.domain.board.Board;
import team7.module.domain.user.User;

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

	@ManyToOne(fetch = FetchType.LAZY)
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
	private Timestamp createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Timestamp updatedAt;

	public void addChildReply(Reply reply) {
		if (reply.getParentReply() != this) {
			reply.setParentReply(this);
			this.childReplies.add(reply);
		}
	}

}