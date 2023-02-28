package Preproject28.server.answer.entity;

import Preproject28.server.answerVote.entity.AnswerVote;
import Preproject28.server.member.entity.Member;
import Preproject28.server.question.entity.Question;
import Preproject28.server.questionVote.entity.QuestionVote;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    @ElementCollection(targetClass=String.class)
    @Column
    private List<String> content = new ArrayList<>();
    @Column
    @ColumnDefault("0")
    private long voteCount;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime modifiedAt;
    @Enumerated(EnumType.STRING)
    private AdoptStatus adoptStatus = AdoptStatus.FALSE;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
    @ManyToOne
    @JoinColumn(name = "member_id")
    @JsonBackReference
    private Member member;

    //삭제 연관관계를 위해 양방향맵핑추가 ( 답변삭제시 -> 답변추천컬럼 같이삭제)
    @OneToMany(mappedBy = "answer", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<AnswerVote> answerVotes = new ArrayList<>();
    public enum AdoptStatus{
        TRUE,
        FALSE
    }
}
