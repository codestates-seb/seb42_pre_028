package Preproject28.server.member.entity;

import Preproject28.server.answer.entity.Answer;
import Preproject28.server.answerVote.entity.AnswerVote;
import Preproject28.server.question.entity.Question;
import Preproject28.server.questionVote.entity.QuestionVote;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonManagedReference
    private Long memberId;
    private String displayName;
    @Column(unique = true)
    private String email;
    private String password;
    private String iconImageUrl;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    @JsonManagedReference
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    @JsonManagedReference
    private List<Answer> answers = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    @JsonManagedReference
    private List<QuestionVote> questionVotes = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    @JsonManagedReference
    private List<AnswerVote> answerVotes = new ArrayList<>();

}


