package Preproject28.server.question.entity;

import Preproject28.server.answer.entity.Answer;
import Preproject28.server.member.entity.Member;
import Preproject28.server.questionVote.entity.QuestionVote;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
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
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonManagedReference
    private Long questionId;
    private String title;
    @ElementCollection(targetClass=String.class)
    @Column
    private List<String> problemBody = new ArrayList<>();
    @ElementCollection(targetClass=String.class)
    @Column
    private List<String> expectingBody = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    private long viewCount;
    private long voteCount;

    @ElementCollection(targetClass=String.class)
    @Column
    private List<String> tag = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "member_id")
    @JsonBackReference
    private Member member;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Answer> answers = new ArrayList<>();

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<QuestionVote> questionVotes = new ArrayList<>();

    private long adoptAnswerId;

}
