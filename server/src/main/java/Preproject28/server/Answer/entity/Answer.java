package Preproject28.server.Answer.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
//@MappedSuperclass
//@EntityListeners(AuditingEntityListener.class)
@Table(name = "Question")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    @Column
    private String content;

    @Column
    private Long voteCount;

    @Column
    @CreatedDate
    private LocalDateTime createdAt;

    @Column
    @CreatedDate
    private LocalDateTime modifiedAt;

    @Column
    private long memberId;

    @Column
    private String answers;

}
